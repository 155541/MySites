package revolhope.splanes.com.mysites.model;

import android.support.annotation.NonNull;

import revolhope.splanes.com.mysites.helper.Constants;

public class Color extends Resource
{
    public Color (@NonNull String id, int resource)
    {
        this.setId(id);
        this.setResource(resource);
        this.setType(Constants.TYPE_COLOR);
    }
}
