import java.sql.*;
import java.util.Scanner;
public class Main {
    static int total_hostel=0;
    static String username,password;
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        try
        { 
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
            Statement stml=con.createStatement();
            boolean check_main=true;
            while(check_main)
            {
                System.out.println("Enter quit or exit as Hostel_Code to exit...");
                System.out.print("Enter Hostel_Code ->");
                username=sc.nextLine();
                if(username.equalsIgnoreCase("exit") || username.equalsIgnoreCase("quit")) return;
                System.out.print("Enter Password ->");
                password=sc.nextLine();
                ResultSet rs=stml.executeQuery("select Password from id_authentication where Code_ID='"+username+"';");
                if(!rs.next()) System.out.println("No Hostel Found. Try Again...");
                else 
                {
                    if(username.equals("yourspace"))
                    {
                        if(password.equals(rs.getString(1)))
                        {
                            Admin.menu();
                            check_main=false;
                            return;
                        }
                        else 
                        {
                            System.out.println("Incorrect Password. Try Again... ");
                        }
                    }
                    else 
                    {
                        if(password.equals(rs.getString(1)))
                        {
                            check_main=false;
                            Hostel_Manager.menu(username,true);
                            return;
                        }
                        else 
                            System.out.println("Incorrect Password. Try Again...");
                    }
                }
                System.out.println("Press any key to continue...");
                sc.nextLine();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
            }
            sc.close();
            con.close();
            stml.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
