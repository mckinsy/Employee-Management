package employee_management;

import java.util.ArrayList;
import java.sql.*;
import java.text.ParseException;

public class Employee_JDBC_Operations 
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

			String query="insert into employee values(?,?,?,?,?)";

			pstmt= con.prepareStatement(query);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	public static ArrayList<Employee> fetchFromDB() throws SQLException
	{
		ArrayList<Employee> data=new ArrayList<Employee>();
		String str="select * from employee";

		boilerPlate();

		rs=pstmt.executeQuery(str);
		while(rs.next())
		{
			Employee employee=new Employee();
			employee.setID(rs.getInt(1));
			employee.setName(rs.getString(2));
			employee.setDOB(rs.getDate(3));
			employee.setGender(rs.getString(4));
			employee.setAddress(rs.getString(5));
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

	public static void updateToDatabase(ArrayList<Employee> employee) throws ParseException
	{
		try {
			pstmt.execute("truncate table employee");
			for(Employee element:employee)
			{
				pstmt.setInt(1,element.getId());
				pstmt.setString(2,element.getName());
				pstmt.setDate(3,(Date) element.getDob(0));
				pstmt.setString(4,element.getGender());
				pstmt.setString(5,element.getAddress());
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("here");
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
	}

}
