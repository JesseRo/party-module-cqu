package hg.party.server;

import hg.party.server.login.UserService;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component(immediate = true, service = CacheCore.class)
public class CacheCore {
    Logger logger = Logger.getLogger(CacheCore.class);

    //可用连接实例的最大数目，默认为8；
    //如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
    private static Integer MAX_TOTAL = 200;
    //控制一个pool最多有多少个状态为idle(空闲)的jedis实例，默认值是8
    private static Integer MAX_IDLE = 10;
    //等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。
    //如果超过等待时间，则直接抛出JedisConnectionException
    private static Integer MAX_WAIT_MILLIS = 10000;
    private static Integer TIMEOUT = 10000;
    //在borrow(用)一个jedis实例时，是否提前进行validate(验证)操作；
    //如果为true，则得到的jedis实例均是可用的
    private static Boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;

    /**
     * 静态块，初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
        /*注意：
            在高版本的jedis jar包，比如本版本2.9.0，JedisPoolConfig没有setMaxActive和setMaxWait属性了
            这是因为高版本中官方废弃了此方法，用以下两个属性替换。
            maxActive  ==>  maxTotal
            maxWait==>  maxWaitMillis
         */
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setTestWhileIdle(true);
            jedisPool = new JedisPool(config, "localhost", 6379, TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public Jedis getJedis() {
        try {
            if (jedisPool != null) {
                logger.info("active redis connection number:" + jedisPool.getNumActive());
                logger.info("idle redis connection number:" + jedisPool.getNumIdle());
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public long incr(String key, int second) {
        Jedis jedis = getJedis();
        long v = jedis.incr(key);
        jedis.expire(key, second);
        jedis.close();
        return v;
    }
}
