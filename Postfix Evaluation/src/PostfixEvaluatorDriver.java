import java.util.Scanner;

/**
 * Class contains driver testing the PostfixEvaluator class
 * @author Michael Dobroski
 */
public class PostfixEvaluatorDriver {

    /**
     * Main method for testing
     * @param args if ran in terminal with parameter
     */
    public static void main(String[] args) {

        while(true) { // allows user to continually enter their own postfix expressions until they type 'exit'
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter postfix expression to be evaluated, or type 'exit' to examine various test cases and end the program...");
            String postfix;
            postfix = input.nextLine();
            if (postfix.equals("exit")) { break; }
            new PostfixEvaluator(new StringBuffer(postfix));
            System.out.println();
        }

        // MOST OF THIS WAS TESTED WITH https://www.free-online-calculator-use.com/postfix-evaluator.html#
        // MODULUS WAS TESTED ON MY OWN BECAUSE IT WASN'T INCLUDED ON THAT SITE

        System.out.println("\nOutput of '4 5 7 2 + - *' should be -16...");
        new PostfixEvaluator(new StringBuffer("4 5 7 2 + - *"));
        System.out.println("\nOutput of '3 4 + 2 * 7 /' should be 2...");
        new PostfixEvaluator(new StringBuffer("3 4 + 2 * 7 /"));
        System.out.println("\nOutput of '5 7 + 6 2 - *' should be 48...");
        new PostfixEvaluator(new StringBuffer("5 7 + 6 2 - *"));
        System.out.println("\nOutput of '4 2 + 3 5 1 - * +' should be 18...");
        new PostfixEvaluator(new StringBuffer("4 2 + 3 5 1 - * +"));
        System.out.println("\nOutput of '5 3 2 3 ^ 5 - 7 -3 * + * -' should be 59...");
        new PostfixEvaluator(new StringBuffer("5 3 2 3 ^ 5 - 7 -3 * + * -"));
        System.out.println("\nOutput of '18 3 / 2 ^ 13 7 + 5 2 ^ * +' should be 536...");
        new PostfixEvaluator(new StringBuffer("18 3 / 2 ^ 13 7 + 5 2 ^ * +"));
        System.out.println("\nOutput of '16 2 + 3 5 1 - * +' should be 30...");
        new PostfixEvaluator(new StringBuffer("16 2 + 3 5 1 - * +"));
        System.out.println("\nOutput of '72 86 + 999 * 18 /' should be 8769...");
        new PostfixEvaluator(new StringBuffer("72 86 + 999 * 18 /"));
        System.out.println("\nOutput of '318 973 + 11 3105 * -' should be -32864...");
        new PostfixEvaluator(new StringBuffer("318 973 + 11 3105 * -"));
        System.out.println("\nOutput of '4513584 2216851 + 13 51334 46831564 - * +' should be -601412555...");
        new PostfixEvaluator(new StringBuffer("4513584 2216851 + 13 51334 46831564 - * +"));
        System.out.println("\nOutput of '4513584 2216851 + 13 51334 46831564 - * + 12 %' should be 1..."); // because -601412555 mod 12 = 1
        new PostfixEvaluator(new StringBuffer("4513584 2216851 + 13 51334 46831564 - * + 12 %"));
        System.out.println("\nOutput of '-2 4513584 2216851 + 13 51334 46831564 - * + *' should be 1202825110...");
        new PostfixEvaluator(new StringBuffer("-2 4513584 2216851 + 13 51334 46831564 - * + *"));
        System.out.println("\nOutput of '-2 4513584 2216851 + 13 51334 46831564 - * + * 16 %' should be 6..."); // because 1202825110 mod 16 = 6
        new PostfixEvaluator(new StringBuffer("-2 4513584 2216851 + 13 51334 46831564 - * + * 16 %"));
        System.out.println("\nOutput of '-2 4513584 2216851 + 13 51334 46831564 - * + * 789 %' should be 133..."); // because 1202825110 mod 789 = 133
        new PostfixEvaluator(new StringBuffer("-2 4513584 2216851 + 13 51334 46831564 - * + * 789 %"));

    }

}
