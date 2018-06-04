package revolhope.splanes.com.mysites.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.model.Tag;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.Holder>
{

    private List<Tag> tagList;

    private LayoutInflater inflater;

    public TagAdapter(Context context)
    {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new Holder(inflater.inflate(R.layout.holder_tag, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {

        if (tagList != null)
        {
            return tagList.size();
        }
        else
        {
            return 0;
        }
    }

    public void setTags(List<Tag> tagList)
    {
        this.tagList = tagList;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder
    {

        private Holder(View view)
        {
            super(view);
        }
    }
}
