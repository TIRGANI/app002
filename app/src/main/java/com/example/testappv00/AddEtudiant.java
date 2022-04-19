package com.example.testappv00;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private LinearLayout blo;
    private TextView tcontact;
    private TextView tpayee;
    private TextView tnumero;
    private static int cpt = 0;
    private String[] names = {"+33", "+49", "+32", "+52", "060"};
    private int[] images = {R.drawable.france, R.drawable.alm, R.drawable.belgiq, R.drawable.brazil, R.drawable.maroc};
    private String selpayname = null;
    String insertUrllo = "http://" + Ip.ip + ":8088/contact";//load

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

                selpayname = names[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //
        blo = findViewById(R.id.blo);
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
        blo.setVisibility(View.GONE);


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

//**********ajouter list contact a la BD***************************
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST,
                        insertUrllo, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        afficherToast();

                    }

                    private void afficherToast() {
                        Log.d("response ****M%MMM%%", "****");
                        Toast toast = Toast.makeText(getApplicationContext(), "message", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> params = new HashMap<String, String>();

                        params.put("id", ++cpt + "");
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


    public boolean verpay(String indicatif, String phone) {
        String indica = phone.substring(0, 3);
        if (indica.equals(indicatif)) {
            return true;
        }
        return false;
    }

    public String payy(String num) {
        String indica = num.substring(0, 3);


        if (indica.equals("060") || indica.equals("070") ) {
            return "Maroc";
        } else if (indica.equals("+33")) {
            return "France";
        } else if (indica.equals("+49")) {
            return "Allemagne";
        } else if (indica.equals("+32")) {
            return "Belgique";
        } else if (indica.equals("+52")) {
            return "Brésil";
        } else return indica;
    }

    @Override
    public void onClick(View v) {
        if (v == Rphone) {
            txtnom.setVisibility(View.GONE);
            nom.setVisibility(View.GONE);
            phone.setVisibility(View.VISIBLE);
            txtphone.setVisibility(View.VISIBLE);
            blo.setVisibility(View.VISIBLE);
        }
        if (v == Rnom) {
            txtphone.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            nom.setVisibility(View.VISIBLE);
            txtnom.setVisibility(View.VISIBLE);
            blo.setVisibility(View.VISIBLE);
        }

        if (v == add) {

            requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.GET,
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
                                Toast toast = Toast.makeText(AddEtudiant.this, "verification payée  : " + verpay(selpayname, e.getPhone()), Toast.LENGTH_SHORT);
                                toast.show();

                                if (verpay(selpayname, e.getPhone())) {

                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout));
                                    final Toast to = new Toast(getApplicationContext());

                                    to.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    to.setDuration(Toast.LENGTH_LONG);
                                    to.setView(layout);
                                    tcontact = layout.findViewById(R.id.tcontact);
                                    tpayee = layout.findViewById(R.id.tpayee);
                                    tnumero = layout.findViewById(R.id.tnumero);
                                    tcontact.setText(e.getName());
                                    tpayee.setText(payy(e.getPhone()));
                                    tnumero.setText(e.getPhone());
                                    to.show();
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
                                Toast toast = Toast.makeText(AddEtudiant.this, "verification payée  : " + verpay(selpayname.toString(), e.getPhone()), Toast.LENGTH_SHORT);
                                toast.show();
                                if (verpay(selpayname, e.getPhone())) {
                                    //


                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout));
                                    final Toast to = new Toast(getApplicationContext());

                                    to.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    to.setDuration(Toast.LENGTH_LONG);
                                    to.setView(layout);
                                    tcontact = layout.findViewById(R.id.tcontact);
                                    tpayee = layout.findViewById(R.id.tpayee);
                                    tnumero = layout.findViewById(R.id.tnumero);
                                    tcontact.setText(nom.getText().toString());
                                    tpayee.setText(payy(e.getPhone()));
                                    tnumero.setText(e.getPhone());
                                    to.show();


                                    break;
                                }
                            }


                            if (Rnom.equals("")) {
                                if (check == false) {

                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout));
                                    final Toast to = new Toast(getApplicationContext());

                                    to.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    to.setDuration(Toast.LENGTH_SHORT);
                                    to.setView(layout);

                                    tcontact = layout.findViewById(R.id.tcontact);
                                    tpayee = layout.findViewById(R.id.tpayee);
                                    tnumero = layout.findViewById(R.id.tnumero);
                                    tcontact.setText(nom.getText().toString());
                                    tpayee.setText(payy(e.getPhone()));
                                    tnumero.setText(e.getPhone());
                                    to.show();

                                }
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