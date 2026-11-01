
import exceptions.BankException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.WithdrawalLimitExceededException;
import safeBank.SafeBank;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        SafeBank safeBank = new SafeBank();

        System.out.println("================================");
        System.out.println("       WELCOME TO SAFE BANK");
        System.out.println("================================");

        try {

            System.out.print("Enter Account Holder Name: ");
            String accountName = input.nextLine();

            safeBank.validateName(accountName);
            safeBank.setName(accountName);

            System.out.print("Enter Opening Balance: ₦");
            double openingBalance = input.nextDouble();

            safeBank.validateOpeningBalance(openingBalance);
            safeBank.setBalance(openingBalance);

        } catch (InputMismatchException e) {

            System.out.println("Invalid input! Please enter a valid numeric amount.");
            return;

        } catch (BankException e) {

            System.out.println("Account Setup Error: " + e.getMessage());
            return;
        }

        String password = "";

        try {

            System.out.print("Create your 4-digit PIN: ");
            password = input.next();

            if (!password.matches("\\d{4}")) {
                throw new BankException(
                        "PIN must contain exactly 4 digits."
                );
            }

        } catch (BankException e) {

            System.out.println("Error: " + e.getMessage());
            return;
        }

        int confirmAttempts = 3;

        while (true) {

            try {

                System.out.print("Confirm your PIN: ");
                String confirmPassword = input.next();

                if (!password.equals(confirmPassword)) {
                    throw new BankException("PINs do not match!");
                }

                System.out.println("PIN confirmed successfully!");
                break;

            } catch (BankException e) {

                confirmAttempts--;

                System.out.println("Error: " + e.getMessage());

                if (confirmAttempts > 0) {
                    System.out.println(confirmAttempts + " attempts remaining.");
                } else {
                    System.out.println("Maximum attempts reached!");
                    System.out.println("Account setup cancelled.");
                    return;
                }
            }
        }

        safeBank.setPassword(password);

        System.out.println("PIN created successfully!");
        System.out.println("Account created successfully!");

        int attempts = 3;

        while (true) {

            try {

                System.out.print("Enter your PIN to access SafeBank: ");
                String enteredPassword = input.next();

                if (!safeBank.getPassword().equals(enteredPassword)) {
                    throw new BankException("Incorrect PIN!");
                }

                System.out.println("Login successful!");
                break;

            } catch (BankException e) {

                attempts--;

                System.out.println(e.getMessage());

                if (attempts > 0) {
                    System.out.println("You have " + attempts + " attempts remaining.");
                } else {
                    System.out.println("You are a thief!");
                    System.out.println("Access denied. Application terminated.");
                    return;
                }
            }
        }

        int choice = 0;

        while (choice != 5) {

            System.out.println("\n========== SAFE BANK MENU ==========");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.println("==================================");

            choice = 0;

            try {

                System.out.print("Enter your choice: ");
                choice = input.nextInt();

                switch (choice) {

                    case 1:

                        System.out.print("Enter amount to deposit: ₦");
                        double depositAmount = input.nextDouble();

                        safeBank.deposit(depositAmount);

                        System.out.println("Deposit successful!");
                        System.out.println("Current Balance: ₦" + safeBank.getBalance());

                        break;

                    case 2:

                        System.out.print("Enter amount to withdraw: ₦");
                        double withdrawAmount = input.nextDouble();

                        safeBank.withdraw(withdrawAmount);

                        System.out.println("Withdrawal successful!");
                        System.out.println("Current Balance: ₦" + safeBank.getBalance());

                        break;

                    case 3:

                        System.out.println("\n===== ACCOUNT DETAILS =====");
                        System.out.println("Account Holder: " + safeBank.getName());
                        System.out.println("Current Balance: ₦" + safeBank.getBalance());

                        break;

                    case 4:

                        System.out.println("\n===== TRANSACTION HISTORY =====");

                        if (safeBank.getTransactions().isEmpty()) {

                            System.out.println("No transactions yet.");

                        } else {

                            for (String transaction : safeBank.getTransactions()) {
                                System.out.println(transaction);
                            }
                        }

                        break;

                    case 5:

                        System.out.println("Thank you for using SafeBank!");
                        System.out.println("Goodbye!");

                        break;

                    default:

                        System.out.println("Invalid choice! Choose between 1 and 5.");
                }

            } catch (InvalidAmountException e) {

                System.out.println("Invalid Amount: " + e.getMessage());

            } catch (InsufficientFundsException e) {

                System.out.println("Insufficient Funds: " + e.getMessage());

            } catch (WithdrawalLimitExceededException e) {

                System.out.println("Withdrawal Error: " + e.getMessage());

            } catch (BankException e) {

                System.out.println("Bank Error: " + e.getMessage());

            } catch (InputMismatchException e) {

                System.out.println("Invalid input! Please enter a valid number.");

                input.nextLine();
            }
        }

    }
}
