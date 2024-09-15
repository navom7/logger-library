package com.navom.logger_library;

import com.navom.logger_library.config.LoggerConfig;
import com.navom.logger_library.enums.LogLevel;
import com.navom.logger_library.logger.Logger;
import com.navom.logger_library.service.SinkService;
import com.navom.logger_library.service.impl.TextFileSinkServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LoggerLibraryApplication {

	public static void main(String[] args) {
		Map<LogLevel, SinkService> sinks = new HashMap<>();
		sinks.put(LogLevel.INFO, new TextFileSinkServiceImpl("/Users/nanhe/Desktop/logger-library/src/logs/application.log", 200));
		sinks.put(LogLevel.WARN, new TextFileSinkServiceImpl("/Users/nanhe/Desktop/logger-library/src/logs/application.log", 200));

		LoggerConfig config = new LoggerConfig(LogLevel.INFO, "yyyy-MM-dd HH:mm:ss", sinks);
		Logger logger = new Logger(config);

		logger.log(LogLevel.INFO, "TestingService", "Starting the service...");
		logger.log(LogLevel.WARN, "TestingService", "Warning: Possible configuration issue.");
	}

}
