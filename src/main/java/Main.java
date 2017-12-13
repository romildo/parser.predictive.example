import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
   private static void run(String[] args) throws IOException {
      java.io.Reader input;
      String fileName;

      if (args.length == 0) {
         fileName = "stdin";
         input = new java.io.InputStreamReader(System.in);
      }
      else {
         fileName = args[0];
         input = new java.io.FileReader(fileName);
      }

      Lexer lex = new Lexer(input);
      MyParser p = new MyParser(lex);

      Double resposta = p.parse();
      System.out.printf("Valor: %f%n", resposta);
   }

   public static void main(String[] args) {
      try {
         run(args);
      }
      catch (CompilationError e) {
         System.err.printf("compilation error:%n  %s%n", e.getMessage());
         System.exit(3);
      }
      catch (FileNotFoundException e) {
         System.err.printf("file not found: %s%n", args[0]);
         System.exit(1);
      }
      catch (IOException e) {
         System.err.println("I/O error");
         System.exit(2);
      }
   }
}
