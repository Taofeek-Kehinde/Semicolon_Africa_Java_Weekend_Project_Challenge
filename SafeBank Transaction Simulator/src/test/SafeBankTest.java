
package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import safeBank.SafeBank;
import exceptions.BankException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.WithdrawalLimitExceededException;

import static org.junit.jupiter.api.Assertions.*;

public class SafeBankTest {

    SafeBank safeBank;

    @BeforeEach
    public void setUp() {
        safeBank = new SafeBank();
        safeBank.setName("Taofeek");
        safeBank.setBalance(0.0);
    }

    @Test
    public void accountBalanceIsZeroTest() {
        assertEquals(0.0, safeBank.getBalance());
    }

    @Test
    public void accountNameTest() {
        assertEquals("Taofeek", safeBank.getName());
    }

    @Test
    public void blankAccountNameTest() {
        assertThrows(BankException.class, () -> {
            safeBank.validateName("");
        });
    }

    @Test
    public void nullAccountNameTest() {
        assertThrows(BankException.class, () -> {
            safeBank.validateName(null);
        });
    }

    @Test
    public void openingBalanceTest() {
        safeBank.validateOpeningBalance(50000);

        safeBank.setBalance(50000);

        assertEquals(50000.0, safeBank.getBalance());
    }

    @Test
    public void negativeOpeningBalanceTest() {
        assertThrows(BankException.class, () -> {
            safeBank.validateOpeningBalance(-500);
        });
    }

    @Test
    public void infiniteOpeningBalanceTest() {
        assertThrows(BankException.class, () -> {
            safeBank.validateOpeningBalance(Double.POSITIVE_INFINITY);
        });
    }

    @Test
    public void nanOpeningBalanceTest() {
        assertThrows(BankException.class, () -> {
            safeBank.validateOpeningBalance(Double.NaN);
        });
    }

    @Test
    public void depositMoneyTest() throws BankException {
        safeBank.deposit(5000);

        assertEquals(5000.0, safeBank.getBalance());
    }

    @Test
    public void withdrawMoneyTest() throws BankException {
        safeBank.setBalance(10000.0);

        safeBank.withdraw(3000);

        assertEquals(7000.0, safeBank.getBalance());
    }

    @Test
    public void insufficientBalanceTest() {
        safeBank.setBalance(5000.0);

        assertThrows(InsufficientFundsException.class, () -> {
            safeBank.withdraw(7000);
        });

        assertEquals(5000.0, safeBank.getBalance());
    }

    @Test
    public void negativeDepositTest() {
        assertThrows(InvalidAmountException.class, () -> {
            safeBank.deposit(-500);
        });

        assertEquals(0.0, safeBank.getBalance());
    }

    @Test
    public void negativeWithdrawalTest() {
        assertThrows(InvalidAmountException.class, () -> {
            safeBank.withdraw(-200);
        });

        assertEquals(0.0, safeBank.getBalance());
    }

    @Test
    public void zeroDepositTest() {
        assertThrows(InvalidAmountException.class, () -> {
            safeBank.deposit(0);
        });

        assertEquals(0.0, safeBank.getBalance());
    }

    @Test
    public void zeroWithdrawalTest() {
        assertThrows(InvalidAmountException.class, () -> {
            safeBank.withdraw(0);
        });

        assertEquals(0.0, safeBank.getBalance());
    }

    @Test
    public void nanDepositTest() {
        assertThrows(InvalidAmountException.class, () -> {
            safeBank.deposit(Double.NaN);
        });
    }

    @Test
    public void infiniteDepositTest() {
        assertThrows(InvalidAmountException.class, () -> {
            safeBank.deposit(Double.POSITIVE_INFINITY);
        });
    }

    @Test
    public void infiniteWithdrawalTest() {
        assertThrows(InvalidAmountException.class, () -> {
            safeBank.withdraw(Double.POSITIVE_INFINITY);
        });
    }

    @Test
    public void withdrawalLimitExceededTest() {
        safeBank.setBalance(200000);

        assertThrows(WithdrawalLimitExceededException.class, () -> {
            safeBank.withdraw(120000);
        });

        assertEquals(200000.0, safeBank.getBalance());
    }

    @Test
    public void maximumWithdrawalLimitTest() throws BankException {
        safeBank.setBalance(200000);

        safeBank.withdraw(100000);

        assertEquals(100000.0, safeBank.getBalance());
    }

    @Test
    public void transactionHistoryTest() throws BankException {
        safeBank.deposit(5000);
        safeBank.withdraw(2000);

        assertEquals(2, safeBank.getTransactions().size());
    }

    @Test
    public void failedTransactionShouldNotEnterHistoryTest() {
        assertThrows(InvalidAmountException.class, () -> {
            safeBank.deposit(-500);
        });

        assertEquals(0, safeBank.getTransactions().size());
    }

    @Test
    public void failedWithdrawalShouldNotEnterHistoryTest() {
        safeBank.setBalance(5000);

        assertThrows(InsufficientFundsException.class, () -> {
            safeBank.withdraw(7000);
        });

        assertEquals(0, safeBank.getTransactions().size());
        assertEquals(5000.0, safeBank.getBalance());
    }

    @Test
    public void successfulTransactionAfterFailureTest() throws BankException {

        assertThrows(InvalidAmountException.class, () -> {
            safeBank.deposit(-500);
        });

        safeBank.deposit(5000);

        assertEquals(5000.0, safeBank.getBalance());
        assertEquals(1, safeBank.getTransactions().size());
    }

    @Test
    public void successfulWithdrawalAfterFailedWithdrawalTest() throws BankException {

        safeBank.setBalance(10000);

        assertThrows(InsufficientFundsException.class, () -> {
            safeBank.withdraw(15000);
        });

        safeBank.withdraw(3000);

        assertEquals(7000.0, safeBank.getBalance());
        assertEquals(1, safeBank.getTransactions().size());
    }

    @Test
    public void validPinTest() {
        safeBank.setPassword("1234");

        assertEquals("1234", safeBank.getPassword());
    }

    @Test
    public void pinMustNotBeLessThanFourDigitsTest() {
        String pin = "123";

        assertFalse(pin.matches("[0-9]{4}"));
    }

    @Test
    public void pinMustNotBeMoreThanFourDigitsTest() {
        String pin = "12345";

        assertFalse(pin.matches("[0-9]{4}"));
    }

    @Test
    public void pinMustNotContainLettersTest() {
        String pin = "12ab";

        assertFalse(pin.matches("[0-9]{4}"));
    }

    @Test
    public void pinMustNotBeEmptyTest() {
        String pin = "";
        assertFalse(pin.matches("[0-9]{4}"));
    }

}
