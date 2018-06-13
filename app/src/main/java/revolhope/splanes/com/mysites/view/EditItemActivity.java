package revolhope.splanes.com.mysites.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.controller.TagAdapter;
import revolhope.splanes.com.mysites.helper.AppDatabase;
import revolhope.splanes.com.mysites.helper.AppDatabaseDao;
import revolhope.splanes.com.mysites.helper.Constants;
import revolhope.splanes.com.mysites.model.Category;
import revolhope.splanes.com.mysites.model.Item;
import revolhope.splanes.com.mysites.model.Tag;

public class EditItemActivity extends AppCompatActivity
{

    private TextInputEditText editText_name;
    private TextInputEditText editText_phone;
    private TextInputEditText editText_location;
    private TextInputEditText editText_email;
    private TextInputEditText editText_web;
    private TextInputEditText editText_notes;

    private RecyclerView recyclerViewTags;
    private ImageView imageView_ubication;
    private TextView textView_noTags;


    private Category currCategory;
    private Item itemToUpdate;
    private List<Tag> tags;
    private TagAdapter adapter;
    private boolean isNew;
    private AppDatabaseDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();

        dao = AppDatabase.getInstance(this);

        editText_name = findViewById(R.id.editText_itemName);
        editText_phone = findViewById(R.id.editText_itemPhone);
        editText_location = findViewById(R.id.editText_itemLocation);
        editText_email = findViewById(R.id.editText_itemEmail);
        editText_web = findViewById(R.id.editText_itemWeb);
        editText_notes = findViewById(R.id.editText_itemNotes);

        recyclerViewTags = findViewById(R.id.recyclerView);
        recyclerViewTags.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new TagAdapter(this);
        recyclerViewTags.setAdapter(adapter);

        Button button_done = findViewById(R.id.button_done);
        ImageView imageView_categoryIcon = findViewById(R.id.imageView_category_icon);

        textView_noTags = findViewById(R.id.textView_noTags);

        recyclerViewTags.setVisibility(View.GONE);
        textView_noTags.setVisibility(View.VISIBLE);

