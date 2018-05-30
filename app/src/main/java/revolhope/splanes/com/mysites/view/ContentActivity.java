package revolhope.splanes.com.mysites.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.controller.CategoryAdapter;
import revolhope.splanes.com.mysites.helper.AppDatabase;
import revolhope.splanes.com.mysites.helper.AppDatabaseDao;
import revolhope.splanes.com.mysites.model.Category;

public class ContentActivity extends AppCompatActivity
{
    private CategoryAdapter adapter;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("My Sites");
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new CategoryAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.layout_newCategory).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        prefs = getSharedPreferences("revolhope.splanes.com.mysites", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppDatabaseDao dao = new AppDatabase(this);
        if (prefs.getBoolean("firstrun", true)) {
            dao.populate(OnPopulate);
            prefs.edit().putBoolean("firstrun", false).apply();
        }


        dao.getCategories(new AppDatabase.OnSelect<Category>()
        {
            @Override
            public void select(final List<Category> selection)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        adapter.setCategories(selection);
                    }
                });
            }
        });
    }
}
