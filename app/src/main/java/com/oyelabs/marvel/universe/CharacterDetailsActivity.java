package com.oyelabs.marvel.universe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class CharacterDetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView character_name;
    TextView about;
    TextView moreInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charecter_details);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String image = intent.getStringExtra("image");
        String more = intent.getStringExtra("more");

       character_name = findViewById(R.id.character_name);
       about = findViewById(R.id.description);
       imageView = findViewById(R.id.character_image);
       moreInfo = findViewById(R.id.more_details);

       try {
           actionBar.setTitle("id:"+id);
           Glide.with(this)
                   .load(image)
                   .into(imageView);
           character_name.setText(name);
           if(description.equals("")){
               about.setText("Not Available");
           }else{
               about.setText(description);
           }

           try {
               moreInfo.setOnClickListener(V->{
                   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(more));
                   startActivity(browserIntent);
               });
           }catch (Exception e){
               Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
           }





       }catch (Exception e){
           e.printStackTrace();
           Toast.makeText(this,"something went wrong!try again later", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}