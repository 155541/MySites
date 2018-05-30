package revolhope.splanes.com.mysites.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

public class Category
{
    private String id;
    private String name;
    private int icon;
    private int color;
    private String description;

    public Category(@NonNull String id, @NonNull String name, int icon, int color, @Nullable String description)
    {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.description = description;
    }

    public Category(@NonNull String name, int icon, int color, String description)
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
