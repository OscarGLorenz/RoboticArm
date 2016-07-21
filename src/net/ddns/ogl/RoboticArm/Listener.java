package net.ddns.ogl.RoboticArm;

import org.gamecontrolplus.ControlButton;
import org.gamecontrolplus.ControlHat;
import org.gamecontrolplus.ControlSlider;

import net.ddns.ogl.RoboticArm.Event.Event;
import net.ddns.ogl.RoboticArm.Event.HatEvent;
import net.ddns.ogl.RoboticArm.Event.PressEvent;
import net.ddns.ogl.RoboticArm.Event.SliderEvent;

public class Listener {
	Listener(ControlButton device, int num) {
		this.device = device;
		this.status = false;
		this.num = num;
	}

	Listener(ControlHat device, Direction dir, int num) {
		this.device = device;
		this.dir = dir;
		this.status = false;
		this.num = num;
	}

	Listener(ControlSlider device, int num) {
		this.device = device;
		this.pos = 0;
		this.num = num;
	}

	private Object device;
	private boolean status;
	private float pos;
	private Direction dir;
	private int num;

	public Event stateChanged() {
		if (device instanceof ControlButton) {
			if (((ControlButton) device).pressed() && !status) {
				status = !status;
				return new PressEvent(true, num);
			} else if (!((ControlButton) device).pressed() && status) {
				status = !status;
				return new PressEvent(false, num);
			}
		} else if (device instanceof ControlHat) {
			if (Direction.pressedDirection(((ControlHat) device), dir) && !status) {
				status = !status;
				return new HatEvent(true, num, dir);
			} else if (!Direction.pressedDirection(((ControlHat) device), dir) && status) {
				status = !status;
				return new HatEvent(false, num, dir);
			}
		} else if (device instanceof ControlSlider) {
			if (!(new Float(((ControlSlider) device).getValue())).equals(new Float(pos))) {
				pos = ((ControlSlider) device).getValue();
				return new SliderEvent(pos, num);
			}
		}
		return null;
	}
}
