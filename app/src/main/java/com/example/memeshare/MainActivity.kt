//package com.example.memeshare
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import android.widget.Toast
//import com.android.volley.Request
//import com.android.volley.Response
//import com.android.volley.toolbox.JsonObjectRequest
//import com.android.volley.toolbox.Volley
//import com.bumptech.glide.Glide
//import java.nio.file.attribute.AttributeView
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//
//
//        loadMeme()
//    }
//
//    private fun loadMeme() {
//        TODO("Not yet implemented")
//    }
//
//    val imageView = findViewById<ImageView>(R.id.memeImageview)
//
//    val queue = Volley.newRequestQueue(this)
//    val url = "https://meme-api.com/gimme"
//    val jsonObjectRequest = JsonObjectRequest(
//        Request.Method.GET, url, null,
//        { response ->
//            val url = response.getString("url")
//            Glide.with(this).load(url).into(
//                imageView
//            )
//
//        },
//        { error ->
//            // TODO: Handle error
//            Toast.makeText(this, "Error loading", Toast.LENGTH_LONG).show()
//        }
//    )
//    queue.add(jsonObjectRequest)
//}
//
//    fun shareMeme(view: View){
//
//    }
//    fun nextMeme(view: View){
//
//    }
//
//}

        package com.example.memeshare

        import android.content.Intent
        import android.graphics.drawable.Drawable
                import androidx.appcompat.app.AppCompatActivity
                import android.os.Bundle
                import android.util.Log
                import android.view.View
                import android.view.View.GONE
                import android.widget.ImageView
                import android.widget.ProgressBar
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

                @Suppress("NAME_SHADOWING")
                class MainActivity : AppCompatActivity() {
                    var currentImageurl: String?=null
                    override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                        setContentView(R.layout.activity_main)
                       var currentImageurl: String?=null
        loadMeme()
    }

    private fun loadMeme(){
        // Instantiate the RequestQueue.
        val progressbar=findViewById<ProgressBar>(R.id.progressBar)
        progressbar.visibility=View.VISIBLE

        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.


        val imageView = findViewById<ImageView>(R.id.memeImageview)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageurl= response.getString("url")
                Glide.with(this).load(currentImageurl).listener(object:RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false

                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility =View.GONE
                        return false
                    }

                }) .into (imageView)

            },
            { error ->
                Toast.makeText(this,"Error loading", Toast.LENGTH_LONG).show()
            }
        )

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
    val intent =Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"HEY CHECKOUT THIS COOL MEME I GOT FROM REDDIT $currentImageurl")
        val chooser=Intent.createChooser(intent,"Share this meme using..." )
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}