public abstract class Account {

	private double balance;
	private int id;
	private double apr;

	protected Account(int id, double apr) {
		this.balance = 0.0;
		this.id = id;
		this.apr = apr;
	}

	public void deposit_money(double amount) {
		balance += amount;
	}

	public void withdraw_money(double amount) {
		if (amount <= balance) {
			balance -= amount;
		} else {
			balance = 0;
		}
	}

	public double getBalance() {
		return balance;
	}

	public int getId() {
		return id;
	}

	public double getApr() {
		return apr;
	}
}
