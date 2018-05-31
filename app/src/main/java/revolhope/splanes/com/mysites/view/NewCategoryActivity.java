package revolhope.splanes.com.mysites.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import revolhope.splanes.com.mysites.R;
import revolhope.splanes.com.mysites.model.Category;

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
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("New category");
            actionBar.setSubtitle("");
        }

        editText_name = findViewById(R.id.editText_catgoryName);
        editText_description = findViewById(R.id.editText_categoryDescription);
        imageView_icon = findViewById(R.id.imageView_category_icon);
        imageView_color = findViewById(R.id.imageView_category_color);
                
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
                        //Category catergory = new Category();
                    }
                }
            });
    }

    private boolean checkFields()
    {
        String name = editText_name.getText().toString();
        int descriptionCount = editText_description.getText().toString().length();

        Object iconObj = imageView_icon.getTag();
        Object colorObj = imageView_color.getTag();

        if (name.isEmpty())
        {
            editText_name.setError("Name field is mandatory");
            editText_name.requestFocus();
            return false;
        }
        else
        {
            editText_name.setError(null);
        }

        if (descriptionCount > 50)
        {
            editText_description.setError("Characters limit is exceed");
        }
        else if (descriptionCount != 0)
        {
            
        }

        if (iconObj == null)
        {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {

        }

        if (colorObj == null)
        {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {

        }



        return true;
    }
}
