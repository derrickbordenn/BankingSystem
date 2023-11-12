import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsAccountTest {
	public static final int ID = 12345678;
	public static final double APR = 2.4;
	SavingsAccount savings;

	@BeforeEach
	void setUp() {
		savings = new SavingsAccount(ID, APR);
	}

	@Test
	void savings_account_is_created_with_empty_balance() {
		double actual = savings.getBalance();

		assertEquals(0, actual);
	}
}