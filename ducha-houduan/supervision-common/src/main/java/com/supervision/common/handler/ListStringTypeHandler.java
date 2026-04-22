package com.supervision.common.handler;

import com.supervision.common.utils.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * List转String字符串抽象类
 * @author gaoqiang
 */
@MappedTypes(List.class)
@MappedJdbcTypes(value = {JdbcType.BLOB, JdbcType.VARCHAR, JdbcType.CHAR})
public abstract class ListStringTypeHandler<T> extends BaseTypeHandler<List<T>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<T> ts, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,toStr(ts));
    }
    
    @Override
    public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        if (StringUtils.isNotBlank(result)){
            return toList(result);
        }
        return null;
    }
    
    @Override
    public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String result = resultSet.getString(i);
        if (StringUtils.isNotBlank(result)){
            return toList(result);
        }
        return null;
    }
    
    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String result = callableStatement.getString(i);
        if (StringUtils.isNotBlank(result)){
            return toList(result);
        }
        return null;
    }
    
    /**
     * List转String实现
     * @param ts List
     * @return String字符串
     */
    protected abstract String toStr(List<T> ts);
    
    /**
     * String转List实现
     * @param str 字符串
     * @return List
     */
    protected abstract List<T> toList(String str);
}
