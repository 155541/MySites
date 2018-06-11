package revolhope.splanes.com.mysites.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.UUID;

public class Tag implements Serializable
{
    private String id;
    private String name;

    public Tag(@NonNull String id, @NonNull String name)
    {
        this.id = id;
        this.name = name;
    }

    public Tag(@NonNull String name)
    {
        this.id = UUID.randomUUID().toString();
        this.name = name;
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
}
