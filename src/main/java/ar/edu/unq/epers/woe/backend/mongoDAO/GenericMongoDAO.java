package ar.edu.unq.epers.woe.backend.mongoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

public class GenericMongoDAO<T> {

    private Jongo jongo;
    private Class<T> entityType;
    protected MongoCollection mongoCollection;

    public GenericMongoDAO(Class<T> entityType){
        this.jongo = MongoConnection.getInstance().getJongo();
        this.entityType = entityType;
        this.mongoCollection = this.getCollectionFor(entityType);
    }

    private MongoCollection getCollectionFor(Class<T> entityType) {
        return this.jongo.getCollection(entityType.getSimpleName());
    }

    // Elimina sólo la colección para el tipo con el que se instanció el objeto.
    public void deleteAll() {
        this.mongoCollection.drop();
    }

    // Elimina todo.
    public void eliminarDatos() {
        this.jongo.getDatabase().dropDatabase();
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

    public List<T> findOrderByDateDesc(String query, Object... parameters) {
        try {
            // sort: 1 for asc and -1 for desc
            MongoCursor<T> all = this.mongoCollection.find(query, parameters).sort("{ fecha: -1 }").as(this.entityType);

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