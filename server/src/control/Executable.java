package control;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import model.Punto;
import model.SocialBrush;
import model.User;

public class Executable 
{	
	private ServerSocket server;
	private SocialBrush model;	
	private static ArrayList<UserThread> users;

	public Executable () 
	{
		try
		{
			System.out.println("Starting Server...");
			server = new ServerSocket(35999);			
			System.out.println("Server up !");
			model = new SocialBrush();
			int total = 0;
			users = new ArrayList<UserThread>();
			
			while (true)
			{
				Socket socket = server.accept();
				total++;
				System.out.println("User # " + total + " connected");
				listenUser(socket);
			}
		}
		catch (Exception e)
		{
			System.out.println("Error al cargar el servidor:  " + e.getMessage());
		}
	}
	
	
	public void listenUser (Socket socket)
	{
		System.out.println("Entro al listenUser");
		UserThread thread = new UserThread(socket, model);
		System.out.println("aqui es antes de agregar el hilo a la lista");
		users.add(thread);
		model.addUser();
		System.out.println("antes del .start");
		thread.start();		
	}
	
	
	public static void sendPointToUsers (ArrayList<User> members, Punto point)
	{
		for(User u : members)
		{			
			for(UserThread thread : users )
			{
				if(thread.getEmail().equalsIgnoreCase(u.getEmail()))
				{
					thread.sendObject(point);
					break;
				}
			}
		}
	}
	
	public static void sendPointToAll (Punto p)
	{
		for(UserThread u : users)
		{
			u.sendObject(p);
		}
		
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Executable exe = new Executable();

	}

}
