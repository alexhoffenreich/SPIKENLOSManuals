package com.example.alex_lenovi.spikenlosmanuals;

import org.w3c.dom.Node;

/**
 * Created by ADSL on 21/07/2016.
 */
public class ThumbModel {
    private String file_name = "";
    private String title = "";
    private String img_ref = "";
    private String description = "";

    public ThumbModel(String file_name, String title, String img_ref, String description) {
        this.file_name = file_name;
        this.title = title;
        this.img_ref = img_ref;
        this.description = description;
    }


    public ThumbModel() {
    }

    public ThumbModel(Node node) {


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_ref() {
        return img_ref;
    }

    public void setImg_ref(String img_ref) {
        this.img_ref = img_ref;
    }
}
