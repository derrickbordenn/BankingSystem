import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsAccountTest {

	SavingsAccount savings;

	@BeforeEach
	void setUp() {
		savings = new SavingsAccount("12345679", 2.4);
	}

	@Test
	void savings_account_is_created_with_empty_balance() {

		assertEquals(0, savings.getBalance());
	}

	@Test
	void savings_account_can_be_created_with_apr() {

		assertEquals(2.4, savings.getApr());
	}

	@Test
	void depositing_money_adds_deposit_amount_to_account_balance() {
		savings.deposit_money(100);

		assertEquals(100, savings.getBalance());
	}

	@Test
	void withdrawing_money_removes_withdrawal_amount_from_account_balance() {
		savings.deposit_money(100);
		savings.withdraw_money(50);

		assertEquals(50, savings.getBalance());
	}

	@Test
	void withdrawing_money_cannot_bring_balance_below_zero() {
		savings.withdraw_money(100);

		assertEquals(0, savings.getBalance());
	}

	@Test
	void depositing_money_twice_into_the_same_account_adds_both_deposit_amounts_to_account_balance() {
		savings.deposit_money(100);
		savings.deposit_money(100);

		assertEquals(200, savings.getBalance());
	}

	@Test
	void withdrawing_money_twice_from_the_same_account_withdraws_both_withdrawal_amounts_from_account() {
		savings.deposit_money(100);
		savings.withdraw_money(25);
		savings.withdraw_money(25);

		assertEquals(50, savings.getBalance());
	}
}
