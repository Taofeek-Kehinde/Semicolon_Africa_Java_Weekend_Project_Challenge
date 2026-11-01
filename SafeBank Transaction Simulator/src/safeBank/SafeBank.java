package safeBank;

import exceptions.BankException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.WithdrawalLimitExceededException;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class SafeBank {

    private String name;
    private double balance = 0.0;
    private String password;

    private List<String> transactions = new ArrayList<>();

    public void validateName(String name) {

        if (name == null || name.isBlank()) {
            throw new BankException("Account holder name cannot be blank.");
        }
    }

    public void validateOpeningBalance(double balance) {

        if (!Double.isFinite(balance) || balance < 0) {
            throw new BankException("Opening balance cannot be negative.");
        }
    }

    public void deposit(double amount) {

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero."
            );
        }

        balance += amount;
        transactions.add("Deposited: ₦" + amount);
    }

    public void withdraw(double amount) {

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero."
            );
        }

        if (amount > 100000) {
            throw new WithdrawalLimitExceededException(
                    "Maximum single withdrawal is ₦100,000."
            );
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Insufficient balance!"
            );
        }

        balance -= amount;
        transactions.add("Withdrawn: ₦" + amount);
    }
}