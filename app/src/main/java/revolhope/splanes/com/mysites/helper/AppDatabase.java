package revolhope.splanes.com.mysites.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.model.Category;
import revolhope.splanes.com.mysites.model.Color;
import revolhope.splanes.com.mysites.model.Icon;
import revolhope.splanes.com.mysites.model.Item;
import revolhope.splanes.com.mysites.model.Resource;
import revolhope.splanes.com.mysites.model.Tag;

public class AppDatabase extends SQLiteOpenHelper implements AppDatabaseDao
{

// ============================================================================
//                            DATABASE PARAMETERS
// ============================================================================

    private static AppDatabase INSTANCE;

    private static final String DB_NAME = "AppDatabase";
    private static int VERSION = 1;

// ============================================================================
//                                  TABLES
// ============================================================================

    private static final String TABLE_ICON = "TABLE_ICON";
    private static final String TABLE_COLOR = "TABLE_COLOR";
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
    private static final String COLOR_RESOURCE_BACKGROUND = "COLOR_RESOURCE_BACKGROUND";

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
                    + COLOR_RESOURCE + " INTEGER UNIQUE,"
                    + COLOR_RESOURCE_BACKGROUND + " INTEGER UNIQUE)";

    private static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY + "("
                    + CATEGORY_ID + " VARCHAR PRIMARY KEY, "
                    + CATEGORY_NAME + " VARCHAR NOT NULL, "
                    + CATEGORY_ICON + " VARCHAR, "
                    + CATEGORY_COLOR + " VARCHAR, "
                    + CATEGORY_DESCRIPTION + " VARCHAR DEFAULT NULL, "
                    + "FOREIGN KEY (" + CATEGORY_ICON + ") REFERENCES " + TABLE_ICON + "(" + ICON_ID + ") "
                    + "ON UPDATE CASCADE ON DELETE CASCADE, "
                    + "FOREIGN KEY (" + CATEGORY_COLOR + ") REFERENCES " + TABLE_COLOR + "(" + COLOR_ID + ") "
                    + "ON UPDATE CASCADE ON DELETE CASCADE)";

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
                    + "FOREIGN KEY (" + LINK_ITEM_TAG_ID_ITEM + ") REFERENCES " + TABLE_ITEM + "(" + ITEM_ID + ") "
                    + "ON UPDATE CASCADE ON DELETE CASCADE, "
                    + "FOREIGN KEY (" + LINK_ITEM_TAG_ID_TAG + ") REFERENCES " + TABLE_TAG + "(" + TAG_ID + ") "
                    + "ON UPDATE CASCADE ON DELETE CASCADE)";

    private static final String CREATE_TABLE_LINK_ITEM_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LINK_ITEM_CATEGORY + "("
                    + LINK_ITEM_CATEGORY_ID_ITEM + " VARCHAR, "
                    + LINK_ITEM_CATEGORY_ID_CATEGORY + " VARCHAR, "
                    + "PRIMARY KEY (" + LINK_ITEM_CATEGORY_ID_ITEM + ", " + LINK_ITEM_CATEGORY_ID_CATEGORY +"), "
                    + "FOREIGN KEY (" + LINK_ITEM_CATEGORY_ID_ITEM + ") REFERENCES " + TABLE_ITEM + "(" + ITEM_ID + ") "
                    + "ON UPDATE CASCADE ON DELETE CASCADE, "
                    + "FOREIGN KEY (" + LINK_ITEM_CATEGORY_ID_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + CATEGORY_ID + ") "
                    + "ON UPDATE CASCADE ON DELETE CASCADE)";

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

