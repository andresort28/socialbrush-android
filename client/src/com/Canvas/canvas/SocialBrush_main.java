package com.Canvas.canvas;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SocialBrush_main extends Activity 
{

	public final static String CONEXION = "com.Canvas.canvas.CONEXION";
	public final static String STATUS = "com.Canvas.canvas.STATUS";

	private Conexion conexion;

	private String id;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		id = null;  
		conexion == null;
	}


	public void clickPlay (View view)
	{
		askId();
		if(id == null)
			System.out.println("La persona cancelo el registro");
		else
		{
			if(conexion == null)
			{
				conexion = new Conexion();		
				String msg = conexion.readMessage();
				conexion.sendMessage(ComunicationProtocol.SEND_ID);
				conexion.sendMessage(id);	
				
				conexion.sendMessage(ComunicationProtocol.PLAY_MODE);
				msg = conexion.readMessage();
				
				if(msg.equalsIgnoreCase(ComunicationProtocol.PLAY_MODE_OK))
				{
					Intent intent = new Intent(this, PintarActivity.class);    	
					intent.putExtra(CONEXION, conexion);    	
					intent.putExtra(STATUS, Lienzo.DRAWING);
					this.startActivity(intent);
				}

				else if(msg.equalsIgnoreCase(ComunicationProtocol.PLAY_MODE_WRONG))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(this);

					builder.setTitle("Sorry !");
					builder.setMessage("No hay usuarios en linea ");
					builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int whichButton) 
						{
							dialog.cancel();
						}
					});

					builder.show();
				}
				
			}
			else
			{
				System.out.println("Ya se habia conectado antes al servidor, por lo tanto no puede...");
			}
		}
	}




	public void clickGuess (View view)
	{
		if(conexion == null)
		{
			conexion = new Conexion();
			conexion.sendMessage(ComunicationProtocol.SEND_ID);
			askId();
			conexion.sendMessage(id);
		}

		conexion.sendMessage(ComunicationProtocol.GUESS_MODE);   	    	 
		String msg = conexion.readMessage();

		if(msg.equalsIgnoreCase(ComunicationProtocol.GUESS_MODE_OK))
		{
			Intent intent = new Intent(this, PintarActivity.class);    	
			intent.putExtra(CONEXION, conexion);  
			intent.putExtra(STATUS, Lienzo.DRAWING);
			this.startActivity(intent);
		}
	}



	public void click (View view)
	{
		conexion = new Conexion();		
		String msg = conexion.readMessage();	
		
		Intent intent = new Intent(this, PintarActivity.class);
		
		if(conexion.serverisEmpty().equalsIgnoreCase("true"))		
			intent.putExtra(STATUS, Lienzo.DRAWING);
		else
			intent.putExtra(STATUS, Lienzo.GUESSING); 
		this.startActivity(intent);
	}



	public void askId ()
	{

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("ID");
		alert.setMessage("Please enter your ID");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int whichButton) 
			{
				id = input.getText().toString();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				id = null;
				// Canceled.
			}
		});
		alert.show();
	}



}