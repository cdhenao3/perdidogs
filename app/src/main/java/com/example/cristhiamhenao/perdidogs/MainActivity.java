package com.example.cristhiamhenao.perdidogs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static int MAXJUGADORES = 4;

    TextView selectJugadores;

    ImageButton Jugador1,Jugador2,Jugador3,Jugador4;
    ImageButton btnAceptar;

    private boolean[] seleccionBotones = {false, false, false, false};
    EditText[] Nombres = new EditText[4];

    private int nJugadores = 0;
    private boolean minimoJugadores = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAceptar =(ImageButton)findViewById(R.id.btnConfirmacion);

        btnAceptar.setEnabled(false);
        btnAceptar.setAlpha(.5f);

        Jugador1=(ImageButton)findViewById(R.id.jugador1);
        Jugador2=(ImageButton)findViewById(R.id.jugador2);
        Jugador3=(ImageButton)findViewById(R.id.jugador3);
        Jugador4=(ImageButton)findViewById(R.id.jugador4);

        Jugador1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegir(0, Jugador1);
            }
        });
        Jugador2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegir(1, Jugador2);;
            }
        });
        Jugador3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegir(2, Jugador3);;
            }
        });
        Jugador4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegir(3, Jugador4);;
            }
        });

        selectJugadores = (TextView) findViewById(R.id.select_jugadores);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/a_song.ttf");
        selectJugadores.setTypeface(custom_font);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardaJugadores();
                Intent intent = new Intent(getBaseContext(), TableroConteo.class);
                startActivity(intent);

            }
        });

    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void elegir(int p, ImageButton b)
    {
        seleccionBotones[p] = !seleccionBotones[p];

        if (seleccionBotones[p]) {
            b.setBackgroundResource(R.drawable.borde);
            //Nombres[p].requestFocus();
        }
        else {
            b.setBackgroundColor(Color.TRANSPARENT);
            //Nombres[p].clearFocus();
            //btnAceptar.requestFocus();
            getWindow().getDecorView().clearFocus();
        }

        nJugadores = 0;

        for (int i=0;i<MAXJUGADORES;i++)
        {
            if (seleccionBotones[i]) nJugadores++;
        }
        minimoJugadores = (nJugadores > 0);

        if (minimoJugadores) {
            btnAceptar.setEnabled(true);
            btnAceptar.setAlpha(1.0f);
        }
        else {
            btnAceptar.setEnabled(false);
            btnAceptar.setAlpha(.5f);
        }
    }

    private void guardaJugadores()
    {
        // Guarda los jugadores y sus nombres
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("njugadores", nJugadores);


        String jugadores = "";

        for (int i=0;i<MAXJUGADORES;i++)
        {
            if (seleccionBotones[i])
            {
                jugadores += i;
            }
        }
        editor.putString("jugadores",jugadores);
        editor.putString("perrito0","Lupe");
        editor.putString("perrito2","Toby");
        editor.putString("perrito1","Tina");
        editor.putString("perrito3","Firulais");
        System.out.println("NJug = "+nJugadores+" jug= "+jugadores);
        editor.commit();
    }
}
