package hg.party.server;

import org.osgi.service.component.annotations.Component;
import redis.clients.jedis.Jedis;

@Component(immediate = true, service = CacheCore.class)
public class CacheCore {
    private Jedis jedis = new Jedis("localhost", 6379);

    public Jedis getJedis() {
        return jedis;
    }
}
