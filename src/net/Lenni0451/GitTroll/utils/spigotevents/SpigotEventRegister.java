package net.Lenni0451.GitTroll.utils.spigotevents;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.utils.Logger;

public class SpigotEventRegister {
	
	public static void registerEvents(final Listener listener) {
		try {
			for(Method method : listener.getClass().getDeclaredMethods()) {
				try {
					EventHandler handler;
					if((handler = method.getAnnotation(EventHandler.class)) != null) {
						if(method.getParameterTypes().length == 1) {
							Class<?> param = method.getParameterTypes()[0];
							if(Event.class.isAssignableFrom(param)) {
								HandlerList handlerList = null;
								for(Field field : param.getDeclaredFields()) {
									if(field.getType().equals(HandlerList.class)) {
										field.setAccessible(true);
										handlerList = (HandlerList) field.get(null);
									}
								}
								if(handlerList != null) {
									handlerList.register(new RegisteredListener(listener, new CustomEventExecutor(method), handler.priority(), GitTroll.getInstance().getParentPlugin(), handler.ignoreCancelled()));
								}
							}
						}
					}
				} catch (Throwable e) {}
			}
		} catch (Throwable e) {
			Logger.broadcastGitMessage("§cAn unknown error occurred whilst registering spigot event listener §6" + listener.getClass().getSimpleName() + ".");
			Logger.broadcastGitMessage("§aException§7: §6" + e.getClass().getSimpleName() + (e.getMessage() != null ? (" §7| §c" + e.getMessage()) : ("")));
		}
	}
	
}
