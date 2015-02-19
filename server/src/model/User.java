package model;

public class User 
{
	private String email;
	private String name;
	private int status;
	private boolean wait;
	
	
	public User (String name, String email, int status)
	{
		this.name = name;
		this.email = email;
		this.status = status;
		this.wait = true;
	}


	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public int getStatus ()
	{
		return status;
	}

	public void changeStatus (int status)
	{
		this.status = status;
	}
	
	public boolean getWait ()
	{
		return wait;
	}
	
	public void changeWait ()
	{
		wait = !wait;
	}
	
}
