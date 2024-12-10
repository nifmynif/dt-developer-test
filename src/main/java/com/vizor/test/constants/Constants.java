package com.vizor.test.constants;

import com.vizor.test.controller.LogController;
import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

@UtilityClass
public class Constants {

    private static final Properties properties = new Properties();
    public static final List<String> EXTENSION_IMAGE;
    public static final String MAIN_FOLDER;

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/Main.properties")) {
            properties.load(fis);
            EXTENSION_IMAGE = Stream.of(properties.getProperty("EXTENSION_IMAGE").split(","))
                    .toList();
            MAIN_FOLDER = properties.getProperty("MAIN_FOLDER", "assets");
        } catch (IOException e) {
            LogController.logError(e.getMessage(), Constants.class);
            throw new IllegalArgumentException(e);
        }
    }
}
