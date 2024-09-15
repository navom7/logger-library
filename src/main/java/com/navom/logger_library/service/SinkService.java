package com.navom.logger_library.service;

import com.navom.logger_library.model.LoggingBody;

public interface SinkService {
    void writeLogs(LoggingBody loggingBody);
    void rotateLogs();
}
