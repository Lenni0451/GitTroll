package net.Lenni0451.GitTroll.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.Lenni0451.GitTroll.event.types.Event;

public class EventManager {
	
	private final Map<Class<? extends Event>, List<EventListener>> eventListener;
	
	public EventManager() {
		this.eventListener = new HashMap<>();
	}
	
	public void registerAllEvents(final EventListener eventListener) {
		this.registerEvent(eventListener, null);
	}
	
	public void registerEvent(final EventListener eventListener, final Class<? extends Event> event) {
		List<EventListener> listener = this.eventListener.get(event);
		if(listener == null) {
			listener = new ArrayList<>();
			this.eventListener.put(event, listener);
		}
		
		if(!listener.contains(eventListener)) {
			listener.add(eventListener);
		}
	}
	
	public void callEvent(final Event event) {
		List<EventListener> nullEvents = this.eventListener.get(null);
		List<EventListener> currentEvents = this.eventListener.get(event.getClass());
		
		if(nullEvents != null) nullEvents.forEach((EventListener listener) -> listener.onEvent(event));
		if(currentEvents != null) currentEvents.forEach((EventListener listener) -> listener.onEvent(event));
	}
	
}