        if (intent != null && intent.hasExtra(Constants.EXTRA_CATEGORY) && intent.hasExtra(Constants.EXTRA_ITEM_ID) && actionBar != null)
        {
            currCategory = (Category) intent.getSerializableExtra(Constants.EXTRA_CATEGORY);

            String id = intent.getStringExtra(Constants.EXTRA_ITEM_ID);
            String name = intent.getStringExtra(Constants.EXTRA_ITEM_NAME);
            String phone = intent.getStringExtra(Constants.EXTRA_ITEM_PHONE);
            String location = intent.getStringExtra(Constants.EXTRA_ITEM_LOCATION);
            String mail = intent.getStringExtra(Constants.EXTRA_ITEM_MAIL);
            String web = intent.getStringExtra(Constants.EXTRA_ITEM_WEB);
            String notes = intent.getStringExtra(Constants.EXTRA_ITEM_NOTES);
            String ubication = intent.getStringExtra(Constants.EXTRA_ITEM_UBICATION);
            Tag[] aux = (Tag[]) intent.getSerializableExtra(Constants.EXTRA_ITEM_TAG_ARRAY);
            itemToUpdate = new Item(id, name, phone, location, mail, web, notes, ubication, Arrays.asList(aux));

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Update Site");

            editText_name.setText(itemToUpdate.getName());
            editText_phone.setText(itemToUpdate.getPhone());
            editText_location.setText(itemToUpdate.getLocation());
            editText_email.setText(itemToUpdate.getMail());
            editText_web.setText(itemToUpdate.getWeb());
            editText_notes.setText(itemToUpdate.getNotes());

            //ConstraintLayout layout = findViewById(R.id.constraintLayout);
            //layout.setBackgroundColor(getColor(currCategory.getColor().getResource()));
            imageView_categoryIcon.setImageDrawable(getDrawable(currCategory.getIcon().getResource()));
            button_done.setText(R.string.button_new_item_update);

            dao.getTagsByItem(itemToUpdate.getId(), new AppDatabase.OnSelect<Tag>()
            {
                @Override
                public void select(final List<Tag> selection)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (selection != null && !selection.isEmpty())
                            {
                                recyclerViewTags.setVisibility(View.VISIBLE);
                                textView_noTags.setVisibility(View.GONE);
                                tags = selection;
                                adapter.setTags(selection);
                            }
                        }
                    });
                }
            });

            isNew = false;
        }
        else if (intent != null && actionBar != null && intent.hasExtra(Constants.EXTRA_CATEGORY))
        {
            currCategory = (Category) intent.getSerializableExtra(Constants.EXTRA_CATEGORY);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("New " + currCategory.getName());

            imageView_categoryIcon.setImageDrawable(getDrawable(currCategory.getIcon().getResource()));
            tags = new ArrayList<>();
            isNew = true;
        }

        button_done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (checkFields())
                {
                    List<Item> list = new ArrayList<>();

                    Item item = new Item(
                            editText_name.getText().toString().trim(),
                            editText_phone.getText().toString().trim(),
                            editText_location.getText().toString().trim(),
                            editText_web.getText().toString().trim(),
                            editText_email.getText().toString().trim(),
                            editText_notes.getText().toString().trim(),
                            null, // TODO: TAKE A LOOK! -> STILL IMPLEMENT
                            tags);
                    list.add(item);

                    if (isNew)
                    {
                        dao.insertItems(list, currCategory.getId(), new AppDatabase.OnInsert()
                        {
                            @Override
                            public void insert(final boolean result)
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        String msg = result ? "Site added!" : "Oops, we've got some troubles while adding site..\nTry again later..";
                                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                    else
                    {
                        List<Item> aux = new ArrayList<>();
                        aux.add(itemToUpdate);
                        dao.updateItems(aux, new AppDatabase.OnUpdate()
                        {
                            @Override
                            public void update(boolean result)
                            {
                                System.out.println(" :......: UPDATE ITEM :......: " + result);
                            }
                        });
                    }
                }
            }
        });

        findViewById(R.id.imageView_item_add_tag).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dao.getTags(new AppDatabase.OnSelect<Tag>()
                {
                    @Override
                    public void select(final List<Tag> selection)
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                AddTagDialog dialog = new AddTagDialog();
                                dialog.allTags = selection;
                                dialog.callback = new AddTagDialog.OnAddTag()
                                {
                                    @Override
                                    public void add(Tag tag)
                                    {
                                        tags.add(tag);
                                        runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                adapter.setTags(tags);
                                                recyclerViewTags.setVisibility(View.VISIBLE);
                                                textView_noTags.setVisibility(View.GONE);
                                            }
                                        });
                                    }
                                };
                                dialog.show(getSupportFragmentManager(), "AddTagDialog");
                            }
                        });
                    }
                });
            }
        });

        findViewById(R.id.imageView_item_remove_tag).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RemoveTagDialog dialog = new RemoveTagDialog();
                dialog.itemTags = tags;
                dialog.callback = new RemoveTagDialog.OnRemoveTag()
                {
                    @Override
                    public void remove(List<Tag> toDelete)
                    {
                        tags.removeAll(toDelete);
                        adapter.setTags(tags);
                        if (tags.isEmpty())
                        {
                            recyclerViewTags.setVisibility(View.GONE);
                            textView_noTags.setVisibility(View.VISIBLE);
                        }
                    }
                };
                dialog.show(getSupportFragmentManager(), "RemoveTagDialog");
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (tags != null && !tags.isEmpty())
        {
            recyclerViewTags.setVisibility(View.VISIBLE);
            textView_noTags.setVisibility(View.GONE);
            adapter.setTags(tags);
        }
    }

    private boolean checkFields()
    {
        String str = editText_name.getText().toString();

        if (str.isEmpty())
        {
            editText_name.setError("Name field is mandatory");
            editText_name.requestFocus();
            return false;
        }
        else
        {
            editText_name.setError(null);
        }

        str = editText_phone.getText().toString();

        if (str.isEmpty())
        {
            editText_phone.setError("Phone field is mandatory");
            editText_phone.requestFocus();
            return false;
        }
        else if(str.length() < 9)
        {
            editText_phone.setError("Phone must have 9 characters minimum");
            editText_phone.requestFocus();
            return false;
        }
        else
        {
            try
            {
                Integer.parseInt(str);
            }
            catch (NumberFormatException ignored)
            {
                editText_phone.setError("Phone entered has not a valid number");
                editText_phone.requestFocus();
                return false;
            }
        }

        str = editText_notes.getText().toString();

        if (str.length() > 75)
        {
            editText_notes.setError("Length must not be longer than 75 characters");
            editText_notes.requestFocus();
            return false;
        }
        else
        {
            editText_notes.setError(null);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class AddTagDialog extends DialogFragment
    {
        private List<Tag> allTags;
        private OnAddTag callback;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            final Context context = getContext();
            Activity activity = getActivity();

            if(allTags == null)
            {
                allTags = new ArrayList<>();
            }

            if (context != null && activity != null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                ViewGroup viewGroup = activity.findViewById(android.R.id.content);

                View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_item, viewGroup, false);

                final AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView_tag);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.select_dialog_item, getTags());

                autoCompleteTextView.setThreshold(1);
                autoCompleteTextView.setAdapter(adapter);

                Spannable spannable = new SpannableString("Add Tag");
                spannable.setSpan(new ForegroundColorSpan(context.getColor(R.color.colorPrimary)), 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.setTitle(spannable);
                builder.setView(view);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String tagName = autoCompleteTextView.getText().toString().trim();
                        if (!tagName.isEmpty())
                        {
                            if (tagName.contains("# "))
                            {
                                tagName = tagName.replace("# ","");
                            }
                            tagName = "# " + tagName;
                            for (Tag tag : allTags)
                            {
                                if (tag.getName().equals(tagName))
                                {
                                    callback.add(tag);
                                    return;
                                }
                            }
                            callback.add(new Tag(tagName));
                        }
                        else
                        {
                            Toast.makeText(context, "Tag name is mandatory", Toast.LENGTH_SHORT).show();
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

            return super.onCreateDialog(savedInstanceState);
        }

        private String[] getTags()
        {
            String[] result = new String[allTags.size()];
            int i = 0;
            for (Tag tag : allTags)
            {
                result[i] = tag.getName();
                i++;
            }
            return result;
        }

        private interface OnAddTag
        {
            void add(Tag tag);
        }
    }

    public static class RemoveTagDialog extends DialogFragment
    {
        private List<Tag> itemTags;
        private List<Tag> toDelete;
        private OnRemoveTag callback;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Context context = getContext();
            if (context != null && itemTags != null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                toDelete = new ArrayList<>();

                Spannable spannable = new SpannableString("Remove Tags");
                spannable.setSpan(new ForegroundColorSpan(context.getColor(R.color.colorPrimary)), 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.setTitle(spannable);
                builder.setMultiChoiceItems(getTags(), null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean checked)
                    {
                        Tag tag = itemTags.get(i);
                        if (checked)
                        {
                            if (!toDelete.contains(tag))
                            {
                                toDelete.add(tag);
                            }
                        }
                        else
                        {
                            toDelete.remove(tag);
                        }
                    }
                });

                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        callback.remove(toDelete);
                    }
                });

                builder.setNegativeButton("back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                return builder.create();
            }
            return super.onCreateDialog(savedInstanceState);
        }

        private String[] getTags()
        {
            String[] result = new String[itemTags.size()];
            int i = 0;
            for (Tag tag : itemTags)
            {
                result[i] = tag.getName();
                i++;
            }
            return result;
        }

        private interface OnRemoveTag
        {
            void remove(List<Tag> toDelete);
        }
    }
}
