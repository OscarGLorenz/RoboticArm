package net.ddns.ogl.RoboticArm;

import org.gamecontrolplus.ControlHat;

public enum Direction {
	UP, RIGHT, LEFT, DOWN;
	static boolean pressedDirection(ControlHat hat, Direction dir) {
		switch(dir) {
		case DOWN:
			return hat.down();
		case LEFT:
			return hat.left();
		case RIGHT:
			return hat.right();
		case UP:
			return hat.up();
		}
		return false;
	}
}
