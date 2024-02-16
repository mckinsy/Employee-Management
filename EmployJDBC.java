package com.empmodule;

import java.util.ArrayList;
import java.sql.*;
import java.text.ParseException;

public class EmployJDBC 
{
	static Connection con=null;
	static PreparedStatement pstmt=null;
	static ResultSet rs=null;

	public static void boilerPlate()
	{

		try
		{  
			Class.forName("oracle.jdbc.driver.OracleDriver");  			  

			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcle123","scott","tiger");  

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	public static ArrayList<Employ> fetchFromDB() throws SQLException
	{
		ArrayList<Employ> data=new ArrayList<Employ>();

		boilerPlate();
		
		String query="select * from emply";

		pstmt= con.prepareStatement(query);
		
		rs=pstmt.executeQuery(query);//"alter table employee drop column age");  
		while(rs.next())
		{
			Employ employee=new Employ();
			employee.setID(rs.getInt(1));
			employee.setName(rs.getString(2));
			employee.setLname(rs.getString(3));
			employee.setPhno(rs.getInt(4));
			employee.setGender(rs.getString(5));
			employee.setSkills(rs.getString(6));
			employee.setDOB(rs.getDate(7));
			employee.setDepartment(rs.getString(8));
			employee.setAddress(rs.getString(9));
			data.add(employee);
		}
		
		try
		{
			rs.close();
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return data;

	}

	public static void closeConnection()
	{
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateToDatabase(Employ employee) throws ParseException
	{
		try {
			
			
			pstmt.execute("truncate table emply"); //boolean return type for dql true for other than dql false
			//pstmt.execute("alter table employee drop column age");
			
//			System.out.println("java");
			
			boilerPlate();
		

//			for(Employ element:employee)
//			{
				pstmt= con.prepareStatement("insert into emply values(?,?,?,?,?,?,?,?,?)");
				pstmt.setInt(1,employee.getId());
				pstmt.setString(2,employee.getName());
				pstmt.setString(3,employee.getLname());
				pstmt.setLong(4,employee.getPhno());
				pstmt.setString(5,employee.getGender());
				pstmt.setString(6,employee.getSkills());
				pstmt.setString(7,employee.getDob(0));//(Date) new SimpleDateFormat().parse(element.getDob()));
				pstmt.setString(8,employee.getDepartment());
				pstmt.setString(9,employee.getAddress());
				pstmt.executeUpdate(); //Execution for DML
//			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			closeConnection();
		}

		//String str="create table Employee(id number(4) primary key, ename varchar2(10) not null, dob date not null, age number(2) check(age between 21 and 59), gender varchar2(6) not null check(gender in('MALE','FEMALE')), address varchar2(100) not null)";
		//id,name,dob,age,gender,address
	}

}
