package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.testappv00.MainActivity;
import com.example.testappv00.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adress.Ip;
import beans.Star;
import service.StarService;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> {
    private static final String TAG = "StarAdapter";
    private List<Star> stars;
    private Context context;
    RequestQueue requestQueue;



    String insertUrl = "http://"+Ip.ip+":8080/Projet01/ws/dropEtudiant.php";

    public StarAdapter(Context context, List<Star> stars) {
        this.stars = stars;
        this.context = context;
    }
    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(this.context).inflate(R.layout.star_item, viewGroup, false);
        final StarViewHolder holder = new StarViewHolder(v);
        //
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(context).inflate(R.layout.star_edit_item, null,
                        false);
                final ImageView img = popup.findViewById(R.id.img);
                final RatingBar bar = popup.findViewById(R.id.ratingBar);
                final TextView idss = popup.findViewById(R.id.idss);

                final TextView nom = v.findViewById(R.id.name);
                final TextView prenom = v.findViewById(R.id.prenom);
                final TextView ville = v.findViewById(R.id.ville);
                final TextView sexe = v.findViewById(R.id.sexe);
                final TextView id = v.findViewById(R.id.ids);

                Bitmap bitmap =
                        ((BitmapDrawable) ((ImageView) v.findViewById(R.id.img)).getDrawable()).getBitmap();
                img.setImageBitmap(bitmap);
                bar.setRating(((RatingBar) v.findViewById(R.id.stars)).getRating());
                idss.setText(((TextView) v.findViewById(R.id.ids)).getText().toString());
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Notez : ")
                        .setMessage("Donner une note entre 1 et 5 :")
                        .setView(popup)
                        .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float s = bar.getRating();
                                int ids = Integer.parseInt(idss.getText().toString());
                                Star star = StarService.getInstance().findById(ids);

                                StarService.getInstance().update(star);
                                notifyItemChanged(holder.getAdapterPosition());


                                //*******************************************
                                requestQueue = Volley.newRequestQueue(context);
                                StringRequest request = new StringRequest(Request.Method.POST,
                                        insertUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //
                                     /*   Toast toast = Toast.makeText(context, "bien Supprimer", Toast.LENGTH_SHORT);
                                        toast.show();*/
                                        //
                                        Log.d(TAG, response);

                                        Intent refresh = new Intent(context,MainActivity.class);
                                        context.startActivity(refresh);
                                        //context.finish();


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        HashMap<String, String> params = new HashMap<String, String>();
                                        params.put("id", idss.getText().toString());
                                      Log.d("id!!!!!!!!!!!!!",idss.getText().toString());
                                        return params;


                                    }
                                };
                                requestQueue.add(request);
                                //*******************************************
                              //  context.startActivity(refresh);
                                //
                               /* Toast toast = Toast.makeText(context, "Bien Supprimer", Toast.LENGTH_SHORT);
                                toast.show();*/
                                //
                            }
                        })
                        .setNegativeButton("Annuler", null)
                        .create();
                dialog.show();
            }
        });
        //
        return new StarViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull StarViewHolder starViewHolder, int i) {
        Log.d(TAG, "onBindView call ! "+ i);
        Glide.with(context)
                .asBitmap()
                .load(stars.get(i).getImg())
                .apply(new RequestOptions().override(100, 100))
                .into(starViewHolder.img);
        starViewHolder.name.setText(stars.get(i).getName().toUpperCase());
        starViewHolder.stars.setRating(stars.get(i).getStar());
        starViewHolder.idss.setText(stars.get(i).getId()+"");
        starViewHolder.prenom.setText(stars.get(i).getPrenom()+"");
        starViewHolder.sexe.setText(stars.get(i).getSexe()+"");
        starViewHolder.ville.setText(stars.get(i).getVille()+"");


    }
    @Override
    public int getItemCount() {
        return stars.size();
    }
    public class StarViewHolder extends RecyclerView.ViewHolder {
        TextView idss;
        ImageView img;
        TextView name;
        TextView prenom;
        TextView sexe;
        TextView ville;
        RatingBar stars;
        RelativeLayout parent;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            idss = itemView.findViewById(R.id.ids);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            stars = itemView.findViewById(R.id.stars);
            parent = itemView.findViewById(R.id.parent);
            prenom  = itemView.findViewById(R.id.prenom);
            ville  = itemView.findViewById(R.id.ville);
            sexe = itemView.findViewById(R.id.sexe);
        }
    }
}

