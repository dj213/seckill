package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class TestSeckillDao {
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void test() {
		System.out.println(DispatcherServlet.class);
		System.out.println(ViewResolver.class);
		System.out.println(View.class);
	}
	
	@Test
	public void testReduceNumber() {
		long seckillId = 3l;
		int reduceNumber = seckillDao.reduceNumber(seckillId, new Date());
		System.out.println(reduceNumber);
	}
	
	@Test
	public void testQueryById() {
		long seckillId = 1l;
		Seckill seckill = seckillDao.queryById(seckillId);
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() {
		int offset = 0;
		int limit = 5;
		List<Seckill> list = seckillDao.queryAll(offset, limit);
		System.out.println(list);
	}

}
