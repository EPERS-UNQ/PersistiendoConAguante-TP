package ar.edu.unq.epers.woe.backend.redisDAO;

import java.util.List;

import ar.edu.unq.epers.woe.backend.redisDAO.JsonSerializer;
import ar.edu.unq.epers.woe.backend.model.evento.Evento;
import redis.clients.jedis.Jedis;

public class RedisDAO {

    private Jedis jedis;

    public RedisDAO() {
        this.jedis = CacheProvider.getInstance().getJedis();
    }

    public void clear() {
        jedis.flushAll();
    }

    public void put(String clave, String valor) {
        jedis.set(clave, valor);
    }
    
    public void putList(String clave, List<Evento> valor) {
    	jedis.set(clave, JsonSerializer.toJson(valor));
    }

    public String get(String clave) {
        return this.jedis.get(clave);
    }

    public List<Evento> getList(String clave){
        String value = this.jedis.get(clave);
        return JsonSerializer.fromJsonList(value, Evento.class);
    }
    
    public int size() {
        return this.jedis.keys("*").size();
    }
    
    public boolean existsKey(String key) {
    	return jedis.exists(key);
    }

    public void remove(String clave) { this.jedis.del(clave); }

}
