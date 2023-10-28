import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingAccountTest {

	CheckingAccount checking;

	@BeforeEach
	void setUp() {
		checking = new CheckingAccount("12345678", 0);
	}

	@Test
	void checking_account_is_created_with_empty_balance() {
		assertEquals(0, checking.getBalance());
	}

	@Test
	void checking_account_is_created_with_apr() {
		assertEquals(0, checking.getApr());
	}

	@Test
	void depositing_money_adds_deposit_amount_to_account_balance() {
		checking.deposit_money(100);

		assertEquals(100, checking.getBalance());
	}

	@Test
	void withdrawing_money_removes_withdrawal_amount_from_account_balance() {
		checking.deposit_money(200);
		checking.withdraw_money(100);

		assertEquals(100, checking.getBalance());
	}

	@Test
	void withdrawing_money_cannot_bring_balance_below_zero() {
		checking.withdraw_money(100);

		assertEquals(0, checking.getBalance());
	}

	@Test
	void depositing_money_twice_into_the_same_account_adds_both_deposit_amounts_to_account_balance() {
		checking.deposit_money(100);
		checking.deposit_money(100);

		assertEquals(200, checking.getBalance());
	}

	@Test
	void withdrawing_money_twice_from_the_same_account_withdraws_both_withdrawal_amounts_from_account() {
		checking.deposit_money(100);
		checking.withdraw_money(25);
		checking.withdraw_money(25);

		assertEquals(50, checking.getBalance());
	}
}
