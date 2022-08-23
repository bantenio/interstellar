package org.tenio.interstellar.dao.mybatis;

import org.apache.ibatis.annotations.*;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;
import org.tenio.interstellar.dao.Dao;

import java.util.List;

public interface MybatisBaseDao extends Dao {

    @InsertProvider(type = BaseSqlProvider.class, method = "save")
    void save(DataObject data);

    @InsertProvider(type = BaseSqlProvider.class, method = "saveAll")
    void saveAll(DataArray datas);

    @SelectProvider(type = BaseSqlProvider.class, method = "one")
    DataObject one(DataObject condition);

    @SelectProvider(type = BaseSqlProvider.class, method = "oneWithFields")
    DataObject oneWithFields(@Param("condition") DataObject condition, @Param("fields") DataObject fields);

    @SelectProvider(type = BaseSqlProvider.class, method = "byId")
    DataObject byId(Object id);

    @SelectProvider(type = BaseSqlProvider.class, method = "byIdWithFields")
    DataObject byIdWithFields(Object id, @Param("fields") DataObject fields);

    @SelectProvider(type = BaseSqlProvider.class, method = "inIdsWithList")
    List<DataObject> inIdsWithList(List<?> ids);

    @SelectProvider(type = BaseSqlProvider.class, method = "inIdsWithDataArray")
    List<DataObject> inIdsWithDataArray(DataArray ids);

    @SelectProvider(type = BaseSqlProvider.class, method = "inIdsWithListSort")
    List<DataObject> inIdsWithListSort(@Param("ids") List<?> ids, @Param("sort") DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "inIdsWithDataArraySort")
    List<DataObject> inIdsWithDataArraySort(@Param("ids") DataArray ids, @Param("sort") DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "all")
    List<DataObject> all();

    @SelectProvider(type = BaseSqlProvider.class, method = "allWithSort")
    List<DataObject> allWithSort(DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "find")
    List<DataObject> find(@Param("condition") DataObject condition);

    @SelectProvider(type = BaseSqlProvider.class, method = "findWithSort")
    List<DataObject> findWithSort(@Param("condition") DataObject condition, @Param("sort") DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "findWithPage")
    List<DataObject> findWithPage(@Param("condition") DataObject condition, int current, int size);

    @SelectProvider(type = BaseSqlProvider.class, method = "findWithPageSort")
    List<DataObject> findWithPageSort(@Param("condition") DataObject condition, int current, int size, @Param("sort") DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "findScroll")
    List<DataObject> findScroll(@Param("condition") DataObject condition, int startIndex, int size, @Param("sort") DataObject sort);

    @SelectProvider(type = BaseSqlProvider.class, method = "count")
    Long count(@Param("conditionFields") DataObject conditionFields);

    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
    void update(@Param("condition") DataObject condition, @Param("setFields") DataObject setFields);

    @UpdateProvider(type = BaseSqlProvider.class, method = "upsert")
    void upsert(@Param("condition") DataObject condition, @Param("setFields") DataObject setFields);

    @UpdateProvider(type = BaseSqlProvider.class, method = "upsertAll")
    void upsertAll(DataArray datas);

    @UpdateProvider(type = BaseSqlProvider.class, method = "updateById")
    void updateById(DataObject entity);

    @DeleteProvider(type = BaseSqlProvider.class, method = "remove")
    void remove(DataObject condition);

    @DeleteProvider(type = BaseSqlProvider.class, method = "removeById")
    void removeById(DataObject entity);

    @DeleteProvider(type = BaseSqlProvider.class, method = "removeByIds")
    void removeByIds(DataArray ids);

    @SelectProvider(type = BaseSqlProvider.class, method = "aggregateScroll")
    List<DataObject> aggregateScroll(int startIndex, int size, @Param("sort") DataObject sort, @Param("aggregates") DataArray aggregates);

    @SelectProvider(type = BaseSqlProvider.class, method = "aggregateToCount")
    Long aggregateToCount(DataArray aggregates);

    @SelectProvider(type = BaseSqlProvider.class, method = "aggregate")
    List<DataObject> aggregate(DataArray aggregates);
}