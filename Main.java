import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Connection dbConnection = DBConnection.getConnection(); 
        

        while (true) {
            int choice = displayMenuAndGetChoice();

            switch (choice) {
                case 1:
                    UserLogin.login(dbConnection);
                    break;
                case 2:
                    UserRegistration.register(dbConnection);
                    break;
                case 3:
                    PasswordChange.changePassword(dbConnection);
                    break;
                case 4:
                    System.out.println("Exiting the application.");
                    dbConnection.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int displayMenuAndGetChoice() {
        System.out.println("Stock to ship");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Change Password");
        System.out.println("4. Exit");

        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please enter a number.");
        }

        return choice;
    }
}