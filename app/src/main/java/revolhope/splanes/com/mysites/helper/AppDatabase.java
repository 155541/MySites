package revolhope.splanes.com.mysites.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.model.Category;
import revolhope.splanes.com.mysites.model.Item;
import revolhope.splanes.com.mysites.model.Tag;

public class AppDatabase extends SQLiteOpenHelper implements AppDatabaseDao
{

// ============================================================================
//                            DATABASE PARAMETERS
// ============================================================================

    private static final String DB_NAME = "AppDatabase";
    private static int VERSION = 0;

// ============================================================================
//                                  TABLES
// ============================================================================

    private static final String TABLE_ICON = "TABLE_ICON";
    private static final String TABLE_COLOR = "TABLE_ICON";
    private static final String TABLE_CATEGORY = "TABLE_CATEGORY";
    private static final String TABLE_ITEM = "TABLE_ITEM";
    private static final String TABLE_TAG = "TABLE_TAG";
    private static final String TABLE_LINK_ITEM_TAG = "TABLE_LINK_ITEM_TAG";
    private static final String TABLE_LINK_ITEM_CATEGORY = "TABLE_LINK_ITEM_CATEGORY";

// ============================================================================
//                                  COLUMNS
// ============================================================================

    private static final String ICON_ID = "ICON_ID";
    private static final String ICON_RESOURCE = "ICON_RESOURCE";

    private static final String COLOR_ID = "COLOR_ID";
    private static final String COLOR_RESOURCE = "COLOR_RESOURCE";

    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String CATEGORY_ICON = "CATEGORY_ICON";
    private static final String CATEGORY_COLOR = "CATEGORY_COLOR";
    private static final String CATEGORY_DESCRIPTION = "CATEGORY_DESCRIPTION";

    private static final String ITEM_ID = "ITEM_ID";
    private static final String ITEM_NAME = "ITEM_NAME";
    private static final String ITEM_PHONE = "ITEM_PHONE";
    private static final String ITEM_LOCATION = "ITEM_LOCATION";
    private static final String ITEM_WEB = "ITEM_WEB";
    private static final String ITEM_MAIL = "ITEM_MAIL";
    private static final String ITEM_NOTES = "ITEM_NOTES";
    private static final String ITEM_UBICATION = "ITEM_UBICATION";

    private static final String TAG_ID = "TAG_ID";
    private static final String TAG_NAME = "TAG_NAME";

    private static final String LINK_ITEM_TAG_ID_ITEM = "LINK_ITEM_TAG_ID_ITEM";
    private static final String LINK_ITEM_TAG_ID_TAG = "LINK_ITEM_TAG_ID_TAG";

    private static final String LINK_ITEM_CATEGORY_ID_ITEM = "LINK_ITEM_CATEGORY_ID_ITEM";
    private static final String LINK_ITEM_CATEGORY_ID_CATEGORY = "LINK_ITEM_CATEGORY_ID_CATEGORY";


// ============================================================================
//                           CREATE TABLE SENTENCES
// ============================================================================

    private static final String CREATE_TABLE_ICON =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ICON + "("
                    + ICON_ID + " VARCHAR PRIMARY KEY, "
                    + ICON_RESOURCE + " INTEGER UNIQUE)";


    private static final String CREATE_TABLE_COLOR =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COLOR + "("
                    + COLOR_ID + " VARCHAR PRIMARY KEY, "
                    + COLOR_RESOURCE + " INTEGER UNIQUE)";

    private static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY + "("
                    + CATEGORY_ID + " VARCHAR PRIMARY KEY, "
                    + CATEGORY_NAME + " VARCHAR NOT NULL, "
                    + CATEGORY_ICON + " VARCHAR, "
                    + CATEGORY_COLOR + " VARCHAR, "
                    + CATEGORY_DESCRIPTION + " VARCHAR DEFAULT NULL, "
                    + "FOREIGN KEY (" + CATEGORY_ICON + ") REFERENCES " + TABLE_ICON + "(" + ICON_ID + "), "
                    + "FOREIGN KEY (" + CATEGORY_COLOR + ") REFERENCES " + TABLE_COLOR + "(" + COLOR_ID + "))";

