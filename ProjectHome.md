sagacity项目是由zhongxuchen通过多年的项目实践总结，为了能给程序员每天从重复的coding时间中节省出一点用来锻炼自己的身体或放松一下心情而倡导的一个开源项目，同时也是为了实现zhongxuchen多年来的一个理念和境界：lazyworker！

sagacity是针对java平台下的企业信息化开发建设综合技术解决方案，整个平台将包括三个重要组成部分：

1、sagacity-core：sagacity核心代码库：
提供基于主流java技术的整合底层代码，如：struts2，spring2.5，hibernate3.2，spring security2.0，以及大量的utils工具类：StringUtil/ImageUtil/DateUtil/URLClassLoaderUtil/BeanShellUtil/BeanUtil/NumberUtil/ArrayUtil/FileUtil/ExcelUtil/DbUtil/ExpressionUtil/SqlUtil/MailUtil/SocketUtil/
以及灵活的页面组件：tree，xtable(提供分页（export excel/pdf/xml）、分组、求和以及自定义可扩展宏)，menu，select、treetable、gt-grid、my97datePicker、flashUpload、图片浏览等(组件资源跟类库统一打包采用Listener机制自动解包，只要拥有jar文件组件就可以用，无需关心组件的image、css、js等)，
最为特色的当属数据库动态查询(命名为陈氏查询)：


&lt;sql-query name="oa\_searchCarParams"&gt;


<![CDATA[
> select t.**> ,t1.NAME
> from OA\_CAR\_PARAMS t,HR\_STAFF\_INFO t1
> where 1=1
> > and t1.STAFF\_NO=t.CREATE\_BY

> and t.IS\_ACTIVE=?
  1. and t.EFFECTIVE\_START\_DATE>=? and t.EFFECTIVE\_START\_DATE<= ? ]
    1. and t.EFFECTIVE\_END\_DATE>=? and t.EFFECTIVE\_END\_DATE<= ? ]
> ]]>


&lt;/sql-query&gt;


sql的简洁明了，调用过程更是简单，开发人员不需要再将复杂的sql拼在代码中，给维护带来不便。**

2.sagacity-tools:工具库，目前包含：
quickide，快速生成项目原型，目前提供基于sagacity技术框架的实现，即：struts2+sitemesh+spring+hibernate+spring security2.0的原型项目，自动根据配置产生对应目录和ant、maven配置文件和权限数据库表信息(权限提供多种模式，通过xml指定)生成即可运行。

quickvo：采用freemarker、databasemeta以及URLClassLoader技术实现的快速生成vo、vo

&lt;-&gt;

po mapping工具；
quick-cstruct：快速通过数据库生成C语言对应数据库表的struct代码工具，给C开发人员带来了方便（题外话：C开发人员一般比较闭塞，数据库设计不用powerdesigner，表的字段顺序还得跟代码中的一致，因为他们习惯get(index)而不是get("COLUMN\_NAME")，数据库改变都乐此不疲的手工写struct，实在是看不下去了，索性帮他们写了一个小工具）。
excel-tools：将excel数据导入数据库的可配置化工具，用于项目初始化阶段，工具几乎可以将任何复杂的数据形式整理成你目标数据库表结构对应的模式导进去（并提供了扩展接口方便开发不同的转换器和任务实现，此工具在宁波农行得到很好验证，目前正计划进行改造，采用一种ESQL模式（Excel-Sql,正在构思还不成熟）如：
<!--关键词-->
<convert name="keyWord"
> class="com.abchina.tools.excel.convert.impl.DBDictConvert">
> 

&lt;param&gt;

<![CDATA[select t.DES,t.DDCOD
> > from DD t where t.SSYCOD='03'
> > and t.DDTYP='KEYWRD']]>

&lt;/param&gt;




Unknown end tag for &lt;/convert&gt;




&lt;task id="" table="" preclear="true" active="true"&gt;




&lt;do&gt;


insert table(col**)
from 'xxx.xls' fields(@keyWord(col1),col2..colx)
start 1
end last
batch 100
翻译：从xx excel文件中的第一行道最后一行的列col..colx插入到表字段(col..colx)中，每100条做一次提交，其中对col1通过关键词转换器转换成对应数据字典中的值。


&lt;/do&gt;**

&lt;do&gt;




&lt;/do&gt;




&lt;/task&gt;


monitor:监控工具，目前还在开发中，初步提供了对cpu、内存、磁盘空间、进程监控实现。
thread：多线程任务调用工具，本实现源于对宁波农行绩效系统日终数据导入调度多任务实现，一般用于数据同步工作。

2、sagacity演示项目：

> sagacity演示项目主要集中体现基于主流技术以及sagacity最新成果的展示（页面以及后台技术的契合），最大的目标是希望通过演示项目告诉你如何更好的构建一个企业管理软件的架构体系以及sagacity架构所提供的各种技术和组件的使用。

对于技术我有自己的理解：时髦慢一拍，实用易用要领先一拍，在项目中技术和项目各类因素的平衡是我最关注的，在闲暇时整理项目中遇到的问题，找到更好的解决途径，技术强调的是契合，关注的是开发人员的因此所带来效率的改变以及后期维护人员的维护成本！
我坚信技术只是实现业务的手段，目标是解决客户遇到的业务问题为企业提供最佳的解决方案为他们带来效率的提升、发现业务的瓶颈从而解决之，以此实现软件开发人员真正的价值：为企业创造远超出软件投入的利润！

人生的感言：
浩瀚星空、人如尘沙；
时空长河、百年转瞬间；
圣雄功绩万众仰止，放之宇宙不及尘烟；
生命永恒非地球一脉，宇宙多重，细胞皆是！
人类需重重跨越，许久乎？生生不息！
若明日行星撞地球，名利权色皆为空！
呜呼，及时行乐乎？奋起努力乎？
我想皆可，观文明之发展，平庸如草芥者大凡是也，何乎多你我一人！
观中华大地：
贪腐者快乐一生，平庸者居显位，无为者苟且偷生，智者多累，真乃人生常态！
志同道合者有乎?不求改变社会，只求做好我自己，做大家喜欢的软件！







