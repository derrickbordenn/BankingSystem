public class CommandStore {
	String account_with_no_id = "create savings 2.4";
	String cd_with_no_id = "create cd 2.4 1200";
	String account_with_no_apr = "create savings 12345678";
	String cd_with_no_apr = "create cd 12345678 1200";
	String cd_with_no_initial_balance = "create cd 12345678 2.4";
	String nonexistent_account_type = "create account 12345678 2.4";
	String account_with_no_attributes = "create savings";
	String account_with_big_apr = "create savings 12345679 -2.3";
	String account_with_negative_apr = "create savings 12345679 100";
	String cd_with_negative_balance = "create cd 12345679 2.3 -100";
	String small_cd_initial_balance = "create cd 12345679 2.3 100";
	String big_cd_initial_balance = "create cd 12345679 2.3 100000";
	String id_not_enough_digits = "create savings 1234567 2.3";
	String id_too_many_digits = "create savings 123456789 2.3";
	String non_numeric_characters_in_id = "create savings 1234567a 2.3";
	String non_numeric_character_in_initial_balance = "create cd 12345678 2.3 twenty";
	String non_numeric_character_in_apr = "create savings 12345678 two.2";
	String invalid_double_apr = "create savings 12345678 2.3.2";
	String decimal_in_id = "create cd 12.45678 2.3 twenty";
	String create_with_no_attributes = "create";
	String deposit_negative = "deposit 12345678 -1000";
}