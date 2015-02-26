package com.Canvas.canvas;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Lienzo extends View {

	public final static int DRAWING = 1;	
	public final static int GUESSING = 2;
	
	
	private Paint paint;
	private float x;
	private ArrayList<Punto>listPuntos;
	private float y;
	private OnTouchListener onTouchListener;
	private int color;
	private int status;
	private int radio;
	private Conexion conexion;

	public Lienzo(Context context, Conexion conexion, int newStatus) 
	{
		super(context);
		this.conexion = conexion;
		changeStatus(newStatus);
		this.radio=5;
		this.color=Color.BLACK;
		setFocusableInTouchMode(true);
		paint=new Paint();
		listPuntos=new ArrayList<Punto>();
		
		
		onTouchListener=new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View view, MotionEvent me) 
			{
				if(status == DRAWING)
				{
					if(me.getAction()==MotionEvent.ACTION_DOWN||me.getAction()==MotionEvent.ACTION_MOVE) {//
						x=me.getX();
						y=me.getY();
						Punto punto=new Punto(radio, x, y,color,false);
						listPuntos.add(punto);
						
						invalidate();
						sendPoint(punto);
					}
					else if(me.getAction()==MotionEvent.ACTION_UP){
						x=me.getX();
						y=me.getY();
						Punto punto=new Punto(radio, x, y,color,true);
						listPuntos.add(punto);						
						invalidate();
						sendPoint(punto);
					}
				}
			}
		};
		setOnTouchListener(onTouchListener);
					
	}
	
	
	public void hiloEscucha ()
	{
		Thread esperador = new Thread() {
			
			public void run ()
			{
				while(true)
				{
					Punto p = (Punto)receivePunto();
					listPuntos.add(p);
					invalidate();
				}
			}
			
		};
		esperador.start();
	}
	
	public Object receivePunto ()
	{
		return conexion.readObject();
	}
	
	public void changeStatus (int newStatus)
	{
		if(newStatus == DRAWING)
			this.status = DRAWING;
		else if(newStatus == GUESSING)
			this.status = GUESSING;
	}
	
	
	public void receivePoints ()
	{
		try
		{
			while (status == GUESSING)
			{				
				Punto punto = (Punto)conexion.readObject();
				listPuntos.add(punto);				
				invalidate();
			}
		}
		catch (Exception e)
		{
			System.out.println("Error al recibir un punto ");
		}		
	}
	
	
	public void sendPoint (Punto punto)
	{		
		try
		{
			if(conexion !=null)
			{
				conexion.sendMessage(ComunicationProtocol.SEND_POINT);
				conexion.sendObject(punto);		
			}
		}
		catch (Exception e)
		{
			System.out.println("Error al enviar el punto por flujo");
		}		
	}
	

	public void borrar(){
		this.listPuntos.clear();
		invalidate();
	}

	@Override
	protected void onDraw(android.graphics.Canvas canvas) 
	{
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);

		for(int i=0;i<listPuntos.size()-1;i++)
		{
			Punto a=listPuntos.get(i);
			Punto b=listPuntos.get(i+1);
			Punto point = listPuntos.get(i);
			paint.setColor(point.getColor());
			paint.setStrokeMiter(point.getRadio()+6);
			paint.setStrokeWidth(point.getRadio()+6);
			if(!(a.isFin()||b.isFin())){
				canvas.drawCircle(point.getX(), point.getY(), point.getRadio(), this.paint);
				canvas.drawLine(a.getX(), a.getY(), b.getX(), b.getY(), paint);
				
				try {
					
					canvas.drawCircle(b.getX(), b.getY(), b.getRadio(), this.paint);
				} catch (Exception e) {}	
			}
		}	
	}
	public void cambiarColor(int color){
		this.color=color;
	}
}