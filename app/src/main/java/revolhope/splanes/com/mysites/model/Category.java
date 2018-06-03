package revolhope.splanes.com.mysites.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.UUID;

public class Category implements Serializable
{
    private String id;
    private String name;
    private Icon icon;
    private Color color;
    private String description;

    public Category(@NonNull String id, @NonNull String name, @Nullable Icon icon, @Nullable Color color, @Nullable String description)
    {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.description = description;
    }

    public Category(@NonNull String name, @Nullable Icon icon, @Nullable Color color, String description)
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

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
