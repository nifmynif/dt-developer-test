package com.vizor.test.module;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ImagesHandler {
    private final ArrayList<ImageDTO> images;
    private ImageDTO prePrev;
    private ImageDTO prev;
    private ImageDTO cur;
    private ImageDTO next;
    private ImageDTO postNext;

    public ImagesHandler(int capacity) {
        this.images = new ArrayList<>(capacity);
    }
}
