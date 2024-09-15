package com.navom.logger_library.service.impl;

import com.navom.logger_library.model.LoggingBody;
import com.navom.logger_library.service.SinkService;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class TextFileSinkServiceImpl implements SinkService {
    private String filePath;
    private long maxFileSize;
    private int fileCounter = 0;

    public TextFileSinkServiceImpl(String filePath, long maxFileSize) {
        this.filePath = filePath;
        this.maxFileSize = maxFileSize;
        createDirectoryIfNotExists();
    }

    @Override
    public void writeLogs(LoggingBody message) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(formatMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rotateLogs();
    }

    private String formatMessage(LoggingBody message) {
        return String.format("%s [%s] %s %s\n",
                message.getLogLevel(),
                message.getTimestamp(),
                message.getNamespace(),
                message.getContent());
    }

    @Override
    public void rotateLogs() {
        File logFile = new File(filePath);
        if (logFile.length() >= maxFileSize) {
            try {
               File copressedFile = new File(filePath + "." + (++fileCounter) + ".gz");
               copressedFile(logFile, copressedFile);

               new PrintWriter(filePath).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void copressedFile(File sourceFile, File targeteFile) throws IOException {
        GZIPOutputStream gzipOut = new GZIPOutputStream(new FileOutputStream(targeteFile));
        try(FileInputStream fileIn = new FileInputStream(sourceFile)) {
            byte[] buffer = new byte[400];
            int len;
            while((len = fileIn.read(buffer)) > 0) {
                gzipOut.write(buffer, 0, len);
            }
        }
    }

    private void createDirectoryIfNotExists() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if(parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if(!created) {
                System.err.println("Failed to create directories: " + parentDir.getPath());
            }
        }
    }
}
