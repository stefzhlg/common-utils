package us.utils.common;

import java.io.Serializable;
import java.util.List;

public class PageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6320022641293215924L;

	private int rowCount = 0;

	private int pageSize = 5;

	private int pageCount = 0;

	private int curPage = 1;

	private String sortValue = "";

	private boolean hasAscing = true;

	@SuppressWarnings("rawtypes")
	private List dataList;

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public PageBean() {

	}

	public PageBean(int rowCount, int size) {
		pageSize = size;
		setRowCount(rowCount);
	}

	public int getFirstResult() {
		return getPageSize() * (getCurPage() - 1);
	}

	public void setRowCount(int rowCount) {

		this.rowCount = rowCount;
		pageCount = rowCount % pageSize == 0 ? rowCount / pageSize : (rowCount / pageSize + 1);

		checkCurrenPage();
	}

	public void checkCurrenPage() {
		if (curPage < 1) {
			this.curPage = 1;
		} else if (curPage > pageCount && pageCount == 0) {
			this.curPage = 1;
		} else if (curPage > pageCount && pageCount != 0) {
			this.curPage = pageCount;
		}
		if (pageCount == 0) {
			this.pageCount = 1;
			this.curPage = 1;
		}

	}

	public int getCurPage() {
		if (curPage < 1)
			curPage = 1;
		return curPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	@SuppressWarnings("rawtypes")
	public List getDataList() {
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public boolean isHasAscing() {
		return hasAscing;
	}

	public void setHasAscing(boolean hasAscing) {
		this.hasAscing = hasAscing;
	}

}