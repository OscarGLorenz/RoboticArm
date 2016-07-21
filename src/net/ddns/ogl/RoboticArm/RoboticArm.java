package net.ddns.ogl.RoboticArm;

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

	public void settings() {
		super.size(X, Y);
	}

	public void draw() {
		String msg = "";
		if (mouseX != XV) {
			XV = mouseX;
			msg += "G0" + (int) map(mouseX, 0, X, 0, 100) + ' ';
			println("G0" + (int) map(mouseX, 0, X, 0, 100));
		}
		if (mouseY != YV) {
			YV = mouseY;
			msg += "G2" + (int) map(mouseY, 0, Y, 0, 100) + ' ';
			println("G2" + (int) map(mouseY, 0, Y, 0, 100));
		}
		serial.write(msg);
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		if (event.getCount() > 0) {
			rYV += (rYV < rY) ? 1 : 0;
			serial.write("G3" + (int) map(rYV, 0, rY, 0, 100) + ' ');
			println("G3" + (int) map(rYV, 0, rY, 0, 100));
		} else if (event.getCount() < 0) {
			rYV -= (rYV > 0) ? 1 : 0;
			serial.write("G3" + (int) map(rYV, 0, rY, 0, 100) + ' ');
			println("G3" + (int) map(rYV, 0, rY, 0, 100));
		}
	}

	@Override
	public void mouseClicked() {
		if (mouseButton == 37) {

		} else if (mouseButton == 39) {
			serial.write("G1" + ((button) ? 1 : 0) + ' ');
			println("G1" + ((button) ? 1 : 0));
			button = !button;
		} else if (mouseButton == 3) {
			serial.write("H" + ' ');
			println("H");
		}
	}

}