    private static final String CREATE_TABLE_ITEM =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ITEM + "("
                    + ITEM_ID + " VARCHAR PRIMARY KEY, "
                    + ITEM_NAME + " VARCHAR NOT NULL UNIQUE, "
                    + ITEM_PHONE + " VARCHAR NOT NULL UNIQUE, "
                    + ITEM_LOCATION + " VARCHAR DEFAULT NULL, "
                    + ITEM_WEB + " VARCHAR DEFAULT NULL, "
                    + ITEM_MAIL + " VARCHAR DEFAULT NULL, "
                    + ITEM_NOTES + " VARCHAR DEFAULT NULL, "
                    + ITEM_UBICATION + " VARCHAR DEFAULT NULL)";

    private static final String CREATE_TABLE_TAG =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TAG + "("
                    + TAG_ID + " VARCHAR PRIMARY KEY, "
                    + TAG_NAME + " VARCHAR NOT NULL UNIQUE)";

    private static final String CREATE_TABLE_LINK_ITEM_TAG =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LINK_ITEM_TAG + "("
                    + LINK_ITEM_TAG_ID_ITEM + " VARCHAR, "
                    + LINK_ITEM_TAG_ID_TAG + " VARCHAR, "
                    + "PRIMARY KEY (" + LINK_ITEM_TAG_ID_ITEM + ", " + LINK_ITEM_TAG_ID_TAG +"), "
                    + "FOREIGN KEY (" + LINK_ITEM_TAG_ID_ITEM + ") REFERENCES " + TABLE_ITEM + "(" + ITEM_ID + "), "
                    + "FOREIGN KEY (" + LINK_ITEM_TAG_ID_TAG + ") REFERENCES " + TABLE_TAG + "(" + TAG_ID + "))";

    private static final String CREATE_TABLE_LINK_ITEM_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LINK_ITEM_CATEGORY + "("
                    + LINK_ITEM_CATEGORY_ID_ITEM + " VARCHAR, "
                    + LINK_ITEM_CATEGORY_ID_CATEGORY + " VARCHAR, "
                    + "PRIMARY KEY (" + LINK_ITEM_CATEGORY_ID_ITEM + ", " + LINK_ITEM_CATEGORY_ID_CATEGORY +"), "
                    + "FOREIGN KEY (" + LINK_ITEM_CATEGORY_ID_ITEM + ") REFERENCES " + TABLE_ITEM + "(" + ITEM_ID + "), "
                    + "FOREIGN KEY (" + LINK_ITEM_CATEGORY_ID_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + CATEGORY_ID + "))";

// ============================================================================
//                INITIALIZER & CONSTRUCTOR & POPULATE & DROP
// ============================================================================

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_ICON);
        db.execSQL(CREATE_TABLE_COLOR);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_LINK_ITEM_TAG);
        db.execSQL(CREATE_TABLE_LINK_ITEM_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ICON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINK_ITEM_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINK_ITEM_CATEGORY);
        VERSION = newV;
        onCreate(db);
    }

    public AppDatabase(Context context)
    {
        super(context, DB_NAME, null, VERSION);
    }

    public void populate(@NonNull OnPopulate callback)
    {
        PopulateAsync async = new PopulateAsync(this.getWritableDatabase(), callback);
        async.execute();
    }

