package com.gamephone.common.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.type.TypeReference;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.gamephone.common.type.OP;
import com.gamephone.common.util.JsonUtil;

public class ShardedRedisService {

    private ThreadLocal<OP> cop=new ThreadLocal<OP>();

    private ShardedJedisPool readShardedJedisPool;

    private ShardedJedisPool writeShardedJedisPool;

    public static final String NOT_FOUND="nil";

    public void setReadShardedJedisPool(ShardedJedisPool readShardedJedisPool) {
        this.readShardedJedisPool=readShardedJedisPool;
    }

    public void setWriteShardedJedisPool(ShardedJedisPool writeShardedJedisPool) {
        this.writeShardedJedisPool=writeShardedJedisPool;
    }

    public void releaseJedisInstance(ShardedJedis jedis) {
        if(cop.get() == OP.READ) {
            readShardedJedisPool.returnResource(jedis);
        } else {
            writeShardedJedisPool.returnResource(jedis);
        }
    }

    public ShardedJedis getJedis() throws TimeoutException {
        if(cop.get() == OP.READ) {
            return readShardedJedisPool.getResource();
        } else {
            return writeShardedJedisPool.getResource();
        }
    }

    /**
     * 得到指导key的值列表。此方法返回一个指定类型的数据列表。
     * @param key Key
     * @param clazz TypeReference， 指导返回的类型。
     * @return
     * @throws TimeoutException
     */
    public <T> T get(String key, TypeReference<T> clazz) throws TimeoutException {
        String json=null;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            json=jedis.get(key);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
        if(json == null) {
            return null;
        } else {
            return JsonUtil.Json2Object(json, clazz);
        }
    }

    public <T> T get(String key, Class<T> clazz) throws TimeoutException {
        String json=null;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            json=jedis.get(key);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
        if(json == null) {
            return null;
        } else {
            return JsonUtil.Json2Object(json, clazz);
        }
    }

