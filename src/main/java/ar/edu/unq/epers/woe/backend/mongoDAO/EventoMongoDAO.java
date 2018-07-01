package ar.edu.unq.epers.woe.backend.mongoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import ar.edu.unq.epers.woe.backend.model.evento.Evento;

public class EventoMongoDAO {

    private Jongo jongo;
    protected MongoCollection mongoCollection;

    public EventoMongoDAO(){
        this.jongo = MongoConnection.getInstance().getJongo();
        this.mongoCollection = this.getCollectionFor();
    }

    private MongoCollection getCollectionFor() {
        return this.jongo.getCollection(Evento.class.getSimpleName());
    }

    // Elimina sólo la colección para el tipo con el que se instanció el objeto.
    public void deleteAll() {
        this.mongoCollection.drop();
    }

    // Elimina todo.
    public void eliminarDatos() {
        this.jongo.getDatabase().dropDatabase();
    }

    public void save(Evento evento) {
        this.mongoCollection.insert(evento);
    }

    public void save(List<Evento> objects) {
        this.mongoCollection.insert(objects.toArray());
    }

    public Evento get(String id) {
        ObjectId objectId = new ObjectId(id);
        Evento e = this.mongoCollection.findOne(objectId).as(Evento.class);
        return e;
    }

    public List<Evento> find(String query, Object... parameters) {
        try {
            MongoCursor<Evento> all = this.mongoCollection. find(query, parameters).as(Evento.class);

            List<Evento> result = this.copyToList(all);
            all.close();

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Evento> findOrderByDateDesc(String query, Object... parameters) {
        try {
            // sort: 1 for asc and -1 for desc
            MongoCursor<Evento> all = this.mongoCollection.find(query, parameters).sort("{ fecha: -1 }").as(Evento.class);

            List<Evento> result = this.copyToList(all);
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

    
	public List<Evento> getByLugar(String lugar) {
		return findOrderByDateDesc("{ nombreLugar: # }", lugar);
	}

	public List<Evento> getByLugares(List<String> lugares) {
		return findOrderByDateDesc("{ $or: [{ nombreLugar: { $in: # }}] }", lugares) ;
	}

	public List<Evento> getByPersonaje(String nombrePj) {
		return findOrderByDateDesc( "{ $or: [{ nombrePJ: # }, { nombreContrincante: # }] }", nombrePj, nombrePj )  ;
	}

}