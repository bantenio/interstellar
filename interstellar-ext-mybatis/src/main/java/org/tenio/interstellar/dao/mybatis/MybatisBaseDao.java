package org.tenio.interstellar.dao.mybatis;

import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;
import org.tenio.interstellar.dao.Dao;

import java.util.List;

public class MybatisBaseDao implements Dao {
    private final String tableName;

    private final BaseMapper baseMapper;

    public MybatisBaseDao(BaseMapper baseMapper, String tableName) {
        this.tableName = tableName;
        this.baseMapper = baseMapper;
    }

    @Override
    public void save(DataObject data) {
        baseMapper.save(tableName, data);
    }

    @Override
    public void saveAll(DataArray datas) {
        baseMapper.saveAll(tableName, datas);
    }

    @Override
    public DataObject one(DataObject condition) {
        return baseMapper.one(tableName, condition);
    }

    @Override
    public DataObject oneWithFields(DataObject condition, DataObject fields) {
        return baseMapper.oneWithFields(tableName, condition, fields);
    }

    @Override
    public DataObject byId(Object id) {
        return baseMapper.byId(tableName, id);
    }

    @Override
    public DataObject byIdWithFields(Object id, DataObject fields) {
        return baseMapper.byIdWithFields(tableName, id, fields);
    }

    @Override
    public List<DataObject> inIdsWithList(List<?> ids) {
        return baseMapper.inIdsWithList(tableName, ids);
    }

    @Override
    public List<DataObject> inIdsWithDataArray(DataArray ids) {
        return baseMapper.inIdsWithDataArray(tableName, ids);
    }

    @Override
    public List<DataObject> inIdsWithListSort(List<?> ids, DataObject sort) {
        return baseMapper.inIdsWithListSort(tableName, ids, sort);
    }

    @Override
    public List<DataObject> inIdsWithDataArraySort(DataArray ids, DataObject sort) {
        return baseMapper.inIdsWithDataArraySort(tableName, ids, sort);
    }

    @Override
    public List<DataObject> all() {
        return baseMapper.all(tableName);
    }

    @Override
    public List<DataObject> allWithSort(DataObject sort) {
        return baseMapper.allWithSort(tableName, sort);
    }

    @Override
    public List<DataObject> find(DataObject condition) {
        return baseMapper.find(tableName, condition);
    }

    @Override
    public List<DataObject> findWithSort(DataObject condition, DataObject sort) {
        return baseMapper.findWithSort(tableName, condition, sort);
    }

    @Override
    public List<DataObject> findWithPage(DataObject condition, int current, int size) {
        return baseMapper.findWithPage(tableName, condition, current, size);
    }

    @Override
    public List<DataObject> findWithPageSort(DataObject condition, int current, int size, DataObject sort) {
        return baseMapper.findWithPageSort(tableName, condition, current, size, sort);
    }

    @Override
    public List<DataObject> findScroll(DataObject condition, int startIndex, int size, DataObject sort) {
        return baseMapper.findScroll(tableName, condition, startIndex, size, sort);
    }

    @Override
    public Long count(DataObject conditionFields) {
        return baseMapper.count(tableName, conditionFields);
    }

    @Override
    public void update(DataObject condition, DataObject setFields) {
        baseMapper.update(tableName, condition, setFields);
    }

    @Override
    public void upsert(DataObject condition, DataObject setFields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void upsertAll(DataArray datas) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateById(DataObject entity) {
        baseMapper.updateById(tableName, entity);
    }

    @Override
    public void updateById(Object id, DataObject entity) {
        baseMapper.updateByIdWithEntity(tableName, id, entity);
    }

    @Override
    public void remove(DataObject condition) {
        baseMapper.remove(tableName, condition);
    }

    @Override
    public void removeById(DataObject entity) {
        baseMapper.removeByIdWithEntity(tableName, entity);
    }

    @Override
    public void removeById(Object id) {
        baseMapper.removeById(tableName, id);
    }

    @Override
    public void removeByIds(DataArray ids) {
        baseMapper.removeByIds(tableName, ids);
    }

    @Override
    public List<DataObject> aggregateScroll(int startIndex, int size, DataObject sort, DataArray aggregates) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long aggregateToCount(DataArray aggregates) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<DataObject> aggregate(DataArray aggregates) {
        throw new UnsupportedOperationException();
    }
}