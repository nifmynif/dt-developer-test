package com.vizor.test.service;

import com.vizor.test.module.ImageDTO;
import com.vizor.test.module.ImagesHandler;
import lombok.Getter;

import java.io.File;
import java.util.Collections;

@Getter
public class ImageService {
    public final ImagesHandler images;

    public ImageService(int capacity) {
        this.images = new ImagesHandler(capacity);
    }

    public void addImage(File file) {
        int index = getIndexByFileName(file.getName());
        if (index < 0)
            index = -index - 1;
        images.getImages().add(index, new ImageDTO(file));
    }

    public void deleteImage(ImageDTO imageDTO) {
        images.getImages().remove(imageDTO);
        setCur(null);
        if(size()<1) {
            setNext(null);
            setPrev(null);
        }
    }

    public int size() {
        return images.getImages().size();
    }

    public int getIndexByFileName(String fileName) {
        return Collections.binarySearch(images.getImages(), fileName);
    }

    public ImageDTO getImageByIndex(int index) {
        return images.getImages().get(index);
    }

    public void setCur(ImageDTO image) {
        images.setCur(image);
    }

    public void setPrev(ImageDTO image) {
        images.setPrev(image);
    }

    public ImageDTO getNext() {
        return images.getNext();
    }

    public ImageDTO getCur() {
        return images.getCur();
    }

    public ImageDTO getPrev() {
        return images.getPrev();
    }

    public void setNext(ImageDTO image) {
        images.setNext(image);
    }

    public void setPostNext(ImageDTO image) {
        images.setPostNext(image);
    }

    public ImageDTO getPostNext() {
        return images.getPostNext();
    }

    public void setPrePrev(ImageDTO image) {
        images.setPrePrev(image);
    }
}
