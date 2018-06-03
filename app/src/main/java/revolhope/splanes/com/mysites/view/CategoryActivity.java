package revolhope.splanes.com.mysites.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.helper.AppBarStateChangeListener;
import revolhope.splanes.com.mysites.helper.AppDatabase;
import revolhope.splanes.com.mysites.helper.AppDatabaseDao;
import revolhope.splanes.com.mysites.helper.Constants;
import revolhope.splanes.com.mysites.model.Category;

public class CategoryActivity extends AppCompatActivity {

    private AppDatabaseDao dao;
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

        dao = AppDatabase.getInstance(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), EditItemActivity.class);
                i.putExtra(Constants.EXTRA_CATEGORY, currCategory);
                startActivity(i);
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

                Intent intent = new Intent(this, EditCategoryActivity.class);
                intent.putExtra(Constants.EXTRA_CATEGORY, currCategory);
                startActivity(intent);
                return true;

            case R.id.menu_remove_category:

                ConfirmationDialog dialog = new ConfirmationDialog();
                dialog.callback = new ConfirmationResult()
                {
                    @Override
                    public void confirmation(boolean result)
                    {
                        if (result)
                        {
                            List<Category> list = new ArrayList<>();
                            list.add(currCategory);
                            dao.removeCategories(list, new AppDatabase.OnRemove()
                            {
                                @Override
                                public void remove(final boolean result) {

                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            String msg;
                                            if (result)
                                            {
                                                msg = "Category has been removed correctly";
                                            }
                                            else
                                            {
                                                msg = "Oops, we've got troubles while removing category..\nTry again later";
                                            }
                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                    }
                };

                dialog.show(getSupportFragmentManager(), "ConfirmationDialog");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class ConfirmationDialog extends DialogFragment
    {
        ConfirmationResult callback;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Context context = getContext();

            if (context != null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                Spannable title = new SpannableString(getString(R.string.prompt_confirmation));
                Spannable msg1 = new SpannableString(getString(R.string.message_confirmation_delete_category1));
                Spannable msg2 = new SpannableString(getString(R.string.message_confirmation_delete_category2));

                title.setSpan(new ForegroundColorSpan(context.getColor(R.color.colorPrimary)), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msg1.setSpan(new ForegroundColorSpan(context.getColor(android.R.color.black)),0, msg1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msg2.setSpan(new RelativeSizeSpan(.5f),0, msg2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                StringBuilder sb = new StringBuilder();
                sb.append(msg1).append("\n").append(msg2);

                builder.setTitle(title);
                builder.setMessage(sb);
                builder.setPositiveButton("delete", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        callback.confirmation(true);
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        callback.confirmation(false);
                    }
                });


                return builder.create();
            }
            else
            {
                return super.onCreateDialog(savedInstanceState);
            }
        }
    }

    private interface ConfirmationResult
    {
        void confirmation(boolean result);
    }
}
