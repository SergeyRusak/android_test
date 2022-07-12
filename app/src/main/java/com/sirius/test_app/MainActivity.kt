package com.sirius.test_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get info about application
        val data = DataModel();

        //get id from view
        val app_name_textView = findViewById<TextView>(R.id.app_name);
        val app_description_textView = findViewById<TextView>(R.id.app_description);
        val review_count_textView = findViewById<TextView>(R.id.review_count);
        val review_count2_textView = findViewById<TextView>(R.id.review_count4);
        val app_rating_text_textView = findViewById<TextView>(R.id.app_rating_text);

        val app_rating_ratingBar = findViewById<RatingBar>(R.id.app_rating);
        val app_rating2_ratingBar = findViewById<RatingBar>(R.id.app_rating4);

        val app_logo_imageView = findViewById<ImageView>(R.id.app_logo);
        val app_preview_imageView = findViewById<ImageView>(R.id.app_preview);



        //insert info in view
        app_name_textView.setText(data.name);
        app_description_textView.setText(data.description);
        app_rating_text_textView.setText(data.rating.toString());
        review_count_textView.setText(data.gradeCnt);
        review_count2_textView.setText(data.gradeCnt);
        app_rating_ratingBar.rating = data.rating;
        app_rating2_ratingBar.rating = data.rating;




    }
}