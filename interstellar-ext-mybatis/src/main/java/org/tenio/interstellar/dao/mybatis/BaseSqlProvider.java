package org.tenio.interstellar.dao.mybatis;

import org.apache.ibatis.jdbc.SQL;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseSqlProvider {

    protected SQL withSort(SQL sql, DataObject sort) {
        if (sort != null && !sort.isEmpty()) {
            for (Map.Entry<String, Object> entry : sort.getMap().entrySet()) {
                sql.ORDER_BY(entry.getKey() + " " + entry.getValue());
            }
        }
        return sql;
    }

    protected SQL withCondition(SQL sql, DataObject condition) {
        if (condition != null && !condition.isEmpty()) {
            for (Map.Entry<String, Object> entry : condition.getMap().entrySet()) {
                sql.WHERE(entry.getKey() + "=#{condition." + entry.getKey() + "}");
            }
        }
        return sql;
    }

    protected SQL withSetFields(SQL sql, DataObject setFields) {
        if (setFields != null && !setFields.isEmpty()) {
            for (Map.Entry<String, Object> entry : setFields.getMap().entrySet()) {
                sql.SET(entry.getKey() + "=#{setFields." + entry.getKey() + "}");
            }
        }
        return sql;
    }

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

    public String save(String tableName, DataObject entity) {
        Set<String> fields = entity.fieldNames();
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName);
        for (String field : fields) {
            sql.VALUES(field, "#{entity." + field + "}");
        }
        return sql.toString();
    }

    public String saveAll(String tableName, DataArray entities) {
        if (entities == null || entities.size() == 0) {
            throw new NullPointerException();
        }
        List<DataObject> list = entities.getList();
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName);
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

    public String one(String tableName, DataObject condition) {
        return oneWithFields(tableName, condition, new DataObject().put("*", ""));
    }

    public String oneWithFields(String tableName, DataObject condition, DataObject fields) {
        String[] fieldsName = fields.fieldNames().toArray(new String[0]);
        SQL sql = new SQL();
        sql.SELECT(fieldsName);
        sql.FROM(tableName);
        return withCondition(sql, condition).toString();
    }

    public String byId(String tableName, Object id) {
        return new SQL()
                .SELECT("*")
                .FROM(tableName)
                .WHERE("id=#{id}")
                .toString();
    }

    public String byIdWithFields(String tableName, Object id, DataObject fields) {
        String[] fieldsName = fields.fieldNames().toArray(new String[0]);
        return new SQL()
                .SELECT(fieldsName)
                .FROM(tableName)
                .WHERE("id=#{id}")
                .toString();
    }

    public String inIdsWithList(String tableName, List<?> ids) {
        return inIdsWithListSort(tableName, ids, null);
    }

    public String inIdsWithListSort(String tableName, List<?> ids, DataObject sort) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM(tableName);
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

    public String inIdsWithDataArray(String tableName, DataArray ids) {
        return inIdsWithDataArraySort(tableName, ids, null);
    }

    public String inIdsWithDataArraySort(String tableName, DataArray ids, DataObject sort) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM(tableName);
        withIds(sql, ids);
        withSort(sql, sort);
        return sql.toString();
    }

    public String all(String tableName) {
        return allWithSort(tableName, null);
    }

    public String allWithSort(String tableName, DataObject sort) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM(tableName);
        return withSort(sql, sort).toString();
    }

    public String find(String tableName, DataObject condition) {
        return findWithSort(tableName, condition, null);
    }

    public String findWithSort(String tableName, DataObject condition, DataObject sort) {
        return findWithPageSort(tableName, condition, -1, -1, sort);
    }

    public String findWithPage(String tableName, DataObject condition, int current, int size) {
        return findWithPageSort(tableName, condition, current, size, null);
    }

    public String findWithPageSort(String tableName, DataObject condition, int current, int size, DataObject sort) {
        int offset = -1;
        if (current > 0 && size > 0) {
            offset = (current - 1) * size;
        }
        return findScroll(tableName, condition, offset, size, sort);
    }

    public String findScroll(String tableName, DataObject condition, int startIndex, int size, DataObject sort) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM(tableName);
        withCondition(sql, condition);
        withSort(sql, sort);
        if (startIndex > 0 && size > 0) {
            sql.LIMIT(size).OFFSET(startIndex);
        }
        return sql.toString();
    }

    public String count(String tableName, DataObject condition) {
        SQL sql = new SQL()
                .SELECT("count(1)")
                .FROM(tableName);
        return withCondition(sql, condition).toString();
    }

    public String update(String tableName, DataObject condition, DataObject setFields) {
        SQL sql = new SQL()
                .UPDATE(tableName);
        withSetFields(sql, setFields);
        return withCondition(sql, condition).toString();
    }

    public String updateByEntityId(String tableName, DataObject entity) {
        SQL sql = new SQL()
                .UPDATE(tableName);
        for (Map.Entry<String, Object> entry : entity.getMap().entrySet()) {
            sql.SET(entry.getKey() + "=#{entity." + entry.getKey() + "}");
        }
        sql.WHERE("id=#{entity.id}");
        return sql.toString();
    }

    public String updateById(String tableName, Object id, DataObject entity) {
        SQL sql = new SQL()
                .UPDATE(tableName);
        for (Map.Entry<String, Object> entry : entity.getMap().entrySet()) {
            sql.SET(entry.getKey() + "=#{entity." + entry.getKey() + "}");
        }
        sql.WHERE("id=#{id}");
        return sql.toString();
    }


    public String remove(String tableName, DataObject condition) {
        SQL sql = new SQL()
                .DELETE_FROM(tableName);
        return withCondition(sql, condition).toString();
    }

    public String removeByEntityId(String tableName, DataObject entity) {
        return new SQL()
                .DELETE_FROM(tableName)
                .WHERE("id=#{entity.id}")
                .toString();
    }

    public String removeById(String tableName, Object id) {
        return new SQL()
                .DELETE_FROM(tableName)
                .WHERE("id=#{id}")
                .toString();
    }

    public String removeByIds(String tableName, DataArray ids) {
        SQL sql = new SQL()
                .DELETE_FROM(tableName);
        withIds(sql, ids);
        return sql.toString();
    }
}
