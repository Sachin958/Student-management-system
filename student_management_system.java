import java.sql.*;
import java.util.Scanner;

public class student_management_system {
    public static void main(String[] args) throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());;
        }
        String url = "jdbc:mysql://localhost:3306/student_management_system";
        String username = "root";
        String password = "1230.";
        Scanner sc = new Scanner(System.in);
        System.out.println("-- WELCOME TO STUDENT MANAGEMENT SYSTEM --");
        System.out.println();
        try{
            Connection connection = DriverManager.getConnection(url , username , password);

            while(true){
                System.out.println(" 1. Add student");
                System.out.println(" 2. Delete student");
                System.out.println(" 3. Update student");
                System.out.println(" 4. View all student");
                System.out.println(" 5. Search student");
                System.out.println(" 0. Exit");
                System.out.println();

                System.out.print("Enter your choice : ");
                int choice = sc.nextInt();
                switch (choice){
                    case 1 :
                        add_student(connection , sc);
                        break;
                    case 2 :
                        delete_student(connection , sc);
                        break;
                    case 3 :
                        update_student(connection , sc);
                        break;
                    case 4 :
                        view_all_student(connection , sc);
                        break;
                    case 5 :
                        search_student(connection , sc);
                        break;
                    case 0 :
                        exit();
                        break;
                }



            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }


    private static void add_student(Connection connection , Scanner sc) throws SQLException{
        String Query = "Insert into student(student_name , age , course , contact_number)values(?,?,?,?);";
        System.out.print("Enter student Name : ");
        String name = sc.next();

        System.out.print("Enter student Age : ");
        int age = sc.nextInt();

        System.out.print("Enter student course : ");
        String course = sc.next();

        System.out.print("Enter student contact number : ");
        String number = sc.next();

        try {
            PreparedStatement ps = connection.prepareStatement(Query);
            ps.setString(1 , name);
            ps.setInt(2 , age);
            ps.setString(3 , course);
            ps.setString(4 , number);

            int row_effected = ps.executeUpdate();
            if(row_effected > 0 ){
                System.out.println("Student data is Inserted!!!");
            }else{
                System.out.println("Failed! Try Again...");
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

    private static void delete_student(Connection connection , Scanner sc) throws SQLException{
        System.out.print("Enter student id :");
        int id = sc.nextInt();

        System.out.print("Enter student name : ");
        String name = sc.next();

        String query = "delete from student where student_id = ? and student_name = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1 , id);
            ps.setString(2 , name);

            int row_effected = ps.executeUpdate();
            if(row_effected > 0){
                System.out.println("Student details has been removed from the database...");
            }else {
                System.out.println("Failed! Try again...");
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    private static void update_student(Connection connection , Scanner sc) throws SQLException, InterruptedException {
        while(true){
            System.out.println("1. update Student name");
            System.out.println("2. Update Student course");
            System.out.println("3. Update student contact number");
            System.out.println("4. exit");

            System.out.print("Enter your choice :");
            int choice = sc.nextInt();

            System.out.print("Enter student id : ");
            int id = sc.nextInt();

            switch (choice){
                case 1 :
                    System.out.print("Enter student new name : ");
                    String name = sc.next();
                    String query = "update student set student_name = ? where student_id = ?;";

                    try{
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1 , name);
                        ps.setInt(2 , id);

                        int row_effected = ps.executeUpdate();
                        if(row_effected > 0){
                            System.out.println("Student name is updated...");
                        }else{
                            System.out.println("Failed! Try Again..");
                        }

                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Enter student new course : ");
                    String course = sc.next();
                    String query2 = "update student set course = ?  where student_id = ?;";

                    try{
                        PreparedStatement ps = connection.prepareStatement(query2);
                        ps.setString(1 , course);
                        ps.setInt(2 , id);

                        int row_effected = ps.executeUpdate();
                        if(row_effected > 0){
                            System.out.println();
                            System.out.println("Student name is updated...");
                        }else{
                            System.out.println("Failed! Try Again..");
                        }

                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3 :
                    System.out.print("Enter student new contact number : ");
                    String number = sc.next();
                    String query3 = "update student set contact_number = ?  where student_id = ?;";

                    try{
                        PreparedStatement ps = connection.prepareStatement(query3);
                        ps.setString(1 , number);
                        ps.setInt(2 , id);

                        int row_effected = ps.executeUpdate();
                        if(row_effected > 0){
                            System.out.println("Student name is updated...");
                        }else{
                            System.out.println("Failed! Try Again..");
                        }

                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    exit();
            }

        }
    }
    private static void view_all_student(Connection connection , Scanner sc) throws SQLException{
        String query = "select student_id , student_name , age, course , contact_number from student;";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Student details : ");
            System.out.println("+---------------+--------------+--------------+-------------+-----------------+");
            System.out.println("|  Student_id   | student_name |      age     |    course   | contact_number  |");
            System.out.println("+---------------+--------------+--------------+-------------+-----------------+");

            while(rs.next()){
                int id = rs.getInt("student_id");
                String name = rs.getString("student_name");
                int age = rs.getInt("age");
                String course = rs.getString("course");
                String number = rs.getString("contact_number");

                System.out.printf("| %-13d | %-12s | %-12d | %-11s | %-13s   |\n",
                        id, name, age, course, number);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    private static void search_student(Connection connection , Scanner sc) throws SQLException{
        System.out.print("Enter student_id : ");
        int id = sc.nextInt();

        System.out.print("Enter student_name : ");
        String name = sc.next();

        String query = "select student_id , student_name , age,course,contact_number from student where student_id = ? and student_name = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1 , id);
            ps.setString(2 , name);
            ResultSet rs = ps.executeQuery();

            System.out.println("Student details : ");
            System.out.println("+---------------+--------------+--------------+-------------+-----------------+");
            System.out.println("|  Student_id   | student_name |      age     |    course   | contact_number  |");
            System.out.println("+---------------+--------------+--------------+-------------+-----------------+");

            while(rs.next()){
                int sid = rs.getInt("student_id");
                String sname = rs.getString("student_name");
                int age = rs.getInt("age");
                String course = rs.getString("course");
                String contact_number = rs.getString("contact_number");

//                System.out.println();
                System.out.printf("| %-13d | %-12s | %-12d | %-11s | %-13s   |\n",
                        sid, sname, age, course, contact_number);


//                System.out.println("Student id : " + sid);
//                System.out.println("Student name : " + sname);
//                System.out.println("Student age : " + age);
//                System.out.println("Student's course : " + course);
//                System.out.println("Student's number : " + contact_number);

                System.out.println();

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    public static void exit() throws InterruptedException {
        System.out.print("Exiting system");
        for(int i = 0 ; i < 5; i++){
            System.out.print(".");
            Thread.sleep(500);

        }
        System.out.println();
        System.out.println("Thank you for using Booking system..");
    }

}
