package com.empmodule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Employ
{
	private int id;
	private String name;
	private String lname;
	private Date dob;
	private String address;
	private String gender;
	private String department;
	private long phno;
	private String skills;
	@SuppressWarnings("unused")
	private int age;
	boolean setSuccess=false;
	//	private Set<String> skills = new LinkedHashSet<String>();

	
	
	public String getSkills() 
	{
		return skills;
	}
	public void setSkills(String skills) 
	{
		this.skills = skills;
	}
	public String getDepartment() 
	{
		return department;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	public int getId() //getter method restricts direct access
	{
		return this.id; //current object id
	}
	public long getPhno() 
	{
		return phno;
	}
	public void setPhno(long phno) 
	{
		this.phno = phno;
	}
	public String getName() 
	{
		return name.toUpperCase();
	}
	public String getLname() 
	{
		return lname.toUpperCase();
	}

	public String getDob() 
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
		return formatter.format(dob);
	}

	public String getDob(int a) 
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
		return formatter.format(dob);
	}

	public String getAddress() 
	{
		return address.toUpperCase();
	}

	public String getGender() 
	{
		return gender.toUpperCase();
	}



	//	------------------------- SETTERS --------------------------------------------------



	public boolean setID(int id) //setter method prevent  data mis-handling
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

	public void setName(String name) //setter method prevent  data mis-handling
	{
		this.name=name;
	}

	public void setLname(String name)
	{
		this.lname=name;
	}

	public void setDOB(String dob)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
		try 
		{
			this.dob=formatter.parse(dob);
		}
		catch (ParseException e) 
		{
			System.out.println("Enter date in proper Format -> dd-MM-yyyy");
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
