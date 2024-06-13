package A_H_SP31;

public class ErrorHandler {
    // Обробити помилку

    public void handleErr(ErrorType error) throws InterpreterException {

       String[] err = {
           "Syntax Error",
           "Unbalanced Parentheses",
           "No Expression Present",
           "Division by Zero",
       };
       throw new InterpreterException(err[error.ordinal()]);
   }

//клас виключень
public class InterpreterException extends Exception {

   String errStr;          // опис помилки

   public InterpreterException(String str) {

       errStr = str;

   }

   public String toString() {

       return errStr;

   }

}
}