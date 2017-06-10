package com.zombo.jd.musicorg.model.dataObjects;

import com.zombo.jd.musicorg.util.GlobalConstants;

/**
 * Created by jd on 12/20/15.
 * This class represents one row in the labels table
 */
public class Label {
    private long id;
    private String name;
    private String city;
    private String country;
    private String contactMade;
    private String musicReleased;
    private String primaryContact;
    private String email;
    private String phone;

    public Label(int id, String name, String city, String country, String contactMade, String musicReleased, String primaryContact, String email, String phone) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.contactMade = contactMade;
        this.musicReleased = musicReleased;
        this.primaryContact = primaryContact;
        this.email = email;
        this.phone = phone;
    }

    public Label() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactMade() {
        return contactMade;
    }

    public void setContactMade(String contactMade) {
        this.contactMade = contactMade;
    }

    public String getMusicReleased() {
        return musicReleased;
    }

    public void setMusicReleased(String musicReleased) {
        this.musicReleased = musicReleased;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return getId() + GlobalConstants.COMMA +
                getCity() + GlobalConstants.COMMA +
                getContactMade() + GlobalConstants.COMMA +
                getCountry() + GlobalConstants.COMMA +
                getEmail() + GlobalConstants.COMMA +
                getMusicReleased() + GlobalConstants.COMMA +
                getName() + GlobalConstants.COMMA +
                getPhone() + GlobalConstants.COMMA +
                getPrimaryContact();
    }
}
