package revolhope.splanes.com.mysites.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

public class Category
{
    private String id;
    private String name;
    private String icon;
    private String color;
    private String description;

    public Category(@NonNull String id, @NonNull String name, String icon, String color, @Nullable String description)
    {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.description = description;
    }

    public Category(@NonNull String name, String icon, String color, String description)
    {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.description = description;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
