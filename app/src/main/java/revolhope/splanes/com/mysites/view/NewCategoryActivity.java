package revolhope.splanes.com.mysites.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import revolhope.splanes.com.mysites.R;

public class NewCategoryActivity extends AppCompatActivity {

    private TextInputEditText editText_name;
    private TextInputEditText editText_description;
    private ImageView imageView_icon;
    private ImageView imageView_color;
        
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("New category");
            actionBar.setSubtitle("");
        }

        editText_name = findViewById(R.id.editText_catgoryName);
        editText_name = findViewById(R.id.catgoryDescription);
        editText_name = findViewById(R.id.imageView_icon);
        editText_name = findViewById(R.id.imageView_color);
                
        findViewById(R.id.button_pick_icon)
            .setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    
                }
            });
        
        findViewById(R.id.button_pick_color)
            .setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    
                }
            });
        
        findViewById(R.id.button_done)
            .setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (checkFields())
                    {
                        Category catergory = new Category();
                    }
                }
            });
    }
}
