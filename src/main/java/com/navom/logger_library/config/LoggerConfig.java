package com.navom.logger_library.config;

import com.navom.logger_library.enums.LogLevel;
import com.navom.logger_library.service.SinkService;
import com.navom.logger_library.service.impl.TextFileSinkServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@AllArgsConstructor
public class LoggerConfig {
    private LogLevel logLevel;
    private String timeFormat;
    private Map<LogLevel, SinkService> logLevelSinkServiceMap;

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void addLogLevelSinkServiceMap(LogLevel logLeve2, SinkService sinkService) {
        this.logLevelSinkServiceMap.put(logLeve2, sinkService);
    }

    public SinkService getSinkService(LogLevel logLevel1) {
        return logLevelSinkServiceMap.get(logLevel1);
    }
}
