package org.seckill.dto;

import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStateEnum;

public class SeckillExecution {
	private long seckillId;
	private long state;
	private String stateInfo;
	private SuccessKill successKill;

	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum,
			SuccessKill successKill) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.successKill = successKill;
	}

	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public SeckillExecution() {
		super();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKill getSuccessKill() {
		return successKill;
	}

	public void setSuccessKill(SuccessKill successKill) {
		this.successKill = successKill;
	}

}
