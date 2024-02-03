package employee_management;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Employee
{
	private int id;
	private String name;
	private Date dob;
	private String address;
	private String gender;
	@SuppressWarnings("unused")
	private int age;
	boolean setSuccess=false;
	public int getId() 
	{
		return this.id; 
	}
	public String getName() 
	{
		return name.toUpperCase();
	}

	public String getDob() 
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
		return formatter.format(dob);
	}
	
	public Date getDob(int a) 
	{
		return dob;
	}

	public String getAddress() 
	{
		return address.toUpperCase();
	}
	public String getGender() 
	{
		return gender.toUpperCase();
	}
	public boolean setID(int id)
	{
		if(id>0)
		{
			this.id=id;
			return true;
		}
		else 
		{
			System.err.println("ID cannot be negative or zero"); 
			return false;
		}
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public void setDOB(String dob)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
			try 
			{
				this.dob=formatter.parse(dob);
			}
			catch (ParseException e) 
			{
				System.out.println("Enter date in proper Format -> dd-MMM-yyyy");
				return;
			}
			setSuccess=true;
	}

	public void setDOB(Date dob)
	{
		this.dob=dob;
	}
	public void setGender(String gender)
	{
		this.gender=gender;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}
	public int getAge() 
	{
		return Math.abs(dob.getYear()-new Date().getYear());
	}
	public void setAge(int age) 
	{
		this.age=age;
	}
}
