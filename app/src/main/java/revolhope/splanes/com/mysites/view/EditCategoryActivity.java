package revolhope.splanes.com.mysites.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.AppDatabase;
import revolhope.splanes.com.mysites.helper.AppDatabaseDao;
import revolhope.splanes.com.mysites.helper.Constants;
import revolhope.splanes.com.mysites.model.Category;
import revolhope.splanes.com.mysites.model.Color;
import revolhope.splanes.com.mysites.model.Icon;
import revolhope.splanes.com.mysites.model.Resource;

public class EditCategoryActivity extends AppCompatActivity {

    private TextInputEditText editText_name;
    private TextInputEditText editText_description;
    private ImageView imageView_icon;
    private ImageView imageView_color;

    private AppDatabaseDao dao;
    private List<Color> mColors;
    private List<Icon> mIcons;

    private boolean isNew;
    private Category categoryToUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();

        editText_name = findViewById(R.id.editText_catgoryName);
        editText_description = findViewById(R.id.editText_categoryDescription);
        imageView_icon = findViewById(R.id.imageView_category_icon);
        imageView_color = findViewById(R.id.imageView_category_color);
        imageView_color.setImageDrawable(getDrawable(R.drawable.baseline_fiber_manual_record_24));

        Button buttonDone = findViewById(R.id.button_done);

        if (intent != null && intent.hasExtra(Constants.EXTRA_CATEGORY) && actionBar != null)
        {
            categoryToUpdate = (Category) intent.getSerializableExtra(Constants.EXTRA_CATEGORY);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Update category");
            actionBar.setSubtitle("");

            editText_name.setText(categoryToUpdate.getName());
            editText_description.setText(categoryToUpdate.getDescription());

            imageView_icon.setImageDrawable(getDrawable(categoryToUpdate.getIcon().getResource()));
            imageView_icon.setTag(categoryToUpdate.getIcon().getId());
            imageView_color.setImageTintList(ColorStateList.valueOf(getColor(categoryToUpdate.getColor().getResource())));
            imageView_color.setTag(categoryToUpdate.getColor().getId());

            buttonDone.setText(R.string.button_new_category_update);

            isNew = false;
        }
        else if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("New category");
            actionBar.setSubtitle("");

