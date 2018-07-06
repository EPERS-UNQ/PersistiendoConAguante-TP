package ar.edu.unq.epers.woe.backend.redisDAO;

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

    public String get(String clave) {
        return this.jedis.get(clave);
    }

    public int size() {
        return this.jedis.keys("*").size();
    }
    
    public boolean existsKey(String key) {
    	return jedis.exists(key);
    }

}
