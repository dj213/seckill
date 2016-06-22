package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;

public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 * 
	 * @return
	 */
	List<Seckill> getSeckillList();

	Seckill getById(long seckillId);

	Exposer exportSeckillUrl(long seckillId);

	SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException,
			SeckillClosedException;

}
