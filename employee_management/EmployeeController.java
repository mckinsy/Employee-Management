package employee_management;

import java.util.*;
import java.sql.*;

public class EmployeeController
{
	ArrayList<Employee> employee;
	boolean root;
	Scanner sc;


	public void menu(String user_name,String password) 
	{
		try {
			employee=Employee_JDBC_Operations.fetchFromDB();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		sc=new Scanner(System.in);
		boolean flag= "1".equals(user_name) && "1".equals(password);
		if(flag)
			System.out.print("\n\tUser Authentication Successful!   Hello : ");System.out.println(user_name+"\n");
			if(!flag)
			{
				System.err.print("\t\t\tOOPs.... ");System.out.println("Wrong Username or Password");
				System.out.println("\nRequired Root privilege without user_name and password. Press y if you want to Enter as Root");
				root = "Y".equalsIgnoreCase(sc.nextLine().toLowerCase());
			}
			if(root||flag)
			{
				while(true) 
				{
					System.out.println(" 1. Insert an Employee \n 2. Display employee \n 3. Delete an Employee \n 4. Modify an Employee \n 0. EXIT \n");
					System.out.print(" Press Any key for action to perform : ");
					try
					{
						String option = sc.next();
						switch(option)
						{

						case "1" :
							if(flag)
							{
								try 
								{
									addEmployee();
									break;
								}
								catch(Exception e)
								{
									System.err.println("Only Numeric Id is Allowed");
								}
							}
							else System.err.println("\nRoot user has Insufficient Authority to Add an Employee \n");

							break;

						case "2" :

							displayAllEmp();
							break;

						case "3" :

							if(flag) 
							{
								System.out.println("\nEnter ID of Employee to Delete");
								try 
								{
									evaluateExistence_and_Delete(sc.nextInt());
									break;
								}
								catch(Exception e)
								{
									System.err.println("ID must be Numeric \n");
								}
							}
							else System.out.println("Root user has No Authority to Delete");
							break;

						case "4" :

							if(flag)
							{
								System.out.println("\nEnter ID Before Modify : ");
								try 
								{
									evaluateExistence_and_Modify(sc.nextInt());
								}
								catch(Exception e)
								{
									System.err.println("\nOnly numeric ID allowed \n");
								}
							}
							else System.err.println("Root user has No Authority to Modify \n");
							break;

						case "0" :

							System.out.println("\n\tExited...");
							sc.close();
							Employee_JDBC_Operations.updateToDatabase(employee);
							System.exit(0);
							break;            

						default:
							System.err.println("\n Sorry! BAD input \n \n Retry\n");
							break;  
						}
					}

					catch(Exception e)
					{
						System.err.println(e);
						sc.close();
						Employee_JDBC_Operations.closeConnection();
						System.exit(0);
					}

				}
			}
			else return; 
	}
	public boolean chk_unique(int id)  
	{
		for(Employee x: employee)
		{
			
			if(x.getId()==id && id>0) 
			{
				System.err.println("\nEnter correct ID this Id's existing one or inappropriate \n");
				return false;
			}
		}
		return true;
	}


	public void evaluateExistence_and_Delete(int id)
	{
		boolean deletionSuccess=false;
		for(Employee x: employee)
		{
			if(x.getId()==id) 
			{
				System.out.print("ID Found! Employee -> ");System.err.println(x.getName()+"\n");
				System.err.print("Are you sure you want to delete?");System.out.print(x.getName());System.err.println(" True/False");
				sc=new Scanner(System.in);

				if(sc.nextBoolean())
					employee.remove(x);

				else return;
				System.out.println("Deletion Success! \n");
				deletionSuccess=true;
				break;
			}
		}
		if(!deletionSuccess)
		{
			System.err.println("\nBad ID! No Employee Of Following ID Exist \n");
		}
	}


	public void evaluateExistence_and_Modify(int id)
	{
		sc=new Scanner(System.in);
		boolean modificationSuccess=false;
		for(Employee x: employee)
		{
			if(x.getId()==id) 
			{
				System.out.println("ID Found, Employee "+x.getName());
				System.out.print("Modify ID? y/n : ");
				
				String option0 = sc.nextLine().toLowerCase();
				if("y".equals(option0))
				{
					System.out.print("Enter New ID - ");
					x.setID(sc.nextInt());
				}
				System.out.print("Modify name? y/n : ");
				String option1 = sc.nextLine().toLowerCase(); 
				if("y".equals(option1)) 
				{
					System.out.print("Enter New Name - ");
					x.setName(sc.nextLine());
				}
				System.out.print("Modify Gender? y/n : ");
				String option2 = sc.nextLine().toLowerCase();
				if("y".equals(option2))
				{
					System.out.print("Enter New Gender - ");
					x.setGender(sc.nextLine());
				}
				System.out.print("Modify DOB? y/n : ");
				
				String option3 = sc.nextLine().toLowerCase();
				if("y".equals(option3))
				{
					x.setSuccess=false;
					while(!x.setSuccess) 
					{
						System.out.print("Enter new DOB - ");
						x.setDOB(sc.nextLine());
					}
				}
				System.out.print("Modify Address? y/n : ");
				//sc.nextLine();
				String option4 = sc.nextLine().toLowerCase();
				if("y".equals(option4))  
				{
					System.out.print("Enter New Address - ");
					x.setAddress(sc.nextLine());
				}
				System.out.println("\nWOHOOO! Changes Saved!\n");
				modificationSuccess=true;
				break;
			}
		}
		if(!modificationSuccess)
			System.err.println("Bad ID! No Employee of ID as Such Exist \n");
	}


	public void addEmployee() 
	{
		sc=new Scanner(System.in);
		Employee emp =new Employee(); 
		System.out.print("\n \t\t : Adding an employee : \n \nEnter Employee ID : ");
		int eid=sc.nextInt(); 
		if(chk_unique(eid)&& emp.setID(eid))
		{
			System.out.print("\nEnter Name of Employee : ");
			sc.nextLine(); 
			String ename=sc.nextLine();
			emp.setName(ename); 
			while(!emp.setSuccess)
			{
				System.out.print("Enter Date of Birth : ");
				String eDoB=sc.nextLine();
				emp.setDOB(eDoB);
			}
			System.out.print("Enter Gender Of Employee : ");
			String egender=sc.next();
			emp.setGender(egender);
			sc.nextLine();
			System.out.print("Enter Address : ");
			String add=sc.nextLine();
			emp.setAddress(add);
			employee.add(emp);
			System.out.println();
			System.out.println("Success! Adding an Employee \n");
		}
		else return; 
	}


	public void displayAllEmp() 
	{
		System.out.println("\nDisplaying Employees \n");
		if (employee.isEmpty())
		{
			System.err.println("No record available.");
			return;
		}
		System.out.println("******************************************************************************************************************************************************************************************************************");
		for(Employee x: employee)
		{
			System.out.print("Employee ID - "+x.getId()+"\t Name - "+x.getName()+"\t Gender - "+x.getGender()+"\t Address - "+x.getAddress()+"\t DOB - "+x.getDob()+"\t Age - "+x.getAge()+"\n");
			System.out.println();
		}
		System.out.println("******************************************************************************************************************************************************************************************************************");
	}


	public static void main(String[] args) 
	{
		Scanner sc=new Scanner(System.in);
		EmployeeController e = new EmployeeController();
		System.out.println("Enter user name");
		String user_name=sc.nextLine();
		System.out.println("Enter Password");
		String password=sc.nextLine();
		e.menu(user_name,password);
		sc.close();
	}
}
