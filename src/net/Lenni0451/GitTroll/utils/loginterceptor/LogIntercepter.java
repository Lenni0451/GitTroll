package net.Lenni0451.GitTroll.utils.loginterceptor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.event.events.EventConsoleLog;

public class LogIntercepter implements Filter {
	
	private final Logger logger;
	
	public LogIntercepter() {
		this.logger = (Logger) LogManager.getRootLogger();
		
		this.logger.addFilter(this);
	}

	@Override
	public Result filter(LogEvent event) {
		EventConsoleLog cEvent = new EventConsoleLog(event.getMessage().getFormattedMessage());
		GitTroll.getInstance().eventManager.callEvent(cEvent);
		if(cEvent.isCancelled()) {
			return Result.DENY;
		}
		if(!event.getMessage().getFormattedMessage().equals(cEvent.getMessage())) {
			Bukkit.getConsoleSender().sendMessage(cEvent.getMessage());
			return Result.DENY;
		}
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object... parameters) {
		return Result.DENY;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object message, Throwable exception) {
		return Result.DENY;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message message, Throwable exception) {
		return Result.DENY;
	}

	@Override
	public Result getOnMatch() {
		return Result.NEUTRAL;
	}

	@Override
	public Result getOnMismatch() {
		return Result.NEUTRAL;
	}
	
}