    public static synchronized AppDatabaseDao getInstance(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new AppDatabase(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private AppDatabase(Context context)
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
    public void getCategories(@NonNull OnSelectMap<Category, Integer> callback)
    {
        SelectCategoriesAsync async = new SelectCategoriesAsync(this.getReadableDatabase(), callback);
        async.execute();
    }

    @Override
    public void getCategoriesByName(@NonNull OnSelectMap<Category, Integer> callback, @NonNull String categoryName)
    {
        SelectCategoriesByNameAsync async = new SelectCategoriesByNameAsync(this.getReadableDatabase(), categoryName, callback);
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

    @Override
    public void getColors(@NonNull OnSelect<Resource> callback)
    {
        SelectColorsAsync async = new SelectColorsAsync(this.getReadableDatabase(), callback);
        async.execute();
    }

    @Override
    public void getIcons(@NonNull OnSelect<Resource> callback)
    {
        SelectIconsAsync async = new SelectIconsAsync(this.getReadableDatabase(), callback);
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

    @Override
    public void updateCategories(List<Category> categories, OnUpdate callback)
    {
        UpdateCategoryAsync async = new UpdateCategoryAsync(this.getWritableDatabase(), callback);
        async.execute(categories.toArray(new Category[0]));
    }

    @Override
    public void updateItems(List<Item> items, OnUpdate callback)
    {
        UpdateItemAsync async = new UpdateItemAsync(this.getWritableDatabase(), callback);
        async.execute(items.toArray(new Item[0]));
    }

    // ============================================================================
//                                    REMOVES
// ============================================================================

    @Override
    public void removeCategories(List<Category> categories, OnRemove callback)
    {
        RemoveCategoryAsync async = new RemoveCategoryAsync(this.getWritableDatabase(), callback);
        async.execute(categories.toArray(new Category[0]));
    }

    // TODO:
    // TODO:
    // TODO: TO REMOVE

    @Override
    public void printDb()
    {
        this.print();
    }

    // TODO: END
    // TODO:
    // TODO:
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

        private final int[] colorsBackground =
                {
                        R.color.resource1_background,R.color.resource2_background,
                        R.color.resource3_background,R.color.resource4_background,
                        R.color.resource5_background,R.color.resource6_background,
                        R.color.resource7_background,R.color.resource8_background,
                        R.color.resource9_background,R.color.resource10_background,
                        android.R.color.tertiary_text_light,android.R.color.white,
                        android.R.color.holo_red_light, R.color.resource_default_background
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
            ContentValues values = new ContentValues();

            // COLORS
            int size = colors.length;
            for (int i = 0; i< size ; i++)
            {
                values.put(COLOR_ID, UUID.randomUUID().toString());
                values.put(COLOR_RESOURCE, colors[i]);
                values.put(COLOR_RESOURCE_BACKGROUND, colorsBackground[i]);
                if (db.insert(TABLE_COLOR, null, values) == -1)
                {
                    //TODO: db.close();
                    return false;
                }
            }

            // ICONS
            values.clear();
            for (int res : drawablesRes)
            {
                values.put(ICON_ID, UUID.randomUUID().toString());
                values.put(ICON_RESOURCE, res);
                if (db.insert(TABLE_ICON, null, values) == -1)
                {
                    //TODO: db.close();
                    return false;
                }
            }
            //TODO: db.close();
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
                values.put(CATEGORY_COLOR, category.getColor().getId());
                values.put(CATEGORY_ICON, category.getIcon().getId());
                values.put(CATEGORY_DESCRIPTION, category.getDescription());

                if (db.insert(TABLE_CATEGORY, null, values) == -1)
                {
                    //TODO: db.close();
                    return false;
                }
            }
            //TODO: db.close();
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
                    return false;
                }

                String query = "SELECT * FROM " + TABLE_TAG + " WHERE " + TAG_ID + " = ?";
                for (Tag tag : item.getTags())
                {
                    ContentValues values1 = new ContentValues(2);
                    values1.put(LINK_ITEM_TAG_ID_ITEM, item.getId());
                    values1.put(LINK_ITEM_TAG_ID_TAG, tag.getId());
                    long l1 = db.insert(TABLE_LINK_ITEM_TAG, null, values1);
                    long l2 = 0;
                    try (Cursor cursor = db.rawQuery(query, new String[]{ tag.getId() }))
                    {
                        if (cursor != null && cursor.getCount() == 0)
                        {
                            ContentValues values2 = new ContentValues();
                            values2.put(TAG_NAME, tag.getName());
                            values2.put(TAG_ID, tag.getId());
                            l2 = db.insert(TABLE_TAG, null, values2);
                        }
                    }
                    if (l1 == -1 || l2 == -1)
                    {
                        return false;
                    }
                }
            }
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
                    //TODO: db.close();
                    return false;
                }
            }
            //TODO: db.close();
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
                    //TODO: db.close();
                    return false;
                }
            }
            //TODO: db.close();
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

    private static class SelectCategoriesAsync extends AsyncTask<Void, Void, Map<Category, Integer>>
    {
        private OnSelectMap<Category, Integer> callback;
        private SQLiteDatabase db;

        private SelectCategoriesAsync(SQLiteDatabase db, OnSelectMap<Category, Integer> callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected Map<Category, Integer> doInBackground(Void... voids)
        {

            String query =
                    "SELECT cat." + CATEGORY_ID + "," +
                            " cat." + CATEGORY_NAME + "," +
                            " ic." + ICON_ID + "," +
                            " ic." + ICON_RESOURCE + "," +
                            " co." + COLOR_ID + "," +
                            " co." + COLOR_RESOURCE + "," +
                            " co." + COLOR_RESOURCE_BACKGROUND + "," +
                            " cat." + CATEGORY_DESCRIPTION + "," +
                            " COUNT( link."+ LINK_ITEM_CATEGORY_ID_ITEM + ")" +
                            " FROM " + TABLE_CATEGORY + " cat " +
                            " LEFT JOIN " + TABLE_ICON + " ic ON cat." + CATEGORY_ICON + " = ic." + ICON_ID +
                            " LEFT JOIN " + TABLE_COLOR + " co ON cat." + CATEGORY_COLOR + " = co." + COLOR_ID +
                            " LEFT JOIN " + TABLE_LINK_ITEM_CATEGORY + " link ON cat." + CATEGORY_ID + " = link." + LINK_ITEM_CATEGORY_ID_CATEGORY +
                            " GROUP BY cat." + CATEGORY_ID +
                            " ORDER BY cat." + CATEGORY_NAME + " ASC";

            try (Cursor cursor = db.rawQuery(query, null))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    Map<Category, Integer> map = new HashMap<>(cursor.getCount());
                    do
                    {
                        String id, name, description, colorId, iconId;
                        int colorRes, colorBackRes, iconRes, itemCounts;

                        id = cursor.getString(cursor.getColumnIndex(CATEGORY_ID));
                        name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
                        iconId = cursor.getString(cursor.getColumnIndex(ICON_ID));
                        colorId = cursor.getString(cursor.getColumnIndex(COLOR_ID));
                        iconRes = cursor.getInt(cursor.getColumnIndex(ICON_RESOURCE));
                        colorRes = cursor.getInt(cursor.getColumnIndex(COLOR_RESOURCE));
                        colorBackRes = cursor.getInt(cursor.getColumnIndex(COLOR_RESOURCE_BACKGROUND));
                        description = cursor.getString(cursor.getColumnIndex(CATEGORY_DESCRIPTION));
                        itemCounts = cursor.getInt(cursor.getColumnCount()-1);

                        Category cat = new Category(id, name, new Icon(iconId, iconRes), new Color(colorId, colorRes, colorBackRes), description);
                        map.put(cat, itemCounts);
                    }
                    while(cursor.moveToNext());
                    //TODO: db.close();
                    return map;
                }
                else
                {
                    //TODO: db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(Map<Category, Integer> map)
        {
            callback.select(map);
        }
    }

    private static class SelectCategoriesByNameAsync extends AsyncTask<Void, Void, Map<Category, Integer>>
    {
        private OnSelectMap<Category, Integer> callback;
        private SQLiteDatabase db;
        private String categoryName;

        private SelectCategoriesByNameAsync(SQLiteDatabase db, String categoryName, OnSelectMap<Category, Integer> callback)
        {
            this.callback = callback;
            this.db = db;
            this.categoryName = categoryName;
        }

        @Override
        protected Map<Category, Integer> doInBackground(Void... voids)
        {

            String query =
                    "SELECT cat." + CATEGORY_ID + "," +
                            " cat." + CATEGORY_NAME + "," +
                            " ic." + ICON_ID + "," +
                            " ic." + ICON_RESOURCE + "," +
                            " co." + COLOR_ID + "," +
                            " co." + COLOR_RESOURCE + "," +
                            " co." + COLOR_RESOURCE_BACKGROUND + "," +
                            " cat." + CATEGORY_DESCRIPTION + "," +
                            " COUNT( link."+ LINK_ITEM_CATEGORY_ID_ITEM + ")" +
                            " FROM " + TABLE_CATEGORY + " cat " +
                            " LEFT JOIN " + TABLE_ICON + " ic ON cat." + CATEGORY_ICON + " = ic." + ICON_ID +
                            " LEFT JOIN " + TABLE_COLOR + " co ON cat." + CATEGORY_COLOR + " = co." + COLOR_ID +
                            " LEFT JOIN " + TABLE_LINK_ITEM_CATEGORY + " link ON cat." + CATEGORY_ID + " = link." + LINK_ITEM_CATEGORY_ID_CATEGORY +
                            " WHERE cat." + CATEGORY_NAME + " LIKE ? " +
                            " GROUP BY cat." + CATEGORY_ID +
                            " ORDER BY cat." + CATEGORY_NAME + " ASC";

            try (Cursor cursor = db.rawQuery(query, new String[]{"%"+ categoryName +"%"}))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    Map<Category, Integer> map = new HashMap<>(cursor.getCount());
                    do
                    {
                        String id, name, description, colorId, iconId;
                        int colorRes, colorBackRes, iconRes, itemCounts;

                        id = cursor.getString(cursor.getColumnIndex(CATEGORY_ID));
                        name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
                        iconId = cursor.getString(cursor.getColumnIndex(ICON_ID));
                        colorId = cursor.getString(cursor.getColumnIndex(COLOR_ID));
                        iconRes = cursor.getInt(cursor.getColumnIndex(ICON_RESOURCE));
                        colorRes = cursor.getInt(cursor.getColumnIndex(COLOR_RESOURCE));
                        colorBackRes = cursor.getInt(cursor.getColumnIndex(COLOR_RESOURCE));
                        description = cursor.getString(cursor.getColumnIndex(CATEGORY_DESCRIPTION));
                        itemCounts = cursor.getInt(cursor.getColumnCount()-1);

                        Category cat = new Category(id, name, new Icon(iconId, iconRes), new Color(colorId, colorRes, colorBackRes), description);
                        map.put(cat, itemCounts);
                    }
                    while(cursor.moveToNext());
                    //TODO: db.close();
                    return map;
                }
                else
                {
                    //TODO: db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(Map<Category, Integer> map)
        {
            callback.select(map);
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
            String query2 = "SELECT * FROM " + TABLE_TAG + " tag" +
                    " LEFT JOIN " + TABLE_LINK_ITEM_TAG + " link ON tag." + TAG_ID + " = link." + LINK_ITEM_TAG_ID_TAG +
                    " WHERE link." + LINK_ITEM_TAG_ID_ITEM + " = ?";


            try (Cursor cursor = db.rawQuery(query, null))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Item> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id, name, phone, mail, web, location, notes, ubication;
                        List<Tag> tags = new ArrayList<>();

                        id = cursor.getString(cursor.getColumnIndex(ITEM_ID));
                        name = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(ITEM_PHONE));
                        mail = cursor.getString(cursor.getColumnIndex(ITEM_MAIL));
                        web = cursor.getString(cursor.getColumnIndex(ITEM_WEB));
                        location = cursor.getString(cursor.getColumnIndex(ITEM_LOCATION));
                        notes = cursor.getString(cursor.getColumnIndex(ITEM_NOTES));
                        ubication = cursor.getString(cursor.getColumnIndex(ITEM_UBICATION));

                        try (Cursor cursor1 = db.rawQuery(query2, new String[]{ id }))
                        {
                            if (cursor1 != null && cursor1.moveToFirst())
                            {
                                do
                                {
                                    String idTag, nameTag;

                                    idTag = cursor1.getString(cursor1.getColumnIndex(TAG_ID));
                                    nameTag = cursor1.getString(cursor1.getColumnIndex(TAG_NAME));
                                    tags.add(new Tag(idTag, nameTag));

                                } while (cursor1.moveToNext());
                            }
                        }

                        list.add(new Item(id, name, phone, location, web, mail, notes, ubication, tags));
                    }
                    while(cursor.moveToNext());
                    //TODO: db.close();
                    return list;
                }
                else
                {
                    //TODO: db.close();
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
                    //TODO: db.close();
                    return list;
                }
                else
                {
                    //TODO: db.close();
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
                    + TABLE_LINK_ITEM_CATEGORY + " link ON  i." + ITEM_ID + " = link." + LINK_ITEM_CATEGORY_ID_ITEM
                    + " WHERE link." + LINK_ITEM_CATEGORY_ID_CATEGORY + " = ?";
            String query2 = "SELECT * FROM " + TABLE_TAG + " tag" +
                    " LEFT JOIN " + TABLE_LINK_ITEM_TAG + " link ON tag." + TAG_ID + " = link." + LINK_ITEM_TAG_ID_TAG +
                    " WHERE link." + LINK_ITEM_TAG_ID_ITEM + " = ?";


            try (Cursor cursor = db.rawQuery(query, new String[]{ categoryId }))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Item> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id, name, phone, mail, web, location, notes, ubication;
                        List<Tag> tags = new ArrayList<>();

                        id = cursor.getString(cursor.getColumnIndex(ITEM_ID));
                        name = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(ITEM_PHONE));
                        mail = cursor.getString(cursor.getColumnIndex(ITEM_MAIL));
                        web = cursor.getString(cursor.getColumnIndex(ITEM_WEB));
                        location = cursor.getString(cursor.getColumnIndex(ITEM_LOCATION));
                        notes = cursor.getString(cursor.getColumnIndex(ITEM_NOTES));
                        ubication = cursor.getString(cursor.getColumnIndex(ITEM_UBICATION));

                        try (Cursor cursor1 = db.rawQuery(query2, new String[]{ id }))
                        {
                            if (cursor1 != null && cursor1.moveToFirst())
                            {
                                do
                                {
                                    String idTag, nameTag;

                                    idTag = cursor1.getString(cursor1.getColumnIndex(TAG_ID));
                                    nameTag = cursor1.getString(cursor1.getColumnIndex(TAG_NAME));
                                    tags.add(new Tag(idTag, nameTag));

                                } while (cursor1.moveToNext());
                            }
                        }

                        list.add(new Item(id, name, phone, location, web, mail, notes, ubication, tags));
                    }
                    while(cursor.moveToNext());
                    return list;
                }
                else
                {
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
                    + TABLE_LINK_ITEM_TAG + " link ON  t." + TAG_ID + " = link." + LINK_ITEM_TAG_ID_TAG
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
                    //TODO: db.close();
                    return list;
                }
                else
                {
                    //TODO: db.close();
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

    private static class SelectColorsAsync extends AsyncTask<Void, Void, List<Resource>>
    {
        private OnSelect<Resource> callback;
        private SQLiteDatabase db;

        private SelectColorsAsync(SQLiteDatabase db, OnSelect<Resource> callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected List<Resource> doInBackground(Void... voids)
        {

            String query = "SELECT * FROM " + TABLE_COLOR;

            try (Cursor cursor = db.rawQuery(query, null))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Resource> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id;
                        int resource, backgroundRes;

                        id = cursor.getString(cursor.getColumnIndex(COLOR_ID));
                        resource = cursor.getInt(cursor.getColumnIndex(COLOR_RESOURCE));
                        backgroundRes = cursor.getInt(cursor.getColumnIndex(COLOR_RESOURCE_BACKGROUND));
                        list.add(new Color(id, resource, backgroundRes));
                    }
                    while(cursor.moveToNext());
                    //TODO: db.close();
                    return list;
                }
                else
                {
                    //TODO: db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Resource> list)
        {
            callback.select(list);
        }
    }

    private static class SelectIconsAsync extends AsyncTask<Void, Void, List<Resource>>
    {
        private OnSelect<Resource> callback;
        private SQLiteDatabase db;

        private SelectIconsAsync(SQLiteDatabase db, OnSelect<Resource> callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected List<Resource> doInBackground(Void... voids)
        {

            String query = "SELECT * FROM " + TABLE_ICON;

            try (Cursor cursor = db.rawQuery(query, null))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    List<Resource> list = new ArrayList<>(cursor.getCount());
                    do
                    {
                        String id;
                        int resource;

                        id = cursor.getString(cursor.getColumnIndex(ICON_ID));
                        resource = cursor.getInt(cursor.getColumnIndex(ICON_RESOURCE));

                        list.add(new Icon(id, resource));
                    }
                    while(cursor.moveToNext());
                    //TODO: db.close();
                    return list;
                }
                else
                {
                    //TODO: db.close();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Resource> list)
        {
            callback.select(list);
        }
    }

    //-------------------------------------------------------------------//
    //                               UPDATES                             //
    //-------------------------------------------------------------------//

    private static class UpdateCategoryAsync extends AsyncTask<Category, Void, Boolean>
    {
        private OnUpdate callback;
        private SQLiteDatabase db;

        private UpdateCategoryAsync(SQLiteDatabase db, OnUpdate callback)
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
                values.put(CATEGORY_COLOR, category.getColor().getId());
                values.put(CATEGORY_ICON, category.getIcon().getId());
                values.put(CATEGORY_DESCRIPTION, category.getDescription());

                if (db.update(TABLE_CATEGORY, values, CATEGORY_ID + " = ?", new String[]{ category.getId() }) != 1)
                {
                    //TODO: db.close();
                    return false;
                }
            }
            //TODO: db.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            callback.update(aBoolean);
        }
    }

    private static class UpdateItemAsync extends AsyncTask<Item, Void, Boolean>
    {
        private OnUpdate callback;
        private SQLiteDatabase db;

        private UpdateItemAsync(SQLiteDatabase db, OnUpdate callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected Boolean doInBackground(Item... items)
        {
            ContentValues values = new ContentValues();
            ContentValues valuesTag = new ContentValues();

            String query = "SELECT * FROM " + TABLE_LINK_ITEM_TAG + " WHERE " + LINK_ITEM_TAG_ID_ITEM + " = ?";
            String tagId;
            List<String> tagsId = new ArrayList<>();

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

                try (Cursor cursor = db.rawQuery(query, new String[] { item.getId() }))
                {
                    if (cursor != null && cursor.moveToFirst())
                    {
                        do
                        {
                            tagId = cursor.getString(cursor.getColumnIndex(LINK_ITEM_TAG_ID_TAG));
                            tagsId.add(tagId);

                        } while(cursor.moveToNext());
                    }
                }
                boolean exists;
                for (Tag tag : item.getTags())
                {
                    exists = false;

                    for (String id : tagsId)
                    {
                        if (tag.getId().equals(id))
                        {
                            exists = true;
                            tagsId.remove(id);
                            break;
                        }
                    }

                    if (!exists)
                    {
                        valuesTag.put(LINK_ITEM_TAG_ID_ITEM, item.getId());
                        valuesTag.put(LINK_ITEM_TAG_ID_TAG, tag.getId());
                        int i = db.update(TABLE_LINK_ITEM_TAG, valuesTag, LINK_ITEM_TAG_ID_ITEM + " = ?", new String[] { item.getId() });
                        if (i != 1)
                        {
                            return false;
                        }
                    }
                }

                for (String id : tagsId)
                {
                    int i = db.delete(TABLE_LINK_ITEM_TAG,
                            LINK_ITEM_TAG_ID_ITEM + " = ? AND " + LINK_ITEM_TAG_ID_TAG + " = ?",
                            new String[]{ item.getId(), id });
                    if (i != 1)
                    {
                        return false;
                    }
                }

                if (db.update(TABLE_CATEGORY, values, CATEGORY_ID + " = ?", new String[]{ item.getId() }) != 1)
                {
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            callback.update(aBoolean);
        }
    }

    //-------------------------------------------------------------------//
    //                               REMOVES                             //
    //-------------------------------------------------------------------//

    private static class RemoveCategoryAsync extends AsyncTask<Category, Void, Boolean>
    {
        private OnRemove callback;
        private SQLiteDatabase db;

        private RemoveCategoryAsync(SQLiteDatabase db, OnRemove callback)
        {
            this.callback = callback;
            this.db = db;
        }

        @Override
        protected Boolean doInBackground(Category... categories)
        {
            for (Category category : categories)
            {
                if (db.delete(TABLE_CATEGORY,CATEGORY_ID + " = ?", new String[]{ category.getId() }) != 1)
                {
                    //TODO: db.close();
                    return false;
                }
            }
            //TODO: db.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            callback.remove(aBoolean);
        }
    }

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

    public interface OnSelectMap<K,V>
    {
        void select(Map<K,V> selectedMap);
    }

// ============================================================================
//                                    PRINT DB
// ============================================================================

    public void print()
    {
        String query = "SELECT * FROM " + TABLE_COLOR;
        SQLiteDatabase db = getReadableDatabase();

        System.out.println(" :......: TABLE COLOR :......:");
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null && c.moveToFirst())
            {
                do
                {
                    System.out.println(" :......: COLOR ID :......: " + c.getString(c.getColumnIndex(COLOR_ID)));
                    System.out.println(" :......: COLOR RESOURCE :......: " + c.getInt(c.getColumnIndex(COLOR_RESOURCE)));
                    System.out.println(" :......: COLOR BACKGROUND RESOURCE :......: " + c.getInt(c.getColumnIndex(COLOR_RESOURCE_BACKGROUND)));

                } while(c.moveToNext());
            }
        }

        query = "SELECT * FROM " + TABLE_ICON;
        System.out.println(" :......: TABLE ICON :......:");
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null && c.moveToFirst())
            {
                do
                {
                    System.out.println(" :......: ICON ID :......: " + c.getString(c.getColumnIndex(ICON_ID)));
                    System.out.println(" :......: ICON RESOURCE :......: " + c.getInt(c.getColumnIndex(ICON_RESOURCE)));

                } while(c.moveToNext());
            }
        }

        query = "SELECT * FROM " + TABLE_CATEGORY;
        System.out.println(" :......: TABLE CATEGORY :......:");
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null && c.moveToFirst())
            {
                do
                {
                    System.out.println(" :......: CATEGORY ID :......: " + c.getString(c.getColumnIndex(CATEGORY_ID)));
                    System.out.println(" :......: CATEGORY NAME :......: " + c.getString(c.getColumnIndex(CATEGORY_NAME)));
                    System.out.println(" :......: CATEGORY COLOR ID :......: " + c.getString(c.getColumnIndex(CATEGORY_COLOR)));
                    System.out.println(" :......: CATEGORY ICON ID :......: " + c.getString(c.getColumnIndex(CATEGORY_ICON)));
                    System.out.println(" :......: CATEGORY DESCRIPTION :......: " + c.getString(c.getColumnIndex(CATEGORY_DESCRIPTION)));

                } while(c.moveToNext());
            }
        }

        query = "SELECT * FROM " + TABLE_ITEM;
        System.out.println(" :......: TABLE ITEM :......:");
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null && c.moveToFirst())
            {
                do
                {
                    System.out.println(" :......: ITEM ID :......: " + c.getString(c.getColumnIndex(ITEM_ID)));
                    System.out.println(" :......: ITEM NAME :......: " + c.getString(c.getColumnIndex(ITEM_NAME)));
                    System.out.println(" :......: ITEM PHONE :......: " + c.getString(c.getColumnIndex(ITEM_PHONE)));
                    System.out.println(" :......: ITEM LOCATION :......: " + c.getString(c.getColumnIndex(ITEM_LOCATION)));
                    System.out.println(" :......: ITEM MAIL :......: " + c.getString(c.getColumnIndex(ITEM_MAIL)));
                    System.out.println(" :......: ITEM WEB :......: " + c.getString(c.getColumnIndex(ITEM_WEB)));
                    System.out.println(" :......: ITEM NOTES :......: " + c.getString(c.getColumnIndex(ITEM_NOTES)));
                    System.out.println(" :......: ITEM UBICATION :......: " + c.getString(c.getColumnIndex(ITEM_UBICATION)));

                } while(c.moveToNext());
            }
        }

        query = "SELECT * FROM " + TABLE_TAG;
        System.out.println(" :......: TABLE TAG :......:");
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null && c.moveToFirst())
            {
                do
                {
                    System.out.println(" :......: TAG ID :......: " + c.getString(c.getColumnIndex(TAG_ID)));
                    System.out.println(" :......: TAG NAME :......: " + c.getString(c.getColumnIndex(TAG_NAME)));

                } while(c.moveToNext());
            }
        }

        query = "SELECT * FROM " + TABLE_LINK_ITEM_CATEGORY;
        System.out.println(" :......: TABLE LINK ITEM-CATEGORY :......:");
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null && c.moveToFirst())
            {
                do
                {
                    System.out.println(" :......: LINK ID CATEGORY :......: " + c.getString(c.getColumnIndex(LINK_ITEM_CATEGORY_ID_CATEGORY)));
                    System.out.println(" :......: LINK ID ITEM :......: " + c.getString(c.getColumnIndex(LINK_ITEM_CATEGORY_ID_ITEM)));

                } while(c.moveToNext());
            }
        }

        query = "SELECT * FROM " + TABLE_LINK_ITEM_TAG;
        System.out.println(" :......: TABLE LINK ITEM-TAG :......:");
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null && c.moveToFirst())
            {
                do
                {
                    System.out.println(" :......: LINK ID TAG :......: " + c.getString(c.getColumnIndex(LINK_ITEM_TAG_ID_TAG)));
                    System.out.println(" :......: LINK ID ITEM :......: " + c.getString(c.getColumnIndex(LINK_ITEM_TAG_ID_ITEM)));

                } while(c.moveToNext());
            }
        }
    }

}
