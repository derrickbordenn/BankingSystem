package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	public void setUP() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
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
	void create_duplicate_account() {
		bank.addSavingsAccount("12345678", 1.2);
		boolean actual = commandValidator.validate("create savings 12345678 1.2");
		assertFalse(actual);
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
	void case_insensitivity_create_CD() {
		boolean actual = commandValidator.validate("create cD 12345678 0.6 2500");
		assertTrue(actual);
	}

	@Test
	void case_insensitivity_create_savings() {
		boolean actual = commandValidator.validate("create savInGS 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	void case_insensitivity_create_checking() {
		boolean actual = commandValidator.validate("create cHeCkIng 12345678 0.6");
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
	void deposit_integer_into_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void deposit_integer_into_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void deposit_double_into_account() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 100.1");
		assertTrue(actual);
	}

	@Test
	void case_insensitivity_in_deposit_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("dEpoSit 12345678 100");

		assertTrue(actual);
	}

	@Test
	void case_insensitivity_in_deposit_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("dEpoSit 12345678 100");

		assertTrue(actual);
	}

	@Test
	void cd_with_zero_initial_balance() {
		boolean actual = commandValidator.validate("create CD 12345678 2.4 0");

		assertFalse(actual);
	}

	@Test
	void deposit_into_CD() {
		bank.addCDAccount("12345678", 2.4, 2500);
		boolean actual = commandValidator.validate("deposit 12345678 0");

		assertFalse(actual);
	}

	@Test
	void deposit_zero_to_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 0");

		assertTrue(actual);
	}

	@Test
	void deposit_max_to_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 2500");

		assertTrue(actual);
	}

	@Test
	void deposit_zero_to_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 0");

		assertTrue(actual);
	}

	@Test
	void deposit_max_to_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 1000");

		assertTrue(actual);
	}

	@Test
	void deposit_negative_into_account() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 -1000");
		assertFalse(actual);
	}

	@Test
	void deposit_too_much_to_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 2501");

		assertFalse(actual);
	}

	@Test
	void deposit_too_much_to_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 1001");

		assertFalse(actual);
	}

	@Test
	void non_numeric_amount_in_deposit_command() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678 12348a");

		assertFalse(actual);
	}

	@Test
	void non_numeric_id_in_deposit_command() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12r45678 100");

		assertFalse(actual);
	}

	@Test
	void cannot_deposit_into_nonexistent_account() {
		boolean actual = commandValidator.validate("deposit 12345678 100");

		assertFalse(actual);
	}

	@Test
	void cannot_deposit_without_specified_amount() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 12345678");

		assertFalse(actual);
	}

	@Test
	void cannot_deposit_without_specified_id() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("deposit 100");

		assertFalse(actual);
	}

	@Test
	void withdraw_zero_from_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 0");

		assertTrue(actual);
	}

	@Test
	void withdraw_valid_amount_from_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 500");

		assertTrue(actual);
	}

	@Test
	void withdraw_maximum_amount_from_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 1000");

		assertTrue(actual);
	}

	@Test
	void withdraw_balance_from_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 250);
		boolean actual = commandValidator.validate("withdraw 12345678 250");

		assertTrue(actual);
	}

	@Test
	void withdraw_zero_from_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 0");

		assertTrue(actual);
	}

	@Test
	void withdraw_valid_amount_from_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 200");

		assertTrue(actual);
	}

	@Test
	void withdraw_maximum_amount_from_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 400");

		assertTrue(actual);
	}

	@Test
	void withdraw_balance_from_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		bank.depositById("12345678", 200);
		boolean actual = commandValidator.validate("withdraw 12345678 200");

		assertTrue(actual);
	}

	@Test
	void withdraw_full_amount_from_CD() {
		bank.addCDAccount("12345678", 2.1, 2000);
		bank.passTime(12);
		boolean actual = commandValidator.validate("withdraw 12345678 2175.098098315957");

		assertTrue(actual);
	}

	@Test
	void case_insensitivity_in_withdraw_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdrAw 12345678 100");

		assertTrue(actual);
	}

	@Test
	void case_insensitivity_in_withdraw_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdrAw 12345678 100");

		assertTrue(actual);
	}

	@Test
	void case_insensitivity_in_withdraw_CD() {
		bank.addCDAccount("12345678", 0, 7500);
		bank.passTime(12);
		boolean actual = commandValidator.validate("withdrAw 12345678 7500");

		assertTrue(actual);
	}

	@Test
	void can_withdraw_twice_from_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		bank.depositById("12345678", 200);
		bank.withdrawById("12345678", 100);
		boolean actual = commandValidator.validate("withdraw 12345678 50");

		assertTrue(actual);
	}

	@Test
	void withdraw_negative_from_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 -200");

		assertFalse(actual);
	}

	@Test
	void withdraw_negative_from_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 -100");

		assertFalse(actual);
	}

	@Test
	void withdraw_negative_from_CD() {
		bank.addCDAccount("12345678", 2.4, 1500);
		boolean actual = commandValidator.validate("withdraw 12345678 -100");

		assertFalse(actual);
	}

	@Test
	void withdraw_amount_less_than_balance_from_CD() {
		bank.addCDAccount("12345678", 2.4, 1500);
		boolean actual = commandValidator.validate("withdraw 12345678 1000");

		assertFalse(actual);
	}

	@Test
	void non_numeric_value_in_withdraw_amount() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 102m");

		assertFalse(actual);
	}

	@Test
	void non_numeric_amount_in_withdraw_command() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678 12348a");

		assertFalse(actual);
	}

	@Test
	void non_numeric_id_in_withdraw_command() {
		bank.addCheckingAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12r45678 100");

		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_from_non_existent_id() {
		boolean actual = commandValidator.validate("withdraw 12345678 1000");

		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_without_specified_amount() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 12345678");

		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_without_specified_id() {
		bank.addSavingsAccount("12345678", 2.4);
		boolean actual = commandValidator.validate("withdraw 10000");

		assertFalse(actual);
	}

	@Test
	void pass_minimum_time() {
		boolean actual = commandValidator.validate("pass 1");

		assertTrue(actual);
	}

	@Test
	void pass_valid_time() {
		boolean actual = commandValidator.validate("pass 10");

		assertTrue(actual);
	}

	@Test
	void pass_maximum_time() {
		boolean actual = commandValidator.validate("pass 60");

		assertTrue(actual);
	}

	@Test
	void cannot_pass_0_months() {
		boolean actual = commandValidator.validate("pass 0");

		assertFalse(actual);
	}

	@Test
	void cannot_pass_negative_time() {
		boolean actual = commandValidator.validate("pass -2");

		assertFalse(actual);
	}

	@Test
	void cannot_pass_more_than_sixty_months() {
		boolean actual = commandValidator.validate("pass 61");

		assertFalse(actual);
	}

	@Test
	void cannot_withdraw_from_savings_twice_in_one_month() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 2500);
		bank.withdrawById("12345678", 100);
		boolean actual = commandValidator.validate("withdraw 12345678 100");

		assertFalse(actual);
	}

	@Test
	void can_withdraw_from_savings_after_passing_time() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 500);
		bank.withdrawById("12345678", 100);
		bank.passTime(1);
		boolean actual = commandValidator.validate("withdraw 12345678 100");

		assertTrue(actual);
	}

	@Test
	void cd_cannot_withdraw_before_passing_12_months() {
		bank.addCDAccount("12345678", 2.4, 2000);
		boolean actual = commandValidator.validate("withdraw 12345678 100");

		assertFalse(actual);
	}

	@Test
	void can_transfer_from_checking_to_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		bank.addCheckingAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 200");

		assertTrue(actual);
	}

	@Test
	void can_transfer_from_checking_to_savings() {
		bank.addCheckingAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		bank.addSavingsAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 200");

		assertTrue(actual);
	}

	@Test
	void can_transfer_from_savings_to_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		bank.addSavingsAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 200");

		assertTrue(actual);
	}

	@Test
	void can_transfer_from_savings_to_checking() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		bank.addCheckingAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 200");

		assertTrue(actual);
	}

	@Test
	void can_transfer_twice_from_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		bank.addCheckingAccount("12345679", 2.4);
		bank.transfer("12345678", "12345679", 100);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 100");

		assertTrue(actual);
	}

	@Test
	void case_insensitivity_in_transfer_command() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		bank.addCheckingAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("traNsFer 12345678 12345679 200");

		assertTrue(actual);
	}

	@Test
	void cannot_transfer_from_cd_account() {
		bank.addCDAccount("12345678", 2.4, 1500);
		bank.passTime(12);
		bank.addSavingsAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 1650.5080405742192");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_to_cd_account() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.addCDAccount("12345679", 2.4, 1500);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 200");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_more_than_maximum_deposit_to_checking() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.addCheckingAccount("12345679", 2.4);
		bank.depositById("12345678", 2500);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 1500");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_more_than_maximum_deposit_to_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.addCheckingAccount("12345679", 2.4);
		bank.depositById("12345678", 2500);
		bank.depositById("12345678", 100);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 2501");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_more_than_maximum_withdraw_from_savings() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.addSavingsAccount("12345679", 2.4);
		bank.depositById("12345678", 2500);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 1001");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_more_than_maximum_withdraw_from_checking() {
		bank.addCheckingAccount("12345678", 2.4);
		bank.addSavingsAccount("12345679", 2.4);
		bank.depositById("12345678", 500);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 450");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_from_savings_twice_in_one_month() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.addCheckingAccount("12345679", 2.4);
		bank.depositById("12345678", 1000);
		bank.transfer("12345678", "12345679", 500);

		boolean actual = commandValidator.validate("transfer 12345678 12345679 500");
		assertFalse(actual);
	}

	@Test
	void can_transfer_from_savings_again_after_pass_time() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.addCheckingAccount("12345679", 2.4);
		bank.depositById("12345678", 1000);
		bank.transfer("12345678", "12345679", 500);
		bank.passTime(1);

		boolean actual = commandValidator.validate("transfer 12345678 12345679 500");
		assertTrue(actual);
	}

	@Test
	void cannot_transfer_from_non_existent_account() {
		bank.addSavingsAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 500");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_to_non_existent_account() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 600);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 500");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_negative_amounts() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		bank.addCheckingAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 -200");

		assertFalse(actual);
	}

	@Test
	void non_numeric_character_in_transfer_amount() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		bank.addCheckingAccount("12345679", 2.4);
		boolean actual = commandValidator.validate("transfer 12345678 12345679 thirty");

		assertFalse(actual);
	}

	@Test
	void cannot_transfer_to_self() {
		bank.addSavingsAccount("12345678", 2.4);
		bank.depositById("12345678", 300);
		boolean actual = commandValidator.validate("transfer 12345678 12345678 thirty");

		assertFalse(actual);
	}
}