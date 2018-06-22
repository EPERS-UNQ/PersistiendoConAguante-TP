package ar.edu.unq.epers.woe.backend.mongoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

public class GenericMongoDAO<T> {

    private Class<T> entityType;
    protected MongoCollection mongoCollection;

    public GenericMongoDAO(Class<T> entityType){
        this.entityType = entityType;
        this.mongoCollection = this.getCollectionFor(entityType);
    }

    private MongoCollection getCollectionFor(Class<T> entityType) {
        Jongo jongo = MongoConnection.getInstance().getJongo();
        return jongo.getCollection(entityType.getSimpleName());
    }

    public void deleteAll() {
        this.mongoCollection.drop();
    }

    public void save(T object) {
        this.mongoCollection.insert(object);
    }

    public void save(List<T> objects) {
        this.mongoCollection.insert(objects.toArray());
    }

    public T get(String id) {
        ObjectId objectId = new ObjectId(id);
        return this.mongoCollection.findOne(objectId).as(this.entityType);
    }

    public List<T> find(String query, Object... parameters) {
        try {
            MongoCursor<T> all = this.mongoCollection.find(query, parameters).as(this.entityType);

            List<T> result = this.copyToList(all);
            all.close();

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Copia el contenido de un iterable en una lista
     */
    protected <X> List<X> copyToList(Iterable<X> iterable) {
        List<X> result = new ArrayList<>();
        iterable.forEach(x -> result.add(x));
        return result;
    }

}