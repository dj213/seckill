package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String salt = "sfdjlkasdgjl;tjotodsankaestjop[923-59-s;aldkl;34";

	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKillDao successKillDao;

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, Integer.MAX_VALUE);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if (null == seckill) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date now = new Date();
		if (now.getTime() < startTime.getTime()
				|| now.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, now.getTime(),
					startTime.getTime(), endTime.getTime());
		}
		String md5 = getMd5(seckillId); 
		return new Exposer(true, md5, seckillId);
	}

	private String getMd5(long seckillId) {
		String base = seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	@Transactional
	public SeckillExecution excuteSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillClosedException {
		if (md5 == null || !md5.equals(getMd5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		Date now = new Date();
		try {
			int insertCount = successKillDao.insertSuccessKill(seckillId,
					userPhone);
			if (insertCount <= 0) {
				throw new RepeatKillException("重复秒杀");
			} else {
				int reduceCount = seckillDao.reduceNumber(seckillId, now);
				if (reduceCount <= 0) {
					throw new SeckillClosedException("秒杀结束");
				} else {
					SuccessKill successKill = successKillDao
							.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,
							SeckillStateEnum.SUCCESS, successKill);
				}
			}
		} catch (SeckillClosedException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			throw new SeckillException("inner error", e);
		}
	}

}