// ============================================================================
//                                    SELECTS
// ============================================================================

    @Override
    public void getCategories(@NonNull OnSelect<Category> callback)
    {
        SelectCategoriesAsync async = new SelectCategoriesAsync(this.getReadableDatabase(), callback);
        async.execute();
    }

    @Override
    public void getItems(@NonNull OnSelect<Item> callback)
    {
        SelectItemsAsync async = new SelectItemsAsync(this.getReadableDatabase(), callback);
        async.execute();
    }

    @Override
    public void getTags(@NonNull OnSelect<Tag> callback)
    {
        SelectTagsAsync async = new SelectTagsAsync(this.getReadableDatabase(), callback);
        async.execute();
    }

    @Override
    public void getItemsByCategory(@NonNull String categoryId, @NonNull OnSelect<Item> callback)
    {
        SelectItemsByCategoryAsync async = new SelectItemsByCategoryAsync(this.getReadableDatabase(), categoryId, callback);
        async.execute();
    }

    @Override
    public void getTagsByItem(@NonNull String itemId, @NonNull OnSelect<Tag> callback)
    {
        SelectTagsByItemAsync async = new SelectTagsByItemAsync(this.getReadableDatabase(), itemId, callback);
        async.execute();
    }

// ============================================================================
//                                    INSERTS
// ============================================================================

    @Override
    public void insertCategories(List<Category> categories, @NonNull OnInsert callback)
    {
        InsertCategoryAsync async = new InsertCategoryAsync(this.getWritableDatabase(), callback);
        async.execute(categories.toArray(new Category[0]));
    }

    @Override
    public void insertItems(List<Item> items, String categoryId, @NonNull OnInsert callback)
    {
        InsertItemAsync async = new InsertItemAsync(this.getWritableDatabase(), categoryId, callback);
        async.execute(items.toArray(new Item[0]));
    }

    @Override
    public void insertTags(List<Tag> tags, @NonNull String itemId, @NonNull OnInsert callback)
    {
        InsertTagAsync async = new InsertTagAsync(this.getWritableDatabase(), itemId, callback);
        async.execute(tags.toArray(new Tag[0]));
    }

    @Override
    public void bindTags(List<String> tagsId, @NonNull String itemId, @NonNull OnInsert callback)
    {
        BindTagAsync async = new BindTagAsync(this.getWritableDatabase(), itemId, callback);
        async.execute(tagsId.toArray(new String[0]));
    }


// ============================================================================
//                                    UPDATES
// ============================================================================

// ============================================================================
//                                    REMOVES
// ============================================================================

