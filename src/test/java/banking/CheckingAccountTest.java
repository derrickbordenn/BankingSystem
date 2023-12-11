package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingAccountTest {
	public static final String ID = "12345678";
	public static final double APR = 0;
	CheckingAccount checking;

	@BeforeEach
	void setUp() {
		checking = new CheckingAccount(ID, APR);
	}

	@Test
	void checking_account_is_created_with_empty_balance() {
		double actual = checking.getBalance();

		assertEquals(0, actual);
	}
}
