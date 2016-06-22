package org.seckill.dao;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class TestSuccessKillDao {
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKillDao successKillDao;	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testInsertSuccessKill() {
		long seckillId = 1l;
		long userPhone = 13476131466l;
		int insertSuccessKill = successKillDao.insertSuccessKill(seckillId, userPhone);
		System.out.println(insertSuccessKill);
	}
	
	@Test
	public void testQueryByIdWithSeckill() {
		long seckillId = 1l;
		long userPhone = 13476131466l;
		SuccessKill successKill = successKillDao.queryByIdWithSeckill(seckillId, userPhone);
		System.out.println(successKill);
	}

}
