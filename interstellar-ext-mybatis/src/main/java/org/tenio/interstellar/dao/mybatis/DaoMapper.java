package org.tenio.interstellar.dao.mybatis;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;
import org.tenio.interstellar.dao.Dao;

import java.util.List;

public interface DaoMapper<T> extends Dao {

    @InsertProvider(BaseProviderMethodResolver.class)
    void save(DataObject data);

    @InsertProvider(BaseProviderMethodResolver.class)
    void saveAll(DataArray datas);

    @SelectProvider(BaseProviderMethodResolver.class)
    DataObject one(DataObject condition);

    @SelectProvider(BaseProviderMethodResolver.class)
    DataObject oneWithFields(DataObject condition, DataObject fields);

    @SelectProvider(BaseProviderMethodResolver.class)
    DataObject byId(Object id);

    @SelectProvider(BaseProviderMethodResolver.class)
    DataObject byIdWithFields(Object id, DataObject fields);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> inIdsWithList(List<?> ids);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> inIdsWithDataArray(DataArray ids);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> inIdsWithListSort(List<?> ids, DataObject sort);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> inIdsWithDataArraySort(DataArray ids, DataObject sort);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> all();

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> allWithSort(DataObject sort);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> find(DataObject condition);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> findWithSort(DataObject condition, DataObject sort);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> findWithPage(DataObject condition, int current, int size);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> findWithPageSort(DataObject condition, int current, int size, DataObject sort);

    @SelectProvider(BaseProviderMethodResolver.class)
    List<DataObject> findScroll(DataObject condition, int startIndex, int size, DataObject sort);

    @SelectProvider(BaseProviderMethodResolver.class)
    Long count(DataObject conditionFields);

    @UpdateProvider(BaseProviderMethodResolver.class)
    void update(DataObject condition, DataObject setFields);

    @Override
    default void upsert(DataObject condition, DataObject setFields) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void upsertAll(DataArray datas) {
        throw new UnsupportedOperationException();
    }

    @UpdateProvider(BaseProviderMethodResolver.class)
    void updateByEntityId(DataObject entity);

    @UpdateProvider(BaseProviderMethodResolver.class)
    void updateById(Object id, DataObject entity);

    @DeleteProvider(BaseProviderMethodResolver.class)
    void remove(DataObject condition);

    @DeleteProvider(BaseProviderMethodResolver.class)
    void removeByEntityId(DataObject entity);

    @DeleteProvider(BaseProviderMethodResolver.class)
    void removeById(Object id);

    @DeleteProvider(BaseProviderMethodResolver.class)
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
