package com.vizor.test.constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class Constants {
    private Constants() {
    }

    private static final Properties properties = new Properties();
    public static final List<String> EXTENSION_IMAGE;
    public static final String MAIN_FOLDER;

    static {
        try (FileInputStream fis = new FileInputStream("properties/Main.properties")) {
            properties.load(fis);
            EXTENSION_IMAGE = Stream.of(properties.getProperty("EXTENSION_IMAGE").split(","))
                    .toList();
            MAIN_FOLDER = properties.getProperty("MAIN_FOLDER", "assets");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
