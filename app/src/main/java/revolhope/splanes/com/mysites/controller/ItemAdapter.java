package revolhope.splanes.com.mysites.controller;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.FlipAnimator;
import revolhope.splanes.com.mysites.model.Item;
import revolhope.splanes.com.mysites.model.Resource;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder>
{

    private SparseBooleanArray sparseArrayIsSwapped;

    private Context context;
    private LayoutInflater inflater;
    private List<Item> items;
    private List<Holder> holders;
    private FragmentManager fragmentManager;

    public ItemAdapter(@NonNull Context context, FragmentManager fragmentManager)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        holders = new ArrayList<>();
        sparseArrayIsSwapped = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Holder holder = new Holder(inflater.inflate(R.layout.holder_item, parent, false), context, fragmentManager);
        holders.add(holder);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {

        if (items != null && items.size() > position)
        {
         
            if (holder.isSwapped)
            {
            
            }
            else
            {
            }
         
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
            */
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

    static class Holder extends RecyclerView.ViewHolder
    {
        private ItemFrontFragment frontFragment;
        private ItemBackFragment backFragment;
        private boolean isSwapped;

        private Holder (View view, final Context context, final FragmentManager fragmentManager)
        {
            super(view);
            isSwapped = false;

            frontFragment = new ItemFrontFragment();
            backFragment = new ItemBackFragment();
            
            fragmentManager.beginTransaction()
                    .add(R.id.container, frontFragment)
                    .commit();

            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (isSwapped) {
                        fragmentManager.popBackStack();
                        isSwapped = false;
                        return;
                    }

                    // Flip to the back.

                    isSwapped = true;

                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.flip_down_in,
                                    R.animator.flip_down_out,
                                    R.animator.flip_up_in,
                                    R.animator.flip_up_out)
                            .replace(R.id.container, backFragment)
                            // Add this transaction to the back stack, allowing users to press
                            // Back to get to the front of the card.
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        public static class ItemFrontFragment extends Fragment {
            
            private TextView name;
            private TextView web;
            private LinearLayout linearLayoutCall;
            private RecyclerView recyclerView;
            private TagAdapter tagAdapter;
            
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                
                View view = inflater.inflate(R.layout.holder_item_content_front, container, false);
                Context context = getContext();
                if (context != null)
                {
                    Resources res = context.getResources();
                    float scale = res.getDisplayMetrics().density * res.getInteger(R.integer.factor_scale);
                    view.setCameraDistance(scale);
                    
                    recyclerView = view.findViewById(R.id.recyclerView_tags);
                    linearLayoutCall = view.findViewById(R.id.layout_item_call);
                    name = view.findViewById(R.id.textView_item_name);
                    web = view.findViewById(R.id.textView_item_web);
                                        
                    tagAdapter = new TagAdapter(context);
                    
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(tagAdapter);
                }
                return view;
            }
        }


        public static class ItemBackFragment extends Fragment {
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.holder_item_content_back, container, false);

                Context context = getContext();
                if (context != null)
                {
                    Resources res = context.getResources();
                    float scale = res.getDisplayMetrics().density * res.getInteger(R.integer.factor_scale);
                    view.setCameraDistance(scale);
                }
                return view;
            }
        }
    }
}
