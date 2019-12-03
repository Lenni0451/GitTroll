package net.Lenni0451.GitTroll.utils.spigotevents;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import net.Lenni0451.GitTroll.utils.Logger;

public class CustomEventExecutor implements EventExecutor {
	
	private final Method method;
	
	public CustomEventExecutor(final Method method) {
		this.method = method;
	}

	@Override
	public void execute(Listener listener, Event event) throws EventException {
		try {
			this.method.invoke(listener, new Object[] {event});
		} catch (Throwable e) {
			if(e instanceof InvocationTargetException && e.getCause() != null) {
				e = e.getCause();
			}
			Logger.broadcastGitMessage("§cAn unknown error occurred whilst calling spigot event §6" + event.getClass().getSimpleName() + ".");
			Logger.broadcastGitMessage("§aException§7: §6" + e.getClass().getSimpleName() + (e.getMessage() != null ? (" §7| §c" + e.getMessage()) : ("")));
		}
	}

}
