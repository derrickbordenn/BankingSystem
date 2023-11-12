import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDAccountTest {

	public static final int ID = 12345678;
	public static final double APR = 2.4;
	public static final double INITIAL_BALANCE = 1000;
	CDAccount CD;

	@BeforeEach
	void setUp() {
		CD = new CDAccount(ID, APR, INITIAL_BALANCE);
	}

	@Test
	void certificate_of_deposit_account_is_created_with_supplied_balance() {
		double actual = CD.getBalance();

		assertEquals(INITIAL_BALANCE, actual);
	}
}