package us.utils.common;

import java.util.List;

/**
 * @author stefanie zhao
 * @date 2014-5-29 上午09:29:52
 */
public class QueryResultBean<T> {

	private Integer curPage = 1;
	private Integer pageSize = 10;
	private Integer pageCount = 0;
	private Integer rowCount = 0;
	private List<T> dataList;
	private Integer totalCoin;

	/**
	 * @param curPage
	 * @param pageSize
	 * @param rowCount
	 * @param dataList
	 * @param totalCoin
	 */
	public QueryResultBean(Integer curPage, List<T> dataList, Integer totalCoin) {
		super();
		this.curPage = curPage;
		this.dataList = dataList;
		this.totalCoin = totalCoin;
	}

	/**
	 * @return the pageCount
	 */
	public Integer getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the curPage
	 */
	public Integer getCurPage() {
		if (curPage < 1)
			curPage = 1;
		return curPage;
	}

	/**
	 * @param curPage
	 *            the curPage to set
	 */
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the rowCount
	 */
	public Integer getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(Integer rowCount) {
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

	/**
	 * @return the dataList
	 */
	public List<T> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList
	 *            the dataList to set
	 */
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return the totalCoin
	 */
	public Integer getTotalCoin() {
		return totalCoin;
	}

	/**
	 * @param totalCoin
	 *            the totalCoin to set
	 */
	public void setTotalCoin(Integer totalCoin) {
		this.totalCoin = totalCoin;
	}

}
