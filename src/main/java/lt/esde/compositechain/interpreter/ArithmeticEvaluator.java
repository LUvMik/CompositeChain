package lt.esde.compositechain.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class ArithmeticEvaluator {

    public static double evaluate(String expression) {
        List<String> rpn = toRPN(expression);
        return evalRPN(rpn);
    }

    private static List<String> toRPN(String expr) {
        Stack<String> ops = new Stack<>();
        List<String> out = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(expr, "+-*/() ", true);

        String prevToken = "";
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (token.matches("\\d+(\\.\\d+)?")) {
                out.add(token);
                prevToken = token;
            } else if (token.equals("(")) {
                ops.push(token);
                prevToken = token;
            } else if (token.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    out.add(ops.pop());
                }
                if (!ops.isEmpty()) ops.pop();
                prevToken = token;
            } else if ("+-*/".contains(token)) {
                if (token.equals("-") && (prevToken.isEmpty() || "(".equals(prevToken) || "+-*/".contains(prevToken))) {
                    out.add("0");
                }

                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    out.add(ops.pop());
                }
                ops.push(token);
                prevToken = token;
            }
        }

        while (!ops.isEmpty()) {
            out.add(ops.pop());
        }

        return out;
    }

    private static int precedence(String op) {
        return switch (op) {
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }

    private static double evalRPN(List<String> rpn) {
        Stack<Double> stack = new Stack<>();
        for (String token : rpn) {
            switch (token) {
                case "+" -> stack.push(stack.pop() + stack.pop());
                case "-" -> {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                }
                case "*" -> stack.push(stack.pop() * stack.pop());
                case "/" -> {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                }
                default -> stack.push(Double.parseDouble(token));
            }
        }
        return stack.pop();
    }
}
