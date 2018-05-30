package revolhope.splanes.com.mysites.helper;

import android.support.annotation.NonNull;

import java.util.List;

import revolhope.splanes.com.mysites.model.Category;
import revolhope.splanes.com.mysites.model.Item;
import revolhope.splanes.com.mysites.model.Tag;

public interface AppDatabaseDao
{
    void populate(@NonNull AppDatabase.OnPopulate callback);

    // SELECTS
    void getCategories(@NonNull AppDatabase.OnSelect<Category> callback);
    void getItems(@NonNull AppDatabase.OnSelect<Item> callback);
    void getTags(@NonNull AppDatabase.OnSelect<Tag> callback);
    void getItemsByCategory(@NonNull String categoryId, @NonNull AppDatabase.OnSelect<Item> callback);
    void getTagsByItem(@NonNull String itemId, @NonNull AppDatabase.OnSelect<Tag> callback);

    // INSERTS
    void insertCategories(List<Category> categories, AppDatabase.OnInsert callback);
    void insertItems(List<Item> items, String categoryId, AppDatabase.OnInsert callback);
    void insertTags(List<Tag> tags, String itemId, AppDatabase.OnInsert callback);
    void bindTags(List<String> tagsId, String itemId, AppDatabase.OnInsert callback);
}
