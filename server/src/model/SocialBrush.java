package model;

import java.util.ArrayList;
import java.util.Hashtable;

import control.UserThread;

public class SocialBrush 
{
	private Hashtable<String, String> database;
	
	private ArrayList<User> listUsers;	
	private ArrayList<Group> listGroup;
	
	private int users;
	
	
	
	public SocialBrush ()
	{
		users = 0;
		
		database = new Hashtable<String, String>();
		
		database.put("001", "Andres Ortiz");
		database.put("002", "Adonis Cruz");
		database.put("003", "Edward Trujillo");
		database.put("004", "MIT");
		
		listUsers = new ArrayList<User>();
		listGroup = new ArrayList<Group>();
	}
	
	public boolean hasUsers ()
	{
		if(users > 0)
			return true;
		return false;
	}
	
		
	public void modifyUser (String email, int nuevoStatus)
	{
		User user = getUser(email);
		if(user == null)
		{
			String name = database.get(email);
			User u = new User(name, email, nuevoStatus);
			listUsers.add(u);
		}
		else
			user.changeStatus(nuevoStatus);
	}
	
	
	public User getUser (String email)
	{
		for(User u : listUsers)
		{
			if(u.getEmail().equalsIgnoreCase(email))
				return u;
		}
		return null;
	}
	
	public Group makeGroup (String email)
	{
		User drawer = getUser(email);
		Group group = new Group(drawer);
		int number = 0;
		for (int i = 0; i < listUsers.size() && number < 2; i++) 
		{
			User u = listUsers.get(i);
			if(u.getStatus() == UserThread.GUESSING)
			{
				group.addUser(u);
				number++;
			}
		}
		
		for(User u : group.getList())
		{
			u.changeWait();
		}
		
		if(number > 0)
			return group;
		return null;
	}
	
	public Group getGroupByDrawer (String email)
	{
		for( Group g : listGroup)
		{
			if(g.getDrawer().getEmail().equalsIgnoreCase(email))
				return g;
		}
		return null;
	}
	
	
	public void deleteUser (String email)
	{
		listUsers.remove(getUser(email));
	}
	
	//solo para prueba de ctc
	
	public void addUser ()
	{
		users++;
	}

}
