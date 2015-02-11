package control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.sound.midi.Receiver;
import model.Group;
import model.Punto;
import model.SocialBrush;
import model.User;
import protocol.ComunicationProtocol;

public class UserThread extends Thread
{
	public final static int DEFAULT = 0;
	public final static int DRAWING = 1;
	public final static int GUESSING = 2;
	
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private SocialBrush model;
	private int status;
	private boolean login;
	private String id;
	
	public UserThread (Socket socket, SocialBrush model)
	{		
		try
		{	
			this.model = model;	
			System.out.println("antes de crear los flujos");
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("medio paso");
			in = new ObjectInputStream(socket.getInputStream());			
			System.out.println("flujos ya creados");
			login = true;
			status = DEFAULT;
			id = "";
		}
		catch (Exception e)
		{
			System.out.println("Error al conectar cliente: " + e.getMessage());
		}
		
	}
	
	public String getEmail ()
	{
		return id;
	}

	public void run ()
	{
		System.out.println("arranca hilo cliente");
		try
		{
			System.out.println("Entro al hilo");
			Group group = null;
			sendMessage(ComunicationProtocol.WAITING_FOR_REQUEST);
			while (login)
			{
				System.out.println("se logeo");
				String msg = in.readUTF();
				
				System.out.println("antes de entrar al switch, mensaje: " + msg);
				switch (msg)
				{
					case ComunicationProtocol.SERVER_IS_EMPTY:
						if(!model.hasUsers())
							sendMessage("true");
						else
							sendMessage("false");
						break;
					
					case ComunicationProtocol.PLAY_MODE:			
						group = model.makeGroup(id);						
						//SE NOTIFICA A USUARIO DE SUS COMPETIDORES						
						if(group != null)
						{
							status = DRAWING;
							model.modifyUser(id, status);
							System.out.println("se creo grupo y se va a enviar al cliente");
							sendMessage("Connected with " + group.getNumberOfUsers());
							sendMessage(ComunicationProtocol.PLAY_MODE_OK);
						}							
						else
						{
							System.out.println("No se pudo crear grupo");
							sendMessage(ComunicationProtocol.PLAY_MODE_WRONG);
						}
							
						break;
						
					case ComunicationProtocol.GUESS_MODE:						
						status = GUESSING;
						model.modifyUser(id, status);
						User user = model.getUser(id);
						
						while (user.getWait())
						{
							//Mantiene en estado de espera por una conexion a un grupo
						}						
						sendMessage(ComunicationProtocol.GUESS_MODE_OK);
						break;
						
					case ComunicationProtocol.LOG_OUT:
						login = false;
						model.deleteUser(id);
						break;
						
					case ComunicationProtocol.SEND_POINT:
						Punto point = (Punto)readObject();
						//group = model.getGroupByDrawer(id);
						//Executable.sendPointToUsers(group.getList(), point);	
						Executable.sendPointToAll(point);
						break;
					
					case ComunicationProtocol.SEND_ID:
						id = readMessage();
						break;
				}
			}
			in.close();
			out.close();
		}
		catch (Exception e)
		{
			System.out.println("Error en el hilo:  " + e.getMessage());
		}
		
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
