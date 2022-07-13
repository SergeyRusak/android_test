package com.sirius.test_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.RequiresApi
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")



    private fun mStringToURL(string: String?): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get info about application
        val data = DataModel();

        //get id from view
        val app_name_textView = findViewById<TextView>(R.id.app_name)
        val app_description_textView = findViewById<TextView>(R.id.app_description)
        val review_count_textView = findViewById<TextView>(R.id.review_count)
        val review_count2_textView = findViewById<TextView>(R.id.review_count4)
        val app_rating_text_textView = findViewById<TextView>(R.id.app_rating_text)

        val app_rating_ratingBar = findViewById<RatingBar>(R.id.app_rating)
        val app_rating2_ratingBar = findViewById<RatingBar>(R.id.app_rating4)

        val app_logo_imageView = findViewById<ImageView>(R.id.app_logo)
        val app_preview_imageView = findViewById<ImageView>(R.id.app_preview)

        val tag_list = findViewById<LinearLayout>(R.id.tag_list)
        val review_list = findViewById<LinearLayout>(R.id.review_list)

        val linf = LayoutInflater.from(this)





        //insert info in view
        app_name_textView.setText(data.name)
        app_description_textView.setText(data.description)
        app_rating_text_textView.setText(data.rating.toString())
        review_count_textView.setText(data.gradeCnt)
        review_count2_textView.setText(data.gradeCnt+" reviews")
        app_rating_ratingBar.rating = data.rating
        app_rating2_ratingBar.rating = data.rating




        downloadImage(data.logo){
            app_logo_imageView.setImageBitmap(it)
        }

        downloadImage(data.image){
            app_preview_imageView.setImageBitmap(it)
        }



        tag_list.removeAllViews()

        for (item in data.tags){
           val tv = TextView(this)
            tv.setTextAppearance(R.style.tagText)
            tv.setBackgroundResource(R.drawable.textview_style)
            tv.text = item
           tag_list.addView(tv)
        }






        review_list.removeAllViews()
        for (item in data.reviews){
            val review = linf.inflate(R.layout.review, null)
            review.findViewById<TextView>(R.id.name).setText(item.userName)
            review.findViewById<TextView>(R.id.date).setText(item.date)
            review.findViewById<TextView>(R.id.comment).setText(item.message)
            downloadImage(item.userImage) {
                review.findViewById<ImageView>(R.id.avatar).setImageBitmap(it)
            }



            review_list.addView(review)

        }






    }



    private fun downloadImage(urls: String?, callback: (Bitmap) -> Unit) {
        thread {
            var bitmap:Bitmap? = null
            val sharedPref = getPreferences(Context.MODE_PRIVATE)
            if (sharedPref.contains(urls)){
               bitmap = decodecashe(sharedPref.getString(urls,""))
            }
            else
            {


            val url: URL = mStringToURL(urls)!!
            val connection: HttpURLConnection?
            try {
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bufferedInputStream = BufferedInputStream(inputStream)
                bitmap = BitmapFactory.decodeStream(bufferedInputStream)
                with (sharedPref.edit()) {
                    putString(urls, encodecashe(bitmap))
                    apply()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }

            }
           runOnUiThread{
                callback.invoke(bitmap!!)
            }

        }

    }

    private fun decodecashe(encode: String?): Bitmap? {
        val imageBytes = Base64.decode(encode, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return image
    }
    private fun encodecashe(img:Bitmap? ): String? {
        val baos = ByteArrayOutputStream()
        img?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

}

