package com.zombo.jd.musicorg.model.dataObjects;

/**
 * Created by jd on 12/20/15.
 * This class represents one row in the releases table.
 */
public class Release {
    private long id;
    private String title;
    private String artist;
    private int labelId;
    private int year;
    private String catalogNumber;
    private byte[] image;
    private String format;

    public Release(int id, String title, String artist, int labelId, int year, String catalogNumber, byte[] image, String format) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.labelId = labelId;
        this.year = year;
        this.catalogNumber = catalogNumber;
        this.image = image;
        this.format = format;
    }

    public Release() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
