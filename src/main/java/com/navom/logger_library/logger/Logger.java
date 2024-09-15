package com.navom.logger_library.logger;

import com.navom.logger_library.config.LoggerConfig;
import com.navom.logger_library.enums.LogLevel;
import com.navom.logger_library.model.LoggingBody;
import com.navom.logger_library.service.SinkService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class Logger {
    private LogLevel currentLogLevel;
    private String timeFormat;
    private Map<LogLevel, SinkService> logLevelToSinkServiceMap = new HashMap<>();

    public Logger(LoggerConfig config) {
        this.currentLogLevel = config.getLogLevel();
        this.timeFormat = config.getTimeFormat();
        configureSinks(config);
    }

    private void configureSinks(LoggerConfig config) {
        for(LogLevel logLevel: LogLevel.values()) {
            if(config.getSinkService(logLevel) != null) {
                logLevelToSinkServiceMap.put(logLevel, config.getSinkService(logLevel));
            }
        }
    }

    public void log(LogLevel logLevel, String namespace, String content) {
        if(logLevel.ordinal() >= currentLogLevel.ordinal()) {
            String timestamp = LocalDateTime.now().format((DateTimeFormatter.ofPattern(timeFormat)) );
            LoggingBody loggingBodyMessage = new LoggingBody(content, logLevel, namespace, timestamp);
            SinkService sinkService = logLevelToSinkServiceMap.get(logLevel);
            if(sinkService != null) {
                sinkService.writeLogs(loggingBodyMessage);
            }
        }
    }

}
