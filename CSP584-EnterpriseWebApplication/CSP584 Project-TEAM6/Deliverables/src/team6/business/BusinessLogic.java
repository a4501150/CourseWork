package team6.business;

import java.time.LocalTime;

public enum BusinessLogic {
	INSTANCE;
	
	private final LocalTime CHECK_IN_TIME = LocalTime.of(14, 0);
	private final LocalTime CHECK_OUT_TIME = LocalTime.of(12, 0);
	
	public LocalTime getCheckInTime() {
		return CHECK_IN_TIME;
	}
	public LocalTime getCheckOutTime() {
		return CHECK_OUT_TIME;
	}
	
}
