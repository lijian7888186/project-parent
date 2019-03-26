package com.project.util.redis;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;

public class RedisService {

    /**
     * 通过key删除（字节）
     * @param key
     */
    public void del(byte [] key){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		jedis.del(key);
		} finally {
			connection.close();
		}
    }
    /**
     * 通过key删除
     * @param key
     */
    public void del(String key){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
			jedis.del(key);
		} finally {
			connection.close();
		}
//        this.getJedis().del(key);
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte [] key,byte [] value,int liveTime){
    	
        this.set(key, value);
        this.expire(key, liveTime);
    }
    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key,String value,int liveTime){
    	this.set(key, value);
    	this.expire(key, liveTime);
    }
    /**
     * 添加key value
     * @param key
     * @param value
     */
    public void set(String key,String value){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		jedis.set(key, value);
		} finally {
			connection.close();
		}
//        this.getJedis().set(key, value);
    }
    /**添加key value (字节)(序列化)
     * @param key
     * @param value
     */
    public void set(byte [] key,byte [] value){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		jedis.set(key, value);
		} finally {
			connection.close();
		}
//        this.getJedis().set(key, value);
    }
    
    /**
     * 获取redis value (String)
     * @param key
     * @return
     */
    public String get(String key){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.get(key);
		} finally {
			connection.close();
		}
//        String value = this.getJedis().get(key);
//        return value;
    }
    /**
     * 获取redis value (byte [] )(反序列化)
     * @param key
     * @return
     */
    public byte[] get(byte [] key){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.get(key);
		} finally {
			connection.close();
		}
//        return this.getJedis().get(key);
    }

    /**
     * 通过正则匹配keys
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.keys(pattern);
		} finally {
			connection.close();
		}
//        return this.getJedis().keys(pattern);
    }
    /**
     * 通过正则匹配keys
     * @param pattern
     * @return
     */
    public Set<byte[]> keys(byte[] pattern){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.keys(pattern);
		} finally {
			connection.close();
		}
//    	return this.getJedis().keys(pattern);
    }

    /**
     * 检查key是否已经存在
     * @param key
     * @return
     */
    public boolean exists(String key){
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.exists(key);
		} finally {
			connection.close();
		}
//        return this.getJedis().exists(key);
    }
    
    /**
     * 检查key的存活时间
     * @param key
     * @return
     */
    public long ttl(String key) {
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.ttl(key);
		} finally {
			connection.close();
		}
//    	return this.getJedis().ttl(key);
    }
    
    
    /**
     * 检查key的存活时间
     * @param key
     * @return
     */
    public long ttl(byte[] key) {
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.ttl(key);
		} finally {
			connection.close();
		}
//    	return this.getJedis().ttl(key);
    }
    /**
     * 重新设置时间
     * @param key
     */
    public void expire(byte[] key , int seconds) {
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		jedis.expire(key, seconds);
		} finally {
			connection.close();
		}
//    	this.getJedis().expire(key, seconds);
    }
    
    /**
     * 重新设置时间
     * @param key
     */
    public void expire(String key , int seconds) {
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		jedis.expire(key, seconds);
		} finally {
			connection.close();
		}
//    	this.getJedis().expire(key, seconds);
    }
    
    /**
     * 向list第一个索引插入元素
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public long lpush(String key ,String value) {
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.lpush(key, value);
		} catch (Exception e) {
			return 0;
		} finally {
			connection.close();
		}
//    	try {
//    		return this.getJedis().lpush(key, value);
//		} catch (Exception e) {
//			return 0;
//		}
    }
    /**
     * 向list最后一个索引插入元素
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public long rpush(String key,String value) {
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.rpush(key, value);
		} catch (Exception e) {
			return 0;
		} finally {
			connection.close();
		}
//    	try {
//    		Jedis jedis = this.getJedis();
//    		return jedis.rpush(key, value);
//		} catch (Exception e) {
//			return 0;
//		}
    }
    
    /**
     * 返回并删除list中的第一个元素
     * @param key
     * @return
     */
    public String lpop(String key) {
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.lpop(key);
		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}
