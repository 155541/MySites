package revolhope.splanes.com.mysites.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.AppBarStateChangeListener;
import revolhope.splanes.com.mysites.helper.Constants;
import revolhope.splanes.com.mysites.model.Category;

public class CategoryActivity extends AppCompatActivity {

    private Category currCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();
        if (intent != null)
        {
            currCategory = (Category) intent.getSerializableExtra(Constants.EXTRA_CATEGORY);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        collapsingToolbarLayout.setTitle(currCategory.getName());

        if (actionBar != null)
        {
            appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener()
            {
                @Override
                public void onStateChanged(AppBarLayout appBarLayout, int state)
                {
                    if (state == Constants.STATE_COLLAPSED)
                    {
                        actionBar.setIcon(null);
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                    else if (state == Constants.STATE_EXPANDED)
                    {
                        actionBar.setIcon(currCategory.getIcon().getResource());
                        actionBar.setDisplayHomeAsUpEnabled(false);
                    }
                }
            });
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_update_category:

                Intent intent = new Intent(this, NewCategoryActivity.class);
                intent.putExtra(Constants.EXTRA_CATEGORY, currCategory);
                startActivity(intent);
                return true;

            case R.id.menu_remove_category:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
