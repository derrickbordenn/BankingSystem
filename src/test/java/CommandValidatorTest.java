import static org.junit.jupiter.api.Assertions.assertFalse;
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

	@Test
	void account_with_no_id() {
		boolean actual = commandValidator.validate("create savings 2.4");
		assertFalse(actual);
	}

	@Test
	void cd_with_no_id() {
		boolean actual = commandValidator.validate("create cd 2.4 1200");
		assertFalse(actual);
	}

	@Test
	void account_with_no_apr() {
		boolean actual = commandValidator.validate("create savings 12345678");
		assertFalse(actual);
	}

	@Test
	void cd_with_no_apr() {
		boolean actual = commandValidator.validate("create cd 12345678 1200");
		assertFalse(actual);
	}

	@Test
	void cd_with_no_initial_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 2.4");
		assertFalse(actual);
	}

	@Test
	void nonexistent_account_type() {
		boolean actual = commandValidator.validate("create account 12345678 2.4");
		assertFalse(actual);
	}

	@Test
	void account_with_no_attributes() {
		boolean actual = commandValidator.validate("create savings");
		assertFalse(actual);
	}

	@Test
	void account_with_negative_apr() {
		boolean actual = commandValidator.validate("create savings 12345679 -2.3");
		assertFalse(actual);
	}

	@Test
	void account_with_big_apr() {
		boolean actual = commandValidator.validate("create savings 12345679 100");
		assertFalse(actual);
	}

	@Test
	void cd_with_negative_initial_balance() {
		boolean actual = commandValidator.validate("create cd 12345679 2.3 -100");
		assertFalse(actual);
	}

	@Test
	void small_cd_initial_balance() {
		boolean actual = commandValidator.validate("create cd 12345679 2.3 100");
		assertFalse(actual);
	}

	@Test
	void big_cd_initial_balance() {
		boolean actual = commandValidator.validate("create cd 12345679 2.3 100000");
		assertFalse(actual);
	}

	@Test
	void id_not_enough_digits() {
		boolean actual = commandValidator.validate("create savings 1234567 2.3");
		assertFalse(actual);
	}

	@Test
	void id_too_many_digits() {
		boolean actual = commandValidator.validate("create savings 123456789 2.3");
		assertFalse(actual);
	}

	@Test
	void non_numeric_characters_in_id() {
		boolean actual = commandValidator.validate("create savings 1234567a 2.3");
		assertFalse(actual);
	}

	@Test
	void non_numeric_character_in_initial_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 2.3 twenty");
		assertFalse(actual);
	}

	@Test
	void non_numeric_character_in_apr() {
		boolean actual = commandValidator.validate("create savings 12345678 two.2");
		assertFalse(actual);
	}

	@Test
	void invalid_float_apr() {
		boolean actual = commandValidator.validate("create savings 12345678 2.3.2");
		assertFalse(actual);
	}

	@Test
	void decimal_in_id() {
		boolean actual = commandValidator.validate("create cd 12.45678 2.3 twenty");
		assertFalse(actual);
	}

	@Test
	void create_with_no_attributes() {
		boolean actual = commandValidator.validate("create");
		assertFalse(actual);
	}

	@Test
	void deposit_integer_into_account() {
		boolean actual = commandValidator.validate("deposit 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void deposit_float_into_account() {
		boolean actual = commandValidator.validate("deposit 12345678 100.1");
		assertTrue(actual);
	}

	@Test
	void deposit_zero_into_account() {
		boolean actual = commandValidator.validate("deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	void deposit_negative_into_account() {
		boolean actual = commandValidator.validate("deposit 12345678 -1000");
		assertFalse(actual);
	}
}