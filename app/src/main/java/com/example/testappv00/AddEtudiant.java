package com.example.testappv00;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import adapter.CustomAdapter;
import adress.Ip;
import beans.Star;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Donnees : ";
    private EditText nom;
    private TextView txtnom, txtphone;
    private EditText phone;
    private Spinner payee;
    private RadioButton Rphone;
    private RadioButton Rnom;
    private Button add, listEtudiants;
    private RequestQueue requestQueue;
    private Spinner s;
    private CustomAdapter ad;
    private String[] names = {"(004)", "(005)", "(006)", "(007)", "(060)"};
    private int[] images = {R.drawable.france, R.drawable.alm, R.drawable.belgiq, R.drawable.brazil, R.drawable.maroc};
    private String selpayname = null;
    String insertUrllo = "http://" + Ip.ip + ":8080/Projet01NemberBook/ws/loadContact.php";
    String insertUrlcr = "http://" + Ip.ip + ":8080/Projet01NemberBook/ws/createContact.php";
    String insertUrlde = "http://" + Ip.ip + ":8080/Projet01NemberBook/ws/dropContact.php";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        s = findViewById(R.id.payee);

        ad = new CustomAdapter(this, names, images);
        s.setAdapter(ad);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getApplicationContext(),names[i],Toast.LENGTH_LONG).show();
                selpayname = names[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //

        nom = (EditText) findViewById(R.id.nom);
        txtnom = findViewById(R.id.txtnom);
        txtphone = findViewById(R.id.txtphone);
        phone = (EditText) findViewById(R.id.phone);
        payee = (Spinner) findViewById(R.id.payee);
        add = (Button) findViewById(R.id.add);
        Rphone = (RadioButton) findViewById(R.id.Rphone);
        Rnom = (RadioButton) findViewById(R.id.Rnom);
        add.setOnClickListener(this);
        Rphone.setOnClickListener(this);
        Rnom.setOnClickListener(this);
        txtnom.setVisibility(View.GONE);
        nom.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        txtphone.setVisibility(View.GONE);
        recupContact();
    }

    public void recupContact() {
        ContentResolver contentResolver = this.getContentResolver();
        //recupérer list des contact
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        if (cursor == null) {
            Log.d("recup", "*******************error cursore !!!");
        } else {

            //parcourir
            while (cursor.moveToNext() == true) {
                @SuppressLint("Range") String nameC = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE));
                @SuppressLint("Range") String phoneC = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //afficher resultat
                // txtkk.setText(nameC + " : " + phoneC);
                // *****************************delet all***************************
            /*    requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST,
                        insertUrlde, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type type = new TypeToken<Collection<Star>>(){}.getType();
                        Collection<Star> etudiants = new Gson().fromJson(response, type);
                        Toast.makeText(AddEtudiant.this, "db updated", Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {


                        return null;

                    }
                };
                requestQueue.add(request);*/

//**********************************ajouter list contact a la BD*********************************
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST,
                        insertUrlcr, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type type = new TypeToken<Collection<Star>>() {
                        }.getType();
                        Collection<Star> etudiants = new Gson().fromJson(response, type);
                        Toast.makeText(AddEtudiant.this, "Bien Enregistrer", Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("nom", nameC);
                        params.put("prenom", "null");
                        params.put("ville", "null");
                        params.put("sexe", "null");
                        params.put("phone", phoneC);
                        return params;

                    }
                };
                requestQueue.add(request);
            }

// *****************************************************************
        }
        //fermer cursor
        cursor.close();
    }


    public boolean verpay(String indicatif,String phone) {
        String[] indica = phone.split(" ");
        if (indica[0].equals(indicatif))
        {
            return true;
        }
        return false;
    }

    public String payy(String num) {
        String[] indica = num.split(" ");

        if (indica[0].equals("(060)")) {
            return "Maroc";
        } else if (indica[0].equals("(004)")) {
            return "France";
        } else if (indica[0].equals("(005)")) {
            return "Allemagne";
        } else if (indica[0].equals("(006)")) {
            return "Belgique";
        } else if (indica[0].equals("(007)")) {
            return "Brésil";
        } else return indica[0].toString();
    }

    @Override
    public void onClick(View v) {
        if (v == Rphone) {
            txtnom.setVisibility(View.GONE);
            nom.setVisibility(View.GONE);
            phone.setVisibility(View.VISIBLE);
            txtphone.setVisibility(View.VISIBLE);
        }
        if (v == Rnom) {
            txtphone.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            nom.setVisibility(View.VISIBLE);
            txtnom.setVisibility(View.VISIBLE);
        }

        if (v == add) {

            requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST,
                    insertUrllo, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d(TAG, response);
                    Type type = new TypeToken<Collection<Star>>() {
                    }.getType();
                    Collection<Star> contacts = new Gson().fromJson(response, type);
                    //check phone
                    if (Rphone.isChecked()) {
                        boolean check01 = false;
                        nom.setText("");

                        for (Star e : contacts) {
                            if (phone.getText().toString().equals(e.getPhone())) {
                                Toast toast = Toast.makeText(AddEtudiant.this, "verification payée  : " +verpay(selpayname.toString(),e.getPhone()) , Toast.LENGTH_SHORT);
                                toast.show();
                                if (verpay(selpayname.toString(),e.getPhone())) {

                                    toast = Toast.makeText(AddEtudiant.this, "Le numéro  : " + phone.getText().toString() + " u le nom :  " + e.getName() + " payee :" + payy(e.getPhone()), Toast.LENGTH_SHORT);
                                    toast.show();
                                    check01 = true;
                                    break;
                                }
                            }
                        }
                        if (phone.equals("")) {
                            if (!check01) {
                                Toast toast = Toast.makeText(AddEtudiant.this, "Le numéro  : " + phone.getText().toString() + " est introuvable", Toast.LENGTH_SHORT);
                                toast.show();

                            }
                        }
                    }
                    //check nom
                    if (Rnom.isChecked()) {
                        phone.setText("");

                        boolean check = false;
                        for (Star e : contacts) {

                            if (nom.getText().toString().equals(e.getName())) {
                                Toast toast = Toast.makeText(AddEtudiant.this, "verification payée  : " +verpay(selpayname.toString(),e.getPhone()) , Toast.LENGTH_SHORT);
                                toast.show();
                                if (verpay(selpayname.toString(),e.getPhone())) {
                                    toast = Toast.makeText(AddEtudiant.this, "Contact : " + nom.getText().toString() + " u le numéro :  " + e.getPhone() + " payee :" + payy(e.getPhone()), Toast.LENGTH_SHORT);
                                    toast.show();
                                    break;
                                }
                            }

                        }
                        if (phone.equals("")) {
                            if (check == false) {
                                Toast toast = Toast.makeText(AddEtudiant.this, "Le numéro  : " + phone.getText().toString() + " est introuvable", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    return null;
                }
            };
            requestQueue.add(request);
        }
        if (v == listEtudiants) {

            Intent intent = new Intent(AddEtudiant.this, MainActivity.class);
            AddEtudiant.this.startActivity(intent);

            this.finish();
        }
    }

}