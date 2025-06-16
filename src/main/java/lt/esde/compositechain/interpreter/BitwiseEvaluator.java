package lt.esde.compositechain.interpreter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
 * Bit-wise expression evaluator that supports ~, <<, >>, &, ^, | operators.
 * The algorithm mirrors {@link ArithmeticEvaluator}: converts infix to RPN and then evaluates.
 */
public class BitwiseEvaluator {

    /* -------------------- String constants -------------------- */
    private static final String WHITESPACE_REGEX = "\\s+";

    private static final String NOT          = "~";
    private static final String SHIFT_LEFT   = "<<";
    private static final String SHIFT_RIGHT  = ">>";
    private static final String AND          = "&";
    private static final String XOR          = "^";
    private static final String OR           = "|";

    private static final String LEFT_PAREN   = "(";
    private static final String RIGHT_PAREN  = ")";
    /* ----------------------------------------------------------- */

    /**
     * Precedence table (higher value → higher precedence).
     */
    private static final Map<String, Integer> PRECEDENCE = Map.of(
            NOT,          3,
            SHIFT_LEFT,   2,
            SHIFT_RIGHT,  2,
            AND,          1,
            XOR,          0,
            OR,          -1
    );

    /**
     * Evaluates a bit-wise expression written in infix notation.
     * @param expr string such as "~1 & (2 | 3) << 1"
     * @return resulting long value
     */
    public static long evaluate(String expr) {
        List<String> rpn = toRPN(expr.replaceAll(WHITESPACE_REGEX, ""));
        return evalRPN(rpn);
    }

    /**
     * Converts infix bit-wise expression to Reverse Polish Notation (RPN).
     */
    private static List<String> toRPN(String expr) {
        List<String> output = new ArrayList<>();
        Deque<String> operators = new ArrayDeque<>();

        for (int i = 0; i < expr.length(); ) {
            char c = expr.charAt(i);

            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    num.append(expr.charAt(i++));
                }
                output.add(num.toString());
                continue;
            }

            if (c == LEFT_PAREN.charAt(0)) {
                operators.push(LEFT_PAREN);
                i++;
                continue;
            }
            if (c == RIGHT_PAREN.charAt(0)) {
                while (!operators.isEmpty() && !operators.peek().equals(LEFT_PAREN)) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty() && operators.peek().equals(LEFT_PAREN)) {
                    operators.pop();
                }
                i++;
                continue;
            }

            String op = String.valueOf(c);
            if ((c == '<' || c == '>') && i + 1 < expr.length() && expr.charAt(i + 1) == c) {
                op = expr.substring(i, i + 2);    // << or >>
                i += 2;
            } else {
                i++;
            }

            while (!operators.isEmpty() && !operators.peek().equals(LEFT_PAREN) &&
                    PRECEDENCE.getOrDefault(operators.peek(), -10) >= PRECEDENCE.getOrDefault(op, -10)) {
                output.add(operators.pop());
            }

            operators.push(op);
        }

        // Drain remaining operators
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    /**
     * Evaluates an RPN sequence containing bit-wise operators.
     */
    private static long evalRPN(List<String> tokens) {
        Deque<Long> stack = new ArrayDeque<>();
        for (String token : tokens) {
            switch (token) {
                case NOT -> stack.push(~stack.pop());
                case SHIFT_LEFT -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a << b);
                }
                case SHIFT_RIGHT -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a >> b);
                }
                case AND -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a & b);
                }
                case OR -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a | b);
                }
                case XOR -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a ^ b);
                }
                default -> stack.push(Long.parseLong(token));
            }
        }
        return stack.pop();
    }
}