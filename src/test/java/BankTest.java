import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	Bank bank;
	Account account1;
	Account account2;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		account1 = new SavingsAccount("12345678", 2.4);
		account2 = new CheckingAccount("12343212", 2.4);
	}

	@Test
	void bank_is_empty_when_created() {
		assertEquals(0, bank.getAccountCount());
	}

	@Test
	void bank_has_one_account_after_adding_one_account() {
		bank.addAccount(account1);
		assertEquals(1, bank.getAccountCount());
	}

	@Test
	void bank_has_two_accounts_after_adding_two_accounts() {
		bank.addAccount(account1);
		bank.addAccount(account2);

		assertEquals(2, bank.getAccountCount());
	}

	@Test
	void retrieving_account_from_bank_returns_the_correct_account() {
		bank.addAccount(account1);
		assertEquals(account1.getId(), bank.getAccountById("12345678").getId());
	}

	@Test
	void depositing_money_by_ID_through_bank_deposits_correct_amount_of_money() {
		bank.addAccount(account1);
		bank.depositById("12345678", 100);

		assertEquals(100, bank.getAccountById("12345678").getBalance());
	}

	@Test
	void withdrawing_money_by_ID_through_bank_subtracts_correct_amount_of_money() {
		bank.addAccount(account1);
		account1.deposit_money(100);
		bank.withdrawById("12345678", 50);

		assertEquals(50, bank.getAccountById("12345678").getBalance());

	}

	@Test
	void depositing_money_twice_by_ID_through_bank_deposits_correct_amount_of_money() {
		bank.addAccount(account1);
		bank.depositById("12345678", 100);
		bank.depositById("12345678", 100);

		assertEquals(200, bank.getAccountById("12345678").getBalance());
	}

	@Test
	void withdrawing_money_twice_by_ID_through_bank_subtracts_correct_amount_of_money() {
		bank.addAccount(account1);
		account1.deposit_money(100);
		bank.withdrawById("12345678", 25);
		bank.withdrawById("12345678", 25);

		assertEquals(50, bank.getAccountById("12345678").getBalance());

	}
}
