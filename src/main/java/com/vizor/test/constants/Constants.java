package com.vizor.test.constants;

import java.util.ArrayList;

public class Constants {
    private Constants() {
    }

    public static final ArrayList<String> EXTENSION_IMAGE = new ArrayList<>();

    static {
        EXTENSION_IMAGE.add(".jpg");
        EXTENSION_IMAGE.add(".jpeg");
        EXTENSION_IMAGE.add(".png");
    }

    public static final String MAIN_FOLDER = "assets";
}
