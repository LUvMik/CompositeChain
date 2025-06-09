package lt.esde.compositechain.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class BitwiseEvaluator {

    private static final Map<String, Integer> PRECEDENCE = Map.of(
            "~", 3,
            "<<", 2, ">>", 2,
            "&", 1,
            "^", 0,
            "|", -1
    );

    public static long evaluate(String expr) {
        List<String> rpn = toRPN(expr.replaceAll("\\s+", ""));
        return evalRPN(rpn);
    }

    private static List<String> toRPN(String expr) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
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

            if (c == '(') {
                operators.push("(");
                i++;
            } else if (c == ')') {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty() && operators.peek().equals("(")) {
                    operators.pop();
                }
                i++;
            } else {
                String op = String.valueOf(c);
                if ((c == '<' || c == '>') && i + 1 < expr.length() && expr.charAt(i + 1) == c) {
                    op = expr.substring(i, i + 2);
                    i += 2;
                } else {
                    i++;
                }

                while (!operators.isEmpty() && !operators.peek().equals("(") &&
                        PRECEDENCE.getOrDefault(operators.peek(), -10) >= PRECEDENCE.getOrDefault(op, -10)) {
                    output.add(operators.pop());
                }

                operators.push(op);
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    private static long evalRPN(List<String> tokens) {
        Stack<Long> stack = new Stack<>();
        for (String token : tokens) {
            switch (token) {
                case "~" -> stack.push(~stack.pop());
                case "<<" -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a << b);
                }
                case ">>" -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a >> b);
                }
                case "&" -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a & b);
                }
                case "|" -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a | b);
                }
                case "^" -> {
                    long b = stack.pop(), a = stack.pop();
                    stack.push(a ^ b);
                }
                default -> stack.push(Long.parseLong(token));
            }
        }
        return stack.pop();
    }
}
