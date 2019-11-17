package net.faithgen.gallery.models;

import net.faithgen.sdk.models.Avatar;
import net.faithgen.sdk.models.Date;

import org.itishka.gsonflatten.Flatten;

public class Album {
    public static final String ID = "ID";
    private String id;
    private String name;
    private String description;
    private Date date;
    private Avatar avatar;
    @Flatten("images::count")
    private int size;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
