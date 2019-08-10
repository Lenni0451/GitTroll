package net.Lenni0451.GitTroll.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class LogIntercepter implements Filter {
	
	public LogIntercepter() {
		((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(this);
	}

	@Override
	public Result filter(LogEvent event) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object... parameters) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object message, Throwable exception) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message message, Throwable exception) {
		return Result.NEUTRAL;
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
