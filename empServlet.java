package com.empcontroller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.empmodule.EmployJDBC;
import com.empmodule.Employee;

@WebServlet("/emp")
public class EmpServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    
    public EmpServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int uniqueId=0;
		ArrayList<Employee> ls = new ArrayList<Employee>();
		try {
			ls  = EmployJDBC.fetchFromDB();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		uniqueId=ls.get(ls.size()-1).getId()+1;
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		long phno = Long.parseLong(request.getParameter("phn"));
		String gender = request.getParameter("gender");
		String skills = request.getParameter("skill");
		String dob = request.getParameter("dob");
		String dept = request.getParameter("department");
		String add = request.getParameter("Address");
		
		Employee e =new Employee();
		e.setID(uniqueId);
		e.setName(fname);
		e.setLname(lname);
		e.setPhno(phno);
		e.setGender(gender);
		e.setSkills(skills);
		e.setDOB(dob);
		e.setDepartment(dept);
		e.setAddress(add);
		
		try {
			EmployJDBC.AddToDatabase(e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		System.out.print("Recently added --> ");
		System.out.println(uniqueId+" "+fname+" "+lname+" "+phno+" "+gender+" "+skills+" "+dob+" "+dept+" "+add);
		
		 request.setAttribute("data",ls); 
		  
	         RequestDispatcher rd =  request.getRequestDispatcher("homepage.jsp");
	  
	          rd.forward(request, response);
		
		
//		response.sendRedirect("");
		
	    }
	}
