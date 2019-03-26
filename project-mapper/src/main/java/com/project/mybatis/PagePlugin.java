package com.project.mybatis;

import java.util.List;

/**
 * 基于线程的分页插件
 * @author Yee
 *
 */
public class PagePlugin {
	
	private static final ThreadLocal<Pager> CONF = new ThreadLocal<Pager>();
	
	private static final ThreadLocal<PageResult> RESULT = new ThreadLocal<>();
	
	public static void setPage(Pager pager) {
		CONF.set(pager);
	}
	
	public static void setPage(Integer pageSize,Integer page) {
		CONF.set(new Pager(pageSize,page));
	}
	
	public static int getTotalRecord() {
		return RESULT.get() == null?0:RESULT.get().getTotalRecord();
	}
	
	public static int getTotalPage() {
		return RESULT.get() == null?0:RESULT.get().getTotalPage();
	}
	
	/**
	 * 包权限的获取pager
	 * @return
	 */
	static Pager getPager() {
		return CONF.get();
	}
	
	/**
	 * order by字段
	 * @param sort
	 */
	public static void setSort(String sort) {
		getPager().setSort(sort);
	}
	
	/**
	 * 排序方式 asc desc
	 * @param order
	 */
	public static void setOrder(String order) {
		getPager().setOrder(order);
	}
	
	/**
	 * 多条件排序,传入数组
	 * @param sort
	 */
	public static void setSorts(SqlSort... sorts) {
		getPager().setSorts(sorts);
	}
	
	/**
	 * 多条件排序,传入List
	 * @param sorts
	 */
	public static void setSorts(List<SqlSort> sorts) {
		getPager().setSorts(sorts);
	}
	
	
	static void setPageResult() {
		PageResult pageResult = new PageResult();
		pageResult.setTotalPage(getPager().getTotalPage());
		pageResult.setTotalRecord(getPager().getTotalRecord());
		RESULT.set(pageResult);
		CONF.remove();
	}
	public static void clear() {
		RESULT.remove();
	}
}
