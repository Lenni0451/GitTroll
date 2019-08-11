package net.Lenni0451.GitTroll.event.events;

import net.Lenni0451.GitTroll.event.types.CancellableEvent;

public class EventConsoleLog extends CancellableEvent {
	
	private String message;
	
	public EventConsoleLog(final String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(final String message) {
		this.message = message;
	}
	
}
