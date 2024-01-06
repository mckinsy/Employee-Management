//tip1- using static everywhere is not a good practice it consumes unnecessary memory
//tip2 - Scanner should always open at class global level and closed at the end of main method termination
package employee_management;

import java.util.*;
//import java.util.Date;
import java.sql.*;

public class EmployeeController
{
	ArrayList<Employee> employee;//dynamic size default initial size is 10 also it is global(only within same class) static variable called as instance variable as array
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
		boolean flag= "1".equals(user_name) && "1".equals(password);//true
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
				while(true) // for(i=0;;i++) for(i=0;true;i--) we can use this also start from any initial value other than 0
				{
					System.out.println(" 1. Insert an Employee \n 2. Display employee \n 3. Delete an Employee \n 4. Modify an Employee \n 0. EXIT \n");
					System.out.print(" Press Any key for action to perform : ");
					try
					{
						String option = sc.next();//int option =new Scanner(System.in).nextInt();
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
							break;            //this works because scanner eventually closes at the end of main method

						default:
							System.err.println("\n Sorry! BAD input \n \n Retry\n");
							break;  //break after default is optional
						}
					}

					catch(Exception e)
					{
						System.err.println(e);//e.printStackTrace();
						sc.close();
						Employee_JDBC_Operations.closeConnection();
						System.exit(0);
					}

				}
			}
			else return; 
	}
	public boolean chk_unique(int id)  //system.in is static if so many objects of scanner class is created in diffrent places and even if one is closed anywhere system.in is closed for all and it will throw java.util.nosuchelement exception
	{
		for(Employee x: employee)
		{
			//if(employee.contains(x.getid())) --> using this gives warning
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
				//				x.setAddress(null);
				//				x.setDOB(null);x.setGender(null);
				//				x.forceSetIDZero();x.setName(null);
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
				//sc.nextLine(); //here it is necessary to write why?
				String option0 = sc.nextLine().toLowerCase();
				if("y".equals(option0))
				{
					System.out.print("Enter New ID - ");
					x.setID(sc.nextInt());
				}
				System.out.print("Modify name? y/n : ");
				String option1 = sc.nextLine().toLowerCase(); //new technique to be noted
				if("y".equals(option1)) //using == does not work for string why??
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
				//sc.nextLine(); when this occurs when not is a big confusion
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
				if("y".equals(option4))  //either do equals ignore case or convert to lower case then check
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
		//sc.close();
	}


	public void addEmployee() 
	{
		sc=new Scanner(System.in);
		Employee emp =new Employee(); // each call of this method creates new employee object making this statement global is a big mistake bcoz each time we want new employee to be added
		System.out.print("\n \t\t : Adding an employee : \n \nEnter Employee ID : ");
		int eid=sc.nextInt(); //risky code handled in menu
		if(chk_unique(eid)&& emp.setID(eid)) //calling static member of another class
		{
			System.out.print("\nEnter Name of Employee : ");
			sc.nextLine(); //to differentiate behavior after nextint and before nextline this is just to convey jvm that after integer string is a input
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
			employee.add(emp); //array list updated
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



//	private static int size; //global(only within same class) static variable called as instance variable
//
//	/*public static void setObject(int size) //size is also a local variable but no error because instance variable is not used along with local variable inside this method if used then due to variable shadowing we must use this keyword to differentiate between instance variable and local variable 
//	  {
//		  ArrayList<Object> al=new ArrayList<Object>(); //dynamic size
//		  for(int i=0;i<size;i++)
//		  {
//		  	EmployeeController emp = new EmployeeController();
//		  	al.add(emp);
//		  }
//		  emp.get.menu();
//	  }//static method to use inside static main method without making object because this method is responsible to make object of each employee
//	 */
//	public EmployeeController()
//	{
//		this.id=setid();
//		this.name=name;
//		this.dob=dob;
//		this.address=address;
//		this.gender=gender;
//	}
//	public String toString()
//	{
//		return  this.id+"\n"+ this.name+"\n"+this.dob+"\n"+this.address+"\n"+this.gender+"\n"+this.age;
//	}
//		  int size=12;
//		  EmployeeController employee[] = new EmployeeController[size1]; //non dynamic logic