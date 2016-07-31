package net.ddns.ogl.RoboticArm;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.gamecontrolplus.ControlDevice;
import org.gamecontrolplus.ControlIO;

import net.ddns.ogl.RoboticArm.Event.Event;
import net.ddns.ogl.RoboticArm.Event.PressEvent;
import net.ddns.ogl.RoboticArm.Event.SliderEvent;

import processing.core.PApplet;
import processing.serial.Serial;

public class RoboticArm extends PApplet {

	private boolean save = false;
	private boolean button = false;

	private Serial serial = new Serial(this, Serial.list()[0], 9600);
	private ControlDevice device = ControlIO.getInstance(this).getDevice(0);
	private EventHandler handler = new EventHandler();

	private Time times[] = new Time[3];
	private long time = System.currentTimeMillis();
	
	private final long delayBtw = 40;

	public void setup() {

		handler.add(new Listener(device.getButton(8), 8));
		handler.add(new Listener(device.getButton(9), 9));
		handler.add(new Listener(device.getButton(7), 7));
		handler.add(new Listener(device.getButton(0), 0));
		handler.add(new Listener(device.getSlider(1), 1));
		handler.add(new Listener(device.getSlider(2), 2));
		handler.add(new Listener(device.getSlider(3), 3));
		for (int i = 0; i < 3; i++) {
			times[i] = new Time();
		}
	}

	public void serialWriter(String str) {
		println(str);
		serial.write(str);
		if (save) {
			try {
				time = System.currentTimeMillis() - time;
				FileWriter writer;
				writer = new FileWriter("data.txt", true);
				writer.append(str);
				writer.close();
				FileWriter writer2;
				writer2 = new FileWriter("timings.txt", true);
				writer2.append(time + " ");
				writer2.close();
				time = System.currentTimeMillis();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void draw() {
		for (Event event : handler.check()) {
			if (event instanceof PressEvent) {
				if (((PressEvent) event).getnum() == 7 && ((PressEvent) event).isPressed()) {
					serialWriter((button) ? "G11 " : "G10 ");
					button = !button;
				} else if (((PressEvent) event).getnum() == 0 && ((PressEvent) event).isPressed()) {
					serialWriter("H ");

				} else if (((PressEvent) event).getnum() == 8 && ((PressEvent) event).isPressed()) {
					if (!save) {
						serialWriter("H ");
						try {
							Files.deleteIfExists(Paths.get("data.txt"));
							Files.createFile(Paths.get("data.txt"));
							Files.deleteIfExists(Paths.get("timings.txt"));
							Files.createFile(Paths.get("timings.txt"));
						} catch (IOException e) {
							e.printStackTrace();
						}			
					}
					save = !save;

				} else if (((PressEvent) event).getnum() == 9 && ((PressEvent) event).isPressed()) {

					String content = null;
					String times = null;
					try {
						content = new String(Files.readAllBytes(Paths.get("data.txt")));
						times = new String(Files.readAllBytes(Paths.get("timings.txt")));
					} catch (IOException e) {
						e.printStackTrace();
					}

					String orders[] = content.split(" ");

					String timings[] = times.split(" ");

					serialWriter("H ");

					for (int i = 0; i < orders.length; i++) {
						serialWriter(orders[i]);
						try {			
							TimeUnit.MILLISECONDS.sleep(Long.parseLong(timings[i]));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} else if (event instanceof SliderEvent) {
				String str = "";
				if (((SliderEvent) event).getnum() == 1 && times[0].check(delayBtw)) {
					str += "A3" + (int) map(((SliderEvent) event).getValue(), -1f, 1f, 0, 400) + " ";
				} else if (((SliderEvent) event).getnum() == 2 && times[1].check(delayBtw)) {
					str += "A2" + (int) map(((SliderEvent) event).getValue(), -1f, 1f, 0, 400) + " ";
				} else if (((SliderEvent) event).getnum() == 3 && times[2].check(delayBtw)) {
					str += "A0" + (int) map(((SliderEvent) event).getValue(), -1f, 1f, 0, 400) + " ";
				}
				if (!str.equals("")) {
					serialWriter(str);
				}
			}
		}
		delay(100);
	}

	class Time {
		public Time() {
			myTime = System.currentTimeMillis();
		}

		public boolean check(long v) {
			boolean ret = (System.currentTimeMillis() - myTime) > v;
			myTime = System.currentTimeMillis();
			return ret;
		}

		private long myTime;
	}

}
