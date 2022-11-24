import java.sql.*;
class Hostel_Manager
{
    void print_gap(int a,int max)
    {
        int count=0;
        if(a<=0)
            count++;
        while(a!=0)
        {
            count++;
            a/=10;
        }
        for(int i=0;i<max-count;i++)
        {
            System.out.print(" ");
        }
    }
    void print_gap(String s,int max)
    {
        for(int i=0;i<max-s.length();i++)
        {
            System.out.print(" ");
        }
    }
    void new_student(String username) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
        Statement stml=con.createStatement();
        ResultSet rs=stml.executeQuery("Select Remaining_Seats from hostel_details where Hostel_Code='"+username+"';");
        rs.next();
        if(rs.getInt(1)==0)
        {
            System.out.println("Hostel is FULL...");
            return;
        }
        int remaining=rs.getInt(1);
        remaining--;
        System.out.print("Enter Name of new Student ->");
        String stu_name=Main.sc.nextLine();
        char gen='F';
        while(true)
        {
            System.out.print("Enter Gender(M/F) ->");
            gen=Main.sc.next().charAt(0);
            Main.sc.nextLine();
            if(gen=='m') gen='M';
            else if(gen=='f') gen='F';
            if(gen=='F' || gen=='M')
                break;
            else
            {
                System.out.println("Invalid Gender try again... Press Enter to continue...");
                Main.sc.nextLine();
            }
        }
        stml.executeUpdate("insert into "+username+"(student_name,Gender) values ('"+stu_name+"','"+gen+"');");
        stml.executeUpdate("update hostel_details set Remaining_Seats = "+remaining+" where Hostel_Code = '"+username+"';");

