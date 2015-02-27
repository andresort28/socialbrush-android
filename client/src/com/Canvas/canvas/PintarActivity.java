package com.Canvas.canvas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;

public class PintarActivity extends Activity {
    /** Called when the activity is first created. */
	private Lienzo lienzo;


	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Conexion conexion = (Conexion)intent.getSerializableExtra(SocialBrush_main.CONEXION);
        int status = (int)intent.getIntExtra(SocialBrush_main.STATUS, 0);       
        
        setContentView(R.layout.canvas);
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.layoutCanvas);
        this.lienzo = new Lienzo(this, conexion, status);
        frameLayout.addView(lienzo);        

        Chronometer chronometer=(Chronometer)findViewById(R.id.chronometer1);
        chronometer.start();
        seleccionarColor();
        
        if(status == Lienzo.GUESSING)
        	lienzo.hiloEscucha();
        //lienzo.receivePoints();
    }
    
    
    public void seleccionarColor()
    {
    	OnClickListener clickListener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				switch (v.getId()) {
				
				case R.id.borrar:
					lienzo.borrar();
					break;
				
				case R.id.amarillo:
					lienzo.cambiarColor(Color.YELLOW);
					break;
					
				case R.id.azul:
					lienzo.cambiarColor(Color.BLUE);
					break;

				case R.id.rojo:
					lienzo.cambiarColor(Color.RED);
					break;
					
				case R.id.verde:
					lienzo.cambiarColor(Color.GREEN);
					break;
					
				case R.id.morado:
					lienzo.cambiarColor(Color.argb(255, 197, 82, 255));
					break;
					
				case R.id.negro:
					lienzo.cambiarColor(Color.BLACK);
					break;
					
				case R.id.cafe:
					lienzo.cambiarColor(Color.argb(255, 187, 144, 0));
					break;
				}
			}
		};
		((Button)findViewById(R.id.amarillo)).setOnClickListener(clickListener);
		((Button)findViewById(R.id.azul)).setOnClickListener(clickListener);
		((Button)findViewById(R.id.rojo)).setOnClickListener(clickListener);
		((Button)findViewById(R.id.negro)).setOnClickListener(clickListener);
		((Button)findViewById(R.id.borrar)).setOnClickListener(clickListener);
		((Button)findViewById(R.id.cafe)).setOnClickListener(clickListener);
		((Button)findViewById(R.id.morado)).setOnClickListener(clickListener);
		((Button)findViewById(R.id.verde)).setOnClickListener(clickListener);
    }
}