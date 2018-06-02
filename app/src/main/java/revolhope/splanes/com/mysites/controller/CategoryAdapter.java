package revolhope.splanes.com.mysites.controller;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Category> categories;

    public CategoryAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new Holder(inflater.inflate(R.layout.holder_category, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {
        if (categories.size() > position)
        {
            Category category = categories.get(position);
            holder.name.setText(category.getName());
            holder.description.setText(category.getDescription());

            int icon = category.getIcon().getResource();
            int color = category.getColor().getResource();
            if (icon > 0)
            {
                holder.icon.setImageDrawable(context.getDrawable(icon));
                if (color > 0 && color != R.color.resource_default)
                {
                    holder.icon.setImageTintList(ColorStateList.valueOf(context.getColor(color)));
                }
            }
            //holder.count.setText(/* GET COUNT */);
        }
    }

    @Override
    public int getItemCount() {
        if (categories != null)
        {
            return categories.size();
        }
        else
        {
            return 0;
        }
    }

    public void setCategories(List<Category> categories)
    {
        this.categories = categories;
        notifyDataSetChanged();
    }

// ============================================================================
//                              HOLDER VIEW
// ============================================================================

    class Holder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView description;
        private TextView count;
        private ImageView icon;

        private Holder (View view)
        {
            super(view);
            name = view.findViewById(R.id.textView_category_name);
            description = view.findViewById(R.id.textView_category_description);
            count = view.findViewById(R.id.textView_category_items_count);
            icon = view.findViewById(R.id.imageView_category_icon);
        }
    }
}
