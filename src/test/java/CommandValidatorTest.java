import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;

	@BeforeEach
	public void setUP() {
		commandValidator = new CommandValidator();
	}

	@Test
	void create_valid_savings() {
		boolean actual = commandValidator.validate("create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	void create_valid_checking() {
		boolean actual = commandValidator.validate("create checking 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	void create_valid_cd() {
		boolean actual = commandValidator.validate("create cd 12345678 0.6 2000");
		assertTrue(actual);
	}

	@Test
	void create_account_capital_letters() {
		boolean actual = commandValidator.validate("create cHeCkIng 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	void create_with_min_apr() {
		boolean actual = commandValidator.validate("create savings 12345678 0");
		assertTrue(actual);
	}

	@Test
	void create_with_max_apr() {
		boolean actual = commandValidator.validate("create savings 12345678 10");
		assertTrue(actual);
	}

	@Test
	void create_with_min_initial_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 0.6 1000");
		assertTrue(actual);
	}

	@Test
	void create_with_max_initial_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 0.6 10000");
		assertTrue(actual);
	}

	@Test
	void create_with_min_id() {
		boolean actual = commandValidator.validate("create savings 00000000 0.2");
		assertTrue(actual);
	}

	@Test
	void create_with_max_id() {
		boolean actual = commandValidator.validate("create savings 99999999 1.9");
		assertTrue(actual);
	}

	@Test
	void create_cd_with_min_id() {
		boolean actual = commandValidator.validate("create cd 00000000 2.4 1200");
		assertTrue(actual);
	}

	@Test
	void create_cd_with_max_id() {
		boolean actual = commandValidator.validate("create cd 99999999 2.4 1200");
		assertTrue(actual);
	}

	@Test
	void create_cd_with_min_apr() {
		boolean actual = commandValidator.validate("create cd 99999999 0 1200");
		assertTrue(actual);
	}

	@Test
	void create_cd_with_max_apr() {
		boolean actual = commandValidator.validate("create cd 99999999 10 1200");
		assertTrue(actual);
	}

	@Test
	void create_cd_capital_letters() {
		boolean actual = commandValidator.validate("create cD 12345678 0.6 2500");
		assertTrue(actual);
	}
}