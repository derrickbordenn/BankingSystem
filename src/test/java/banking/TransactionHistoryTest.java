package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionHistoryTest {
	Bank bank;
	TransactionHistory transactionHistory;
	List<String> validCommands;
	List<String> invalidCommands;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		transactionHistory = new TransactionHistory(bank);
		validCommands = new ArrayList<>();
		invalidCommands = new ArrayList<>();
	}

	@Test
	void accountType_capitalized() {
		Account account = new CheckingAccount("12345678", 0);
		String status = transactionHistory.getAccountStatus(account);
		String[] parts = status.split(" ");
		String actual = parts[0];
		assertEquals("Checking", actual);
	}

	@Test
	void id_left_in_original_string_form() {
		Account account = new CheckingAccount("12345678", 0);
		String status = transactionHistory.getAccountStatus(account);
		String[] parts = status.split(" ");
		String actual = parts[1];
		assertEquals("12345678", actual);
	}

	@Test
	void balance_trunicated_to_two_decimals() {
		Account account = new CheckingAccount("12345678", 0);
		account.deposit_money(234.23432423);
		String status = transactionHistory.getAccountStatus(account);
		String[] parts = status.split(" ");
		String actual = parts[2];
		assertEquals("234.23", actual);
	}

	@Test
	void apr_trunicated_to_two_decimals() {
		Account account = new CheckingAccount("12345678", 2.012349873284910);
		account.deposit_money(234.23432423);
		String status = transactionHistory.getAccountStatus(account);
		String[] parts = status.split(" ");
		String actual = parts[3];
		assertEquals("2.01", actual);
	}

	@Test
	void id_left_in_original_string_for() {
		Account account = new CheckingAccount("12345678", 0);
		String status = transactionHistory.getAccountStatus(account);
		String[] parts = status.split(" ");
		String actual = parts[1];
		assertEquals("12345678", actual);
	}

	@Test
	void no_action_wilL_return_empty_account_status() {
		Account account = new CheckingAccount("", 0);
		String actual = transactionHistory.getAccountStatus(account);
		assertEquals("Checking  0.00 0.00", actual);
	}

	@Test
	void get_status_of_empty_savings_account() {
		Account savingsAccount = new SavingsAccount("12345768", 2.4);
		String actual = transactionHistory.getAccountStatus(savingsAccount);

		assertEquals("Savings 12345768 0.00 2.40", actual);
	}

	@Test
	void get_status_of_empty_checking_account() {
		Account checkingAccount = new CheckingAccount("12345768", 2.4);
		String actual = transactionHistory.getAccountStatus(checkingAccount);

		assertEquals("Checking 12345768 0.00 2.40", actual);
	}

	@Test
	void get_status_of_savings_account_with_money() {
		Account savingsAccount = new SavingsAccount("12345768", 2.4);
		savingsAccount.deposit_money(1000);
		String actual = transactionHistory.getAccountStatus(savingsAccount);

		assertEquals("Savings 12345768 1000.00 2.40", actual);
	}

	@Test
	void get_status_of_checking_account_with_money() {
		Account checkingAccount = new CheckingAccount("12345768", 2.4);
		checkingAccount.deposit_money(1000);
		String actual = transactionHistory.getAccountStatus(checkingAccount);

		assertEquals("Checking 12345768 1000.00 2.40", actual);
	}

	@Test
	void get_status_of_cd_account() {
		Account cdAccount = new CDAccount("12345768", 2.4, 1500);
		String actual = transactionHistory.getAccountStatus(cdAccount);

		assertEquals("Cd 12345768 1500.00 2.40", actual);
	}

	@Test
	void get_transaction_history_on_savings_using_all_commands() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.addCheckingAccount("98765432", 2.4);
		validCommands.add("Create Savings 1234578 2.4");
		bank.depositById("12345678", 100);
		validCommands.add("deposit 12345678 100");
		bank.withdrawById("12345678", 50);
		validCommands.add("withdraw 12345678 50");
		invalidCommands.add("withdraw 12345678 50");
		bank.transfer("12345678", "98765432", 10);
		validCommands.add("transfer 12345678 98765432 10");

		List<String> actual = transactionHistory.getTransactionHistory(validCommands, invalidCommands);

		assertEquals("Savings 12345678 40.00 2.40", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Withdraw 12345678 50", actual.get(2));
		assertEquals("Transfer 12345678 98765432 10", actual.get(3));
		assertEquals("Checking 98765432 10.00 2.40", actual.get(4));
		assertEquals("Transfer 12345678 98765432 10", actual.get(5));
		assertEquals("withdraw 12345678 50", actual.get(6));
	}

	@Test
	void get_transaction_history_on_checking_using_all_commands() {
		bank.addCheckingAccount("12345678", 2.4);
		bank.addSavingsAccount("98765432", 2.4);
		validCommands.add("Create Checking 1234578 2.4");
		validCommands.add("Create Savings 98765432 2.4");
		bank.depositById("12345678", 100);
		validCommands.add("deposit 12345678 100");
		bank.withdrawById("12345678", 50);
		validCommands.add("withdraw 12345678 50");
		invalidCommands.add("withdraw 12345678 50");
		bank.transfer("12345678", "98765432", 10);
		validCommands.add("transfer 12345678 98765432 10");

		List<String> actual = transactionHistory.getTransactionHistory(validCommands, invalidCommands);

		assertEquals("Checking 12345678 40.00 2.40", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Withdraw 12345678 50", actual.get(2));
		assertEquals("Transfer 12345678 98765432 10", actual.get(3));
		assertEquals("Savings 98765432 10.00 2.40", actual.get(4));
		assertEquals("Transfer 12345678 98765432 10", actual.get(5));
		assertEquals("withdraw 12345678 50", actual.get(6));
	}

	@Test
	void get_transaction_history_on_cd_using_all_commands() {

		bank.addCDAccount("12345678", 2.4, 1500);
		bank.passTime(12);
		validCommands.add("Pass 12");
		double balance = bank.getAccountById("12345678").getBalance();
		bank.withdrawById("12345678", balance);
		String formatBalance = Double.toString(balance);
		validCommands.add("withdraw 12345678 " + formatBalance);
		invalidCommands.add("deposit 12345678 1000");
		invalidCommands.add("transfer 12345678 12346464 200");
		List<String> actual = transactionHistory.getTransactionHistory(validCommands, invalidCommands);

		assertEquals("Cd 12345678 0.00 2.40", actual.get(0));
		assertEquals("Withdraw 12345678 " + formatBalance, actual.get(1));
		assertEquals("deposit 12345678 1000", actual.get(2));
		assertEquals("transfer 12345678 12346464 200", actual.get(3));

	}
}