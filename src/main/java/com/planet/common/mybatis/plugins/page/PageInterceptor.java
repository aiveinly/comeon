package com.planet.common.mybatis.plugins.page;

import com.planet.common.reflect.ReflectHelper;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.xml.bind.PropertyException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by yehao on 2016/1/14.
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {
    /**
     * 数据库类型，默认为mysql
     */
    private static String dialect = "mysql";
    /**
     * mapper.xml中需要拦截的id（正则匹配）
     */
    private static String pageSqlId = "";

    public Object intercept(Invocation ivk) throws Throwable {
        if (ivk.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
/** 拦截分页sql */
            if (mappedStatement.getId().matches(pageSqlId)) {
                BoundSql boundSql = delegate.getBoundSql();
/**
 * 分页sql<select>中parameterType属性对应的实体参数，及Mapper接口中执行分页方法的参数，
 * 改参数不得为空
 */
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    throw new NullPointerException("分页参数为空！");
                } else {
                    Connection connection = (Connection) ivk.getArgs()[0];
                    String sql = boundSql.getSql();
/** 此处sqlserver的统计方式稍有差别，子查询中不允许带有order by子句 */
                    if ("sqlServer".equals(dialect)) {
                        if (sql.indexOf("order") > 0) {
                            sql = sql.substring(0, sql.indexOf("order"));
                        }
                    }
/** 统计记录 */
                    String countSql = "select count(0) from (" + sql + ") tmp_count";
                    PreparedStatement countStmt = connection.prepareStatement(countSql);
                    BoundSql countBs = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
                    setParameters(countStmt, mappedStatement, countBs, parameterObject);
                    ResultSet rs = countStmt.executeQuery();
                    int count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    rs.close();
                    countStmt.close();
                    Pagination pagination = LoadPagination(parameterObject);
                    pagination.setCount(count);
                    String pageSql = generatePageSql(sql, pagination);
                    ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
                }
            }
        }
        return ivk.proceed();
    }

    private String generatePageSql(String sql, Pagination pagination) {
        if (pagination == null) {
            return sql;
        }
        if ("mysql".equals(dialect)) {
            StringBuilder pageSql = new StringBuilder(sql);
            pageSql.append(" limit " + pagination.getBegin() + "," + pagination.getPageSize());
            return pageSql.toString();
        } else if ("oracle".equals(dialect)) {
            sql = sql.trim();
            String forUpdateClause = null;
            boolean isForUpdate = false;
            final int forUpdateIndex = sql.toLowerCase().lastIndexOf("for update");
            if (forUpdateIndex > -1) {
                forUpdateClause = sql.substring(forUpdateIndex);
                sql = sql.substring(0, forUpdateIndex - 1);
                isForUpdate = true;
            }
            boolean hasOffset = pagination.getBegin() > 0;
            StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
            if (hasOffset) {
                pagingSelect.append("select * from (select row_.*,rownum rownum_from(");
            } else {
                pagingSelect.append("select * from (");
            }
            pagingSelect.append(sql);
            if (hasOffset) {
                pagingSelect.append(") row_where rownum <= " + pagination.getEnd() + ") where rownum_ > " + pagination.getBegin());
            }
            if (isForUpdate) {
                pagingSelect.append(" ");
                pagingSelect.append(forUpdateClause);
            }
            return pagingSelect.toString();
        } else if ("sqlServer".equals(dialect)) {
            sql = sql.trim();
            StringBuilder pageSql = new StringBuilder(sql);
            if (sql.startsWith("select")) {
                boolean hasOffset = pagination.getBegin() > 0;
                if (!hasOffset) {
                    String replaceStr = "select top " + pagination.getEnd() + " ";
                    pageSql.replace(0, "select".length(), replaceStr);
                } else {
                    String replaceStr = "select * from( select row_number() over (order by tempcolumn) temprownumber,* from ( select top " + pagination.getEnd() + " tempcolumn = 0, ";
                    pageSql.replace(0, "select".length(), replaceStr);
                    pageSql.append(")t )tt where temprownumber > " + pagination.getBegin());
                }
            }
            return pageSql.toString();
        }
        return sql;
    }

    private Pagination LoadPagination(Object parameterObject) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
/** 判断参数是不是分页实体 */
        if (parameterObject instanceof Pagination) {
            Pagination pagination = (Pagination) parameterObject;
            return pagination;
        } else if (parameterObject instanceof Map) {
            for (Object value : ((Map<?, ?>) parameterObject).values()) {
                if (value instanceof Pagination) {
                    return (Pagination) value;
                }
            }
            return null;
        } else if (parameterObject instanceof Set) {
            for (Object value : (Set<?>) parameterObject) {
                if (value instanceof Pagination) {
                    return (Pagination) value;
                }
            }
            return null;
        } else {
/** 参数为某个实体,该实体拥有Page属性 */
            Field pageField = ReflectHelper.getFieldByFieldName(parameterObject, "pagination");
            if (pageField != null) {
                Pagination pagination = (Pagination) ReflectHelper.getValueByFieldName(parameterObject, "pagination");
                if (pagination == null)
                    pagination = new Pagination();
                ReflectHelper.setValueByFieldName(parameterObject, "pagination", pagination);
                return pagination;
            } else {
                throw new NoSuchFieldException(parameterObject.getClass().getName() + "不存在 pagination 属性");
            }
        }
    }

    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer tokenizer = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(tokenizer.getName())) {
                        value = boundSql.hasAdditionalParameter(tokenizer.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(tokenizer.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter" + propertyName + " of statment " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties properties) {
        dialect = properties.getProperty("dialect");
        if (isEmpty(dialect)) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pageSqlId = properties.getProperty("pageSqlId");
        if (isEmpty(pageSqlId)) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getDialect() {
        return dialect;
    }

    public static void setDialect(String dialect) {
        PageInterceptor.dialect = dialect;
    }

    public static String getPageSqlId() {
        return pageSqlId;
    }

    public static void setPageSqlId(String pageSqlId) {
        PageInterceptor.pageSqlId = pageSqlId;
    }
}