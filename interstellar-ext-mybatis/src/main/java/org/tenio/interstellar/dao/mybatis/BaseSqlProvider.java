package org.tenio.interstellar.dao.mybatis;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseSqlProvider {

    public String save(String tableName, DataObject dataObject, ProviderContext providerContext) {
        String[] fields = dataObject.fieldNames().toArray(new String[0]);
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName);
        for (String field : fields) {
            sql.VALUES(field, "#{" + field + "}");
        }
        return sql.toString();
    }

    public String saveAll(String tableName, DataArray datas, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        if (datas == null || datas.size() == 0) {
            throw new NullPointerException();
        }
        List<DataObject> list = datas.stream().map(o -> ((DataObject) o)).collect(Collectors.toList());
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName);
        String[] fields = list.get(0).fieldNames().toArray(new String[0]);
        sql.INTO_COLUMNS(fields);
        StringBuilder sb = new StringBuilder();
        int collIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            if (collIndex != 0) {
                sb.append("),(");
            }
            int valueIndex = 0;
            for (String field : fields) {
                sb.append(valueIndex == 0 ? "#{list[" + i + "]." : ",#{list[" + i + "].");
                sb.append(field).append("}");
                valueIndex++;
            }
            collIndex++;
        }
        sql.INTO_VALUES(sb.toString());
        return sql.toString();
    }

    public String one(String tableName, @Param("condition") DataObject condition, ProviderContext providerContext) {
        return oneWithFields(tableName, condition, new DataObject().put("*", ""), providerContext);
    }

    public String oneWithFields(String tableName, @Param("condition") DataObject condition, @Param("fields") DataObject fields, ProviderContext providerContext) {
        String[] fieldsName = fields.fieldNames().toArray(new String[0]);
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.SELECT(fieldsName);
        sql.FROM(tableName);
        for (Map.Entry<String, Object> entry : condition.getMap().entrySet()) {
            sql.WHERE(entry.getKey() + "=#{condition." + entry.getKey() + "}");
        }
        return sql.toString();
    }

    public String byId(String tableName, Object id, ProviderContext providerContext) {
        return byIdWithFields(tableName, id, new DataObject().put("*", ""), providerContext);
    }

    public String byIdWithFields(String tableName, Object id, @Param("fields") DataObject fields, ProviderContext providerContext) {
        DataObject condition = new DataObject().put("id", id);
        return oneWithFields(tableName, condition, fields, providerContext);
    }

    public String inIdsWithList(String tableName, @Param("ids") List<?> ids, ProviderContext providerContext) {
        return inIdsWithListSort(tableName, ids, null, providerContext);
    }

    public String inIdsWithDataArray(String tableName, DataArray ids, ProviderContext providerContext) {
        return inIdsWithDataArraySort(tableName, ids, null, providerContext);
    }

    public String inIdsWithListSort(String tableName, @Param("ids") List<?> ids, @Param("sort") DataObject sort, ProviderContext providerContext) {
        DataArray dataArray = new DataArray(ids);
        return inIdsWithDataArraySort(tableName, dataArray, sort, providerContext);
    }

    public String inIdsWithDataArraySort(String tableName, @Param("ids") DataArray ids, @Param("sort") DataObject sort, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(tableName);
        List<DataObject> list = ids.stream().map(o -> ((DataObject) o)).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder("id in (");
        int collIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            sb.append(collIndex == 0 ? "" : ",");
            sb.append("#{list[").append(i).append("].id}");
            collIndex++;
        }
        sb.append(")");
        sql.WHERE(sb.toString());
        if (sort != null) {
            for (Map.Entry<String, Object> entry : sort.getMap().entrySet()) {
                sql.ORDER_BY(entry.getKey() + " " + entry.getValue());
            }
        }
        return sql.toString();
    }

    public String all(String tableName, ProviderContext providerContext) {
        return allWithSort(tableName, null, providerContext);
    }

    public String allWithSort(String tableName, DataObject sort, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(tableName);
        if (sort != null) {
            for (Map.Entry<String, Object> entry : sort.getMap().entrySet()) {
                sql.ORDER_BY(entry.getKey() + " " + entry.getValue());
            }
        }
        return sql.toString();
    }

    public String find(String tableName, @Param("condition") DataObject condition, ProviderContext providerContext) {
        return findWithSort(tableName, condition, null, providerContext);
    }

    public String findWithSort(String tableName, @Param("condition") DataObject condition, @Param("sort") DataObject sort, ProviderContext providerContext) {
        return findWithPageSort(tableName, condition, -1, -1, sort, providerContext);
    }

    public String findWithPage(String tableName, @Param("condition") DataObject condition, int current, int size, ProviderContext providerContext) {
        return findWithPageSort(tableName, condition, current, size, null, providerContext);
    }

    public String findWithPageSort(String tableName, @Param("condition") DataObject condition, int current, int size, @Param("sort") DataObject sort, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(tableName);
        for (Map.Entry<String, Object> entry : condition.getMap().entrySet()) {
            sql.WHERE(entry.getKey() + "=#{condition." + entry.getKey() + "}");
        }
        if (sort != null && sort.size() > 0) {
            for (Map.Entry<String, Object> entry : sort.getMap().entrySet()) {
                sql.ORDER_BY(entry.getKey() + " " + entry.getValue());
            }
        }
        if (current > 0 && size > 0) {
            int offset = (current - 1) * size;
            sql.LIMIT(size).OFFSET(offset);
        }
        return sql.toString();
    }

    public String findScroll(String tableName, @Param("condition") DataObject condition, int startIndex, int size, @Param("sort") DataObject sort, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(tableName);
        for (Map.Entry<String, Object> entry : condition.getMap().entrySet()) {
            sql.WHERE(entry.getKey() + "=#{condition." + entry.getKey() + "}");
        }
        if (sort != null && sort.size() > 0) {
            for (Map.Entry<String, Object> entry : sort.getMap().entrySet()) {
                sql.ORDER_BY(entry.getKey() + " " + entry.getValue());
            }
        }
        if (startIndex > 0 && size > 0) {
            sql.LIMIT(size).OFFSET(startIndex);
        }
        return sql.toString();
    }

    public String count(String tableName, DataObject conditionFields, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        sql.FROM(tableName);
        for (Map.Entry<String, Object> entry : conditionFields.getMap().entrySet()) {
            sql.WHERE(entry.getKey() + "=#{condition." + entry.getKey() + "}");
        }
        return sql.toString();
    }

    public String update(String tableName, @Param("condition") DataObject condition, @Param("setFields") DataObject setFields, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.UPDATE(tableName);
        for (Map.Entry<String, Object> entry : setFields.getMap().entrySet()) {
            sql.SET(entry.getKey() + "=#{setFields." + entry.getKey() + "}");
        }
        for (Map.Entry<String, Object> entry : condition.getMap().entrySet()) {
            sql.WHERE(entry.getKey() + "=#{condition." + entry.getKey() + "}");
        }
        return sql.toString();
    }

    public String updateById(String tableName, DataObject entity, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.UPDATE(tableName);
        for (Map.Entry<String, Object> entry : entity.getMap().entrySet()) {
            sql.SET(entry.getKey() + "=#{" + entry.getKey() + "}");
        }
        sql.WHERE("id=#{id}");
        return sql.toString();
    }

    public String remove(String tableName, DataObject condition, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.DELETE_FROM(tableName);
        for (Map.Entry<String, Object> entry : condition.getMap().entrySet()) {
            sql.WHERE(entry.getKey() + "=#{" + entry.getKey() + "}");
        }
        return sql.toString();
    }

    public String removeById(String tableName, DataObject entity, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.DELETE_FROM(tableName);
        sql.WHERE("id=#{id}");
        return sql.toString();
    }

    public String removeByIds(String tableName, DataArray ids, ProviderContext providerContext) {
//        String tableName = getTableName(providerContext);
        SQL sql = new SQL();
        sql.DELETE_FROM(tableName);
        List<DataObject> list = ids.stream().map(o -> ((DataObject) o)).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder("id in (");
        int collIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            sb.append(collIndex == 0 ? "" : ",");
            sb.append("#{list[").append(i).append("].id}");
            collIndex++;
        }
        sb.append(")");
        sql.WHERE(sb.toString());
        return sql.toString();
    }


    private String getTableName(ProviderContext providerContext) {
        Class<?> mapperType = providerContext.getMapperType();
        String simpleName = mapperType.getSimpleName();
        int mapperIndex = simpleName.lastIndexOf("Mapper");
        String tableName = simpleName.substring(0, mapperIndex);
        tableName = StrUtil.toUnderlineCase(tableName);
        return tableName;
    }
}
