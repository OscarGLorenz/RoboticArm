package net.ddns.ogl.RoboticArm.Event;

public class PressEvent implements Event {
	private boolean pressed;
	private int num;
	
	public PressEvent(boolean pressed, int num) {
		this.pressed = pressed;
		this.num = num;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public int getnum() {
		return num;
	}

}