// ============================================================================
//                                    ASYNC TASK
// ============================================================================

    //-------------------------------------------------------------------//
    //                              POPULATE                             //
    //-------------------------------------------------------------------//

    private static class PopulateAsync extends AsyncTask<Void, Void, Boolean>
    {
        private SQLiteDatabase db;
        private OnPopulate callback;

        private final int[] colors =
                {
                        R.color.resource1,R.color.resource2,
                        R.color.resource3,R.color.resource4,
                        R.color.resource5,R.color.resource6,
                        R.color.resource7,R.color.resource8,
                        R.color.resource9,R.color.resource10,
                        android.R.color.black, android.R.color.white,
                        android.R.color.holo_red_dark, android.R.color.tertiary_text_light,
                };

        private final int[] drawablesRes =
                {
                        R.drawable.resource1_fastfood_24, R.drawable.resource2_hotel_24,
                        R.drawable.resource3_local_atm_24, R.drawable.resource4_local_bar_24,
                        R.drawable.resource5_local_cafe_24, R.drawable.resource6_local_pizza_24,
                        R.drawable.resource7_local_florist_24, R.drawable.resource8_local_play_24,
                        R.drawable.resource9_place_24, R.drawable.resource10_restaurant_24,
                        R.drawable.resource11_restaurant_menu_24, R.drawable.resource12_satellite_24
                };


        private PopulateAsync(SQLiteDatabase db, OnPopulate callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            ContentValues values= new ContentValues();

            // COLORS
            for (int color : colors)
            {
                values.put(COLOR_ID, UUID.randomUUID().toString());
                values.put(COLOR_RESOURCE, color);
                if (db.insert(TABLE_COLOR, null, values) == -1)
                {
                    db.close();
                    return false;
                }
            }

            // ICONS
            values.clear();
            for (int res : drawablesRes)
            {
                values.put(ICON_ID, UUID.randomUUID().toString());
                values.put(ICON_RESOURCE, res);
                if (db.insert(TABLE_COLOR, null, values) == -1)
                {
                    db.close();
                    return false;
                }
            }
            db.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            callback.populate(aBoolean);
        }
    }

    //-------------------------------------------------------------------//
    //                               INSERTS                             //
    //-------------------------------------------------------------------//

    private static class InsertCategoryAsync extends AsyncTask<Category, Void, Boolean>
    {
        private OnInsert callback;
        private SQLiteDatabase db;

        private InsertCategoryAsync(SQLiteDatabase db, OnInsert callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected Boolean doInBackground(Category... categories)
        {
            ContentValues values = new ContentValues();
            for (Category category : categories)
            {
                values.put(CATEGORY_ID, category.getId());
                values.put(CATEGORY_NAME, category.getName());
                values.put(CATEGORY_COLOR, category.getColor());
                values.put(CATEGORY_ICON, category.getIcon());
                values.put(CATEGORY_DESCRIPTION, category.getDescription());

                if (db.insert(TABLE_CATEGORY, null, values) == -1)
                {
                    db.close();
                    return false;
                }
            }
            db.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            callback.insert(aBoolean);
        }
    }

    private static class InsertItemAsync extends AsyncTask<Item, Void, Boolean>
    {
        private OnInsert callback;
        private SQLiteDatabase db;
        private String categoryId;

        private InsertItemAsync(SQLiteDatabase db, String categoryId, OnInsert callback)
        {
            this.callback = callback;
            this.db = db;
            this.categoryId = categoryId;
        }

        @Override
        protected Boolean doInBackground(Item... items)
        {
            ContentValues values = new ContentValues();
            ContentValues valuesLink = new ContentValues(2);
            for (Item item : items)
            {
                values.put(ITEM_ID, item.getId());
                values.put(ITEM_NAME, item.getName());
                values.put(ITEM_PHONE, item.getPhone());
                values.put(ITEM_LOCATION, item.getLocation());
                values.put(ITEM_MAIL, item.getMail());
                values.put(ITEM_WEB, item.getWeb());
                values.put(ITEM_NOTES, item.getNotes());
                values.put(ITEM_UBICATION, item.getUbication());

                valuesLink.put(LINK_ITEM_CATEGORY_ID_ITEM, item.getId());
                valuesLink.put(LINK_ITEM_CATEGORY_ID_CATEGORY, categoryId);

                if (db.insert(TABLE_ITEM, null, values) == -1 ||
                        db.insert(TABLE_LINK_ITEM_CATEGORY, null, valuesLink) == -1)
                {
                    db.close();
                    return false;
                }
            }
            db.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            callback.insert(aBoolean);
        }
    }

    private static class InsertTagAsync extends AsyncTask<Tag, Void, Boolean>
    {
        private OnInsert callback;
        private SQLiteDatabase db;
        private String itemId;

        private InsertTagAsync(SQLiteDatabase db, String itemId, OnInsert callback)
        {
            this.callback = callback;
            this.db = db;
            this.itemId = itemId;
        }

        @Override
        protected Boolean doInBackground(Tag... tags)
        {
            ContentValues values = new ContentValues();
            ContentValues valuesLink = new ContentValues(2);
            for (Tag tag : tags)
            {
                values.put(ITEM_ID, tag.getId());
                values.put(ITEM_NAME, tag.getName());

                valuesLink.put(LINK_ITEM_TAG_ID_TAG, tag.getId());
                valuesLink.put(LINK_ITEM_TAG_ID_ITEM, itemId);

                if (db.insert(TABLE_ITEM, null, values) == -1 ||
                        db.insert(TABLE_LINK_ITEM_TAG, null, valuesLink) == -1)
                {
                    db.close();
                    return false;
                }
            }
            db.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            callback.insert(aBoolean);
        }
    }

    private static class BindTagAsync extends AsyncTask<String, Void, Boolean>
    {
        private OnInsert callback;
        private SQLiteDatabase db;
        private String itemId;

        private BindTagAsync(SQLiteDatabase db, String itemId, OnInsert callback)
        {
            this.callback = callback;
            this.db = db;
            this.itemId = itemId;
        }

        @Override
        protected Boolean doInBackground(String... tagsId)
        {
            ContentValues values = new ContentValues(2);
            for (String tagId : tagsId)
            {
                values.put(LINK_ITEM_TAG_ID_TAG, tagId);
                values.put(LINK_ITEM_TAG_ID_ITEM, itemId);

                if (db.insert(TABLE_LINK_ITEM_TAG, null, values) == -1)
                {
                    db.close();
                    return false;
                }
            }
            db.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            callback.insert(aBoolean);
        }
    }

    //-------------------------------------------------------------------//
    //                               SELECTS                             //
    //-------------------------------------------------------------------//

    private static class SelectCategoriesAsync extends AsyncTask<Void, Void, List<Category>>
    {
        private OnSelect<Category> callback;
        private SQLiteDatabase db;

        private SelectCategoriesAsync(SQLiteDatabase db, OnSelect<Category> callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected List<Category> doInBackground(Void... voids)
        {

            String query = "SELECT * FROM " + TABLE_CATEGORY;

            try (Cursor cursor = db.rawQuery(query, null))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Category> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id, name, description;
                        int color, icon;

                        id = cursor.getString(cursor.getColumnIndex(CATEGORY_ID));
                        name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
                        icon = cursor.getInt(cursor.getColumnIndex(CATEGORY_ICON));
                        color = cursor.getInt(cursor.getColumnIndex(CATEGORY_COLOR));
                        description = cursor.getString(cursor.getColumnIndex(CATEGORY_DESCRIPTION));

                        list.add(new Category(id, name, icon, color, description));
                    }
                    while(cursor.moveToNext());
                    db.close();
                    return list;
                }
                else
                {
                    db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Category> list)
        {
            callback.select(list);
        }
    }

    private static class SelectItemsAsync extends AsyncTask<Void, Void, List<Item>>
    {
        private OnSelect<Item> callback;
        private SQLiteDatabase db;

        private SelectItemsAsync(SQLiteDatabase db, OnSelect<Item> callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected List<Item> doInBackground(Void... voids)
        {

            String query = "SELECT * FROM " + TABLE_ITEM;

            try (Cursor cursor = db.rawQuery(query, null))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Item> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id, name, phone, mail, web, location, notes, ubication;

                        id = cursor.getString(cursor.getColumnIndex(ITEM_ID));
                        name = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(ITEM_PHONE));
                        mail = cursor.getString(cursor.getColumnIndex(ITEM_MAIL));
                        web = cursor.getString(cursor.getColumnIndex(ITEM_WEB));
                        location = cursor.getString(cursor.getColumnIndex(ITEM_LOCATION));
                        notes = cursor.getString(cursor.getColumnIndex(ITEM_NOTES));
                        ubication = cursor.getString(cursor.getColumnIndex(ITEM_UBICATION));

                        list.add(new Item(id, name, phone, location, web, mail, notes, ubication));
                    }
                    while(cursor.moveToNext());
                    db.close();
                    return list;
                }
                else
                {
                    db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Item> list)
        {
            callback.select(list);
        }
    }

    private static class SelectTagsAsync extends AsyncTask<Void, Void, List<Tag>>
    {
        private OnSelect<Tag> callback;
        private SQLiteDatabase db;

        private SelectTagsAsync(SQLiteDatabase db, OnSelect<Tag> callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected List<Tag> doInBackground(Void... voids)
        {

            String query = "SELECT * FROM " + TABLE_TAG;

            try (Cursor cursor = db.rawQuery(query, null))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Tag> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id, name;

                        id = cursor.getString(cursor.getColumnIndex(TAG_ID));
                        name = cursor.getString(cursor.getColumnIndex(TAG_NAME));

                        list.add(new Tag(id, name));
                    }
                    while(cursor.moveToNext());
                    db.close();
                    return list;
                }
                else
                {
                    db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Tag> list)
        {
            callback.select(list);
        }
    }

    private static class SelectItemsByCategoryAsync extends AsyncTask<Void, Void, List<Item>>
    {
        private OnSelect<Item> callback;
        private String categoryId;
        private SQLiteDatabase db;

        private SelectItemsByCategoryAsync(SQLiteDatabase db, @NonNull String categoryId ,OnSelect<Item> callback)
        {
            this.callback = callback;
            this.categoryId = categoryId;
            this.db = db;
        }

        @Override
        protected List<Item> doInBackground(Void... voids)
        {

            String query =
                    " SELECT * FROM " + TABLE_ITEM + " i LEFT JOIN "
                    + TABLE_LINK_ITEM_CATEGORY + "link ON  i." + ITEM_ID + " = link." + LINK_ITEM_CATEGORY_ID_ITEM
                    + " WHERE link." + LINK_ITEM_CATEGORY_ID_CATEGORY + " = ?";

            try (Cursor cursor = db.rawQuery(query, new String[]{ categoryId }))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Item> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id, name, phone, mail, web, location, notes, ubication;

                        id = cursor.getString(cursor.getColumnIndex(ITEM_ID));
                        name = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(ITEM_PHONE));
                        mail = cursor.getString(cursor.getColumnIndex(ITEM_MAIL));
                        web = cursor.getString(cursor.getColumnIndex(ITEM_WEB));
                        location = cursor.getString(cursor.getColumnIndex(ITEM_LOCATION));
                        notes = cursor.getString(cursor.getColumnIndex(ITEM_NOTES));
                        ubication = cursor.getString(cursor.getColumnIndex(ITEM_UBICATION));

                        list.add(new Item(id, name, phone, location, web, mail, notes, ubication));
                    }
                    while(cursor.moveToNext());
                    db.close();
                    return list;
                }
                else
                {
                    db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Item> list)
        {
            callback.select(list);
        }
    }

    private static class SelectTagsByItemAsync extends AsyncTask<Void, Void, List<Tag>>
    {
        private OnSelect<Tag> callback;
        private String itemId;
        private SQLiteDatabase db;

        private SelectTagsByItemAsync(SQLiteDatabase db, @NonNull String itemId, OnSelect<Tag> callback)
        {
            this.callback = callback;
            this.itemId = itemId;
            this.db = db;
        }

        @Override
        protected List<Tag> doInBackground(Void... voids)
        {
            String query =
                    " SELECT * FROM " + TABLE_TAG + " t LEFT JOIN "
                    + TABLE_LINK_ITEM_TAG + "link ON  t." + TAG_ID + " = link." + LINK_ITEM_TAG_ID_TAG
                    + " WHERE link." + LINK_ITEM_TAG_ID_ITEM + " = ?";

            try (Cursor cursor = db.rawQuery(query, new String[]{ itemId }))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Tag> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id, name;

                        id = cursor.getString(cursor.getColumnIndex(TAG_ID));
                        name = cursor.getString(cursor.getColumnIndex(TAG_NAME));

                        list.add(new Tag(id, name));
                    }
                    while(cursor.moveToNext());
                    db.close();
                    return list;
                }
                else
                {
                    db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Tag> list)
        {
            callback.select(list);
        }
    }

    //-------------------------------------------------------------------//
    //                               UPDATES                             //
    //-------------------------------------------------------------------//

    //-------------------------------------------------------------------//
    //                               REMOVES                             //
    //-------------------------------------------------------------------//

// ============================================================================
//                                    CALLBACKS
// ============================================================================

    public interface OnPopulate
    {
        void populate(boolean result);
    }

    public interface OnInsert
    {
        void insert(boolean result);
    }

    public interface OnUpdate
    {
        void update(boolean result);
    }

    public interface OnRemove
    {
        void remove(boolean result);
    }

    public interface OnSelect<T>
    {
        void select(List<T> selection);
    }

}