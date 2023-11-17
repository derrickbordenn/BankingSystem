public class CDAccount extends Account {

	public CDAccount(int id, double apr, double balance) {
		super(id, apr);
		this.deposit_money(balance);
		setAccountType("cd");
	}
}
