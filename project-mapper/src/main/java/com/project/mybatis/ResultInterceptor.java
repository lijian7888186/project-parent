package com.project.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }))
public class ResultInterceptor implements Interceptor {
    @SuppressWarnings("unused")
    private final static Log log = LogFactory.getLog(ResultInterceptor.class);

    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
}
