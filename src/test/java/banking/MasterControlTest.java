package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MasterControlTest {
	private MasterControl masterControl;
	private List<String> input;

	@BeforeEach
	void setup() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStore(),
				new TransactionHistory(bank));
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void invalid_create_command_with_typo() {
		input.add("cret checking 12345678 0.01");
		assertSingleCommand("cret checking 12345678 0.01", masterControl.start(input));
	}

	@Test
	void invalid_deposit_command_with_typo() {
		input.add("depositt 12345678 1000");
		assertSingleCommand("depositt 12345678 1000", masterControl.start(input));
	}

	@Test
	void invalid_withdraw_command_with_typo() {
		input.add("withdrew 12345678 1000");
		assertSingleCommand("withdrew 12345678 1000", masterControl.start(input));
	}

	@Test
	void invalid_transfer_command_with_typo() {
		input.add("tranfar 12345678 12345677 1000");
		assertSingleCommand("tranfar 12345678 12345677 1000", masterControl.start(input));
	}

	@Test
	void invalid_pass_command_with_typo() {
		input.add("pas 12");
		assertSingleCommand("pas 12", masterControl.start(input));
	}

	@Test
	void two_invalid_commands_with_typos() {
		input.add("creat checking 12345678 0.01");
		input.add("withdrew 12345678 1000");
		List<String> result = masterControl.start(input);
		assertEquals(2, result.size());
		assertEquals("creat checking 12345678 0.01", result.get(0));
		assertEquals("withdrew 12345678 1000", result.get(1));
	}

	@Test
	void cannot_create_an_account_with_the_same_ID() {
		input.add("create checking 12345678 0.01");
		input.add("create checking 12345678 0.01");
		List<String> result = masterControl.start(input);
		assertEquals(2, result.size());
		assertEquals("Checking 12345678 0.00 0.01", result.get(0));
		assertEquals("create checking 12345678 0.01", result.get(1));
	}

	@Test
	void cannot_create_checking_account_with_typo() {
		input.add("create chcking 12345678 0.01");
		assertSingleCommand("create chcking 12345678 0.01", masterControl.start(input));
	}

	@Test
	void cannot_create_savings_account_with_typo() {
		input.add("create saving 12345678 0.01");
		assertSingleCommand("create saving 12345678 0.01", masterControl.start(input));
	}

	@Test
	void cannot_create_cd_account_without_starting_balance() {
		input.add("create cd 12345678 0.01");
		assertSingleCommand("create cd 12345678 0.01", masterControl.start(input));
	}

	@Test
	void cannot_deposit_into_account_that_does_not_exist() {
		input.add("create checking 12345676 0.01");
		input.add("deposit 12345678 100");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Checking 12345676 0.00 0.01", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
	}

	@Test
	void cannot_deposit_into_a_CD_account() {
		input.add("create cd 12345678 0.01 5000");
		input.add("deposit 12345678 100");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Cd 12345678 5000.00 0.01", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
	}

	@Test
	void cannot_depost_more_than_a_thousand_into_checking_accounts() {
		input.add("create checking 12345678 0.6");
		input.add("Deposit 12345678 1000" + "1");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000" + "1", actual.get(1));
	}

	@Test
	void cannot_deposit_more_than_maximum_amount_to_savings() {
		input.add("create savings 12345678 3");
		input.add("Deposit 12345678 1000" + "10");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 0.00 3.00", actual.get(0));
		assertEquals("Deposit 12345678 1000" + "10", actual.get(1));
	}

	@Test
	void can_deposits_into_checking_and_savings() {
		input.add("create checking 12345678 0.01");
		input.add("deposit 12345678 500");
		input.add("create savings 87654321 0.01");
		input.add("deposit 87654321 1250");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 500.00 0.01", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("Savings 87654321 1250.00 0.01", actual.get(2));
		assertEquals("Deposit 87654321 1250", actual.get(3));
	}

	@Test
	void cannot_create_an_account_that_is_not_an_eight_digit_number() {
		input.add("create checking 123ac738 0.01");
		assertSingleCommand("create checking 123ac738 0.01", masterControl.start(input));
	}

	@Test
	void cannot_withdraw_from_an_account_that_does_not_exist() {
		input.add("create checking 12345678 0.6");
		input.add("withdraw 23456789 200");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 0.60", actual.get(0));
		assertEquals("withdraw 23456789 200", actual.get(1));
	}

	@Test
	void cannot_withdraw_more_than_four_hundred_from_checking() {
		input.add("create checking 12345678 0.6");
		input.add("deposit 12345678 500");
		input.add("withdraw 12345678 450");
		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 500.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("withdraw 12345678 450", actual.get(2));
	}

	@Test
	void cannot_withdraw_more_than_thousand_from_checking() {
		input.add("create savings 12345678 3");
		input.add("deposit 12345678 2000");
		input.add("withdraw 12345678 1500");
		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 2000.00 3.00", actual.get(0));
		assertEquals("Deposit 12345678 2000", actual.get(1));
		assertEquals("withdraw 12345678 1500", actual.get(2));
	}

	@Test
	void cannot_withdraw_from_savings_account_twice_in_one_month() {
		input.add("create savings 12345678 3");
		input.add("deposit 12345678 2000");
		input.add("withdraw 12345678 200");
		input.add("withdraw 12345678 300");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 1800.00 3.00", actual.get(0));
		assertEquals("Deposit 12345678 2000", actual.get(1));
		assertEquals("Withdraw 12345678 200", actual.get(2));
		assertEquals("withdraw 12345678 300", actual.get(3));
	}

	@Test
	void cannot_withdraw_from_before_twelve_months() {
		input.add("create cd 12345678 0.01 2000");
		input.add("withdraw 12345678 5000");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Cd 12345678 2000.00 0.01", actual.get(0));
		assertEquals("withdraw 12345678 5000", actual.get(1));
	}

	@Test
	void withdraw_from_checking() {
		input.add("create checking 12345678 0.6");
		input.add("Deposit 12345678 100");
		input.add("withdraw 12345678 20");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 80.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Withdraw 12345678 20", actual.get(2));
	}

	@Test
	void withdraw_from_savings() {
		input.add("create savings 12345678 0.6");
		input.add("depOsit 12345678 2500");
		input.add("withdraw 12345678 1000");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 1500.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 2500", actual.get(1));
		assertEquals("Withdraw 12345678 1000", actual.get(2));
	}

	@Test
	void cannot_make_transfer_between_accounts_if_one_doesnt_exist() {
		input.add("create checking 12345678 0.6");
		input.add("depOsiT 12345678 100");
		input.add("transfer 12345678 87654321 100");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 100.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("transfer 12345678 87654321 100", actual.get(2));
	}

	@Test
	void cannot_transfer_greater_than_maximum_withdraw_from_checking() {
		input.add("create checking 12345678 0.6");
		input.add("Deposit 12345678 1000");
		input.add("create checking 87654321 1.2");
		input.add("transfer 12345678 87654321 1000");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 1000.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Checking 87654321 0.00 1.20", actual.get(2));
		assertEquals("transfer 12345678 87654321 1000", actual.get(3));
	}

	@Test
	void cannot_transfer_greater_than_thousand_between_savings_accounts() {
		input.add("create savings 12345678 0.6");
		input.add("deposit 12345678 2500");
		input.add("create savings 87654321 1.2");
		input.add("transfer 12345678 87654321 2500");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 2500.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 2500", actual.get(1));
		assertEquals("Savings 87654321 0.00 1.20", actual.get(2));
		assertEquals("transfer 12345678 87654321 2500", actual.get(3));
	}

	@Test
	void cannot_transfer_more_than_maximum_from_checking_to_savings() {
		input.add("create checking 12345678 0.6");
		input.add("Deposit 12345678 1000");
		input.add("Deposit 12345678 1000");
		input.add("deposit 12345678 500");
		input.add("create savings 87654321 1.2");
		input.add("transfer 12345678 87654321 401");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Checking 12345678 2500.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Deposit 12345678 1000", actual.get(2));
		assertEquals("Deposit 12345678 500", actual.get(3));
		assertEquals("Savings 87654321 0.00 1.20", actual.get(4));
		assertEquals("transfer 12345678 87654321 401", actual.get(5));
	}

	@Test
	void transfer_from_checking_to_savings() {
		input.add("create checking 12345678 0.6");
		input.add("Deposit 12345678 1000");
		input.add("create savings 87654321 1.2");
		input.add("transfer 12345678 87654321 200");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Checking 12345678 800.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Transfer 12345678 87654321 200", actual.get(2));
		assertEquals("Savings 87654321 200.00 1.20", actual.get(3));
		assertEquals("Transfer 12345678 87654321 200", actual.get(4));
	}

	@Test
	void cannot_withdraw_and_transfer_twice_from_savings_before_time_pass() {
		input.add("create savings 12345678 0.6");
		input.add("Deposit 12345678 1000");
		input.add("create checking 87654321 1.2");
		input.add("withdraw 12345678 200");
		input.add("transfer 12345678 87654321 300");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 800.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Withdraw 12345678 200", actual.get(2));
		assertEquals("Checking 87654321 0.00 1.20", actual.get(3));
		assertEquals("transfer 12345678 87654321 300", actual.get(4));
	}

	@Test
	void pass_time_with_three_unique_accounts_and_subtract_from_accounts_under_100_balance() {
		input.add("create checking 12345678 1.50");
		input.add("deposit 12345678 80");
		input.add("create savings 87654321 1.50");
		input.add("deposit 87654321 2500");
		input.add("deposit 87654321 2500");
		input.add("create checking 23456789 0.6");
		input.add("create cd 98765432 1 5000");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Checking 12345678 55.00 1.50", actual.get(0));
		assertEquals("Deposit 12345678 80", actual.get(1));
		assertEquals("Savings 87654321 5006.25 1.50", actual.get(2));
		assertEquals("Deposit 87654321 2500", actual.get(3));
		assertEquals("Deposit 87654321 2500", actual.get(4));
		assertEquals("Cd 98765432 5016.66 1.00", actual.get(5));
	}

	@Test
	void can_create_another_account_with_id_from_closed_account() {
		input.add("create checking 12345678 0.6");
		input.add("deposit 12345678 100");
		input.add("create savings 87654321 0.6");
		input.add("pass 1");
		input.add("create cd 87654321 0.6 10000");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 100.05 0.60", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Cd 87654321 10000.00 0.60", actual.get(2));
	}

	@Test
	void sample_make_sure_this_passes_unchanged() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}
}
