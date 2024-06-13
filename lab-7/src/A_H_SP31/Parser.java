package A_H_SP31;
import A_H_SP31.ErrorHandler.InterpreterException;

public class Parser {

    final String EOP = "\0";
    ErrorHandler errorHandler;
    private char[] expression;
    private int expressionIdx;
    private String token;
    private TokenType tokenType;

    public Parser() {
        errorHandler = new ErrorHandler();
    }

    public double evaluate(char[] exp) throws InterpreterException {
        expression = exp;
        expressionIdx = 0; // Initialize index
        double result = 0.0;
        getToken();
        if (token.equals(EOP))
            errorHandler.handleErr(ErrorType.NOEXP);
        result = evaluateAdditionSubtraction();
        return result;
    }

    private double evaluateAdditionSubtraction() throws InterpreterException {
        char op;
        double result;
        double partialResult;

        result = evaluateDivisionMultiplication();

        while ((op = token.charAt(0)) == '+' || op == '-') {
            getToken();
            partialResult = evaluateDivisionMultiplication();

            switch (op) {
                case '-':
                    result = result - partialResult;
                    break;
                case '+':
                    result = result + partialResult;
                    break;
            }
        }
        return result;
    }

    private double evaluateDivisionMultiplication() throws InterpreterException {
        char op;
        double result;
        double partialResult;

        result = evaluatePower();

        while ((op = token.charAt(0)) == '*' || op == '/' || op == '%') {
            getToken();
            partialResult = evaluatePower();

            switch (op) {
                case '*':
                    result = result * partialResult;
                    break;
                case '/':
                    if (partialResult == 0.0)
                        errorHandler.handleErr(ErrorType.DIVBYZERO);
                    result = result / partialResult;
                    break;
                case '%':
                    if (partialResult == 0.0)
                        errorHandler.handleErr(ErrorType.DIVBYZERO);
                    result = result % partialResult;
                    break;
            }
        }
        return result;
    }

    private double evaluatePower() throws InterpreterException {
        double result;
        double partialResult;

        result = evaluateUnaryPlusMinus();

        if (token.equals("^")) {
            getToken();
            partialResult = evaluatePower();
            result = Math.pow(result, partialResult);
        }

        return result;
    }

    private double evaluateUnaryPlusMinus() throws InterpreterException {
        double result;
        String op = "";

        if ((tokenType == TokenType.DELIMITER) && (token.equals("+") || token.equals("-"))) {
            op = token;
            getToken();
        }

        result = evaluateTrigonometry();

        if (op.equals("-"))
            result = -result;

        return result;
    }

    private double evaluateTrigonometry() throws InterpreterException {
        double result;
        String func = "";

        if (tokenType == TokenType.FUNCTION) {
            func = token;
            getToken();
        }

        result = evaluateParentheses();

        switch (func) {
            case "Sin":
                result = Math.sin(Math.toRadians(result));
                break;
            case "Cos":
                result = Math.cos(Math.toRadians(result));
                break;
            case "Tan":
                result = Math.tan(Math.toRadians(result));
                break;
            case "Ctg":
                result = 1.0 / Math.tan(Math.toRadians(result));
                break;
        }

        return result;
    }

    private double evaluateParentheses() throws InterpreterException {
        double result;

        if (token.equals("(")) {
            getToken();
            result = evaluateAdditionSubtraction();

            if (!token.equals(")")) {
                errorHandler.handleErr(ErrorType.UNBALPARENS);
            }

            getToken();
        } else {
            result = atom();
        }

        return result;
    }

    private double atom() throws InterpreterException {
        double result = 0.0;

        switch (tokenType) {
            case NUMBER:
                try {
                    result = Double.parseDouble(token);
                } catch (NumberFormatException exc) {
                    errorHandler.handleErr(ErrorType.SYNTAX);
                }
                getToken();
                break;
            default:
                errorHandler.handleErr(ErrorType.SYNTAX);
                break;
        }

        return result;
    }

    private void getToken() throws InterpreterException {
        char ch;
        tokenType = TokenType.NONE;
        token = "";

        if (expressionIdx == expression.length) {
            token = EOP;
            return;
        }

        while (expressionIdx < expression.length && isSpace(expression[expressionIdx]))
            expressionIdx++;

        if (expressionIdx == expression.length) {
            token = EOP;
            tokenType = TokenType.DELIMITER;
            return;
        }

        if (isDelim(expression[expressionIdx])) {
            token += expression[expressionIdx];
            expressionIdx++;
            tokenType = TokenType.DELIMITER;
        } else if (Character.isDigit(expression[expressionIdx]) || expression[expressionIdx] == '.') {
            while (!isDelim(expression[expressionIdx])) {
                token += expression[expressionIdx];
                expressionIdx++;
                if (expressionIdx >= expression.length)
                    break;
            }
            tokenType = TokenType.NUMBER;
        } else if (isFunction()) {
            while (expressionIdx < expression.length && Character.isLetter(expression[expressionIdx])) {
                token += expression[expressionIdx];
                expressionIdx++;
            }
            tokenType = TokenType.FUNCTION;
        } else {
            errorHandler.handleErr(ErrorType.SYNTAX);
        }
    }

    private boolean isDelim(char c) {
        return "+-/*%^=()".indexOf(c) != -1;
    }

    private boolean isFunction() {
        String[] functions = {"Sin", "Cos", "Tan", "Ctg"};
        for (String func : functions) {
            if (expression.length - expressionIdx >= func.length()) {
                if (new String(expression, expressionIdx, func.length()).equalsIgnoreCase(func)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSpace(char c) {
        return c == ' ' || c == '\t';
    }
}
