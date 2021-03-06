package revolhope.splanes.com.mysites.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Item
{
    private String id;
    private String name;
    private String phone;
    private String location;
    private String web;
    private String mail;
    private String notes;
    private String ubication;
    private List<Tag> tags;

    public Item(@NonNull String id, @NonNull String name, @Nullable String phone,
                @Nullable String location, @Nullable String web, @Nullable String mail,
                @Nullable String notes, @Nullable String ubication, @Nullable List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.web = web;
        this.mail = mail;
        this.notes = notes;
        this.ubication = ubication;
        this.tags = tags;
        if (this.tags == null)
        {
            this.tags = new ArrayList<>();
        }
    }

    public Item(@NonNull String name, @Nullable String phone, @Nullable String location,
                @Nullable String web, @Nullable String mail, @Nullable String notes,
                @Nullable String ubication, @Nullable List<Tag> tags) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.web = web;
        this.mail = mail;
        this.notes = notes;
        this.ubication = ubication;
        this.tags = tags;
        if (this.tags == null)
        {
            this.tags = new ArrayList<>();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
