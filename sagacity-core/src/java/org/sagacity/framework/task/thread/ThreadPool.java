/**
 * 
 */
package org.sagacity.framework.task.thread;

import java.util.Vector;

/**
 * @Project Name:sagacity
 * @Desciption:
 * @author Administrator
 * @version $Id:ThreadPool.java, Revision v1.0, Oct 7, 2007 9:41:43 AM $
 */
public class ThreadPool {
	public static final int MAX_THREADS = 100;
    public static final int MAX_SPARE_THREADS = 50;
    public static final int MIN_SPARE_THREADS = 10;
    public static final int WORK_WAIT_TIMEOUT = 60 * 1000;
    
    protected Vector pool;
    protected MonitorRunnable monitor;
    protected int maxThreads;
    protected int minSpareThreads;
    protected int maxSpareThreads;
    protected int currentThreadCount;
    protected int currentThreadsBusy;
    protected boolean stopThePool;

    public ThreadPool() {
        maxThreads = MAX_THREADS;
        maxSpareThreads = MAX_SPARE_THREADS;
        minSpareThreads = MIN_SPARE_THREADS;
        currentThreadCount = 0;
        currentThreadsBusy = 0;
        stopThePool = false;
    }

    public synchronized void start() {
        adjustLimits();
        openThreads(minSpareThreads);
        monitor = new MonitorRunnable(this);
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMinSpareThreads(int minSpareThreads) {
        this.minSpareThreads = minSpareThreads;
    }

    public int getMinSpareThreads() {
        return minSpareThreads;
    }

    public void setMaxSpareThreads(int maxSpareThreads) {
        this.maxSpareThreads = maxSpareThreads;
    }

    public int getMaxSpareThreads() {
        return maxSpareThreads;
    }
    
    public void runIt(ThreadPoolRunnable r) {
        if (null == r) {
            throw new NullPointerException();
        }
        if (0 == currentThreadCount || stopThePool) {
            throw new IllegalStateException();
        }
        ControlRunnable c = null;
        synchronized (this) {
            if (currentThreadsBusy == currentThreadCount) {
                if (currentThreadCount < maxThreads) {
                    int toOpen = currentThreadCount + minSpareThreads;
                    openThreads(toOpen);
                } else {
                    while (currentThreadsBusy == currentThreadCount) {
                        try {
                            this.wait();
                        }catch (InterruptedException e) {
                        }
                        if (0 == currentThreadCount || stopThePool) {
                            throw new IllegalStateException();
                        }
                    }
                }
            }
            c = (ControlRunnable) pool.lastElement();
            pool.removeElement(c);
            currentThreadsBusy++;
        }
        c.runIt(r);
    }
   
    public synchronized void shutdown() {
        if (!stopThePool) {
            stopThePool = true;
            monitor.terminate();
            monitor = null;
            for (int i = 0; i < (currentThreadCount - currentThreadsBusy); i++) {
                try {
                    ((ControlRunnable) (pool.elementAt(i))).terminate();
                } catch (Throwable t) {
                }
            }
            currentThreadsBusy = currentThreadCount = 0;
            pool = null;
            notifyAll();
        }
    }
    
    protected synchronized void checkSpareControllers() {
        if (stopThePool) {
            return;
        }
        
        if ((currentThreadCount - currentThreadsBusy) > maxSpareThreads) {
            int toFree = currentThreadCount - currentThreadsBusy - maxSpareThreads;
            for (int i = 0; i < toFree; i++) {
                ControlRunnable c = (ControlRunnable) pool.firstElement();
                pool.removeElement(c);
                c.terminate();
                currentThreadCount--;
            }
        }
    }
    
    protected synchronized void returnController(ControlRunnable c) {
        if (0 == currentThreadCount || stopThePool) {
            c.terminate();
            return;
        }
        currentThreadsBusy--;
        
        pool.addElement(c);
        notify();
    }
    
    protected synchronized void notifyThreadEnd() {
        currentThreadsBusy--;
        currentThreadCount--;
        notify();
        openThreads(minSpareThreads);
    }
    
    protected void adjustLimits() {
        if (maxThreads <= 0) {
            maxThreads = MAX_THREADS;
        }
        if (maxSpareThreads >= maxThreads) {
            maxSpareThreads = maxThreads;
        }
        if (maxSpareThreads <= 0) {
            if (1 == maxThreads) {
                maxSpareThreads = 1;
            } else {
                maxSpareThreads = maxThreads / 2;
            }
        }
        if (minSpareThreads > maxSpareThreads) {
            minSpareThreads = maxSpareThreads;
        }
        if (minSpareThreads <= 0) {
            if (1 == maxSpareThreads) {
                minSpareThreads = 1;
            } else {
                minSpareThreads = maxSpareThreads / 2;
            }
        }
    }

    protected void openThreads(int toOpen) {
        if (toOpen > maxThreads) {
            toOpen = maxThreads;
        }
        if (0 == currentThreadCount) {
            pool = new Vector(toOpen);
        }
        for (int i = currentThreadCount; i < toOpen; i++) {
            pool.addElement(new ControlRunnable(this));
        }
        currentThreadCount = toOpen;
    }
    
    class MonitorRunnable implements Runnable {
        ThreadPool p;
        Thread t;
        boolean shouldTerminate;
        MonitorRunnable(ThreadPool p) {
            shouldTerminate = false;
            this.p = p;
            t = new Thread(this);
            t.start();
        }
        public void run() {
            while (true) {
                try {
                    synchronized (this) {
                        this.wait(WORK_WAIT_TIMEOUT);
                    }
                    if (shouldTerminate) {
                        break;
                    }
                    p.checkSpareControllers();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        
        public synchronized void terminate() {
            shouldTerminate = true;
            this.notify();
        }
    }
    
    class ControlRunnable implements Runnable {
        ThreadPool p;
        Thread t;
        ThreadPoolRunnable toRun;
        boolean shouldTerminate;
        boolean shouldRun;
        boolean noThData;
        Object thData[] = null;

        ControlRunnable(ThreadPool p) {
            toRun = null;
            shouldTerminate = false;
            shouldRun = false;
            this.p = p;
            t = new Thread(this);
            t.start();
            noThData = true;
            thData = null;
        }

        public void run() {
            while (true) {
                try {
                    synchronized (this) {
                        if (!shouldRun && !shouldTerminate) {
                            this.wait();
                        }
                    }
                    if (shouldTerminate) {
                        break;
                    }
                    try {
                        if (noThData) {
                            thData = toRun.getInitData();
                            noThData = false;
                        }
                        if (shouldRun) {
                            toRun.runIt(thData);
                        }
                    } catch (Throwable t) {
                        System.err.println("ControlRunnable Throwable: ");
                        t.printStackTrace();
                        shouldTerminate = true;
                        shouldRun = false;
                        p.notifyThreadEnd();
                    } finally {
                        if (shouldRun) { 
                            shouldRun = false;
                            p.returnController(this);
                        }
                    }
                    if (shouldTerminate) {
                        break;
                    }
                } catch (InterruptedException ie) {
                }
            }
        }

        public synchronized void runIt(ThreadPoolRunnable toRun) {
            if (toRun == null) {
                throw new NullPointerException("No Runnable");
            }
            this.toRun = toRun;
            shouldRun = true;
            this.notify();
        }

        public synchronized void terminate() {
            shouldTerminate = true;
            this.notify();
        }
    }

}
