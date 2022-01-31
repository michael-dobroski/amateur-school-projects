/**
 * Loan account subclass
 * @author Michael Dobroski
 */
public class LoanAccount extends Account {

    /**
     * Interest rate, is used by addInterest function, final because doesn't change after initialization
     */
    private final double interestRate;

    /**
     * Constructor for loan account
     * @param balance loan amount to be paid
     * @param accountNum same as superclass
     * @param accountHolder same as superclass
     * @param interestRate interestRate, specific to savings and loan accounts
     */
    public LoanAccount(double balance, int accountNum, String accountHolder, double interestRate) {
        super(balance, accountNum, accountHolder); // superclass constructor
        this.interestRate = interestRate;
    }

    /**
     * Adds monthly interest to loan, assumed to be called every month
     */
    public void addInterest() {
        setBalance(roundDollars(getBalance() * (1 + (interestRate/12))));
    }

    /**
     * Basically the withdraw function but different idea
     * @param payment amount being paid from loan
     */
    public void makePayment(double payment) {
        if ((getBalance() - payment) > 0) { // if there's still loan to be paid after payment
            setBalance(getBalance() - payment);
        } else if ((getBalance() - payment) == 0) { // if loan is exactly $0 after payment
            System.out.println("\n     Loan fully paid off!");
            setBalance(0);
        } else { // if you pay too much for loan, returns extra
            System.out.println("\n     Loan fully paid off, but you paid too much! Returning the extra $" + roundDollars(payment - getBalance()) + "...");
            setBalance(0);
        }
    }

    /**
     * Loan account's print function, displays all necessary info about the class
     */
    public void print() {
        System.out.println("\n     ~ " + getAccountHolder() + "'s Loan Account ~");
        System.out.println("     Account Number - #" + getAccountNum());
        System.out.println("     Money Due - $" + getBalance());
        System.out.println("     Interest rate - " + interestRate + "\n");
    }

}