package com.example.cristhiamhenao.perdidogs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class TableroDireccion extends AppCompatActivity {

    int nJugadores;
    String jugadores;

    private String[] Nombre={"","","","",""};

    ImageView ruleta;
    TextView perritoNombre;

    Random r;
    int degree = 0, degreeOld = 0;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    private static final float FACTOR = 45.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablerodireccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRuleta);
        setSupportActionBar(toolbar);

        perritoNombre = (TextView) findViewById(R.id.txt_title);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/a_song.ttf");
        perritoNombre.setTypeface(custom_font);

        // Recupera la informacion de los jugadores
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();
        nJugadores = sharedPref.getInt("njugadores", 0);
        jugadores = sharedPref.getString("jugadores", "");

        for (int i = 0; i < nJugadores; i++) {
            int orden = Character.getNumericValue(jugadores.charAt(i));
            Nombre[i] = sharedPref.getString("perrito" + orden, "");
            colocaImagen("perrito" + orden, Nombre[i]);

        }

        ruleta = (ImageView) findViewById(R.id.ruletaFlechas);

        r = new Random();

        ruleta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                degreeOld = degree % 360;
                degree = r.nextInt(3600) + 720;
                RotateAnimation rotate = new RotateAnimation(degreeOld, degree,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        String direccion = currentNumber(360 - (degree % 360));

                        editor.putString("direccion",direccion);
                        editor.commit();

                        Intent intent = new Intent(getBaseContext(), Instrucciones.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                ruleta.startAnimation(rotate);
            }
        });

    }

    private void colocaImagen(String imgNombrePerro, String nombrePerro) {
        ImageView perrito = (ImageView) findViewById(R.id.perrito);
        int idImg = getResources().getIdentifier("com.example.cristhiamhenao.perdidogs:drawable/"+imgNombrePerro, null, null);
        perrito.setImageResource(idImg);

        perritoNombre.setText(nombrePerro);

    }

    private String currentNumber(int degrees){
        String text = "";
        System.out.println("degrees " + degrees);
        if(degrees >= (FACTOR * 1) && degrees <(FACTOR * 3)){
            text = "flechaderecha";
        }

        if(degrees >= (FACTOR * 3) && degrees <(FACTOR * 5)){
            text = "flechaabajo";
        }

        if(degrees >= (FACTOR * 5) && degrees <(FACTOR * 7)){
            text = "flechaizquierda";
        }

        if(degrees >= (FACTOR * 7) && degrees < 360 || (degrees >= 0 && degrees < (FACTOR * 1))){
            text = "flechaarriba";
        }

        return text;
    }
}