//    	try {
//    		return this.getJedis().lpop(key);
//		} catch (Exception e) {
//			return null;
//		}
    }
    
    /**
     * 返回并删除list中最后一个元素
     * @param key
     * @return
     */
    public String rpop(String key ) {
    	JedisConnection connection = getConnection();
    	try {
    		Jedis jedis = getJedis(connection);
    		return jedis.rpop(key);
		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}
//    	try {
//    		Jedis jedis = this.getJedis();
//    		String value = jedis.rpop(key);
//    		return value;
//		} catch (Exception e) {
//			return null;
//		}
    }
    
    /**
     * 返回list
     * @param key
     * @return
     */
    public List<String> list(String key) {
    	JedisConnection connection = getConnection();
    	try {
    		List<String> lrange = this.getJedis(connection).lrange(key, 0, -1);
    		if (lrange.size() == 0) {
				return null;
			}
    		return lrange;
		} catch (Exception e) {
			return null;
		} finally {
			connection.close();
		}
    }
    
    /**
     * 删除list中的value
     * @param key
     * @param count
     * @param value
     * @return
     */
    public boolean lrem(String key, int count, String value) {
    	JedisConnection connection = getConnection();
    	try {
    		Long flag = this.getJedis(connection).lrem(key, count, value);
    		return flag > 0;
    	} catch (Exception e) {
    		return false;
    	} finally {
    		connection.close();
    	}
    }
    
    /**
     * 列表长度
     * @param key
     * @return
     */
    public long listLength(String key) {
    	JedisConnection connection = getConnection();
    	try {
			Long llen = this.getJedis(connection).llen(key);
			return llen;
		} catch (Exception e) {
			return 0;
		}finally {
			connection.close();
		}
    }
    
    /**
     * set集合插入
     * @param key
     * @param value
     * @return
     */
    public long sadd(String key , String value) {
    	JedisConnection connection = getConnection();
    	try {
    		Long sadd = this.getJedis(connection).sadd(key, value);
    		return sadd;
		} finally {
			connection.close();
		}
    }
    
    /**
     * set集合中是否包含此key
     * @param key
     * @param value
     * @return
     */
    public boolean sismember(String key , String value) {
    	JedisConnection connection = getConnection();
    	try {
    		Boolean sismember = this.getJedis(connection).sismember(key, value);
    		return sismember;
		} finally {
			connection.close();
		}
    }
    
    /**
     * 
     * @date	2018年7月6日 下午7:53:16
     * @desc	移除指定set中的某个值
     * @param key	set的key
     * @param value	set中的值
     * @return
     */
    public boolean srem(String key, String value) {
    	JedisConnection connection = getConnection();
    	try {
    		Long srem = this.getJedis(connection).srem(key, value);
    		return srem > 0;
		} finally {
			connection.close();
		}
    }
    
    /**
     * 检查redis是否含有此key
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).ttl(key) != -2;
		} finally {
			connection.close();
		}
    }
    
    /**
     * 向redis中插入对象
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hset(String key , String field , String value) {
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).hset(key, field, value);
		} finally {
			connection.close();
		}
    }
    
    /**
     * 向redis中插入对象
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hset(byte[] key , byte[] field , byte[] value) {
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).hset(key, field, value);
		} finally {
			connection.close();
		}
    }
    
    /**
     * 删除对象属性
     * @param key
     * @param field
     * @return
     */
    public long hdel(String key , String field) {
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).hdel(key, field);
		} finally {
			connection.close();
		}
	}
    
    /**
     * 删除对象属性
     * @param key
     * @param field
     * @return
     */
    public long hdel(byte[] key , byte[] field) {
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).hdel(key, field);
		} finally {
			connection.close();
		}
	}
    
    /**
     * 向redis中插入对象,并设置存活时间
     * @param key
     * @param field
     * @param value
     * @param liveTime
     * @return
     */
    public long hset(String key , String field , String value ,int liveTime) {
    	JedisConnection connection = getConnection();
    	try {
    		Long hset = this.getJedis(connection).hset(key, field, value);
    		this.expire(key, liveTime);
    		return hset; 
		} finally {
			connection.close();
		}
    }
    
    /**
     * 向redis中插入对象,并设置存活时间
     * @param key
     * @param field
     * @param value
     * @param liveTime
     * @return
     */
    public long hset(byte[] key , byte[] field , byte[] value ,int liveTime) {
    	JedisConnection connection = getConnection();
    	try {
    		Long hset = this.getJedis(connection).hset(key, field, value);
    		this.expire(key, liveTime);
    		return hset; 
		} finally {
			connection.close();
		}
    }
    
    /**
     * 从redis中取出key的字段值
     * @param key
     * @param field
     * @return
     */
    public String hget(String key , String field) {
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).hget(key, field);
		} finally {
			connection.close();
		}
    }
    
    /**
     * 从redis中取出key的字段值
     * @param key
     * @param field
     * @return
     */
    public byte[] hget(byte[] key , byte[] field) {
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).hget(key, field);
		} finally {
			connection.close();
		}
    }
    
    /**
     * 清空redis 所有数据
     * @return
     */
    public String flushDB(){
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).flushDB();
		} finally {
			connection.close();
		}
    }
    /**
     * 查看redis里有多少数据
     */
    public long dbSize(){
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).dbSize();
		} finally {
			connection.close();
		}
    }
    
    /**
     * 关闭
     */
    public String quit(){
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).quit();
		} finally {
			connection.close();
		}
    }
    /**
     * 检查是否连接成功
     * @return
     */
    public String ping(){
    	JedisConnection connection = getConnection();
    	try {
    		return this.getJedis(connection).ping();
		} finally {
			connection.close();
		}
    }
    
    /**
     * 获取一个jedis 客户端
     * @return
     */
    private Jedis getJedis(){
        if(jedis == null){
        	return jedisConnectionFactory.getShardInfo().createResource();
        }
        return jedis;
    }
    
    private Jedis getJedis(JedisConnection connection) {
    	return connection.getNativeConnection();
    }
    
    private JedisConnection getConnection() {
    	return (JedisConnection) jedisConnectionFactory.getConnection();
    }
    
    private RedisService (){
    	
    }
    //操作redis客户端
    private static Jedis jedis;
    @Autowired
    @Qualifier("jedisConnectionFactory")
    private JedisConnectionFactory jedisConnectionFactory;
}