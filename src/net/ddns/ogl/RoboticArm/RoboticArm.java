package net.ddns.ogl.RoboticArm;

import org.gamecontrolplus.ControlDevice;
import org.gamecontrolplus.ControlIO;

import net.ddns.ogl.RoboticArm.Event.Event;
import net.ddns.ogl.RoboticArm.Event.PressEvent;
import net.ddns.ogl.RoboticArm.Event.SliderEvent;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.serial.Serial;

public class RoboticArm extends PApplet {

	private final int X = 640;
	private final int Y = 640;
	private final int rY = 20;

	private int rYV = rY / 2;
	private int YV = Y / 2;
	private int XV = X / 2;

	private boolean button = false;

	private Serial serial = new Serial(this, Serial.list()[0], 9600);
	ControlDevice device = ControlIO.getInstance(this).getDevice(0);
	EventHandler handler = new EventHandler();

	public void setup() {
		handler.add(new Listener(device.getButton(7), 7));
		handler.add(new Listener(device.getButton(0), 0));
		handler.add(new Listener(device.getSlider(1), 1));
		handler.add(new Listener(device.getSlider(2), 2));
		handler.add(new Listener(device.getSlider(2), 3));

	}

	public void serialWriter(String str) {
		println(str);
		serial.write(str + " ");
	}

	public void draw() {
		for (Event event : handler.check()) {
			if (event instanceof PressEvent) {
				if (((PressEvent) event).getnum() == 7 && ((PressEvent) event).isPressed()) {
					serialWriter((button) ? "G11" : " G10");
					button = !button;
				} else if (((PressEvent) event).getnum() == 0 && ((PressEvent) event).isPressed()) {
					serialWriter("H");
				}
			} else if (event instanceof SliderEvent) {
				if (((SliderEvent) event).getnum() == 1) {
					serialWriter("G3" + (int) map(((SliderEvent) event).getValue(), -1f, 1f, 0, 100));
				} else if (((SliderEvent) event).getnum() == 2) {
					serialWriter("G2" + (int) map(((SliderEvent) event).getValue(), -1f, 1f, 0, 100));
				} else if (((SliderEvent) event).getnum() == 3) {
					serialWriter("G0" + (int) map(((SliderEvent) event).getValue(), -1f, 1f, 0, 100));
				}
			}
		}
	}

}
