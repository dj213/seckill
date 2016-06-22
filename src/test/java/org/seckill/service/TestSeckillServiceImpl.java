package org.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class TestSeckillServiceImpl {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckillList = seckillService.getSeckillList();
		System.out.println(seckillList);
		logger.info("seckillList={}",seckillList);
	}

	@Test
	public void testGetById() {
		long seckillId = 1l;
		Seckill seckill = seckillService.getById(seckillId);
		System.out.println(seckill);
		logger.info("seckill={}",seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		long seckillId = 3l;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);	
		System.out.println(exposer);
		logger.info("exposer={}",exposer);
	}

	@Test
	public void testExcuteSeckill() {
		long seckillId = 2l;
		long userPhone = 12311112222l;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);	
		if (exposer.isExposed()) {
			logger.info("exposer={}",exposer);
			SeckillExecution excuteSeckill = null;
			try {
				excuteSeckill = seckillService.excuteSeckill(seckillId, userPhone, exposer.getMd5());
			} catch (RepeatKillException e) {
				logger.warn(e.getMessage());
			} catch (SeckillClosedException e) {
				logger.warn(e.getMessage());
			} catch (SeckillException e) {
				logger.warn(e.getMessage());
			}
			logger.info("excuteSeckill={}",excuteSeckill);
			System.out.println(excuteSeckill);
		}else{
			logger.warn("exposer={}",exposer);
		}
		
	}

}
