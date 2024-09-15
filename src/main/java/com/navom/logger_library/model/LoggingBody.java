package com.navom.logger_library.model;

import com.navom.logger_library.enums.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggingBody {
    private String content;
    private LogLevel logLevel;
    private String namespace;
    private String timestamp;
}
