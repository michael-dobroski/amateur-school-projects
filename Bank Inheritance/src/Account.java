import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Account class, several classes inherit from it
 */
public class Account {

    /**
     * Currency balance in the account
     */
    private double balance;

    /**
     * Account number associated with the account, final because it's only initialized and never changes
     */
    private final int accountNum;

    /**
     * Name of the account holder associated with the account, final because it's only initialized and never changes
     */
    private final String accountHolder;

    /**
     * Constructor, gets called by subclasses
     * @param balance currency balance in the account
     * @param accountNum account number associated with the account
     * @param accountHolder name of the account holder associated with the account
     */
    public Account(double balance, int accountNum, String accountHolder) {
        this.balance = roundDollars(balance);
        this.accountNum = accountNum;
        this.accountHolder = accountHolder;
    }

    /**
     * Deposit function, adds deposit to balance, used in subclasses
     * @param dep amount of money to be added to balance
     */
    public void deposit(double dep) { balance = roundDollars(balance + dep); }

    /**
     * Withdraw function, subtracts amount from balance, used in subclasses
     * @param amt amount of money to be subtracted from balance
     */
    public void withdraw(double amt) { balance = roundDollars(balance - amt); }

    /**
     * Getter for balance, used in subclasses
     * @return the balance
     */
    public double getBalance() { return balance; }

    /**
     * Setter for balance, used in subclasses
     * @param balance the balance that you would like to set
     */
    public void setBalance(double balance) { this.balance = balance; }

    /**
     * Getter for account number, used in subclass print methods
     * @return the account number
     */
    public int getAccountNum() { return accountNum; }

    /**
     * Getter for name of account holder
     * @return name of account holder
     */
    public String getAccountHolder() { return accountHolder; }

    /**
     * Rounds input to two decimal places, used frequently all over program because we have cents in our currency
     * @param num number to be rounded
     * @return rounded number
     */
    public double roundDollars(double num) {
        DecimalFormat df = new DecimalFormat("#.##"); // two decimal points
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        return Double.parseDouble(df.format(num));
    }

}