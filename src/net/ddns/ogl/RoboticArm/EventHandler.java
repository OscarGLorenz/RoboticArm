package net.ddns.ogl.RoboticArm;

import java.util.ArrayList;
import java.util.List;

import net.ddns.ogl.RoboticArm.Event.Event;

public class EventHandler {
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public void add(Listener listener) {
		listeners.add(listener);
	}
	
	public List<Event> check() {
		List<Event> events = new ArrayList<Event>();
		for (Listener listener : listeners) {
			Event event = listener.stateChanged();
			if (event != null) {
				events.add(event);
			}
		}
		return events;
	}
}
