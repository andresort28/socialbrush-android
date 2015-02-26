package com.Canvas.canvas;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Conexion implements Serializable
{
	

	private static final long serialVersionUID = 1L;
	
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
		
	
	public Conexion ()
	{
		try
		{
			socket = new Socket("192.168.0.102", 35999);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
		}
		catch (Exception e)
		{
			System.out.println("Error de conexion : " + e.getMessage());
		}
	}
	
	
	public String serverisEmpty ()
	{
		sendMessage(ComunicationProtocol.SERVER_IS_EMPTY);
		return readMessage();
	}
	
	
	public void sendMessage (String message)
	{
		try
		{
			out.writeUTF(message);
		}
		catch (Exception e)
		{
			System.out.println("Error al enviar mensaje:  " + e.getMessage());
		}
	}
	
	
	public void sendObject (Object obj)
	{
		try
		{
			out.writeObject(obj);
		}
		catch (Exception e)
		{
			System.out.println("Error al enviar mensaje:  " + e.getMessage());
		}
	}
	
	
	
	public String readMessage ()
	{
		try
		{
			return in.readUTF();
		}
		catch (Exception e)
		{
			System.out.println("Error al leer mensaje:  " + e.getMessage());
			return "";
		}
	}
	

	public Object readObject ()
	{
		try
		{
			return in.readObject();
		}
		catch (Exception e)
		{
			System.out.println("Error al leer mensaje:  " + e.getMessage());
			return null;
		}
	}
}
