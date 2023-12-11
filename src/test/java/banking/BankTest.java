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

	@Test
	void pass_time_adds_time() {
		bank.passTime(10);
		int actual = bank.getMonths();
		assertEquals(10, actual);
	}

	@Test
	void pass_time_removes_accounts_with_zero_balance() {
		bank.addAccount(checking);
		bank.withdrawById(ID + 1, DEPOSIT_AMOUNT);
		bank.passTime(1);

		Account actual = bank.getAccountById(ID + 1);

		assertEquals(null, actual);
	}

	@Test
	void if_balance_under_100_deduct_25() {
		bank.addAccount(checking);
		bank.depositById(ID + 1, DEPOSIT_AMOUNT - 1);
		bank.passTime(1);

		double actual = bank.getAccountById(ID + 1).getBalance();

		assertEquals(74, actual);
	}

	@Test
	void pass_time_accrus_interest() {
		bank.addAccount(savings);
		bank.depositById(ID, DEPOSIT_AMOUNT);
		bank.passTime(4);

		double actual = bank.getAccountById(ID).getBalance();

		assertEquals(100.8024032016, actual);
	}

	@Test
	void pass_time_accrues_interest_for_CD() {
		bank.addAccount(new CDAccount(12345687, 2.1, 2000));
		bank.passTime(1);

		double actual = bank.getAccountById(12345687).getBalance();
		assertEquals(2014.0, actual);
	}

	@Test
	void withdrawing_more_than_balance_brings_balance_to_zero() {
		bank.addAccount(new SavingsAccount(ID, APR));
		bank.depositById(ID, DEPOSIT_AMOUNT);
		bank.withdrawById(12345678, 101);

		double actual = bank.getAccountById(ID).getBalance();
		assertEquals(0, actual);
	}

	@Test
	void withdrawing_more_than_balance_brings_from_CD_balance_to_zero() {
		bank.addAccount(new CDAccount(ID, APR, 1000));
		bank.passTime(12);
		bank.withdrawById(ID, 5000);

		double actual = bank.getAccountById(ID).getBalance();
		assertEquals(0, actual);
	}

	@Test
	void transfer_between_two_checking_accounts_adds_amount_to_balance_of_receiving_account() {
		bank.addAccount(checking);
		bank.addAccount(new CheckingAccount(ID, APR));
		bank.depositById(ID + 1, 300);
		bank.transfer(ID + 1, ID, 200);

		double actual = bank.getAccountById(ID).getBalance();
		assertEquals(200, actual);
	}

	@Test
	void transfer_between_two_checking_accounts_subtracts_amount_from_balance_of_from_account() {
		bank.addAccount(checking);
		bank.addAccount(new CheckingAccount(ID, APR));
		bank.depositById(ID + 1, 300);
		bank.transfer(ID + 1, ID, 200);

		double actual = bank.getAccountById(ID + 1).getBalance();
		assertEquals(100, actual);
	}

	@Test
	void transfer_between_two_savings_accounts_adds_amount_to_balance_of_receiving_account() {
		bank.addAccount(savings);
		bank.addAccount(new SavingsAccount(ID + 1, APR));
		bank.depositById(ID, 300);
		bank.transfer(ID, ID + 1, 200);

		double actual = bank.getAccountById(ID + 1).getBalance();
		assertEquals(200, actual);
	}

	@Test
	void transfer_between_two_savings_accounts_subtracts_amount_from_balance_of_from_account() {
		bank.addAccount(savings);
		bank.addAccount(new SavingsAccount(ID + 1, APR));
		bank.depositById(ID, 300);
		bank.transfer(ID, ID + 1, 200);

		double actual = bank.getAccountById(ID).getBalance();
		assertEquals(100, actual);
	}

	@Test
	void transfer_from_checking_to_savings_accounts_adds_amount_to_balance_of_savings_account() {
		bank.addAccount(checking);
		bank.addAccount(savings);
		bank.depositById(ID + 1, 300);
		bank.transfer(ID + 1, ID, 200);

		double actual = bank.getAccountById(ID).getBalance();
		assertEquals(200, actual);
	}

	@Test
	void transfer_from_checking_to_savings_accounts_withdraws_amount_from_balance_of_checking_account() {
		bank.addAccount(checking);
		bank.addAccount(savings);
		bank.depositById(ID + 1, 300);
		bank.transfer(ID + 1, ID, 200);

		double actual = bank.getAccountById(ID + 1).getBalance();
		assertEquals(100, actual);
	}

	@Test
	void transfer_from_savings_to_checking_accounts_adds_amount_to_balance_of_checking_account() {
		bank.addAccount(savings);
		bank.addAccount(checking);
		bank.depositById(ID, 300);
		bank.transfer(ID, ID + 1, 200);

		double actual = bank.getAccountById(ID + 1).getBalance();
		assertEquals(200, actual);
	}

	@Test
	void transfer_from_savings_to_checking_accounts_withdraws_amount_from_balance_of_savings_account() {
		bank.addAccount(savings);
		bank.addAccount(checking);
		bank.depositById(ID, 300);
		bank.transfer(ID, ID + 1, 200);

		double actual = bank.getAccountById(ID).getBalance();
		assertEquals(100, actual);
	}

	@Test
	void transferring_amount_larger_than_from_checking_account_balance_adds_from_account_balance_to_receiving_account() {
		bank.addAccount(checking);
		bank.addAccount(new CheckingAccount(ID, APR));
		bank.depositById(ID + 1, 300);
		bank.transfer(ID + 1, ID, 400);

		double actual = bank.getAccountById(ID).getBalance();
		assertEquals(300, actual);
	}

	@Test
	void transferring_amount_larger_than_from_checking_account_balance_brings_from_account_balance_to_zero() {
		bank.addAccount(checking);
		bank.addAccount(new CheckingAccount(ID, APR));
		bank.depositById(ID + 1, 300);
		bank.transfer(ID + 1, ID, 400);

		double actual = bank.getAccountById(ID + 1).getBalance();
		assertEquals(0, actual);
	}

	@Test
	void transferring_amount_larger_than_from_savings_account_balance_adds_from_account_balance_to_receiving_account() {
		bank.addAccount(savings);
		bank.addAccount(new SavingsAccount(ID + 1, APR));
		bank.depositById(ID, 300);
		bank.transfer(ID, ID + 1, 400);

		double actual = bank.getAccountById(ID + 1).getBalance();
		assertEquals(300, actual);
	}

	@Test
	void transferring_amount_larger_than_from_savings_account_balance_brings_from_account_balance_to_zero() {
		bank.addAccount(savings);
		bank.addAccount(new SavingsAccount(ID + 1, APR));
		bank.depositById(ID, 300);
		bank.transfer(ID, ID + 1, 400);

		double actual = bank.getAccountById(ID).getBalance();
		assertEquals(0, actual);
	}
}
