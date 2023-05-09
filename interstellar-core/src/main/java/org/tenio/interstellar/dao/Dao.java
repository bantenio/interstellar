package org.tenio.interstellar.dao;

import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;

import java.util.List;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public interface Dao {

    /**
     * TODO
     *
     * @param data TODO
     */
    void save(DataObject data);

    /**
     * TODO
     *
     * @param datas TODO
     */
    void saveAll(DataArray datas);

    /**
     * TODO
     *
     * @param condition TODO
     * @return TODO
     */
    DataObject one(DataObject condition);

    /**
     * TODO
     *
     * @param condition TODO
     * @param fields    TODO
     * @return TODO
     */
    DataObject oneWithFields(DataObject condition, DataObject fields);

    /**
     * TODO
     *
     * @param id TODO
     * @return TODO
     */
    DataObject byId(Object id);

    /**
     * TODO
     *
     * @param id     TODO
     * @param fields TODO
     * @return TODO
     */
    DataObject byIdWithFields(Object id, DataObject fields);

    /**
     * TODO
     *
     * @param ids TODO
     * @return TODO
     */
    List<DataObject> inIdsWithList(List<?> ids);

    /**
     * TODO
     *
     * @param ids TODO
     * @return TODO
     */
    List<DataObject> inIdsWithDataArray(DataArray ids);

    /**
     * TODO
     *
     * @param ids  TODO
     * @param sort TODO
     * @return TODO
     */
    List<DataObject> inIdsWithListSort(List<?> ids, DataObject sort);

    /**
     * TODO
     *
     * @param ids  TODO
     * @param sort TODO
     * @return TODO
     */
    List<DataObject> inIdsWithDataArraySort(DataArray ids, DataObject sort);

    /**
     * TODO
     *
     * @return TODO
     */
    List<DataObject> all();

    /**
     * TODO
     *
     * @param sort TODO
     * @return TODO
     */
    List<DataObject> allWithSort(DataObject sort);

    /**
     * TODO
     *
     * @param condition TODO
     * @return TODO
     */
    List<DataObject> find(DataObject condition);

    /**
     * TODO
     *
     * @param condition TODO
     * @param sort      TODO
     * @return TODO
     */
    List<DataObject> findWithSort(DataObject condition, DataObject sort);

    /**
     * TODO
     *
     * @param condition TODO
     * @param current   TODO
     * @param size      TODO
     * @return TODO
     */
    List<DataObject> findWithPage(DataObject condition, int current, int size);

    /**
     * TODO
     *
     * @param condition TODO
     * @param current   TODO
     * @param size      TODO
     * @param sort      TODO
     * @return TODO
     */
    List<DataObject> findWithPageSort(DataObject condition, int current, int size, DataObject sort);

    /**
     * TODO
     *
     * @param condition  TODO
     * @param startIndex TODO
     * @param size       TODO
     * @param sort       TODO
     * @return TODO
     */
    List<DataObject> findScroll(DataObject condition, int startIndex, int size, DataObject sort);

    /**
     * TODO
     *
     * @param conditionFields TODO
     * @return TODO
     */
    Long count(DataObject conditionFields);

    /**
     * TODO
     *
     * @param condition TODO
     * @param setFields TODO
     */
    void update(DataObject condition, DataObject setFields);

    /**
     * TODO
     *
     * @param condition TODO
     * @param setFields TODO
     */
    void upsert(DataObject condition, DataObject setFields);

    /**
     * TODO
     *
     * @param datas TODO
     */
    void upsertAll(DataArray datas);

    /**
     * TODO
     *
     * @param entity TODO
     */
    void updateById(DataObject entity);

    /**
     * TODO
     *
     * @param id     TODO
     * @param entity TODO
     */
    void updateById(Object id, DataObject entity);

    /**
     * TODO
     *
     * @param condition TODO
     */
    void remove(DataObject condition);

    /**
     * TODO
     *
     * @param entity TODO
     */
    void removeById(DataObject entity);

    /**
     * TODO
     *
     * @param id TODO
     */
    void removeById(Object id);

    /**
     * TODO
     *
     * @param ids TODO
     */
    void removeByIds(DataArray ids);

    /**
     * TODO
     *
     * @param startIndex TODO
     * @param size       TODO
     * @param sort       TODO
     * @param aggregates TODO
     * @return TODO
     */
    List<DataObject> aggregateScroll(int startIndex, int size, DataObject sort, DataArray aggregates);

    /**
     * TODO
     *
     * @param aggregates TODO
     * @return TODO
     */
    Long aggregateToCount(DataArray aggregates);

    /**
     * TODO
     *
     * @param aggregates TODO
     * @return TODO
     */
    List<DataObject> aggregate(DataArray aggregates);

}
