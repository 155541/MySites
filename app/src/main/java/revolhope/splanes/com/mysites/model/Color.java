package revolhope.splanes.com.mysites.model;

import android.support.annotation.NonNull;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.Constants;

public class Color extends Resource
{
    public Color (@NonNull String id, int resource)
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
        this.setType(Constants.TYPE_COLOR);
    }
}
