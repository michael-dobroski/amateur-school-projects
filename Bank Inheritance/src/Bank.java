/**
 * Bank class that contains the main function, all from banker's perspective
 * @author Michael Dobroski
 */
public class Bank {

    /**
     * Main function
     * @param arg java syntax
     */
    public static void main(String[] arg) {

        System.out.println("// Create new Checking Account with $0 balance, account number #1, account holder Shéyaa Bin Abraham-Joseph, and overdraft limit $500...");
        System.out.println("CheckingAccount sheyaaChecking = new CheckingAccount(0, 1, \"Shéyaa Bin Abraham-Joseph\", 500);");
        CheckingAccount sheyaaChecking = new CheckingAccount(0, 1, "Shéyaa Bin Abraham-Joseph", 500);
        sheyaaChecking.print();

        System.out.println("// Deposit $147.89 into Shéyaa's checking account...");
        System.out.println("sheyaaChecking.deposit(147.89);");
        sheyaaChecking.deposit(147.89);
        sheyaaChecking.print();

        System.out.println("// Withdraw $600.00 from Shéyaa's checking account...");
        System.out.println("sheyaaChecking.withdraw(600);");
        sheyaaChecking.withdraw(600);
        sheyaaChecking.print();

        System.out.println("// Withdraw $100.00 from Shéyaa's checking account...");
        System.out.println("sheyaaChecking.withdraw(100);");
        sheyaaChecking.withdraw(100);
        sheyaaChecking.print();

        System.out.println("// Create new Savings Account with $1,000.00 balance, account number #2, account holder Jacques Berman Webster II, and interest rate 0.5...");
        System.out.println("SavingsAccount jacquesSavings = new SavingsAccount(1000, 2, \"Jacques Berman Webster II\", 0.5);");
        SavingsAccount jacquesSavings = new SavingsAccount(1000, 2, "Jacques Berman Webster II", 0.5);
        jacquesSavings.print();

        System.out.println("// A month has passed, so we add interest...");
        System.out.println("jacquesSavings.addInterest();");
        jacquesSavings.addInterest();
        jacquesSavings.print();

        System.out.println("// Deposit $298.12 into Jacques's savings account...");
        System.out.println("jacquesSavings.deposit(298.12);");
        jacquesSavings.deposit(298.12);
        jacquesSavings.print();

        System.out.println("// A month has passed, so we add interest...");
        System.out.println("jacquesSavings.addInterest();");
        jacquesSavings.addInterest();
        jacquesSavings.print();

        System.out.println("// Withdraw $892.18 from Jacques's savings account...");
        System.out.println("jacquesSavings.withdraw(892.18)");
        jacquesSavings.withdraw(892.18);
        jacquesSavings.print();

        System.out.println("// A month has passed, so we add interest...");
        System.out.println("jacquesSavings.addInterest();");
        jacquesSavings.addInterest();
        jacquesSavings.print();

        System.out.println("// Create new Loan for $100,000, account number #3, account holder Sergio Giavanni Kitchens, and interest rate 1.5...");
        System.out.println("LoanAccount sergioLoan = new LoanAccount(100000, 3, \"Sergio Giavanni Kitchens\", 1.5);");
        LoanAccount sergioLoan = new LoanAccount(100000, 3, "Sergio Giavanni Kitchens", 1.5);
        sergioLoan.print();

        System.out.println("// A month has passed, so we add interest to Sergio's loan...");
        System.out.println("sergioLoan.addInterest();");
        sergioLoan.addInterest();
        sergioLoan.print();

        System.out.println("// Sergio paid $0.23 of his loan...");
        System.out.println("sergioLoan.makePayment(0.23);");
        sergioLoan.makePayment(0.23);
        sergioLoan.print();

        System.out.println("// A month has passed, so we add interest to Sergio's loan...");
        System.out.println("sergioLoan.addInterest();");
        sergioLoan.addInterest();
        sergioLoan.print();

        System.out.println("// Sergio paid $130,000 of his loan...");
        System.out.println("sergioLoan.makePayment(130000);");
        sergioLoan.makePayment(130000);
        sergioLoan.print();

    }

}