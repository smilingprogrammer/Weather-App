package com.example.weatherapp

import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONObject

// weather url to get JSON
var weather_url1 = ""

// api id for url
var api_id1 = "549a4333059ff580877bfcd5377167b7"

private lateinit var textView: TextView
private lateinit var fusedLocationClient: FusedLocationProviderClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)

        // link the textView in which the
        // temperature will be displayed
        textView = findViewById(R.id.textView)

        // create an instance of the Fused
        // Location Provider Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Log.e("lat", weather_url1)

        // on clicking this button function to
        // get the coordinates will be called
        button.setOnClickListener {
            Log.e("lat", "onClick")
            // function to find the coordinates
            // of the last location
            obtainLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun obtainLocation() {
        Log.e("lat", "function")
        // get the last location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // get the latitude and longitude
                // and create the http URL
    /*            weather_url1 = "https://api.weatherbit.io/v2.0/current?" + "lat=" + location?.latitude + "&lon=" + location?.longitude + "&key=" + api_id1 */
                weather_url1 = "https://api.openweathermap.org/data/2.5/onecall?lat=lat&lon={lon}&exclude={part}&appid={API key}"
                Log.e("lat", weather_url1.toString())
                // this function will
                // fetch data from URL
                getTemp()
            }
    }

    fun getTemp() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = weather_url1
        Log.e("lat", url)

        // Request a string response
        // from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.e("lat", response.toString())

                // get the JSON object
                val obj = JSONObject(response)

                // get the Array from obj of name - "data"
                val arr = obj.getJSONArray("data")
                Log.e("lat obj1", arr.toString())

                // get the JSON object from the
                // array at index position 0
                val obj2 = arr.getJSONObject(0)
                Log.e("lat obj2", obj2.toString())

                // set the temperature and the city
                // name using getString() function
                textView.text = obj2.getString("temp") + " deg Celcius in " + obj2.getString("city_name")
            },
            // In case of any error
            Response.ErrorListener { textView!!.text = "That didn't work!" })
        queue.add(stringReq)
    }
}