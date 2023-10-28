import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDAccountTest {

	CDAccount CD;

	@BeforeEach
	void setUp() {
		CD = new CDAccount("12345679", 2.4, 1000);
	}

	@Test
	void certificate_of_deposit_account_is_created_with_supplied_balance() {

		assertEquals(1000, CD.getBalance());
	}

	@Test
	void certificate_of_deposit_account_can_be_created_with_apr() {

		assertEquals(2.4, CD.getApr());
	}

	@Test
	void depositing_money_adds_deposit_amount_to_account_balance() {
		CD.deposit_money(100);

		assertEquals(1100, CD.getBalance());
	}

	@Test
	void withdrawing_money_removes_withdrawal_amount_from_account_balance() {
		CD.withdraw_money(500);

		assertEquals(500, CD.getBalance());
	}

	@Test
	void withdrawing_money_cannot_bring_balance_below_zero() {
		CD.withdraw_money(1001);

		assertEquals(0, CD.getBalance());
	}

	@Test
	void depositing_money_twice_into_the_same_account_adds_both_deposit_amounts_to_account_balance() {
		CD.deposit_money(100);
		CD.deposit_money(100);

		assertEquals(1200, CD.getBalance());
	}

	@Test
	void withdrawing_money_twice_from_the_same_account_withdraws_both_withdrawal_amounts_from_account() {
		CD.withdraw_money(250);
		CD.withdraw_money(250);

		assertEquals(500, CD.getBalance());
	}
}
