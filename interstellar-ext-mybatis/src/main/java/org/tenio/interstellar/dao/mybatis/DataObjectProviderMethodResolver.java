package org.tenio.interstellar.dao.mybatis;

import cn.hutool.core.text.CharSequenceUtil;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class DataObjectProviderMethodResolver implements ProviderMethodResolver {
    private static final Logger log = LoggerFactory.getLogger(DataObjectProviderMethodResolver.class);
    private static final Map<Class<?>, String> TYPE_TABLE_NAME_MAPPING = new ConcurrentHashMap<>();

    // region helper functions

    /**
     *
     * TODO
     *
     * @param sql TODO
     * @param sort TODO
     * @return TODO
     */
    protected SQL withSort(SQL sql, DataObject sort) {
        if (sort != null && !sort.isEmpty()) {
            for (Map.Entry<String, Object> entry : sort.getMap().entrySet()) {
                sql.ORDER_BY(entry.getKey() + " " + entry.getValue());
            }
        }
        return sql;
    }

    /**
     *
     * TODO
     *
     * @param sql TODO
     * @param condition TODO
     * @return TODO
     */
    protected SQL withCondition(SQL sql, DataObject condition) {
        if (condition != null && !condition.isEmpty()) {
            for (Map.Entry<String, Object> entry : condition.getMap().entrySet()) {
                sql.WHERE(entry.getKey() + "=#{condition." + entry.getKey() + "}");
            }
        }
        return sql;
    }

    /**
     *
     * TODO
     *
     * @param sql TODO
     * @param setFields TODO
     * @return TODO
     */
    protected SQL withSetFields(SQL sql, DataObject setFields) {
        if (setFields != null && !setFields.isEmpty()) {
            for (Map.Entry<String, Object> entry : setFields.getMap().entrySet()) {
                sql.SET(entry.getKey() + "=#{setFields." + entry.getKey() + "}");
            }
        }
        return sql;
    }

    /**
     *
     * TODO
     *
     * @param sql TODO
     * @param ids TODO
     * @return TODO
     */
    protected SQL withIds(SQL sql, DataArray ids) {
        StringBuilder whereBuf = new StringBuilder("id in (");
        int collIndex = 0;
        for (int i = 0; i < ids.size(); i++) {
            whereBuf.append(collIndex == 0 ? ' ' : ',');
            whereBuf.append("#{ids.getValue(").append(i).append(")}");
            collIndex++;
        }
        whereBuf.append(")");
        sql.WHERE(whereBuf.toString());
        return sql;
    }

    /**
     *
     * TODO
     *
     * @param context TODO
     * @return TODO
     */
    public static Class<?> entityType(ProviderContext context) {
        Class<?> clazz = context.getMapperType();
        return (Class<?>) ((ParameterizedType) (clazz.getGenericInterfaces()[0])).getActualTypeArguments()[0];
    }

    /**
     *
     * TODO
     *
     * @param providerContext TODO
     * @return TODO
     */
    protected String getTableName(ProviderContext providerContext) {
        return TYPE_TABLE_NAME_MAPPING.computeIfAbsent(entityType(providerContext),
                clazz -> CharSequenceUtil.toUnderlineCase(clazz.getSimpleName()));
    }

    /**
     *
     * TODO
     *
     * @param providerContext TODO
     * @return TODO
     */
    protected String tableName(ProviderContext providerContext) {
        return getTableName(providerContext);
    }
    // endregion

    /**
     *
     * TODO
     *
     * @param entity TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String save(DataObject entity, ProviderContext providerContext) {
        Set<String> fields = entity.fieldNames();
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName(providerContext));
        for (String field : fields) {
            sql.VALUES(field, "#{entity." + field + "}");
        }
        return sql.toString();
    }

    /**
     *
     * TODO
     *
     * @param entities TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String saveAll(DataArray entities, ProviderContext providerContext) {
        if (entities == null || entities.size() == 0) {
            throw new NullPointerException();
        }
        List<DataObject> list = entities.getList();
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName(providerContext));
        String[] fields = list.get(0).fieldNames().toArray(new String[0]);
        sql.INTO_COLUMNS(fields);
        StringBuilder sb = new StringBuilder();
        int collIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            if (collIndex != 0) {
                sb.append(",");
            }
            sb.append('(');
            int valueIndex = 0;
            for (String field : fields) {
                sb.append(valueIndex == 0 ? ' ' : ',')
                        .append("#{entities.getValue(").append(i).append(").")
                        .append(field)
                        .append("}");
                valueIndex++;
            }
            sb.append(')');
            collIndex++;
        }
        sql.INTO_VALUES(sb.toString());
        return sql.toString();
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String one(DataObject condition, ProviderContext providerContext) {
        return oneWithFields(condition, new DataObject().put("*", ""), providerContext);
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param fields          TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String oneWithFields(DataObject condition, DataObject fields, ProviderContext providerContext) {
        String[] fieldsName = fields.fieldNames().toArray(new String[0]);
        SQL sql = new SQL();
        sql.SELECT(fieldsName);
        sql.FROM(tableName(providerContext));
        return withCondition(sql, condition).toString();
    }

    /**
     * TODO
     *
     * @param providerContext TODO
     * @return TODO
     */
    public String byId(ProviderContext providerContext) {
        return new SQL()
                .SELECT("*")
                .FROM(tableName(providerContext))
                .WHERE("id=#{id}")
                .toString();
    }

    /**
     * TODO
     *
     * @param id              TODO
     * @param fields          TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String byIdWithFields(Object id, DataObject fields, ProviderContext providerContext) {
        String[] fieldsName = fields.fieldNames().toArray(new String[0]);
        return new SQL()
                .SELECT(fieldsName)
                .FROM(tableName(providerContext))
                .WHERE("id=#{id}")
                .toString();
    }

    /**
     * TODO
     *
     * @param ids             TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String inIdsWithList(List<?> ids, ProviderContext providerContext) {
        return inIdsWithListSort(ids, null, providerContext);
    }

    /**
     * TODO
     *
     * @param ids             TODO
     * @param sort            TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String inIdsWithListSort(List<?> ids, DataObject sort, ProviderContext providerContext) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM(tableName(providerContext));
        StringBuilder whereBuf = new StringBuilder("id in (");
        int collIndex = 0;
        for (int i = 0; i < ids.size(); i++) {
            whereBuf.append(collIndex == 0 ? ' ' : ',');
            whereBuf.append("#{ids[").append(i).append("]}");
            collIndex++;
        }
        whereBuf.append(")");
        sql.WHERE(whereBuf.toString());
        return withSort(sql, sort).toString();
    }

    /**
     * TODO
     *
     * @param ids             TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String inIdsWithDataArray(DataArray ids, ProviderContext providerContext) {
        return inIdsWithDataArraySort(ids, null, providerContext);
    }

    /**
     * TODO
     *
     * @param ids             TODO
     * @param sort            TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String inIdsWithDataArraySort(DataArray ids, DataObject sort, ProviderContext providerContext) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM(tableName(providerContext));
        withIds(sql, ids);
        withSort(sql, sort);
        return sql.toString();
    }

    /**
     * TODO
     *
     * @param providerContext TODO
     * @return TODO
     */
    public String all(ProviderContext providerContext) {
        return allWithSort(null, providerContext);
    }

    /**
     * TODO
     *
     * @param sort            TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String allWithSort(DataObject sort, ProviderContext providerContext) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM(tableName(providerContext));
        return withSort(sql, sort).toString();
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String find(DataObject condition, ProviderContext providerContext) {
        return findWithSort(condition, null, providerContext);
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param sort            TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String findWithSort(DataObject condition, DataObject sort, ProviderContext providerContext) {
        return findWithPageSort(condition, -1, -1, sort, providerContext);
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param current         TODO
     * @param size            TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String findWithPage(DataObject condition, int current, int size, ProviderContext providerContext) {
        return findWithPageSort(condition, current, size, null, providerContext);
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param current         TODO
     * @param size            TODO
     * @param sort            TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String findWithPageSort(DataObject condition, int current, int size, DataObject sort, ProviderContext providerContext) {
        int offset = -1;
        if (current > 0 && size > 0) {
            offset = (current - 1) * size;
        }
        return findScroll(condition, offset, size, sort, providerContext);
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param startIndex      TODO
     * @param size            TODO
     * @param sort            TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String findScroll(DataObject condition, int startIndex, int size, DataObject sort, ProviderContext providerContext) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM(tableName(providerContext));
        withCondition(sql, condition);
        withSort(sql, sort);
        if (startIndex > 0 && size > 0) {
            sql.LIMIT(size).OFFSET(startIndex);
        }
        return sql.toString();
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String count(DataObject condition, ProviderContext providerContext) {
        SQL sql = new SQL()
                .SELECT("count(1)")
                .FROM(tableName(providerContext));
        return withCondition(sql, condition).toString();
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param setFields       TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String update(DataObject condition, DataObject setFields, ProviderContext providerContext) {
        SQL sql = new SQL()
                .UPDATE(tableName(providerContext));
        withSetFields(sql, setFields);
        return withCondition(sql, condition).toString();
    }

    /**
     * TODO
     *
     * @param entity          TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String updateByEntityId(DataObject entity, ProviderContext providerContext) {
        SQL sql = new SQL()
                .UPDATE(tableName(providerContext));
        for (Map.Entry<String, Object> entry : entity.getMap().entrySet()) {
            sql.SET(entry.getKey() + "=#{entity." + entry.getKey() + "}");
        }
        sql.WHERE("id=#{entity.id}");
        return sql.toString();
    }

    /**
     * TODO
     *
     * @param id              TODO
     * @param entity          TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String updateById(Object id, DataObject entity, ProviderContext providerContext) {
        SQL sql = new SQL()
                .UPDATE(tableName(providerContext));
        for (Map.Entry<String, Object> entry : entity.getMap().entrySet()) {
            sql.SET(entry.getKey() + "=#{entity." + entry.getKey() + "}");
        }
        sql.WHERE("id=#{id}");
        return sql.toString();
    }

    /**
     * TODO
     *
     * @param condition       TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String remove(DataObject condition, ProviderContext providerContext) {
        SQL sql = new SQL()
                .DELETE_FROM(tableName(providerContext));
        return withCondition(sql, condition).toString();
    }

    /**
     * TODO
     *
     * @param entity          TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String removeByEntityId(DataObject entity, ProviderContext providerContext) {
        return new SQL()
                .DELETE_FROM(tableName(providerContext))
                .WHERE("id=#{entity.id}")
                .toString();
    }

    /**
     * TODO
     *
     * @param id              TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String removeById(Object id, ProviderContext providerContext) {
        return new SQL()
                .DELETE_FROM(tableName(providerContext))
                .WHERE("id=#{id}")
                .toString();
    }

    /**
     * TODO
     *
     * @param ids             TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String removeByIds(DataArray ids, ProviderContext providerContext) {
        SQL sql = new SQL()
                .DELETE_FROM(tableName(providerContext));
        withIds(sql, ids);
        return sql.toString();
    }
}
