package org.tenio.interstellar.dao.mybatis;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;
import org.tenio.interstellar.dao.Dao;

import java.util.List;

public interface DataObjectDaoMapper<T> extends Dao {

    @InsertProvider(DataObjectProviderMethodResolver.class)
    void save(DataObject data);

    @InsertProvider(DataObjectProviderMethodResolver.class)
    void saveAll(DataArray datas);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    DataObject one(DataObject condition);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    DataObject oneWithFields(DataObject condition, DataObject fields);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    DataObject byId(Object id);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    DataObject byIdWithFields(Object id, DataObject fields);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> inIdsWithList(List<?> ids);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> inIdsWithDataArray(DataArray ids);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> inIdsWithListSort(List<?> ids, DataObject sort);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> inIdsWithDataArraySort(DataArray ids, DataObject sort);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> all();

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> allWithSort(DataObject sort);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> find(DataObject condition);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> findWithSort(DataObject condition, DataObject sort);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> findWithPage(DataObject condition, int current, int size);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> findWithPageSort(DataObject condition, int current, int size, DataObject sort);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    List<DataObject> findScroll(DataObject condition, int startIndex, int size, DataObject sort);

    @SelectProvider(DataObjectProviderMethodResolver.class)
    Long count(DataObject conditionFields);

    @UpdateProvider(DataObjectProviderMethodResolver.class)
    void update(DataObject condition, DataObject setFields);

    @Override
    default void upsert(DataObject condition, DataObject setFields) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void upsertAll(DataArray datas) {
        throw new UnsupportedOperationException();
    }

    @UpdateProvider(DataObjectProviderMethodResolver.class)
    void updateByEntityId(DataObject entity);

    @UpdateProvider(DataObjectProviderMethodResolver.class)
    void updateById(Object id, DataObject entity);

    @DeleteProvider(DataObjectProviderMethodResolver.class)
    void remove(DataObject condition);

    @DeleteProvider(DataObjectProviderMethodResolver.class)
    void removeByEntityId(DataObject entity);

    @DeleteProvider(DataObjectProviderMethodResolver.class)
    void removeById(Object id);

    @DeleteProvider(DataObjectProviderMethodResolver.class)
    void removeByIds(DataArray ids);

    @Override
    default void updateById(DataObject entity) {
        updateByEntityId(entity);
    }

    @Override
    default void removeById(DataObject entity) {
        removeByEntityId(entity);
    }

    @Override
    default List<DataObject> aggregateScroll(int startIndex, int size, DataObject sort, DataArray aggregates) {
        throw new UnsupportedOperationException();
    }

    @Override
    default Long aggregateToCount(DataArray aggregates) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<DataObject> aggregate(DataArray aggregates) {
        throw new UnsupportedOperationException();
    }
}
