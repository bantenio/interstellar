package org.tenio.interstellar.dao.mongo;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;
import org.tenio.interstellar.dao.Dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MongoBaseDao implements Dao {

    private final MongoDatabase database;

    private String collName;

    public MongoBaseDao(MongoDatabase database, String collName) {
        this.database = database;
    }

    public String getCollName() {
        return collName;
    }

    public void setCollName(String collName) {
        this.collName = collName;
    }

    @Override
    public void save(DataObject data) {
        MongoCollection<Document> collection = database.getCollection(collName);
        collection.insertOne(new Document(data.getMap()));
    }

    @Override
    public void saveAll(DataArray datas) {
        List<Document> documents = new ArrayList<>();
        List<DataObject> collect = datas.stream().map(o -> ((DataObject) o)).collect(Collectors.toList());
        for (DataObject dataObject : collect) {
            documents.add(new Document(dataObject.getMap()));
        }
        MongoCollection<Document> collection = database.getCollection(collName);
        collection.insertMany(documents);
    }

    @Override
    public DataObject one(DataObject condition) {
        return oneWithFields(condition, null);
    }

    @Override
    public DataObject oneWithFields(DataObject condition, DataObject fields) {
        MongoCollection<Document> collection = database.getCollection(collName);
        Document conditionDoc = new Document(condition.getMap());


        FindIterable<Document> documents = collection.find(conditionDoc);
        if (fields != null) {
            Document fieldsDoc = new Document(fields.getMap());
            documents.projection(fieldsDoc);
        }
        Document document = documents.first();
        return new DataObject(document);
    }

    @Override
    public DataObject byId(Object id) {
        return byIdWithFields(id, null);
    }

    @Override
    public DataObject byIdWithFields(Object id, DataObject fields) {
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        conditionMap.put("_id", id);
        DataObject condition = new DataObject(conditionMap);
        return oneWithFields(condition, fields);
    }

    @Override
    public List<DataObject> inIdsWithList(List<?> ids) {
        return inIdsWithListSort(ids, null);
    }

    @Override
    public List<DataObject> inIdsWithDataArray(DataArray ids) {
        return inIdsWithDataArraySort(ids, null);
    }

    @Override
    public List<DataObject> inIdsWithListSort(List<?> ids, DataObject sort) {
        DataArray dataArray = new DataArray(ids);
        return inIdsWithDataArraySort(dataArray, sort);
    }

    @Override
    public List<DataObject> inIdsWithDataArraySort(DataArray ids, DataObject sort) {
        Map<String, Object> idsMap = new LinkedHashMap<>();
        idsMap.put("$in", ids);
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        conditionMap.put("_id", idsMap);
        DataObject dataObject = new DataObject(conditionMap);
        return findWithSort(dataObject, sort);
    }

    @Override
    public List<DataObject> all() {
        return find(new DataObject());
    }

    @Override
    public List<DataObject> allWithSort(DataObject sort) {
        return findWithSort(new DataObject(), sort);
    }

    @Override
    public List<DataObject> find(DataObject condition) {
        return findWithSort(condition, null);
    }

    @Override
    public List<DataObject> findWithSort(DataObject condition, DataObject sort) {
        return findWithPageSort(condition, -1, -1, sort);
    }

    @Override
    public List<DataObject> findWithPage(DataObject condition, int current, int size) {
        return findWithPageSort(condition, current, size, null);
    }

    @Override
    public List<DataObject> findWithPageSort(DataObject condition, int current, int size, DataObject sort) {
        int startIndex = 0;
        if (current > 0) {
            startIndex = (current - 1) * size;
        }
        return findScroll(condition, startIndex, size, sort);
    }

    @Override
    public List<DataObject> findScroll(DataObject condition, int startIndex, int size, DataObject sort) {
        MongoCollection<Document> collection = database.getCollection(collName);
        Document searchDoc = new Document(condition.getMap());
        FindIterable<Document> documents = collection.find(searchDoc);
        if (startIndex >= 0 && size > 0) {
            documents.skip(startIndex).limit(size);
        }
        if (sort != null) {
            Document sortDoc = new Document(sort.getMap());
            documents.sort(sortDoc);
        }
        List<DataObject> result = new ArrayList<>();
        for (Document document : documents) {
            result.add(new DataObject(document));
        }
        return result;
    }

    @Override
    public Long count(DataObject conditionFields) {
        MongoCollection<Document> collection = database.getCollection(collName);
        return collection.countDocuments(new Document(conditionFields.getMap()));
    }

    @Override
    public void update(DataObject condition, DataObject setFields) {
        MongoCollection<Document> collection = database.getCollection(collName);
        Document conditionDoc = new Document(condition.getMap());
        collection.updateOne(conditionDoc, new Document("$set", new Document(setFields.getMap())));
    }

    @Override
    public void upsert(DataObject condition, DataObject setFields) {
        MongoCollection<Document> collection = database.getCollection(collName);
        Document conditionDoc = new Document(condition.getMap());
        Document setFieldsDoc = new Document("$set", new Document(setFields.getMap()));
        collection.updateOne(conditionDoc, setFieldsDoc, new UpdateOptions().upsert(true));
    }

    @Override
    public void upsertAll(DataArray datas) {
        MongoCollection<Document> collection = database.getCollection(collName);
        List<DataObject> collect = datas.stream().map(o -> ((DataObject) o)).collect(Collectors.toList());
        List<WriteModel<Document>> writes = new ArrayList<>();
        for (DataObject dataObject : collect) {
            ReplaceOneModel<Document> documentReplaceOneModel = new ReplaceOneModel<>(
                    new Document(dataObject.getMap()), // filter
                    new Document(dataObject.getMap()), // update
                    new UpdateOptions().upsert(true)
            );
            writes.add(documentReplaceOneModel);
        }
        collection.bulkWrite(writes);
    }

    @Override
    public void updateById(DataObject entity) {
        MongoCollection<Document> collection = database.getCollection(collName);
        Document conditionDoc = new Document();
        conditionDoc.put("_id", entity.getValue("_id"));
        collection.updateOne(conditionDoc, new Document(entity.getMap()));
    }

    @Override
    public void remove(DataObject condition) {
        MongoCollection<Document> collection = database.getCollection(collName);
        Document document = new Document(condition.getMap());
        collection.deleteMany(document);
    }

    @Override
    public void removeById(DataObject entity) {
        MongoCollection<Document> collection = database.getCollection(collName);
        Document conditionDoc = new Document();
        conditionDoc.put("_id", entity.getValue("_id"));
        collection.deleteOne(conditionDoc);
    }

    @Override
    public void removeByIds(DataArray ids) {
        MongoCollection<Document> collection = database.getCollection(collName);
        Map<String, Object> idsMap = new LinkedHashMap<>();
        idsMap.put("$in", ids);
        Document conditionDoc = new Document();
        conditionDoc.put("_id", idsMap);
        collection.deleteMany(conditionDoc);
    }

    @Override
    public List<DataObject> aggregateScroll(int startIndex, int size, DataObject sort, DataArray aggregates) {
        if (sort != null && sort.size() > 0) {
            aggregates.add(new DataObject().put("$sort", sort));
        }
        aggregates.add(new DataObject().put("$skip", startIndex));
        aggregates.add(new DataObject().put("$limit", size));
        return aggregate(aggregates);
    }

    @Override
    public Long aggregateToCount(DataArray aggregates) {
        aggregates.add(new DataObject().put("$count", "total"));
        List<DataObject> dataObjectList = aggregate(aggregates);
        if (dataObjectList == null || dataObjectList.size() == 0) {
            return 0L;
        }
        return dataObjectList.get(0).getLong("total");
    }

    @Override
    public List<DataObject> aggregate(DataArray aggregates) {
        MongoCollection<Document> collection = database.getCollection(collName);
        List<DataObject> collect = aggregates.stream().map(o -> ((DataObject) o)).collect(Collectors.toList());
        List<Document> aggregateList = new ArrayList<>();
        for (DataObject dataObject : collect) {
            aggregateList.add(new Document(dataObject.getMap()));
        }
        AggregateIterable<Document> aggregate = collection.aggregate(aggregateList);
        List<DataObject> result = new ArrayList<>();
        for (Document document : aggregate) {
            result.add(new DataObject(document));
        }
        return result;
    }
}
