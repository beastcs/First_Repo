import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PasswordChange {
    public static void changePassword(Connection dbConnection) {
        System.out.println("Password Change");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        if (!verifyUser(dbConnection, username)) {
            System.out.println("No such user is present.");
            return;
        }

        String email = getUserEmail(dbConnection, username);

        System.out.print("Enter the email code sent to your email: ");
        String emailCode = scanner.nextLine();

        if (verifyEmailCode(email, emailCode)) {
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();

            if (updatePassword(dbConnection, username, newPassword)) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Failed to update the password.");
            }
        } else {
            System.out.println("Invalid email code. Please try again.");
        }
    }

    private static boolean verifyUser(Connection dbConnection, String username) {
        String query = "SELECT COUNT(*) FROM user_tab WHERE username = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
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

    private static String getUserEmail(Connection dbConnection, String username) {
        String query = "SELECT email FROM user_tab WHERE username = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean verifyEmailCode(String email, String emailCode) {
        // Implement code to verify if the provided email code matches the one sent to the user's email
        return emailCode.equals("123456"); // Replace with actual code
    }

    private static boolean updatePassword(Connection dbConnection, String username, String newPassword) {
        String query = "UPDATE user_tab SET password = ? WHERE username = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}