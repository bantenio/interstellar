package org.tenio.interstellar.dao;

import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;

import java.util.List;

public interface Dao {

    void save(DataObject data);

    void saveAll(DataArray datas);

    DataObject one(DataObject condition);

    DataObject oneWithFields(DataObject condition, DataObject fields);

    DataObject byId(Object id);

    DataObject byIdWithFields(Object id, DataObject fields);

    List<DataObject> inIdsWithList(List<?> ids);

    List<DataObject> inIdsWithDataArray(DataArray ids);

    List<DataObject> inIdsWithListSort(List<?> ids, DataObject sort);

    List<DataObject> inIdsWithDataArraySort(DataArray ids, DataObject sort);

    List<DataObject> all();

    List<DataObject> allWithSort(DataObject sort);

    List<DataObject> find(DataObject condition);

    List<DataObject> findWithSort(DataObject condition, DataObject sort);

    List<DataObject> findWithPage(DataObject condition, int current, int size);

    List<DataObject> findWithPageSort(DataObject condition, int current, int size, DataObject sort);

    List<DataObject> findScroll(DataObject condition, int startIndex, int size, DataObject sort);

    Long count(DataObject conditionFields);

    void update(DataObject condition, DataObject setFields);

    void upsert(DataObject condition, DataObject setFields);

    void upsertAll(DataArray datas);

    void updateById(DataObject entity);

    void updateById(Object id, DataObject entity);

    void remove(DataObject condition);

    void removeById(DataObject entity);

    void removeById(Object id);

    void removeByIds(DataArray ids);

    List<DataObject> aggregateScroll(int startIndex, int size, DataObject sort, DataArray aggregates);

    Long aggregateToCount(DataArray aggregates);

    List<DataObject> aggregate(DataArray aggregates);

}
