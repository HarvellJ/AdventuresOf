package com.adventuresof.game.combat;

public enum CoolDownEnum {
	VeryShort(500), // for basic abilities - e.g. first ability
	Short(1000), // for basic abilities - e.g. first ability
	Medium(2000), 
	Long(5000), 
	VeryLong(20000); // for big abilities - e.g. the character's ultimate
	
	private long coolDownDuration;
	
	CoolDownEnum(long coolDownDuration){
		this.coolDownDuration = coolDownDuration;
	}

	public long getCoolDownDuration() {
		return coolDownDuration;
	}
}
