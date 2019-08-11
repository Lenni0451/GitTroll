package net.Lenni0451.GitTroll.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.Logger;

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
		try {
			List<EventListener> nullEvents = this.eventListener.get(null);
			List<EventListener> currentEvents = this.eventListener.get(event.getClass());
			
			if(nullEvents != null) nullEvents.forEach((EventListener listener) -> listener.onEvent(event));
			if(currentEvents != null) currentEvents.forEach((EventListener listener) -> listener.onEvent(event));
		} catch (Throwable e) {
			Logger.broadcastGitMessage("§cAn unknown error occurred whilst calling event §6" + event.getClass().getSimpleName() + ".");
			Logger.broadcastGitMessage("§aException§7: §6" + e.getClass().getSimpleName());
		}
	}
	
}
