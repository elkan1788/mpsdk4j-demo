package org.elkan1788.extra.nuby.bean;

/**
 * Created by Senhui on 4/30/2015.
 */
public class ImageSrc {

    private String title;
    private String desc = "努比Nuby";
    private String imgSrc;

    public ImageSrc() {
    }

    public ImageSrc(String title, String imgSrc) {
        this.title = title;
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
