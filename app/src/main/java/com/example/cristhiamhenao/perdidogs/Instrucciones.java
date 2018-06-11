package com.example.cristhiamhenao.perdidogs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.text.Line;

import org.w3c.dom.Text;

public class Instrucciones extends AppCompatActivity{

    ImageView kira, control, carro, direccion;
    TextView mueve, steps, hacia, encontraste, oel, camino;
    Button no, fin, siguiente;
    LinearLayout instruccionesLayout, contentLayout1, contentLayout2, contentLayout3, botones;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);

        kira = (ImageView) findViewById(R.id.kira);
        control = (ImageView) findViewById(R.id.control);
        carro = (ImageView) findViewById(R.id.carro);
        mueve = (TextView) findViewById(R.id.txt_mov_msj);
        steps = (TextView) findViewById(R.id.txt_steps);
        hacia= (TextView) findViewById(R.id.txt_hacia);
        encontraste = (TextView) findViewById(R.id.txt_encontraste_a);
        oel = (TextView) findViewById(R.id.txt_o_el);
        camino= (TextView) findViewById(R.id.txt_camino);
        no = (Button) findViewById(R.id.no);
        fin = (Button) findViewById(R.id.fin);
        direccion = (ImageView) findViewById(R.id.direccion);
        instruccionesLayout = (LinearLayout) findViewById(R.id.instrucciones_layout);
        contentLayout1 = (LinearLayout) findViewById(R.id.content_layout1);
        contentLayout2 = (LinearLayout) findViewById(R.id.content_layout2);
        contentLayout3 = (LinearLayout) findViewById(R.id.content_layout3);
        botones = (LinearLayout) findViewById(R.id.botones);
        siguiente = (Button) findViewById(R.id.siguiente);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/a_song.ttf");
        mueve.setTypeface(custom_font);
        steps.setTypeface(custom_font);
        hacia.setTypeface(custom_font);
        encontraste.setTypeface(custom_font);
        oel.setTypeface(custom_font);
        camino.setTypeface(custom_font);
        no.setTypeface(custom_font);
        fin.setTypeface(custom_font);
        botones.setVisibility(View.GONE);


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        String color = sharedPref.getString("color","");
        String pasos = sharedPref.getString("numero","");
        String donde = sharedPref.getString("direccion","");

        System.out.println(donde);
        int idImg = getResources().getIdentifier("com.example.cristhiamhenao.perdidogs:drawable/"+donde, null, null);
        direccion.setImageResource(idImg);
        steps.setText(pasos);
        steps.setTextColor(Color.parseColor(color));

        kira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("obstaculo","kira");
                editor.commit();

                Intent intent = new Intent(Instrucciones.this, Scanner.class);
                startActivityForResult(intent,0);
            }
        });

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("obstaculo","control");
                editor.commit();

                Intent intent = new Intent(Instrucciones.this, Scanner.class);
                startActivityForResult(intent,0);
            }
        });


        carro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("obstaculo","carro");
                editor.commit();

                Intent intent = new Intent(Instrucciones.this, Scanner.class);
                startActivityForResult(intent,0);
            }
        });


        no.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TableroConteo.class);
                startActivity(intent);
            }
        });

        fin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TableroConteo.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    sharedPref = PreferenceManager.getDefaultSharedPreferences(Instrucciones.this);
                    String obstaculo = sharedPref.getString("obstaculo","");
                    contentLayout1.setVisibility(View.GONE);
                    contentLayout2.setVisibility(View.GONE);
                    contentLayout3.setVisibility(View.GONE);
                    botones.setVisibility(View.VISIBLE);


                    switch (barcode.displayValue){
                        case "ladrido":
                            if(obstaculo == "kira"){
                                instruccionesLayout.setBackground(getResources().getDrawable(R.drawable.correcto));
                                //carita.setImageDrawable(getResources().getDrawable(R.drawable.correcto));
                            }else{
                                instruccionesLayout.setBackground(getResources().getDrawable(R.drawable.incorrecto));
                                //carita.setImageDrawable(getResources().getDrawable(R.drawable.incorrecto));
                            }
                            break;
                        case "excavacion":
                            if(obstaculo == "control" || obstaculo == "carro"){
                                instruccionesLayout.setBackground(getResources().getDrawable(R.drawable.correcto));
                            }else{
                                instruccionesLayout.setBackground(getResources().getDrawable(R.drawable.incorrecto));
                            }
                            break;
                        case "salto":
                            if(obstaculo == "control" || obstaculo == "carro"){
                                instruccionesLayout.setBackground(getResources().getDrawable(R.drawable.correcto));
                            }else{
                                instruccionesLayout.setBackground(getResources().getDrawable(R.drawable.incorrecto));
                            }
                            break;
                        default:
                            instruccionesLayout.setBackground(getResources().getDrawable(R.drawable.chulo));

                    }
                }else{
                    System.out.println("Nada de data");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
