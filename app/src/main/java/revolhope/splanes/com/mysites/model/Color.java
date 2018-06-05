package revolhope.splanes.com.mysites.model;

import android.support.annotation.NonNull;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.Constants;

public class Color extends Resource
{
    private int backgroundResource;

    public Color (@NonNull String id, int resource, int backgroundResource)
    {
        this.setId(id);
        if (resource > 0)
        {
            this.setResource(resource);
        }
        else
        {
            this.setResource(R.color.resource_default);
        }
        if (backgroundResource > 0)
        {
            this.setBackgroundResource(backgroundResource);
        }
        else
        {
            this.setBackgroundResource(R.color.resource_default_background);
        }
        this.setType(Constants.TYPE_COLOR);
    }

    public int getBackgroundResource()
    {
        return backgroundResource;
    }

    public void setBackgroundResource(int backgroundResource)
    {
        this.backgroundResource = backgroundResource;
    }
}
