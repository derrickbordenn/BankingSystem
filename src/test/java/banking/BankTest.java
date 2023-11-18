package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final int ID = 12345678;
	public static final double APR = 2.4;
	public static final double DEPOSIT_AMOUNT = 100;
	public static final double WITHDRAWAL_AMOUNT = 50;

	Bank bank;
	Account savings;
	Account checking;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		savings = new SavingsAccount(ID, APR);
		checking = new CheckingAccount(ID + 1, APR + 0.1);
	}

	@Test
	void bank_is_empty_when_created() {
		int actual = bank.getAccountCount();
		assertEquals(0, actual);
	}

	@Test
	void bank_has_one_account_after_adding_one_account() {
		bank.addAccount(savings);
		int actual = bank.getAccountCount();

		assertEquals(1, actual);
	}

	@Test
	void bank_has_two_accounts_after_adding_two_accounts() {
		bank.addAccount(savings);
		bank.addAccount(checking);
		int actual = bank.getAccountCount();

		assertEquals(2, actual);
	}

	@Test
	void retrieving_account_from_bank_returns_the_correct_account() {
		bank.addAccount(savings);
		bank.addAccount(checking);
		Account actual = bank.getAccountById(ID);

		assertEquals(savings, actual);
	}

	@Test
	void depositing_money_by_ID_through_bank_deposits_correct_amount_of_money() {
		bank.addAccount(savings);
		bank.addAccount(checking);
		bank.depositById(ID, DEPOSIT_AMOUNT);
		double actual = bank.getAccountById(ID).getBalance();

		assertEquals(DEPOSIT_AMOUNT, actual);
	}

	@Test
	void withdrawing_money_by_ID_through_bank_subtracts_correct_amount_of_money() {
		bank.addAccount(savings);
		bank.addAccount(checking);
		savings.deposit_money(DEPOSIT_AMOUNT);
		bank.withdrawById(ID, WITHDRAWAL_AMOUNT);
		double actual = bank.getAccountById(ID).getBalance();

		assertEquals(DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT, actual);

	}

	@Test
	void depositing_money_twice_by_ID_through_bank_deposits_correct_amount_of_money() {
		bank.addAccount(savings);
		bank.addAccount(checking);
		bank.depositById(ID, DEPOSIT_AMOUNT);
		bank.depositById(ID, DEPOSIT_AMOUNT);

		double actual = bank.getAccountById(ID).getBalance();

		assertEquals(DEPOSIT_AMOUNT * 2, actual);
	}

	@Test
	void withdrawing_money_twice_by_ID_through_bank_subtracts_correct_amount_of_money() {
		bank.addAccount(savings);
		bank.addAccount(checking);
		savings.deposit_money(DEPOSIT_AMOUNT * 2);
		bank.withdrawById(ID, WITHDRAWAL_AMOUNT);
		bank.withdrawById(ID, WITHDRAWAL_AMOUNT);
		double actual = bank.getAccountById(ID).getBalance();

		assertEquals(DEPOSIT_AMOUNT * 2 - WITHDRAWAL_AMOUNT * 2, actual);

	}
}
