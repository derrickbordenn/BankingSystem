package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	public static final double APR = 2.4;
	public static final int ID = 12345678;
	public static final double DEPOSIT_AMOUNT = 200;
	public static final double WITHDRAWAL_AMOUNT = 50;
	Account savings;

	@BeforeEach
	public void setUp() {
		savings = new SavingsAccount(ID, APR);
	}

	@Test
	public void account_created_with_apr() {
		double actual = savings.getApr();

		assertEquals(APR, actual);
	}

	@Test
	public void depositing_money_adds_deposit_amount_to_account_balance() {
		savings.deposit_money(DEPOSIT_AMOUNT);
		double actual = savings.getBalance();

		assertEquals(DEPOSIT_AMOUNT, actual);
	}

	@Test
	public void depositing_twice_into_same_account_twice_adds_correct_value_to_balance() {
		savings.deposit_money(DEPOSIT_AMOUNT);
		savings.deposit_money(DEPOSIT_AMOUNT);
		double actual = savings.getBalance();

		assertEquals(2 * DEPOSIT_AMOUNT, actual);
	}

	@Test
	public void withdrawing_money_subtracts_amount_from_account_balance() {
		savings.deposit_money(DEPOSIT_AMOUNT);
		savings.withdraw_money(WITHDRAWAL_AMOUNT);
		double actual = savings.getBalance();

		assertEquals(DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT, actual);
	}

	@Test
	public void withdrawing_money_twice_from_same_account_subtracts_withdrawal_amount_twice_from_balance() {
		savings.deposit_money(DEPOSIT_AMOUNT);
		savings.withdraw_money(WITHDRAWAL_AMOUNT);
		savings.withdraw_money(WITHDRAWAL_AMOUNT);

		double actual = savings.getBalance();

		assertEquals(DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT * 2, actual);
	}

	@Test
	public void withdrawing_larger_amount_than_account_balance_does_not_result_in_negative_balance() {
		savings.deposit_money(DEPOSIT_AMOUNT);
		savings.withdraw_money(DEPOSIT_AMOUNT + 1);
		double actual = savings.getBalance();

		assertEquals(0, actual);
	}
}
