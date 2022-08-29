package org.tenio.interstellar.dao.mybatis;

import org.apache.ibatis.annotations.*;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;
import org.tenio.interstellar.dao.Dao;

import java.util.List;

public interface BaseMapper {

    @InsertProvider(type = BaseSqlProvider.class, method = "save")
    void save(String tableName, @Param("entity") DataObject entity);

    @InsertProvider(type = BaseSqlProvider.class, method = "saveAll")
    void saveAll(String tableName, @Param("entities") DataArray entities);

    @SelectProvider(type = BaseSqlProvider.class, method = "one")
    DataObject one(String tableName, @Param("condition") DataObject condition);

    @SelectProvider(type = BaseSqlProvider.class, method = "oneWithFields")
    DataObject oneWithFields(String tableName, @Param("condition") DataObject condition, DataObject fields);

    @SelectProvider(type = BaseSqlProvider.class, method = "byId")
    DataObject byId(String tableName, @Param("id") Object id);

    @SelectProvider(type = BaseSqlProvider.class, method = "byIdWithFields")
    DataObject byIdWithFields(String tableName, @Param("id") Object id, DataObject fields);

    @SelectProvider(type = BaseSqlProvider.class, method = "inIdsWithList")
    List<DataObject> inIdsWithList(String tableName, @Param("ids") List<?> ids);

    @SelectProvider(type = BaseSqlProvider.class, method = "inIdsWithDataArray")
    List<DataObject> inIdsWithDataArray(String tableName, @Param("ids") DataArray ids);

    @SelectProvider(type = BaseSqlProvider.class, method = "inIdsWithListSort")
    List<DataObject> inIdsWithListSort(String tableName, @Param("ids") List<?> ids, DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "inIdsWithDataArraySort")
    List<DataObject> inIdsWithDataArraySort(String tableName, @Param("ids") DataArray ids, DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "all")
    List<DataObject> all(String tableName);

    @SelectProvider(type = BaseSqlProvider.class, method = "allWithSort")
    List<DataObject> allWithSort(String tableName, DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "find")
    List<DataObject> find(String tableName, @Param("condition") DataObject condition);

    @SelectProvider(type = BaseSqlProvider.class, method = "findWithSort")
    List<DataObject> findWithSort(String tableName, @Param("condition") DataObject condition, DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "findWithPage")
    List<DataObject> findWithPage(String tableName, @Param("condition") DataObject condition, int current, int size);

    @SelectProvider(type = BaseSqlProvider.class, method = "findWithPageSort")
    List<DataObject> findWithPageSort(String tableName, @Param("condition") DataObject condition, int current, int size, DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "findScroll")
    List<DataObject> findScroll(String tableName, @Param("condition") DataObject condition, int startIndex, int size, DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "count")
    Long count(String tableName, @Param("condition") DataObject condition);

    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
    void update(String tableName, @Param("condition") DataObject condition, @Param("setFields") DataObject setFields);

    @UpdateProvider(type = BaseSqlProvider.class, method = "updateByEntityId")
    void updateById(String tableName, @Param("entity") DataObject entity);

    @UpdateProvider(type = BaseSqlProvider.class, method = "updateById")
    void updateById(String tableName, @Param("id") Object id, @Param("entity") DataObject entity);

    @DeleteProvider(type = BaseSqlProvider.class, method = "remove")
    void remove(String tableName, @Param("condition") DataObject condition);

    @DeleteProvider(type = BaseSqlProvider.class, method = "removeByEntityId")
    void removeById(String tableName, @Param("entity") DataObject entity);

    @DeleteProvider(type = BaseSqlProvider.class, method = "removeById")
    void removeById(String tableName, @Param("id") Object id);

    @DeleteProvider(type = BaseSqlProvider.class, method = "removeByIds")
    void removeByIds(String tableName, @Param("ids") DataArray ids);
}