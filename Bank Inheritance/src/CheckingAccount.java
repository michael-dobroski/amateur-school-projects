/**
 * Checking Account subclass
 * @author Michael Dobroski
 */
public class CheckingAccount extends Account {

    /**
     * Overdraft limit AKA the amount of negative balance that you can't go over, final because it won't get changed after initialization
     */
    private final double overdraftLimit;

    /**
     * Constructor for checking account
     * @param balance same as superclass
     * @param accountNum same as superclass
     * @param accountHolder same as superclass
     * @param overdraftLimit overdraft limit, specific to checking accounts
     */
    public CheckingAccount(double balance, int accountNum, String accountHolder, double overdraftLimit) {
        super(balance, accountNum, accountHolder); // superclass constructor
        this.overdraftLimit = roundDollars(overdraftLimit);
    }

    /**
     * Overrides superclass function because we have to take in account the overdraft limit
     * @param amt amount of money to be subtracted from balance
     */
    public void withdraw(double amt) {
        if ((getBalance() - amt) < -1 * overdraftLimit) { // checks to see if you've breach overdraft limit
            System.out.println("\n     Withdrawal will breach overdraft limit. Nothing has been withdrawn.");
        } else {
            setBalance(getBalance() - amt);
        }
    }

    /**
     * Checking account's print function, displays all necessary info about the class
     */
    public void print() {
        System.out.println("\n     ~ " + getAccountHolder() + "'s Checking Account ~");
        System.out.println("     Account Number - #" + getAccountNum());
        System.out.println("     Balance - $" + getBalance());
        System.out.println("     Overdraft limit - $" + overdraftLimit + "\n");
    }

}