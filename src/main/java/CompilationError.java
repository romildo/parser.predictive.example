public class CompilationError extends RuntimeException {

   public CompilationError(String message) {
      super(message);
   }

   public CompilationError(String format, Object... args) {
      this(String.format(format, args));                      ''
   }

   public CompilationError(int line, int col, String format, Object... args) {
      this("(%d,%d) %s", line, col, String.format(format, args));
   }


   public static CompilationError error(String message) {
      return new CompilationError(message);
   }

   public static CompilationError error(String format, Object... args) {
      return new CompilationError(String.format(format, args));
   }

   public static CompilationError error(int line, int col, String format, Object... args) {
      return new CompilationError(line, col, format, args);
   }

}
