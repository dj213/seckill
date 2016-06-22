package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKill;

public interface SuccessKillDao {

	int insertSuccessKill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
	
	SuccessKill queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
}
