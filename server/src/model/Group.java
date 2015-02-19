package model;

import java.util.ArrayList;

public class Group 
{
	private User drawer;
	private ArrayList<User> users;
	
	
	public Group (User drawer)
	{
		this.drawer = drawer;
		users = new ArrayList<User>();
	}
	
	public void setDrawer (User user)
	{
		if(this.drawer == null)
			this.drawer = user;
		else
			changeDrawer(user);
	}
	
	public void changeDrawer (User user)
	{
		for (int i = 0; i < users.size(); i++) 
		{
			User u = users.get(i);
			if(u.getEmail().equalsIgnoreCase(user.getEmail()))
			{
				users.set(i, this.drawer);
				this.drawer = u;
				break;
			}
		}
	}
	
	public boolean addUser (User user)
	{
		if(!user.getEmail().equalsIgnoreCase(this.drawer.getEmail()))
		{
			users.add(user);
			return true;
		}
		else
			return false;
	}
	
	public User getDrawer ()
	{
		return drawer;
	}
	
	
	public int getNumberOfUsers ()
	{
		return users.size();
	}
	
	public ArrayList<User> getList ()
	{
		return users;
	}
	
}
