package org.seckill.enums;

public enum SeckillStateEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT_KILL(-1,"重复秒杀"),
	INNER_ERROR(-2,"内部错误"),
	DATA_REWRITE(-3,"数据篡改");
	private int state;
	private String stateInfo;
	private SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public SeckillStateEnum stateOf(int state){
		for (SeckillStateEnum seckillStateEnum : SeckillStateEnum.values()) {
			if (seckillStateEnum.getState() == state) {
				return seckillStateEnum;
			}
		}
		return null;
	}
}
