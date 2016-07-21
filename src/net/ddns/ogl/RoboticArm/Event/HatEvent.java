package net.ddns.ogl.RoboticArm.Event;

import net.ddns.ogl.RoboticArm.Direction;

public class HatEvent implements Event {
	private boolean pressed;
	private int num;
	private Direction dir;

	public HatEvent(boolean pressed, int num, Direction dir) {
		this.pressed = pressed;
		this.num = num;
		this.dir = dir;
	}

	public boolean isPressed() {
		return pressed;
	}

	public int getnum() {
		return num;
	}

	public Direction getDir() {
		return dir;
	}

}
