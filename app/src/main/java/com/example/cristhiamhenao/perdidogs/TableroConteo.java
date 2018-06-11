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

import org.w3c.dom.Text;

import java.util.Random;

public class TableroConteo extends AppCompatActivity {

    int nJugadores;
    String jugadores;

    private String[] Nombre={"","","","",""};

    ImageView ruleta;
    TextView perritoNombre;

    Random r;
    int degree = 0, degreeOld = 0;

    private static final float FACTOR = 36.0f;


    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableroconteo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRuleta);
        setSupportActionBar(toolbar);

        // Recupera la informacion de los jugadores
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        nJugadores = sharedPref.getInt("njugadores", 0);
        jugadores = sharedPref.getString("jugadores", "");

        perritoNombre = (TextView) findViewById(R.id.txt_title);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/a_song.ttf");
        perritoNombre.setTypeface(custom_font);

        for (int i = 0; i < nJugadores; i++) {
            int orden = Character.getNumericValue(jugadores.charAt(i));
            Nombre[i] = sharedPref.getString("perrito" + orden, "");
            colocaImagen("perrito" + orden, Nombre[i]);

        }

        ruleta = (ImageView) findViewById(R.id.ruletaDedos);
        r = new Random();

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

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
                        String numero = currentNumber(360 - (degree % 360));

                        editor.putString("numero",numero);
                        switch (numero){
                            case "1":
                            editor.putString("color","#5A00E8");
                            break;
                            case "2":
                            editor.putString("color","#F4E31A");
                            break;
                            case "3":
                            editor.putString("color","#BA00F0");
                            break;
                            case "4":
                            editor.putString("color","#00D837");
                            break;
                            case "5":
                            editor.putString("color","#FF0000");
                            break;
                        }

                        editor.commit();

                        Intent intent = new Intent(getBaseContext(), TableroDireccion.class);
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

        if(degrees >= (FACTOR * 1) && degrees <(FACTOR * 3)){
            text = "2";
        }

        if(degrees >= (FACTOR * 3) && degrees <(FACTOR * 5)){
            text = "3";
        }

        if(degrees >= (FACTOR * 5) && degrees <(FACTOR * 7)){
            text = "4";
        }

        if(degrees >= (FACTOR * 7) && degrees <(FACTOR * 9)){
            text = "5";
        }

        if(degrees >= (FACTOR * 9) && degrees < 360 || (degrees >= 0 && degrees < (FACTOR * 1))){
            text = "1";
        }

        return text;
    }
}
