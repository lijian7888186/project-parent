package com.project.mybatis;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.sql.Connection;


public class PluginDialectOracle implements PluginDialect {
    @Override
    public String prepareStatement(MappedStatement mappedStatement, BoundSql boundSql, Connection connection, Object page) {
        return null;
    }
}