        System.out.println("Data entered successfully...");
        con.close();
        stml.close();
    }
    void remove_student(String username) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
        Statement stml=con.createStatement();
        System.out.print("Enter Student ID to remove ->");
        int id=Main.sc.nextInt();
        Main.sc.nextLine();
        ResultSet rs=stml.executeQuery("select * from "+username+" where student_id ="+id+";");
        if(!rs.next())
        {
            System.out.println("Wrong Student ID.");
            return;
        }
        stml.executeUpdate("delete from "+username+" where student_id="+id+";");
        rs=stml.executeQuery("select Remaining_Seats from hostel_details where Hostel_Code='"+username+"';");
        rs.next();
        int remaining=rs.getInt(1);
        remaining++;
        stml.executeUpdate("update hostel_details set Remaining_Seats = "+remaining+" where Hostel_Code = '"+username+"';");
        System.out.println("Data Deleted Successfully...");
        con.close();
        stml.close();
    }
    void student_details(String username) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
        Statement stml=con.createStatement();
        ResultSet rs=stml.executeQuery("select * from "+username+";");
        if(!rs.next())
        {
            System.out.println("No Student Present as of now...");
            return;
        }
        rs=stml.executeQuery("select * from "+username+";");
        int max=20;
        print_gap("Student_ID",max);
        System.out.print("Student_ID");
        print_gap("Student_Name", max);
        System.out.print("Student_Name");
        print_gap("Gender", max);
        System.out.println("Gender");
        System.out.println();
        while(rs.next())
        {
            print_gap(rs.getInt(1),max);
            System.out.print(rs.getInt(1));
            print_gap(rs.getString(2),max);
            System.out.print(rs.getString(2));
            print_gap(rs.getString(3),max);
            System.out.print(rs.getString(3));
            System.out.println();
        }
        con.close();
        stml.close();
    }
    void change_seats(String username) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
        Statement stml=con.createStatement();
        System.out.print("Enter updated no of seats ->");
        int new_seats=Main.sc.nextInt();
        Main.sc.nextLine();
        ResultSet rs=stml.executeQuery("select Remaining_Seats,Total_Seats from hostel_details where Hostel_Code='"+username+"';");
        rs.next();
        if(new_seats<rs.getInt(1))
        {
            System.out.println("Update can't be applied...");
            return;
        }
        int remaining=rs.getInt(1) + (new_seats-rs.getInt(2));
        stml.executeUpdate("update hostel_details set Total_Seats = "+new_seats+" where Hostel_Code = '"+username+"';");
        stml.executeUpdate("update hostel_details set Remaining_Seats = "+remaining+" where Hostel_Code = '"+username+"';");
        System.out.println("Changes Applied...");
        con.close();
        stml.close();
    }
    void change_password(String username) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
        Statement stml=con.createStatement();
        System.out.print("Enter new Password -:");
        String pass=Main.sc.nextLine();
        stml.executeUpdate("update id_authentication set Password='"+pass+"' where Code_ID='"+username+"';");
        System.out.println("Password has been updated...");
        con.close();
        stml.close();
    }
    static void menu(String hostel_Code,boolean check)
    {
        boolean condition=true;
        while(condition)
        {
            try{
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.println("Welcome to "+hostel_Code+" Management Page...");
                System.out.println();
                System.out.println("******** Main-Menu ********");
                System.out.println("1. Add New Student Detail.");
                System.out.println("2. Remove Existing Student.");
                System.out.println("3. Change Password.");
                System.out.println("4. See Details of every students.");
                System.out.println("5. Update Total Seats.");
                if(check==true)
                    System.out.println("6. Exit.");
                else 
                    System.out.println("6. Back.");
                System.out.print("Enter your choice -:");
                int choice = Main.sc.nextInt();
                Main.sc.nextLine();
                condition=choose(choice,hostel_Code);
            }
            catch(Exception e)
            {
                System.out.println(e);
                System.out.println("Try again... Press Enter to continue...");
                Main.sc.nextLine();
                Main.sc.nextLine();
            }
        }
    }

    static boolean choose(int choice,String hostel_Code)
    {
        System.out.println();
        Hostel_Manager obj=new Hostel_Manager();
        try{
            switch(choice)
            {
                case 1:
                    obj.new_student(hostel_Code);
                    break;
                case 2:
                    obj.remove_student(hostel_Code);
                    break;
                case 3:
                    obj.change_password(hostel_Code);
                    break;
                case 4:
                    obj.student_details(hostel_Code);
                    break;
                case 5:
                    obj.change_seats(hostel_Code);
                    break;
                case 6:
                    return false;
                default:
                    System.out.println("Invalid Choice. Try Again...");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("Press Enter to continue...");
        Main.sc.nextLine();
        return true;
    }
}

public class Admin extends Hostel_Manager{
    private static String org_name="Your Space";
    void new_hostel()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
            Statement stml=con.createStatement();
            System.out.print("Enter Hostel_Code for New Hostel ->");
            String username=Main.sc.nextLine();
            System.out.print("Enter New Password ->");
            String pass=Main.sc.nextLine();
            System.out.print("Enter Total Seats -:");
            int total_seats=Main.sc.nextInt();
            Main.sc.nextLine();
            
            stml.executeUpdate("create table "+username+"(student_id int auto_increment,student_name varchar(40),Gender varchar(1),primary key (student_id));");
            stml.executeUpdate("Insert into hostel_details values ('"+username+"',"+total_seats+","+total_seats+");");
            stml.executeUpdate("Insert into id_authentication values ('"+username+"','"+pass+"');");
            System.out.println("Hostel Added Successfully...");
            con.close();
            stml.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    void remove_hostel()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
            Statement stml=con.createStatement();
            ResultSet rs=stml.executeQuery("select Hostel_Code from hostel_details;");
            System.out.println("List of all Hostel ID's -:");
            while(rs.next())
            {
                System.out.println(rs.getString(1));
            }
            System.out.println();
            System.out.print("Enter Hostel_Code for the Hostel to be deleted->");
            String username=Main.sc.nextLine();
            rs=stml.executeQuery("select * from hostel_details where Hostel_Code='"+username+"';");
            if(!rs.next())
            {
                System.out.println("No Hostel found.");
                return;
            }
            stml.executeUpdate("drop table "+username+";");
            stml.executeUpdate("delete from id_authentication where Code_ID='"+username+"';");
            stml.executeUpdate("delete from hostel_details where Hostel_Code='"+username+"';");
            System.out.println(username+" deleted successfully...");
            con.close();
            stml.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    void view_hostels() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management","harshit","Harsh@9977");
        Statement stml=con.createStatement();
        ResultSet rs=stml.executeQuery("select * from hostel_details;");
        if(!rs.next())
        {
            System.out.println("No Hostel under the Organization...");
            return;
        }
        rs=stml.executeQuery("select * from hostel_details;");
        int max=20;
        print_gap("Hostel",max);
        System.out.print("Hostel");
        print_gap("Remaining", max);
        System.out.print("Remaining");
        print_gap("Total", max);
        System.out.println("Total");
        System.out.println();
        while(rs.next())
        {
            print_gap(rs.getString(1),max);
            System.out.print(rs.getString(1));
            print_gap(rs.getInt(2),max);
            System.out.print(rs.getInt(2));
            print_gap(rs.getInt(3),max);
            System.out.print(rs.getInt(3));
            System.out.println();
        }
        con.close();
        stml.close();
    }
    static void menu()
    {
        boolean condition=true;
        while(condition)
        {
            System.out.print("\033[H\033[2J");  
            System.out.flush();
            System.out.println();
            System.out.println("Welcome to "+org_name+" Management Page...");
            System.out.println();
            System.out.println("******** Main-Menu ********");
            System.out.println("1. Add New Hostel.");
            System.out.println("2. Remove Existing Hostel.");
            System.out.println("3. Change Password.");
            System.out.println("4. Student Details");
            System.out.println("5. View all Hostel Details.");
            System.out.println("6. Exit.");
            System.out.print("Enter your choice -:");
            try{
                int choice = Main.sc.nextInt();
                Main.sc.nextLine();
                condition=choose(choice);
            }
            catch(Exception e)
            {
                System.out.println(e);
                System.out.println("Try Again... Press Enter to continue...");
                Main.sc.nextLine();
                Main.sc.nextLine();
            }
        }
    }
    static String getUser() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_management", "harshit", "Harsh@9977");
        Statement stml=con.createStatement();
        ResultSet rs=stml.executeQuery("select Hostel_Code from hostel_details;");
        System.out.println("List of all Hostel ID's -:");
        while(rs.next())
        {
            System.out.println(rs.getString(1));
        }
        System.out.println();
        System.out.println("Enter User-Name of your choice -:");
        String username=Main.sc.nextLine();
        rs=stml.executeQuery("select Hostel_Code from hostel_details where Hostel_code='"+username+"';");
        if(!rs.next())
            return "NF";
        else 
            return username;
    }
    static boolean choose(int choice)
    {
        System.out.println();
        Admin obj=new Admin();
        try{
            switch(choice)
            {
                case 1:
                    obj.new_hostel();
                    break;
                case 2:
                    obj.remove_hostel();
                    break;
                case 3:
                    obj.change_password("yourspace");
                    break;
                case 4:
                    String username=getUser();
                    if(username.equals("NF")) System.out.println("Invalid User_Name. Try Again...");
                    else menu(username,false);
                    break;
                case 5:
                    obj.view_hostels();
                    break;
                case 6:
                    return false;
                default:
                    System.out.println("Invalid Choice. Try Again...");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("Press Enter to continue...");
        Main.sc.nextLine();
        return true;
    }
}
