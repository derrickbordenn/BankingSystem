package banking;

public class CDAccount extends Account {

	public CDAccount(String id, double apr, double balance) {
		super(id, apr);
		this.deposit_money(balance);
		setAccountType("cd");
	}
}