    public void set(String key, Object o) throws TimeoutException {
        String s=JsonUtil.Object2Json(o);
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.set(key, s);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            releaseJedisInstance(jedis);
        }
    }

    public Long del(String... keys) throws TimeoutException {
        Long result=0L;
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            for(String key: keys) {
                jedis.del(key);
            }
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public void del(String key) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.del(key);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            releaseJedisInstance(jedis);
        }
    }

    public void setAndExpire(String key, Object o, int expire) throws TimeoutException {
        String s=JsonUtil.Object2Json(o);
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.set(key, s);
            jedis.expire(key, expire);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            releaseJedisInstance(jedis);
        }
    }

    public Long incr(String key) throws TimeoutException {
        ShardedJedis jedis = null;
        cop.set(OP.WRITE);
        try {
            jedis = getJedis();
            return jedis.incr(key);
        } catch (TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null){
                releaseJedisInstance(jedis);
            }
        }
    }
    
    public Long decr(String key) throws TimeoutException {
        ShardedJedis jedis = null;
        cop.set(OP.WRITE);
        try {
            jedis = getJedis();
            return jedis.decr(key);
        } catch (TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null){
                releaseJedisInstance(jedis);
            }
        }
    }
    
    /**
     * Return the number of items in a hash.
     * <p>
     * <b>Time complexity:</b> O(1)
     * @param key
     * @return The number of entries (fields) contained in the hash stored at key. If the specified key does not exist, 0 is
     *         returned assuming an empty hash.
     */
    public Long hlen(String key) throws TimeoutException {
        Long len=0L;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            len=jedis.hlen(key);
        } finally {
            releaseJedisInstance(jedis);
        }
        return len;
    }

    /**
     * Set the specified hash field to the specified value.
     * <p>
     * If key does not exist, a new key holding a hash is created.
     * <p>
     * <b>Time complexity:</b> O(1)
     * @param key
     * @param field
     * @param value
     * @return If the field already exists, and the HSET just produced an update of the value, 0 is returned, otherwise if a new
     *         field is created 1 is returned.
     */
    public Long hset(String key, String field, String value) throws TimeoutException {
        Long result=0L;
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            result=jedis.hset(key, field, value);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * If key holds a hash, retrieve the value associated to the specified field.
     * <p>
     * If the field is not found or the key does not exist, a special 'nil' value is returned.
     * <p>
     * <b>Time complexity:</b> O(1)
     * @param key
     * @param field
     * @return if not find, null is returned.
     */
    public String hget(String key, String field) throws TimeoutException {
        String result=null;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            String str=jedis.hget(key, field);
            if(!NOT_FOUND.equals(str)) {
                result=str;
            }
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Set the respective fields to the respective values. HMSET replaces old values with new values.
     * <p>
     * If key does not exist, a new key holding a hash is created.
     * <p>
     * <b>Time complexity:</b> O(N) (with N being the number of fields)
     * @param key
     * @param hash
     * @return Always OK because HMSET can't fail
     */
    public String hmset(String key, Map<String, String> hash) throws TimeoutException {
        String result=null;
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            result=jedis.hmset(key, hash);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Retrieve the values associated to the specified fields.
     * <p>
     * If some of the specified fields do not exist, nil values are returned. Non existing keys are considered like empty hashes.
     * <p>
     * <b>Time complexity:</b> O(N) (with N being the number of fields)
     * @param key
     * @param fields
     * @return Multi Bulk Reply specifically a list of all the values associated with the specified fields, in the same order of the
     *         request. If fields is not exists, will return null.
     * @throws TimeoutException
     */
    public List<String> hmget(final String key, final String... fields) throws TimeoutException {
        List<String> result=null;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            result=jedis.hmget(key, fields);
            if(result != null && !result.isEmpty()) {
                result.remove(NOT_FOUND);
            }
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Return all the fields and associated values in a hash.
     * <p>
     * <b>Time complexity:</b> O(N), where N is the total number of entries
     * @param key
     * @return All the fields and values contained into a hash.
     */
    public Map<String, String> hgetAll(String key) throws TimeoutException {
        Map<String, String> result=new HashMap<String, String>();
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            result=jedis.hgetAll(key);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list stored at key. If the key does not exist an empty list
     * is created just before the append operation. If the key exists but is not a List an error is returned.
     * <p>
     * Time complexity: O(1)
     * @see ShardedJedis#lpush(String, String)
     * @param key
     * @param string
     * @return Integer reply, specifically, the number of elements inside the list after the push operation.
     */
    public Long rpush(String key, String string) throws TimeoutException {
        Long result=0L;
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            result=jedis.rpush(key, string);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list stored at key. If the key does not exist an empty list
     * is created just before the append operation. If the key exists but is not a List an error is returned.
     * <p>
     * Time complexity: O(1)
     * @see ShardedJedis#rpush(String, String)
     * @param key
     * @param string
     * @return Integer reply, specifically, the number of elements inside the list after the push operation.
     */
    public Long lpush(String key, String string) throws TimeoutException {
        Long result=0L;
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            result=jedis.lpush(key, string);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Add the Object to the head (LPUSH) or tail (RPUSH) of the list stored at key. If the key does not exist an empty list is
     * created just before the append operation. If the key exists but is not a List an error is returned.
     * <p>
     * Time complexity: O(1)
     * @see ShardedJedis#rpush(String, String)
     * @param key
     * @param 0 Object
     * @return Integer reply, specifically, the number of elements inside the list after the push operation.
     */
    public Long lpush(String key, Object o) throws TimeoutException {
        Long result=0L;
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            String string=JsonUtil.Object2Json(o);

            result=jedis.lpush(key, string);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Add the Object list to the head (LPUSH) or tail (RPUSH) of the list stored at key. If the key does not exist an empty list is
     * created just before the append operation. If the key exists but is not a List an error is returned.
     * <p>
     * Time complexity: O(1)
     * @see ShardedJedis#rpush(String, String)
     * @param key
     * @param oList list of object
     */
    public <T> void lpush(String key, List<T> oList) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            T o=null;
            for(int i=oList.size() - 1; i >= 0; i--) {
                o=oList.get(i);
                String string=JsonUtil.Object2Json(o);

                jedis.lpush(key, string);
            }
        } finally {
            releaseJedisInstance(jedis);
        }
    }

    /**
     * Return the length of the list stored at the specified key. If the key does not exist zero is returned (the same behaviour as
     * for empty lists). If the value stored at key is not a list an error is returned.
     * <p>
     * Time complexity: O(1)
     * @param key
     * @return The length of the list.
     */
    public Long llen(String key) throws TimeoutException {
        Long result=0L;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            result=jedis.llen(key);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Return the specified elements of the list stored at the specified key. Start and end are zero-based indexes. 0 is the first
     * element of the list (the list head), 1 the next element and so on.
     * <p>
     * For example LRANGE foobar 0 2 will return the first three elements of the list.
     * <p>
     * start and end can also be negative numbers indicating offsets from the end of the list. For example -1 is the last element of
     * the list, -2 the penultimate element and so on.
     * <p>
     * <b>Consistency with range functions in various programming languages</b>
     * <p>
     * Note that if you have a list of numbers from 0 to 100, LRANGE 0 10 will return 11 elements, that is, rightmost item is
     * included. This may or may not be consistent with behavior of range-related functions in your programming language of choice
     * (think Ruby's Range.new, Array#slice or Python's range() function).
     * <p>
     * LRANGE behavior is consistent with one of Tcl.
     * <p>
     * <b>Out-of-range indexes</b>
     * <p>
     * Indexes out of range will not produce an error: if start is over the end of the list, or start > end, an empty list is
     * returned. If end is over the end of the list Redis will threat it just like the last element of the list.
     * <p>
     * Time complexity: O(start+n) (with n being the length of the range and start being the start offset)
     * @param key
     * @param start
     * @param end
     * @return Multi bulk reply, specifically a list of elements in the specified range.
     */
    public List<String> lrange(String key, int start, int end) throws TimeoutException {
        List<String> result=new ArrayList<String>();
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            result=jedis.lrange(key, start, end);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public <T> List<T> lrange(String key, Class<T> clazz, int start, int end) throws TimeoutException {
        List<T> result=new ArrayList<T>();
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            List<String> strings=jedis.lrange(key, start, end);

            if(strings != null && !strings.isEmpty()) {
                for(final String string: strings) {
                    result.add(JsonUtil.Json2Object(string, clazz));
                }
            }
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Set a timeout on the specified key. After the timeout the key will be automatically deleted by the server. A key with an
     * associated timeout is said to be volatile in Redis terminology.
     * <p>
     * Voltile keys are stored on disk like the other keys, the timeout is persistent too like all the other aspects of the dataset.
     * Saving a dataset containing expires and stopping the server does not stop the flow of time as Redis stores on disk the time
     * when the key will no longer be available as Unix time, and not the remaining seconds.
     * <p>
     * Since Redis 2.1.3 you can update the value of the timeout of a key already having an expire set. It is also possible to undo
     * the expire at all turning the key into a normal key using the {@link #persist(String) PERSIST} command.
     * <p>
     * Time complexity: O(1)
     * @see <ahref="http://code.google.com/p/redis/wiki/ExpireCommand">ExpireCommand</a>
     * @param key
     * @param seconds
     * @return Integer reply, specifically: 1: the timeout was set. 0: the timeout was not set since the key already has an
     *         associated timeout (this may happen only in Redis versions < 2.1.3, Redis >= 2.1.3 will happily update the timeout),
     *         or the key does not exist.
     */
    public Long expire(String key, int seconds) throws TimeoutException {
        Long result=0L;
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            result=jedis.expire(key, seconds);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Test if the specified key exists. The command returns "0" if the key exists, otherwise "1" is returned. Note that even keys
     * set with an empty string as value will return "1". Time complexity: O(1)
     * @param key
     * @return Boolean reply
     */
    public Boolean exists(String key) throws TimeoutException {
        Boolean result=Boolean.FALSE;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            result=jedis.exists(key);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Remove the specified field from an hash stored at key.
     * <p>
     * <b>Time complexity:</b> O(1)
     * @param key
     * @param field
     * @return If the field was present in the hash it is deleted and 1 is returned, otherwise 0 is returned and no operation is
     *         performed.
     */
    public Long hdel(String key, String field) throws TimeoutException {
        Long result=0L;
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            result=jedis.hdel(key, field);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Get the value of the specified key. If the key does not exist the special value 'nil' is returned. If the value stored at key
     * is not a string an error is returned because GET can only handle string values.
     * <p>
     * Time complexity: O(1)
     * @param key
     * @return if not find, null id returned.
     */
    public String get(String key) throws TimeoutException {
        String result=null;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            String str=jedis.get(key);
            if(!NOT_FOUND.equals(str)) {
                result=str;
            }
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * Get the value of the specified key. If the key does not exist the special value 'nil' is returned. If the value stored at key
     * is not a string an error is returned because GET can only handle string values.
     * <p>
     * Time complexity: O(1)
     * @param key
     * @return Bulk reply
     */
    public String set(String key, String value) throws TimeoutException {
        String result="";
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            result=jedis.set(key, value);
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public <T> void rpush(String key, List<T> oList) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            for(Object o: oList) {
                String s=JsonUtil.Object2Json(o);
                jedis.rpush(key, s);
            }
        } catch(TimeoutException e) {
            throw e;
        } finally {
            releaseJedisInstance(jedis);
        }
    }

    public <T> T lrange(String key, int start, int end, TypeReference<T> clazz) throws TimeoutException {
        List<String> jsonList=null;
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            jsonList=jedis.lrange(key, start, end);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
        if(jsonList == null || jsonList.isEmpty()) {
            return null;
        } else {
            return JsonUtil.Json2Object(jsonList.toString(), clazz);
        }

    }

    public <T> List<T> lpop(String key, Class<T> clazz, int size) throws TimeoutException {
        String json=null;
        ShardedJedis jedis=null;
        int count=0;
        List<T> results=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            long exists=jedis.llen(key);
            count=(int)(exists > size ? size : exists);

            if(count > 0) {
                results=new ArrayList<T>();
            }

            for(int i=0; i < count; i++) {
                json=jedis.lpop(key);

                if(json != null) {
                    results.add(JsonUtil.Json2Object(json, clazz));
                }
            }
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }

        return results;
    }

    public String lpop(String key) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            return jedis.lpop(key);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public String lindex(String key, int index) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            return jedis.lindex(key, index);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public String lset(String key, int index, String value) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            return jedis.lset(key, index, value);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public Long lrem(String key, int count, String value) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            return jedis.lrem(key, count, value);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public void sadd(String key, String member) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.sadd(key, member);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public void saddAll(String key, Set<String> members) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            for(final String member: members) {
                jedis.sadd(key, member);
            }
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public Set<String> smembers(String key) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            return jedis.smembers(key);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public Long scard(String key) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            return jedis.scard(key);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public void srem(String key, String member) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.srem(key, member);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public void sremMany(String key, Set<String> members) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            for(final String member: members) {
                jedis.srem(key, member);
            }
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public Long ttl(String key) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            return jedis.ttl(key);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public Long zcard(String key) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            return jedis.zcard(key);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public Set<String> zrange(String key, int start, int end) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            return jedis.zrange(key, start, end);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public Set<String> zrevrange(String key, int start, int end) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.READ);
        try {
            jedis=getJedis();
            return jedis.zrevrange(key, start, end);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public void zrem(String key, String member) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.zrem(key, member);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public void zadd(String key, Long score, String member) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.zadd(key, score, member);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public void zremrangeByScore(String key, Double start, Double end) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.zremrangeByScore(key, start, end);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }

    public void zremrangeByRank(String key, int start, int end) throws TimeoutException {
        ShardedJedis jedis=null;
        cop.set(OP.WRITE);
        try {
            jedis=getJedis();
            jedis.zremrangeByRank(key, start, end);
        } catch(TimeoutException e) {
            throw e;
        } finally {
            if(jedis != null) {
                releaseJedisInstance(jedis);
            }
        }
    }
    
}
