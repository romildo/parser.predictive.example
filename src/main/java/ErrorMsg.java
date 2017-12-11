
public class ErrorMsg {
   public static void error(int line, int column, String msg1, String msg2) {
      System.out.printf("(%d,%d) %s: %s\n", line, column, msg1, msg2);
   }
}
