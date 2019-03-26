package com.project.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BasePluginDialect implements PluginDialect {
    private static final Log log = LogFactory.getLog(BasePluginDialect.class);

    /**
     * 产生查询总数SQL
     * @param page
     * @param sql
     * @return
     */
    protected abstract String generateCountSql(Object obj, String sql);

    /**
     * 产生查询List SQL
     * @param page
     * @param sql
     * @return
     */
    protected abstract String generateListSql(Object obj, String sql);

    /**
     * 产生SUMList SQL
     * @param page
     * @param sql
     * @return
     */
    protected abstract String generateSumSql(Object obj, String sql);

    /**
     * 产生AVGList SQL
     * @param page
     * @param sql
     * @return
     */
    protected abstract String generateAvgSql(Object obj, String sql);

    @Override
    public String prepareStatement(MappedStatement mappedStatement, BoundSql boundSql, Connection connection, Object obj) {
        String sql = boundSql.getSql();
        Pager page = null;
        if (obj instanceof Pager) {
			page = (Pager) obj;
		}else {
			page = PagePlugin.getPager();
		}
        if (page.getPageSize() != null) {
            page.setTotalRecord(queryTotalRecord(obj, mappedStatement, connection));
        }
        if (page.getSumFields() != null || page.getAvgFields() != null) {
            page.setFooter(queryFooter(page, mappedStatement, connection));
        }
        sql = generateListSql(obj, sql);
        return sql;
    }
    
    /**
     * 查询footer
     * @param page
     * @param mappedStatement
     * @param connection
     * @return
     */
    protected List<Map<String, Object>> queryFooter(Object obj, MappedStatement mappedStatement, Connection connection) {
        List<Map<String, Object>> footer = new ArrayList<Map<String, Object>>(2);
        List<Map<String, Object>> list = null;
        Map<String, Object> result = null;
        Pager page = null;
        if (obj instanceof Pager) {
			page = (Pager) obj;
		}
        if (page == null) {
			page = PagePlugin.getPager();
		}
        String footerNameKey = page.getFooterNameKey();
        BoundSql boundSql = mappedStatement.getBoundSql(obj);
        String sql = boundSql.getSql();
        if (footerNameKey == null) footerNameKey = "footerName";
        try {
            if (page.getAvgFields() != null) {
                list = queryForList(page, generateAvgSql(page, sql), mappedStatement, connection);
                if (list == null || list.isEmpty()) {
                    throw new RuntimeException("Empty Set");
                }
                result = list.get(0);
                result.put("isFooter", 1);
                result.put(footerNameKey, "平均");
                footer.add(result);
            }
            if (page.getSumFields() != null) {
                list = queryForList(page, generateSumSql(page, sql), mappedStatement, connection);
                if (list == null || list.isEmpty()) {
                    throw new RuntimeException("Empty Set");
                }
                result = list.get(0);
                result.put("isFooter", 1);
                result.put(footerNameKey, "总计");
                footer.add(result);
            }
        } catch (Exception e) {
            log.error("查询" + mappedStatement.getId() + "的footer失败！", e);
        }
        return footer;
    }

    /**
     * 查询记录总数
     * @param page
     * @param mappedStatement
     * @param connection
     * @return
     */
    protected int queryTotalRecord(Object obj, MappedStatement mappedStatement, Connection connection) {
        int totalRecord = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Pager page = null;
        if (obj instanceof Pager) {
			page = (Pager) obj;
		}
        BoundSql boundSql = mappedStatement.getBoundSql(obj);
        String sql = boundSql.getSql();
        sql = generateCountSql(obj, sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, parameterMappings, page);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, obj, countBoundSql);
        try {
            pstmt = connection.prepareStatement(sql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                totalRecord = rs.getInt(1);
            } else {
                throw new RuntimeException("Empty Set");
            }
        } catch (Exception e) {
            log.error("查询" + mappedStatement.getId() + "的记录总数失败！", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (Exception e2) {
                log.error(e2);
            }
        }
        return totalRecord;
    }

    private List<Map<String, Object>> queryForList(Object obj, String sql, MappedStatement mappedStatement, Connection connection) {
    	Pager page = null;
    	if (obj instanceof Pager) {
			page = (Pager) obj;
		}
    	List<Map<String, Object>> list = null;
        Map<String, Object> result = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        int colCount = 0;
        int i = 0;
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, parameterMappings, page);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, obj, countBoundSql);
        try {
            pstmt = connection.prepareStatement(sql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                list = new ArrayList<Map<String, Object>>();
                rsmd = rs.getMetaData();
                colCount = rsmd.getColumnCount();
                do {
                    result = new HashMap<String, Object>();
                    for (i = 1; i <= colCount; i++) {
                        result.put(rsmd.getColumnName(i), rs.getObject(i));
                    }
                    list.add(result);
                } while (rs.next());
            } else {
                throw new RuntimeException("Empty Set");
            }
        } catch (Exception e) {
            log.error("查询" + mappedStatement.getId() + "的附加List失败！", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (Exception e2) {
                log.error(e2);
            }
        }
        return list;
    }
}
