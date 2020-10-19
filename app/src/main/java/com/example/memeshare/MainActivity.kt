package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageURL:String?=null
    var url:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }
private fun loadMeme(){
    progressBar.visibility=View.VISIBLE
   // val queue = Volley.newRequestQueue(this)
    currentImageURL = "https://meme-api.herokuapp.com/gimme"


// Request a string response from the provided URL.
    val jsonobjectRequest = JsonObjectRequest(
        Request.Method.GET, currentImageURL, null,
        { response ->
             url=response.getString("url")
            Glide.with(this).load(url).listener(object:RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility=View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility=View.GONE
                    return false
                }
            }).into(imageView)

        },
        {
          Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
        })

// Add the request to the RequestQueue.
    //queue.add(jsonobjectRequest)
    MySingleton.getInstance(this).addToRequestQueue(jsonobjectRequest)
}
    fun shareMeme(view: View) {

        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check out this meme $url")

        val chooser=Intent.createChooser(intent,"share this meme using...")
        startActivity(chooser)
    }


    fun nextMeme(view: View) {
loadMeme()
    }
}