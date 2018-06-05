package revolhope.splanes.com.mysites.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.FlipAnimator;
import revolhope.splanes.com.mysites.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<Item> items;
    private List<Holder> holders;

    public ItemAdapter(@NonNull Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        holders = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Holder holder = new Holder(inflater.inflate(R.layout.holder_item, parent, false));
        holders.add(holder);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {

        if (items != null && items.size() > position)
        {
            Item item = items.get(position);

            holder.textView_name.setText(item.getName());
            if (item.getWeb() != null && !item.getWeb().isEmpty())
            {
                holder.textView_web.setText(item.getWeb());
            }
            else if (item.getLocation() != null && !item.getLocation().isEmpty())
            {
                holder.textView_web.setText(item.getLocation());
            }
            else
            {
                holder.textView_web.setText(item.getPhone());
            }

            RecyclerView recyclerView = holder.recyclerView;

            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            TagAdapter tagAdapter = new TagAdapter(context);
            recyclerView.setAdapter(tagAdapter);

            tagAdapter.setTags(item.getTags());
        }

    }

    @Override
    public int getItemCount()
    {
        if (items != null)
        {
            return items.size();
        }
        else
        {
            return 0;
        }
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder
    {
        private CardView frontView;
        private CardView backView;

        private LinearLayout linearLayout_call;
        private TextView textView_name;
        private TextView textView_web;
        private RecyclerView recyclerView;

        private boolean isSwapped;

        private Holder (View view)
        {
            super(view);
            isSwapped = false;
            linearLayout_call = view.findViewById(R.id.layout_item_call);
            textView_name = view.findViewById(R.id.textView_item_name);
            recyclerView = view.findViewById(R.id.recyclerView_tags);
            textView_web = view.findViewById(R.id.textView_item_web);

            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (!isSwapped)
                    {
                        FlipAnimator.flip(context, frontView, backView);
                    }
                    else
                    {
                        FlipAnimator.flipBack(context, frontView, backView);
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    return false;
                }
            });

        }
    }
}
