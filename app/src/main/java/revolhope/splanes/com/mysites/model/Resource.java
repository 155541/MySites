package revolhope.splanes.com.mysites.model;

import java.io.Serializable;

public abstract class Resource implements Serializable
{
    private String id;
    private int resource;
    private int type;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getResource()
    {
        return resource;
    }

    public void setResource(int resource)
    {
        this.resource = resource;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
