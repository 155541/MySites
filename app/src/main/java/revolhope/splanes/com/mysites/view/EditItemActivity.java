package revolhope.splanes.com.mysites.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.AppDatabase;
import revolhope.splanes.com.mysites.helper.AppDatabaseDao;
import revolhope.splanes.com.mysites.helper.Constants;
import revolhope.splanes.com.mysites.model.Category;
import revolhope.splanes.com.mysites.model.Item;

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
    private Button button_done;

    private ImageView imageView_categoryIcon;
    private Category currCategory;
    private Item itemToUpdate;
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

        button_done = findViewById(R.id.button_done);

        imageView_categoryIcon = findViewById(R.id.imageView_category_icon);


        if (intent != null && intent.hasExtra(Constants.EXTRA_CATEGORY) && intent.hasExtra(Constants.EXTRA_ITEM) && actionBar != null)
        {
            currCategory = (Category) intent.getSerializableExtra(Constants.EXTRA_CATEGORY);
            itemToUpdate = (Item) intent.getSerializableExtra(Constants.EXTRA_ITEM);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Update Site");

            editText_name.setText(itemToUpdate.getName());
            editText_phone.setText(itemToUpdate.getPhone());
            editText_location.setText(itemToUpdate.getLocation());
            editText_email.setText(itemToUpdate.getMail());
            editText_web.setText(itemToUpdate.getWeb());
            editText_notes.setText(itemToUpdate.getNotes());

            //editText_name.setText(itemToUpdate.getName()); ------> TAGS

            imageView_categoryIcon.setImageDrawable(getDrawable(currCategory.getIcon().getResource()));
            button_done.setText(R.string.button_new_item_update);

            isNew = false;
        }
        else if (intent != null && actionBar != null && intent.hasExtra(Constants.EXTRA_CATEGORY))
        {
            currCategory = (Category) intent.getSerializableExtra(Constants.EXTRA_CATEGORY);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("New " + currCategory.getName());

            imageView_categoryIcon.setImageDrawable(getDrawable(currCategory.getIcon().getResource()));
            isNew = true;
        }
    }
}
