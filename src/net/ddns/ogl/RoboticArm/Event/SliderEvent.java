package net.ddns.ogl.RoboticArm.Event;

public class SliderEvent implements Event {
	private float value;
	private int num;

	public SliderEvent(float value, int num) {
		this.value = value;
		this.num = num;
	}
	
	public float getValue() {
		return value;
	}

	public int getnum() {
		return num;
	}
}
