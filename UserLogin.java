import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin {
    public static void login(Connection dbConnection) {
        System.out.println("User Login");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticateUser(dbConnection, username, password)) {
            System.out.println("Login successful. Welcome, " + username + "!");
            //Direct to homepage
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static boolean authenticateUser(Connection dbConnection, String username, String password) {
        String query = "SELECT COUNT(*) FROM user_tab WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}



