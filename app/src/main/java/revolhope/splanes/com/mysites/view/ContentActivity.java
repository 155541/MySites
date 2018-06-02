package revolhope.splanes.com.mysites.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private AppDatabaseDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        dao = AppDatabase.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
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
                Intent i = new Intent(getApplicationContext(), NewCategoryActivity.class);
                startActivity(i);
            }
        });

        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                {
                    if (actionBar != null)
                    {
                        actionBar.setTitle("");
                    }
                    searchView.setMaxWidth(Integer.MAX_VALUE);
                }
                else
                {
                    if (actionBar != null)
                    {
                        actionBar.setTitle("My Sites");
                    }
                    onResume();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if (!query.isEmpty())
                {
                    dao.getCategoriesByName(new AppDatabase.OnSelect<Category>()
                    {
                        @Override
                        public void select(final List<Category> selection)
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    adapter.setCategories(selection);
                                }
                            });
                        }
                    }, query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });



        prefs = getSharedPreferences("revolhope.splanes.com.mysites", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true))
        {
            dao.populate(new AppDatabase.OnPopulate()
            {
                @Override
                public void populate(boolean result)
                {
                    // TODO
                }
            });
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
