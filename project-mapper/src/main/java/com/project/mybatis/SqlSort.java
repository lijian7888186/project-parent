package com.project.mybatis;

public class SqlSort {
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	public SqlSort() {
	}

	public SqlSort(String orderBy, String sort) {
		this.orderBy = orderBy;
		this.sort = sort;
	}
	public SqlSort(String orderBy, Esort sort) {
		this.orderBy = orderBy;
		this.sort = sort.toString();
	}

	private String orderBy;
	private String sort = ASC;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(Esort sort) {
		this.sort = sort.toString();
	}
}
