package com.example.testappv00;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import adapter.StarAdapter;
import adress.Ip;
import beans.Contact;
import beans.Star;
import service.StarService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RequestQueue requestQueue;


    String insertUrllo = "http://"+ Ip.ip+":8088/Projet01NemberBook/ws/loadContact.php";
    private Button addbb;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private List<Star> stars;
    private StarService service;


    AlertDialog.Builder builder;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addbb = (Button)findViewById(R.id.addbb);
        Log.d("***********Votre Ip add  : "+Ip.ip, "list");


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,
                insertUrllo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response);
                Type type = new TypeToken<Collection<Contact>>() {
                }.getType();
                Collection<Contact> contact = new Gson().fromJson(response, type);
                stars = new ArrayList<>();
                service = new StarService();
                for (Contact e : contact) {

                    // Log.d("TAG", e.toString());
                    Star s = new Star(e.getNom(), e.getPrenom(), e.getVille(), e.getSexe(), R.mipmap.star, e.getId());
                    s.setId(e.getId());
                    // Star s = new Star(e)
                    service.create(s);


                }
                recyclerView = findViewById(R.id.recycle_view);
                //insérer le code
                starAdapter = new StarAdapter(MainActivity.this, service.findAll());
                recyclerView.setAdapter(starAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
        //
        addbb.setOnClickListener(MainActivity.this);
    }

    @SuppressLint("LongLogTag")
    public void init() {
     /*   service.create(new Star("kate bosworth","prenom","homme","Marrakech",R.mipmap.star, 3.5f));
        service.create(new Star("kate bosworth","prenom","homme","Marrakech",R.mipmap.star, 3.5f));
        service.create(new Star("kate bosworth","prenom","homme","Marrakech",R.mipmap.star, 3.5f));
        service.create(new Star("kate bosworth","prenom","homme","Marrakech",R.mipmap.star, 3.5f));
        service.create(new Star("kate bosworth","prenom","homme","Marrakech",R.mipmap.star, 3.5f));
        service.create(new Star("kate bosworth","prenom","homme","Marrakech",R.mipmap.star, 3.5f));
        service.create(new Star("kate bosworth","prenom","homme","Marrakech",R.mipmap.star, 3.5f));
        service.create(new Star("kate bosworth","prenom","homme","Marrakech",R.mipmap.star, 3.5f));
        //*/
        //


    }
   @Override
    public void onClick(View v) {
        if(v==addbb)
        {
            Intent refresh = new Intent(this,AddEtudiant.class);
            this.startActivity(refresh);
            this.finish();
        }

    }


}
