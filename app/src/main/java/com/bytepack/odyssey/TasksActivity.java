package com.bytepack.odyssey;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    String Gallery="";
    String Museum="";
    String Park="";
    String Zoo="";

    JSONObject Itemset=new JSONObject();
    JSONObject Loc=new JSONObject();
    JSONObject Loct=new JSONObject();
    String Item;
    int num=0;
    int resID;
    TextView t;
    String Result[]=new String[100];
    String Resultset1[]=new String[100];
    String Resultset2[]=new String[100];
    String Target[]=new String[100];


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


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername=mFirebaseUser.getEmail();


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




        showGallery(JsonURLGallery);
        showMuseum(JsonURLMuseum);
        showPark(JsonURLPark);
        showZoo(JsonURLZoo);
        //showContents();


    }

    public void showGallery(String JsonURL) {

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        //results = (TextView) findViewById(R.id.jsonData);

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
                            Result=Gallery.split("\n");
                            Target[1]=Result[0];
                            Target[2]=Result[1];
                            Resultset1=Result[0].split(",");
                            Resultset2=Result[1].split(",");
                            //Toast.makeText(TasksActivity.this,Result[0],Toast.LENGTH_LONG).show();

                            num=num+1;
                            resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            t=(TextView) findViewById(resID);
                            t.setText(Resultset1[0]);
                            num=num+1;
                            resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            t=(TextView) findViewById(resID);
                            t.setText(Resultset2[0]);




                            //results.setText(Gallery);
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
        //results = (TextView) findViewById(R.id.jsonData);

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
                            Result=Museum.split("\n");
                            Target[3]=Result[0];
                            Target[4]=Result[1];
                            Resultset1=Result[0].split(",");
                            Resultset2=Result[1].split(",");
                            //Toast.makeText(TasksActivity.this,Result[0],Toast.LENGTH_LONG).show();

                            num=num+1;
                            resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            t=(TextView) findViewById(resID);
                            t.setText(Resultset1[0]);
                            num=num+1;
                            resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            t=(TextView) findViewById(resID);
                            t.setText(Resultset2[0]);


                            //results.setText(Museum);
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
       // results = (TextView) findViewById(R.id.jsonData);

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
                            Result=Park.split("\n");
                            Target[5]=Result[0];
                            Target[6]=Result[1];
                            Resultset1=Result[0].split(",");
                            Resultset2=Result[1].split(",");
                            //Toast.makeText(TasksActivity.this,Result[0],Toast.LENGTH_LONG).show();

                            num=num+1;
                            resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            t=(TextView) findViewById(resID);
                            t.setText(Resultset1[0]);
                            num=num+1;
                            resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            t=(TextView) findViewById(resID);
                            t.setText(Resultset2[0]);


                            //results.setText(Park);
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
        //results = (TextView) findViewById(R.id.jsonData);

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
                            Result=Zoo.split("\n");
                            Target[7]=Result[0];
                            Target[8]=Result[1];
                            Resultset1=Result[0].split(",");
                            Resultset2=Result[1].split(",");
                            //Toast.makeText(TasksActivity.this,Result[0],Toast.LENGTH_LONG).show();

                            num=num+1;
                            resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            t=(TextView) findViewById(resID);
                            t.setText(Resultset1[0]);
                            num=num+1;
                            resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            t=(TextView) findViewById(resID);
                            t.setText(Resultset2[0]);


                            //results.setText(Zoo);
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




    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("root/users/"+mUsername+"/activeTask");

    public void taskGo1(View v) {
        String Res[]=new String[100];
        myRef.setValue(Target[1]);
        Res=Target[1].split(",");

        //Toast.makeText(TasksActivity.this,Res[1],Toast.LENGTH_LONG).show();
        //Toast.makeText(TasksActivity.this,Res[2],Toast.LENGTH_LONG).show();
        Intent i =new Intent(TasksActivity.this,MapsActivity.class);
        i.putExtra("name",Res[0]);
        i.putExtra("lat",Res[1]);
        i.putExtra("lng",Res[2]);
        startActivity(i);
    }

    public void taskGo2(View v) {
        String Res[]=new String[100];
        Res=Target[2].split(",");

        //Toast.makeText(TasksActivity.this,Res[1],Toast.LENGTH_LONG).show();
        //Toast.makeText(TasksActivity.this,Res[2],Toast.LENGTH_LONG).show();
        Intent i =new Intent(TasksActivity.this,MapsActivity.class);
        i.putExtra("name",Res[0]);
        i.putExtra("lat",Res[1]);
        i.putExtra("lng",Res[2]);
        startActivity(i);
    }

    public void taskGo3(View v) {
        String Res[]=new String[100];
        Res=Target[3].split(",");

        //Toast.makeText(TasksActivity.this,Res[1],Toast.LENGTH_LONG).show();
        //Toast.makeText(TasksActivity.this,Res[2],Toast.LENGTH_LONG).show();
        Intent i =new Intent(TasksActivity.this,MapsActivity.class);
        i.putExtra("name",Res[0]);
        i.putExtra("lat",Res[1]);
        i.putExtra("lng",Res[2]);
        startActivity(i);
    }

    public void taskGo4(View v) {
        String Res[]=new String[100];
        Res=Target[4].split(",");

        //Toast.makeText(TasksActivity.this,Res[1],Toast.LENGTH_LONG).show();
        //Toast.makeText(TasksActivity.this,Res[2],Toast.LENGTH_LONG).show();
        Intent i =new Intent(TasksActivity.this,MapsActivity.class);
        i.putExtra("name",Res[0]);
        i.putExtra("lat",Res[1]);
        i.putExtra("lng",Res[2]);
        startActivity(i);
    }

    public void taskGo5(View v) {
        String Res[]=new String[100];
        Res=Target[5].split(",");

        //Toast.makeText(TasksActivity.this,Res[1],Toast.LENGTH_LONG).show();
        //Toast.makeText(TasksActivity.this,Res[2],Toast.LENGTH_LONG).show();
        Intent i =new Intent(TasksActivity.this,MapsActivity.class);
        i.putExtra("name",Res[0]);
        i.putExtra("lat",Res[1]);
        i.putExtra("lng",Res[2]);
        startActivity(i);
    }

    public void taskGo6(View v) {
        String Res[]=new String[100];
        Res=Target[6].split(",");

        //Toast.makeText(TasksActivity.this,Res[1],Toast.LENGTH_LONG).show();
        //Toast.makeText(TasksActivity.this,Res[2],Toast.LENGTH_LONG).show();
        Intent i =new Intent(TasksActivity.this,MapsActivity.class);
        i.putExtra("name",Res[0]);
        i.putExtra("lat",Res[1]);
        i.putExtra("lng",Res[2]);
        startActivity(i);
    }

    public void taskGo7(View v) {
        String Res[]=new String[100];
        Res=Target[7].split(",");

        //Toast.makeText(TasksActivity.this,Res[1],Toast.LENGTH_LONG).show();
        //Toast.makeText(TasksActivity.this,Res[2],Toast.LENGTH_LONG).show();
        Intent i =new Intent(TasksActivity.this,MapsActivity.class);
        i.putExtra("name",Res[0]);
        i.putExtra("lat",Res[1]);
        i.putExtra("lng",Res[2]);
        startActivity(i);
    }

    public void taskGo8(View v) {
        String Res[]=new String[100];
        Res=Target[8].split(",");

        //Toast.makeText(TasksActivity.this,Res[1],Toast.LENGTH_LONG).show();
        //Toast.makeText(TasksActivity.this,Res[2],Toast.LENGTH_LONG).show();
        Intent i =new Intent(TasksActivity.this,MapsActivity.class);
        i.putExtra("name",Res[0]);
        i.putExtra("lat",Res[1]);
        i.putExtra("lng",Res[2]);
        startActivity(i);
    }


}

