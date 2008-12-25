package org.sagacity.framework.web.model;

import java.util.List;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>分页数据模型</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:PaginationModel.java,Revision:v1.0,Date:Oct 19, 2007 10:18:30 AM $
 */
public class PaginationModel {
	/**
	 * 默认每页记录长度
	 */
	public final static int PAGE_SIZE = 10;

	private int pageSize = PAGE_SIZE;

	/**
	 * 当前页数
	 */
	private int pageNo = 1;

	/**
	 * 分页查询出的数据明细
	 */
	private List items;
	
	/**
	 * 总记录数
	 */
	private int recordCount;
	
	/**
	 * 是否分页
	 */
	private String isPagination;
	
	/**
	 * 全部显示功能最大页限制
	 */
	private int showAllLimitPage=20;

	private int[] indexes = new int[0];
	
	/**
	 * 起始记录
	 */
	private int startIndex = 0;
	
	/**
	 * 分页查询条件1:两�?�搭配为了提供区段查询条�?
	 */
	private Object condition1;
	
	/**
	 * 分页查询条件2
	 */
	private Object condition2;
	
	public PaginationModel() {

	}

	public PaginationModel(List items, int recordCount) {
		setPageSize(PAGE_SIZE);
		setRecordCount(recordCount);
		setItems(items);
		setStartIndex(0);
	}

	public PaginationModel(List items, int recordCount, int startIndex) {
		setPageSize(PAGE_SIZE);
		setRecordCount(recordCount);
		setItems(items);
		setStartIndex(startIndex);
	}

	public PaginationModel(List items, int recordCount, int pageSize,
			int startIndex) {
		setPageSize(pageSize);
		setRecordCount(recordCount);
		setItems(items);
		setStartIndex(startIndex);
	}

	public List getItems() {
		return this.items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		if (recordCount > 0) {
			this.recordCount = recordCount;
			int count = recordCount / pageSize;
			if (recordCount % pageSize > 0)
				count++;
			indexes = new int[count];
			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.recordCount = 0;
		}
	}

	/**
	 * Access method for the pageNo property.
	 * @return   the current value of the pageNo property
	 */
	public int getPageNo() {
		if (this.pageNo == 0)
			return 1;
		else
			return this.pageNo;
	}

	/**
	 * Sets the value of the pageNo property.
	 * @param aPageNo the new value of the pageNo property
	 * where pageNo==-1 then show all page
	 */
	public void setPageNo(int pageNo) {
		//this.pageNo = pageNo;
		this.pageNo = pageNo;
	}

	public int[] getIndexes() {
		return indexes;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		if (recordCount <= 0)
			this.startIndex = 0;
		else if (startIndex >= recordCount)
			this.startIndex = indexes[indexes.length - 1];
		else if (startIndex < 0)
			this.startIndex = 0;
		else {
			this.startIndex = indexes[startIndex / pageSize];
		}
	}

	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= recordCount)
			return getStartIndex();
		else
			return nextIndex;
	}

	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;
		if (previousIndex < 0)
			return 0;
		else
			return previousIndex;
	}

	/**
	 * 返回上一页号
	 * @return 上一页号
	 */
	public int getPriorPage() {
		if (this.pageNo > 1) {
			return this.pageNo - 1;
		} else {
			return this.pageNo;
		}
	}

	/**
	 * 返回�?后一�?
	 * @return �?后一�?
	 */
	public int getLastPage() {
		return (recordCount - 1) / getPageSize() + 1;
	}

	/**
	 * 返回第一�?
	 * @return 第一�?
	 */
	public int getFirstPage() {
		return 1;
	}

	/**
	 * 返回下一页号
	 * @return 下一页号
	 */
	public int getNextPage() {
		if (this.pageNo + 1 >= getLastPage()) {
			return getLastPage();
		} else {
			return this.pageNo + 1;
		}
	}

	/**
	 * 总页�?
	 * @return totalPage
	 */
	public int getTotalPage() {
		int ret = 0;
		if (this.pageSize <= 0) {
			return ret;
		}
		ret = this.recordCount / this.pageSize;
		if (this.recordCount > ret * this.getPageSize()) {
			ret++;
		}
		return ret;
	}

	/**
	 * @return 返回 condition1�?
	 */
	public Object getCondition1() {
		return condition1;
	}

	/**
	 * @param condition1 要设置的 condition1�?
	 */
	public void setCondition1(Object condition1) {
		this.condition1 = condition1;
	}

	/**
	 * @return 返回 condition2�?
	 */
	public Object getCondition2() {
		return condition2;
	}

	/**
	 * @param condition2 要设置的 condition2�?
	 */
	public void setCondition2(Object condition2) {
		this.condition2 = condition2;
	}

	/**
	 * @return the isPagination
	 */
	public String getIsPagination() {
		return isPagination;
	}

	/**
	 * @param isPagination the isPagination to set
	 */
	public void setIsPagination(String isPagination) {
		this.isPagination = isPagination;
	}

	/**
	 * @return the showAllLimitPage
	 */
	public int getShowAllLimitPage() {
		return showAllLimitPage;
	}

	/**
	 * @param showAllLimitPage the showAllLimitPage to set
	 */
	public void setShowAllLimitPage(int showAllLimitPage) {
		this.showAllLimitPage = showAllLimitPage;
	}
}