            isNew = true;
        }

        dao = AppDatabase.getInstance(this);

        dao.getColors(new AppDatabase.OnSelect<Resource>()
        {
            @Override
            public void select(List<Resource> selection)
            {
                if (selection != null)
                {
                    mColors = new ArrayList<>(selection.size());
                    for (Resource res : selection)
                    {
                        mColors.add((Color)res);
                    }
                }
            }
        });

        dao.getIcons(new AppDatabase.OnSelect<Resource>()
        {
            @Override
            public void select(List<Resource> selection)
            {
                if (selection != null)
                {
                    mIcons = new ArrayList<>(selection.size());
                    for (Resource res : selection)
                    {
                        mIcons.add((Icon)res);
                    }
                }
            }
        });

        findViewById(R.id.button_pick_icon)
            .setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mIcons == null)
                    {
                        Toast.makeText(getApplicationContext(), "Ops.. Icons are not loaded yet..\nWait a few seconds please", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        PickerDialog pickerDialog = new PickerDialog();
                        pickerDialog.resources = new ArrayList<>(mIcons.size());
                        pickerDialog.resources.addAll(mIcons);
                        pickerDialog.resourceType = Constants.TYPE_ICON;
                        pickerDialog.callback = new OnDialogPicker()
                        {
                            @Override
                            public void picked(final Resource resource)
                            {
                                if (resource != null)
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageView_icon.setImageDrawable(getResources().getDrawable(resource.getResource(), null));
                                            imageView_icon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.resource_default, null)));
                                            imageView_icon.setTag(resource.getId());
                                        }
                                    });
                                }
                                else
                                {
                                    // TODO:
                                }
                            }
                        };
                        pickerDialog.show(getSupportFragmentManager(), "PickerIconDialog");
                    }
                }
            });
        
        findViewById(R.id.button_pick_color)
            .setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mColors == null)
                    {
                        Toast.makeText(getApplicationContext(), "Ops.. Colors are not loaded yet..\nWait a few seconds please", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        PickerDialog pickerDialog = new PickerDialog();
                        pickerDialog.resources = new ArrayList<>(mColors.size());
                        pickerDialog.resources.addAll(mColors);
                        pickerDialog.resourceType = Constants.TYPE_COLOR;
                        pickerDialog.callback = new OnDialogPicker()
                        {
                            @Override
                            public void picked(final Resource resource)
                            {
                                if (resource != null)
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageView_color.setImageTintList(ColorStateList.valueOf(getColor(resource.getResource())));
                                            imageView_color.setTag(resource.getId());
                                        }
                                    });
                                }
                                else
                                {
                                    // TODO
                                }
                            }
                        };
                        pickerDialog.show(getSupportFragmentManager(), "PickerColorDialog");
                    }
                }
            });
        
        buttonDone
            .setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (checkFields())
                    {
                        final String name = editText_name.getText().toString();
                        String description = editText_description.getText().toString();
                        String icon = (String) imageView_icon.getTag();
                        String color = (String) imageView_color.getTag();

                        if (isNew)
                        {
                            List<Category> list = new ArrayList<>();
                            list.add(new Category(name, new Icon(icon, 0), new Color(color, 0), description));
                            dao.insertCategories(list, new AppDatabase.OnInsert() {
                                @Override
                                public void insert(final boolean result) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String msg1 = "Category " + name + " added!";
                                            String msg2 = "Oops, there were troubles while creating category..\n.try again later";
                                            if (result)
                                            {
                                                Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), msg2, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        else
                        {
                            List<Category> list = new ArrayList<>();
                            categoryToUpdate.setName(name);
                            categoryToUpdate.setDescription(description);
                            categoryToUpdate.setIcon(new Icon(icon, 0));
                            categoryToUpdate.setColor(new Color(color, 0));

                            list.add(categoryToUpdate);
                            dao.updateCategories(list, new AppDatabase.OnUpdate()
                            {
                                @Override
                                public void update(final boolean result)
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String msg1 = "Category " + name + " updated!";
                                            String msg2 = "Oops, there were troubles while updating category..\n.try again later";
                                            if (result)
                                            {
                                                Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), msg2, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }

                    }
                }
            });
    }

    private boolean checkFields()
    {
        String name = editText_name.getText().toString();
        int descriptionCount = editText_description.getText().toString().length();

        if (name.isEmpty())
        {
            editText_name.setError("Name field is mandatory");
            editText_name.requestFocus();
            return false;
        }
        else
        {
            editText_name.setError(null);
        }

        if (descriptionCount > 50)
        {
            editText_description.setError("Characters limit is exceed");
            editText_name.requestFocus();
            return false;
        }

        return true;
    }

    public static class PickerDialog extends DialogFragment
    {
        private OnDialogPicker callback;
        private List<Resource> resources;
        private Resource selectedResource;
        private int resourceType;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Context context = getContext();
            Activity activity = getActivity();
            if (context != null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view;
                LayoutInflater inflater = LayoutInflater.from(context);
                if (activity != null)
                {
                    ViewGroup viewGroup = activity.findViewById(android.R.id.content);
                    view = inflater.inflate(R.layout.dialog_picker, viewGroup, false);
                }
                else
                {
                    view = inflater.inflate(R.layout.dialog_picker, null, false);
                }

                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                PickerAdapter adapter = new PickerAdapter(inflater, resources, new OnClickResource()
                {
                    @Override
                    public void onClick(Resource resource)
                    {
                        selectedResource = resource;
                    }
                });

                recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                recyclerView.setAdapter(adapter);

                Spannable span = new SpannableString(resourceType == Constants.TYPE_ICON ? "Pick category icon:" : "Pick category color:");
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary, null)),
                        0,
                        span.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setTitle(span);
                builder.setView(view);
                builder.setPositiveButton("Pick", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (selectedResource != null)
                        {
                            callback.picked(selectedResource);
                        }
                        else
                        {
                            callback.picked(null);
                        }

                    }
                });
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {}
                });


                return builder.create();
            }
            else
            {
                return super.onCreateDialog(savedInstanceState);
            }
        }

        private class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.Holder>
        {
            private LayoutInflater inflater;
            private List<Resource> resources;
            private List<Holder> mHolders;
            private int size;
            private OnClickResource callback;

            private PickerAdapter(LayoutInflater inflater, List<Resource> resources, OnClickResource callback)
            {
                this.inflater = inflater;
                this.resources = resources;
                this.callback = callback;
                if (resources != null)
                {
                    size = resources.size();
                }
                mHolders = new ArrayList<>();
            }

            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                Holder holder = new Holder(inflater.inflate(R.layout.holder_dialog_picker, parent, false));
                mHolders.add(holder);
                return holder;
            }


            @Override
            public void onBindViewHolder(@NonNull Holder holder, int position)
            {
                if (position < size)
                {
                    Resource resource = resources.get(position);
                    int type = resource.getType();
                    if (type == Constants.TYPE_ICON)
                    {
                        holder.iv.setImageDrawable(getResources().getDrawable(resource.getResource(), null));
                    }
                    else
                    {
                        holder.iv.setImageDrawable(getResources().getDrawable(R.drawable.baseline_fiber_manual_record_24, null));
                        holder.iv.setImageTintList(ColorStateList.valueOf(getResources().getColor(resource.getResource(), null)));
                    }
                }
            }


            @Override
            public int getItemCount()
            {
                if (resources != null)
                {
                    return resources.size();
                }
                else
                {
                    return 0;
                }
            }


            class Holder extends RecyclerView.ViewHolder
            {
                LinearLayout linearLayout;
                ImageView iv;

                Holder(View v)
                {
                    super(v);
                    iv = v.findViewById(R.id.imageView_resource);
                    linearLayout = v.findViewById(R.id.linearLayout);

                    iv.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View view)
                        {
                            int index = getAdapterPosition();
                            if (index < size)
                            {
                                Resource resource = resources.get(index);
                                for (Holder holder : mHolders)
                                {
                                    if (holder.getAdapterPosition() != getAdapterPosition())
                                    {
                                        holder.linearLayout.setBackgroundColor(getResources().getColor(android.R.color.white, null));
                                    }
                                    else
                                    {
                                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                                    }
                                }
                                callback.onClick(resource);
                            }
                        }
                    });

                }
            }
        }

        private interface OnClickResource
        {
            void onClick(Resource resource);
        }
    }

    private interface OnDialogPicker
    {
        void picked(Resource resource);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
