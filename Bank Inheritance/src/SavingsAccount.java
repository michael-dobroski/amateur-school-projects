/**
 * Savings Account subclass
 * @author Michael Dobroski
 */
public class SavingsAccount extends Account {

    /**
     * Interest rate, is used by addInterest function, final because doesn't change after initialization
     */
    private final double interestRate;

    /**
     * Constructor for savings account
     * @param balance same as superclass
     * @param accountNum same as superclass
     * @param accountHolder same as superclass
     * @param interestRate interestRate, specific to savings and loan accounts
     */
    public SavingsAccount(double balance, int accountNum, String accountHolder, double interestRate) {
        super(balance, accountNum, accountHolder); // superclass constructor
        this.interestRate = interestRate;
    }

    /**
     * Adds monthly interest to balance, assumed to be called every month
     */
    public void addInterest() { setBalance(roundDollars(getBalance() * (1 + (interestRate/12)))); }

    /**
     * Savings account's print function, displays all necessary info about the class
     */
    public void print() {
        System.out.println("\n     ~ " + getAccountHolder() + "'s Savings Account ~");
        System.out.println("     Account Number - #" + getAccountNum());
        System.out.println("     Balance - $" + getBalance());
        System.out.println("     Interest rate - " + interestRate + "\n");
    }

}