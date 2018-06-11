package revolhope.splanes.com.mysites.controller;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.Constants;
import revolhope.splanes.com.mysites.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder>
{

    private Context context;
    private LayoutInflater inflater;
    private List<Item> items;
    private List<Holder> holders;
    private FragmentManager fragmentManager;
    private ViewGroup viewGroup;
    private ItemAdapterListener listener;

    public ItemAdapter(@NonNull Context context, FragmentManager fragmentManager, ItemAdapterListener listener)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        holders = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewGroup == null)
        {
            viewGroup = parent;
        }

        Holder holder = new Holder(inflater.inflate(R.layout.holder_item, parent, false), fragmentManager);
        holders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {

        if (items != null && items.size() > position)
        {

            Item item = items.get(position);

            holder.frontFragment.context = context;
            holder.backFragment.context = context;
            holder.frontFragment.findViews(inflater, viewGroup);
            holder.backFragment.findViews(inflater, viewGroup);

            holder.listener = listener;
            holder.item = item;

            // FRONT
            holder.frontFragment.name.setText(item.getName());
            if (item.getWeb() != null && !item.getWeb().isEmpty())
            {
                holder.frontFragment.web.setText(item.getWeb());
            }
            else if (item.getLocation() != null && !item.getLocation().isEmpty())
            {
                holder.frontFragment.web.setText(item.getLocation());
            }
            else
            {
                holder.frontFragment.web.setText(item.getPhone());
            }
            holder.frontFragment.tagAdapter.setTags(item.getTags());

            // BACK

            if (item.getNotes() != null && !item.getNotes().isEmpty())
            {
                holder.backFragment.linearLayoutNotes.setVisibility(View.VISIBLE);
                holder.backFragment.linearLayoutNotesNoInfo.setVisibility(View.GONE);
                holder.backFragment.notes.setText(item.getNotes());
            }
            else
            {
                holder.backFragment.linearLayoutNotes.setVisibility(View.GONE);
                holder.backFragment.linearLayoutNotesNoInfo.setVisibility(View.VISIBLE);
            }

            if (item.getUbication() != null && !item.getUbication().isEmpty())
            {
                holder.backFragment.linearLayoutUbication.setVisibility(View.VISIBLE);
                holder.backFragment.linearLayoutUbicationNoInfo.setVisibility(View.GONE);
                holder.backFragment.ubication.setText(item.getUbication());
            }
            else
            {
                holder.backFragment.linearLayoutUbication.setVisibility(View.GONE);
                holder.backFragment.linearLayoutUbicationNoInfo.setVisibility(View.VISIBLE);
            }

            // TODO: Set onClick listener to BackCard buttons

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
        private int id;
        private FrameLayout layout;
        private ItemFrontFragment frontFragment;
        private ItemBackFragment backFragment;
        private ItemAdapterListener listener;
        private Item item;
        private boolean isSwapped;

        private Holder (View view, @NonNull final FragmentManager fragmentManager)
        {
            super(view);
            isSwapped = false;

            frontFragment = new ItemFrontFragment();
            backFragment = new ItemBackFragment();

            backFragment.callback = new ItemCallback()
            {
                @Override
                public void onClick(int code)
                {
                    listener.onClick(item, code);
                }
            };

            id = generateId();
            layout = view.findViewById(R.id.container);
            layout.setId(id);

            fragmentManager.beginTransaction()
                    .add(id, frontFragment, String.valueOf(getAdapterPosition()))
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
                            .replace(id, backFragment, String.valueOf(getAdapterPosition()))
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        private int generateId()
        {
            return (int)System.currentTimeMillis();
        }


        public static class ItemFrontFragment extends Fragment {

            private View view;
            private Context context;

            private TextView name;
            private TextView web;
            private LinearLayout linearLayoutCall;
            private RecyclerView recyclerView;
            private TagAdapter tagAdapter;
            
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                if (context != null)
                {
                    Resources res = context.getResources();
                    float scale = res.getDisplayMetrics().density * res.getInteger(R.integer.factor_scale);
                    view.setCameraDistance(scale);
                }
                return view;
            }

            private void findViews(@NonNull LayoutInflater inflater, ViewGroup container)
            {
                view = inflater.inflate(R.layout.holder_item_content_front, container, false);
                if (context != null)
                {
                    recyclerView = view.findViewById(R.id.recyclerView_tags);
                    linearLayoutCall = view.findViewById(R.id.layout_item_call);
                    name = view.findViewById(R.id.textView_item_name);
                    web = view.findViewById(R.id.textView_item_web);

                    tagAdapter = new TagAdapter(context);

                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(tagAdapter);
                }
            }
        }


        public static class ItemBackFragment extends Fragment {

            private Context context;
            private View view;

            private LinearLayout linearLayoutNotes;
            private LinearLayout linearLayoutNotesNoInfo;

            private LinearLayout linearLayoutUbication;
            private LinearLayout linearLayoutUbicationNoInfo;

            private TextView notes;
            private TextView ubication;

            private ImageView iv_ubication;

            private ImageView edit;
            private ImageView hashtags;
            private ImageView delete;

            private ItemCallback callback;

            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                if (context != null)
                {
                    Resources res = context.getResources();
                    float scale = res.getDisplayMetrics().density * res.getInteger(R.integer.factor_scale);
                    view.setCameraDistance(scale);
                }
                return view;
            }

            private void findViews(@NonNull LayoutInflater inflater, ViewGroup container)
            {
                view = inflater.inflate(R.layout.holder_item_content_back, container, false);
                linearLayoutNotes = view.findViewById(R.id.linearLayoutNotes);
                linearLayoutNotesNoInfo = view.findViewById(R.id.linearLayoutNotesNoInfo);

                linearLayoutUbication = view.findViewById(R.id.linearLayoutUbication);
                linearLayoutUbicationNoInfo = view.findViewById(R.id.linearLayoutUbicationNoInfo);

                notes = view.findViewById(R.id.textView_notes);
                ubication = view.findViewById(R.id.textView_ubication);

                iv_ubication = view.findViewById(R.id.imageView_button_ubication);

                edit = view.findViewById(R.id.imageView_button_edit);
                hashtags = view.findViewById(R.id.imageView_button_hashtag);
                delete = view.findViewById(R.id.imageView_button_delete);

                hashtags.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {

                    }
                });

                edit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        callback.onClick(Constants.ITEM_CODE_EDIT);
                    }
                });

            }
        }

        private interface ItemCallback
        {
            void onClick(int code);
        }

    }


    public interface ItemAdapterListener
    {
        void onClick(Item item, int code);
        // TODO: UBICATION
    }

}
