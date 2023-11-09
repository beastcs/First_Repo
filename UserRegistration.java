import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserRegistration {
    public static void register(Connection con) {
        PreparedStatement checkUsernameStatement = null;
        PreparedStatement userStatement = null;
        ResultSet resultSet = null;

        try {
            // Input from the user
            Scanner scanner = new Scanner(System.in);

            // Input values
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("EMPID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            // Check the username uniqueness
            String checkUsernameQuery = "SELECT username FROM user_tab WHERE username = ?";
            checkUsernameStatement = con.prepareStatement(checkUsernameQuery);
            checkUsernameStatement.setString(1, username);
            resultSet = checkUsernameStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Username already exists. Please choose a different username.");
            } else {
                // If the username doesn't exist, continue with registration
                System.out.print("Department ID: ");
                int departmentId = Integer.parseInt(scanner.nextLine());
                System.out.print("Role ID: ");
                int roleId = Integer.parseInt(scanner.nextLine());

                // Insert into user_tab table
                String insertUserQuery = "INSERT INTO user_tab (username, name, password, employee_id, email, department_id, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
                userStatement = con.prepareStatement(insertUserQuery);
                userStatement.setString(1, username);
                userStatement.setString(2, name);
                userStatement.setString(3, password);
                userStatement.setString(4, employeeId);
                userStatement.setString(5, email);
                userStatement.setInt(6, departmentId);
                userStatement.setInt(7, roleId);

                userStatement.executeUpdate();

                // Commit the transaction
                con.commit();

                System.out.println("User registered successfully.");

                // Close resources
                if (userStatement != null)
                    userStatement.close();
                if (checkUsernameStatement != null)
                    checkUsernameStatement.close();
                if (resultSet != null)
                    resultSet.close();
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback(); // Rollback the transaction in case of an error
                } catch (SQLException ex) {
                    System.err.println("Rollback failed: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.err.println("Connection close failed: " + e.getMessage());
            }
        }
    }
}