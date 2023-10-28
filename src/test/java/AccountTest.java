import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

	CDAccount cd;
	SavingsAccount savings;
	CheckingAccount checking;

	@BeforeEach
	void setUp() {
		savings = new SavingsAccount("12345678", 2.4);
		cd = new CDAccount("12345677", 2.4, 1000);
		checking = new CheckingAccount("12345768", 0);
	}

	@Test
	void checking_and_savings_accounts_are_created_with_empty_balance() {
		double savingsBalance = savings.getBalance();
		double checkingBalance = checking.getBalance();

		assertEquals(0, savingsBalance, checkingBalance);
	}

	@Test
	void certificate_of_deposit_account_is_created_with_supplied_balance() {
		double cdBalance = cd.getBalance();

		assertEquals(1000, cdBalance);
	}

	@Test
	void account_can_be_created_with_apr() {
		double cdApr = cd.getApr();
		double savingsApr = savings.getApr();
		double checkingApr = checking.getApr();
		assertEquals(2.4, cdApr);
		assertEquals(2.4, savingsApr);
		assertEquals(0, checkingApr);
	}

	@Test
	void depositing_money_adds_deposit_amount_to_account_balance() {
		cd.deposit_money(100);
		savings.deposit_money(100);
		checking.deposit_money(100);
		assertEquals(100, savings.getBalance());
		assertEquals(100, checking.getBalance());
		assertEquals(1100, cd.getBalance());
	}

	@Test
	void withdrawing_money_removes_withdrawal_amount_from_account_balance() {
		cd.withdraw_money(950);
		savings.deposit_money(100);
		savings.withdraw_money(50);
		checking.deposit_money(100);
		checking.withdraw_money(50);

		assertEquals(50, cd.getBalance());
		assertEquals(50, savings.getBalance());
		assertEquals(50, checking.getBalance());
	}

	@Test
	void withdrawing_money_cannot_bring_balance_below_zero() {
		cd.withdraw_money(1001);
		savings.deposit_money(100);
		savings.withdraw_money(101);
		checking.deposit_money(100);
		checking.withdraw_money(101);

		assertEquals(0, cd.getBalance());
		assertEquals(0, savings.getBalance());
		assertEquals(0, checking.getBalance());
	}

	@Test
	void depositing_money_twice_into_the_same_account_adds_both_deposit_amounts_to_account_balance() {
		cd.deposit_money(100);
		cd.deposit_money(100);
		savings.deposit_money(100);
		savings.deposit_money(100);
		checking.deposit_money(100);
		checking.deposit_money(100);
		assertEquals(200, savings.getBalance());
		assertEquals(200, checking.getBalance());
		assertEquals(1200, cd.getBalance());
	}

}
