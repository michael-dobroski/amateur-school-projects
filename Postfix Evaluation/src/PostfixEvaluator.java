import java.io.PrintWriter;
import java.util.Stack;

/**
 * Handles the postfix evaluation of any given postfix expression including digits larger than 9 such as '72 86 + 999 * 18 /'
 * @author Michael Dobroski
 */
public class PostfixEvaluator {

    /**
     * Constructor, when called, displays the evaluation of a given postfix expression in a neat manner
     * @param postfix postfix expression to be evaluated
     */
    public PostfixEvaluator(StringBuffer postfix) {

        System.out.println("The evaluation of '" + postfix + "' is " + evaluatePostfixExpression(postfix));

    }

    /**
     * The postfix evaluation itself
     * @param postfix postfix expression to be evaluated
     * @return int value evaluation of postfix expression
     */
    private int evaluatePostfixExpression(StringBuffer postfix) {

        Stack<Integer> operandStack = new Stack<>();
        String operands = "0123456789"; // string containing valid operands used in if statement checking if char is a operand
        String operators = "+-*/^%"; // string containing valid operators used in if statement checking if char is an operator
        int x; int y; // temporary vars used in operator step

        postfix.insert(postfix.length(), ")");

        for (int i = 0; postfix.charAt(i) != ')'; i++) { // stops when ')' char is encountered

            StringBuffer operand = new StringBuffer(); // for temporary creation of current integer in postfix
            while (operands.contains(String.valueOf(postfix.charAt(i)))) { // while char is an operand
                operand.append(postfix.charAt(i));
                i++;
            }
            if (!String.valueOf(operand).equals("")) { // so that we don't attempt to parse empty string into int
                operandStack.push(Integer.parseInt(String.valueOf(operand))); // parses StringBuffer into respective int and pushes it to the operands stack
            }

            if (postfix.charAt(i) == '-' && operands.contains(String.valueOf(postfix.charAt(i+1)))) { // in case of a negative number
                i++;
                while (operands.contains(String.valueOf(postfix.charAt(i)))) { // while char is an operand
                    operand.append(postfix.charAt(i));
                    i++;
                }
                operandStack.push(Integer.parseInt(String.valueOf(operand)) * -1);
            }

            else if (operators.contains(String.valueOf(postfix.charAt(i)))) { // if char is an operator
                x = operandStack.pop();
                y = operandStack.pop();
                operandStack.push(calculate(y, postfix.charAt(i), x));
            }

        }

        return operandStack.pop();

    }

    /**
     * Method used by evaluatePostfixExpression() in order to evaluate an expression of two ints and an operator
     * @param operand1 int value of first operand
     * @param operator respective operator used in calculation such as +, -, *, and /
     * @param operand2 int value of second operand
     * @return int evaluation of operand operator operand expression
     */
    private int calculate(int operand1, char operator, int operand2) {

        switch (operator) { // switch statement was covered in IEC, perfect for this scenario
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            case '^':
                return (int) StrictMath.pow(operand1, operand2);
            default: // we need a default case and only other operator left is '%' so we do that operation here
                if (operand1 > 0) {
                    return operand1 % operand2;
                } else { // first element is negative
                    return operand1 % operand2 + operand2; // fixes java's weird way of doing negative modulus
            }

        }

    }

}
