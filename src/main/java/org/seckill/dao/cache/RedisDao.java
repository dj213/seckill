package org.seckill.dao.cache;

import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class RedisDao {
	private final JedisPool jedisPool;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String host, int port){
		jedisPool = new JedisPool(host, port);
	}
	
	public Seckill getSeckill(long seckillId){
		try {
			Jedis jedis = jedisPool.getResource();
			
			try {
				String key = "Seckill:"+seckillId;
				byte[] bytes = jedis.get(key.getBytes());
				Seckill seckill = schema.newMessage();
				if (null != bytes) {
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
			} finally{
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill){
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "Seckill:"+seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout = 60*60;
				String result = jedis.setex(key.getBytes(), timeout,  bytes);
				return result;
			} finally{
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}	
		return null;
	}
	
	
}
