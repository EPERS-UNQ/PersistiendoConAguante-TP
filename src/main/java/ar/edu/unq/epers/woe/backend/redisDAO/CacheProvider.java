package ar.edu.unq.epers.woe.backend.redisDAO;

import redis.clients.jedis.Jedis;

public class CacheProvider {

    private static CacheProvider INSTANCE;
    private Jedis jedis;

    public synchronized static CacheProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CacheProvider();
        }
        return INSTANCE;
    }

    private CacheProvider() {
        this.jedis = new Jedis("localhost");
    }

    public Jedis getJedis(){
        return jedis;
    }

}