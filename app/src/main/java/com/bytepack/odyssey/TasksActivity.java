package com.bytepack.odyssey;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TasksActivity extends AppCompatActivity {

    // Will show the string "data" that holds the results
    TextView results;
    // URL of object to be parsed

    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;
    double Lat,Lng;

    String Gallery="";
    String Museum="";
    String Park="";
    String Zoo="";

    JSONObject Itemset=new JSONObject();
    JSONObject Loc=new JSONObject();
    JSONObject Loct=new JSONObject();
    String Item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //////

        Lat=8.5241;
        Lng=76.9366;

        String JsonURLGallery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Lat+","+Lng+"&radius=5000&type=art_gallery&key=AIzaSyByF7l6SrHHP3mSPVkxxuxRrAyKdm4TB5Q";
        Log.d("URL",JsonURLGallery);
        String JsonURLMuseum = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Lat+","+Lng+"&radius=5000&type=museum&key=AIzaSyByF7l6SrHHP3mSPVkxxuxRrAyKdm4TB5Q";
        Log.d("URL",JsonURLMuseum);
        String JsonURLPark = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Lat+","+Lng+"&radius=5000&type=park&key=AIzaSyByF7l6SrHHP3mSPVkxxuxRrAyKdm4TB5Q";
        Log.d("URL",JsonURLPark);
        String JsonURLZoo = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Lat+","+Lng+"&radius=5000&type=zoo&key=AIzaSyByF7l6SrHHP3mSPVkxxuxRrAyKdm4TB5Q";
        Log.d("URL",JsonURLZoo);




        //showGallery(JsonURLGallery);
        //showMuseum(JsonURLMuseum);
        //showPark(JsonURLPark);
        //showZoo(JsonURLZoo);


    }

    public void showGallery(String JsonURL) {

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {



                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray records = response.getJSONArray("results");
                            //Toast.makeText(TasksActivity.this,records.toString(),Toast.LENGTH_LONG).show();
                            String str="";
                            for(int i=0;i<records.length();++i)
                            {
                                Itemset=records.getJSONObject(i);
                                //Toast.makeText(TasksActivity.this,Itemset.toString(),Toast.LENGTH_SHORT).show();
                                Loc=Itemset.getJSONObject("geometry");
                                Loct=Loc.getJSONObject("location");
                                //str=str+Itemset.getString("name")+": "+Loct.getString("lat")+"//"+Loct.getString("lng")+"\n";

                                Item=Itemset.getString("name")+","+Loct.getDouble("lat")+","+Loct.getDouble("lng");
                                Gallery=Gallery+Item+"\n";
                            }
                            results.setText(Gallery);
                            //results.setText(str);
                            //JSONObject b=obj.getJSONObject(0);

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            //String color = obj.getString("colorName");
                            //String desc = obj.getString("description");

                            // Adds strings from object to the "data" string

                            //data = obj.getString(0);
                            //Toast.makeText(TasksActivity.this,"***",Toast.LENGTH_LONG).show();
                            // Adds the data string to the TextView "results"
                            //results.setText(data);

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
                            //Toast.makeText(TasksActivity.this,"+++",Toast.LENGTH_LONG).show();
                            //results.setText(e.getMessage());
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Log.d("check",error.getMessage());
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }



    public void showMuseum(String JsonURL) {

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {



                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray records = response.getJSONArray("results");
                            //Toast.makeText(TasksActivity.this,records.toString(),Toast.LENGTH_LONG).show();
                            String str="";
                            for(int i=0;i<records.length();++i)
                            {
                                Itemset=records.getJSONObject(i);
                                //Toast.makeText(TasksActivity.this,Itemset.toString(),Toast.LENGTH_SHORT).show();
                                Loc=Itemset.getJSONObject("geometry");
                                Loct=Loc.getJSONObject("location");
                                //str=str+Itemset.getString("name")+": "+Loct.getString("lat")+"//"+Loct.getString("lng")+"\n";

                                Item=Itemset.getString("name")+","+Loct.getDouble("lat")+","+Loct.getDouble("lng");
                                Museum=Museum+Item+"\n";
                            }
                            results.setText(Museum);
                            //results.setText(str);
                            //JSONObject b=obj.getJSONObject(0);

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            //String color = obj.getString("colorName");
                            //String desc = obj.getString("description");

                            // Adds strings from object to the "data" string

                            //data = obj.getString(0);
                            //Toast.makeText(TasksActivity.this,"***",Toast.LENGTH_LONG).show();
                            // Adds the data string to the TextView "results"
                            //results.setText(data);

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
                            //Toast.makeText(TasksActivity.this,"+++",Toast.LENGTH_LONG).show();
                            //results.setText(e.getMessage());
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Log.d("check",error.getMessage());
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }

    public void showPark(String JsonURL) {

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {



                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray records = response.getJSONArray("results");
                            //Toast.makeText(TasksActivity.this,records.toString(),Toast.LENGTH_LONG).show();
                            String str="";
                            for(int i=0;i<records.length();++i)
                            {
                                Itemset=records.getJSONObject(i);
                                //Toast.makeText(TasksActivity.this,Itemset.toString(),Toast.LENGTH_SHORT).show();
                                Loc=Itemset.getJSONObject("geometry");
                                Loct=Loc.getJSONObject("location");
                                //str=str+Itemset.getString("name")+": "+Loct.getString("lat")+"//"+Loct.getString("lng")+"\n";

                                Item=Itemset.getString("name")+","+Loct.getDouble("lat")+","+Loct.getDouble("lng");
                                Park=Park+Item+"\n";
                            }
                            results.setText(Park);
                            //results.setText(str);
                            //JSONObject b=obj.getJSONObject(0);

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            //String color = obj.getString("colorName");
                            //String desc = obj.getString("description");

                            // Adds strings from object to the "data" string

                            //data = obj.getString(0);
                            //Toast.makeText(TasksActivity.this,"***",Toast.LENGTH_LONG).show();
                            // Adds the data string to the TextView "results"
                            //results.setText(data);

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
                            //Toast.makeText(TasksActivity.this,"+++",Toast.LENGTH_LONG).show();
                            //results.setText(e.getMessage());
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Log.d("check",error.getMessage());
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }


    public void showZoo(String JsonURL) {

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {



                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray records = response.getJSONArray("results");
                            //Toast.makeText(TasksActivity.this,records.toString(),Toast.LENGTH_LONG).show();
                            String str="";
                            for(int i=0;i<records.length();++i)
                            {
                                Itemset=records.getJSONObject(i);
                                //Toast.makeText(TasksActivity.this,Itemset.toString(),Toast.LENGTH_SHORT).show();
                                Loc=Itemset.getJSONObject("geometry");
                                Loct=Loc.getJSONObject("location");
                                //str=str+Itemset.getString("name")+": "+Loct.getString("lat")+"//"+Loct.getString("lng")+"\n";

                                Item=Itemset.getString("name")+","+Loct.getDouble("lat")+","+Loct.getDouble("lng");
                                Zoo=Zoo+Item+"\n";
                            }
                            results.setText(Zoo);
                            //results.setText(str);
                            //JSONObject b=obj.getJSONObject(0);

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            //String color = obj.getString("colorName");
                            //String desc = obj.getString("description");

                            // Adds strings from object to the "data" string

                            //data = obj.getString(0);
                            //Toast.makeText(TasksActivity.this,"***",Toast.LENGTH_LONG).show();
                            // Adds the data string to the TextView "results"
                            //results.setText(data);

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
                            //Toast.makeText(TasksActivity.this,"+++",Toast.LENGTH_LONG).show();
                            //results.setText(e.getMessage());
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Log.d("check",error.getMessage());
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }



}

