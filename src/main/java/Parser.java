import java.io.IOException;

public abstract class Parser<T> {
   protected Lexer lex;
   protected Token tok;

   public Parser(Lexer lex) throws IOException {
      this.lex = lex;
      this.tok = lex.getToken();
   }

   protected void error(Token.T[] expected) {
      StringBuilder b = new StringBuilder();
      if (expected.length > 0) {
         b.append(expected[0]);
         for (int i = 1; i < expected.length; i++)
            b.append(", ").append(expected[i]);
      }
      ErrorMsg.error(tok.line, tok.col, "syntax error", "expecting " + b.toString() + ", found " + tok.type);
      System.exit(3);
   }

   protected void advance() throws IOException {
      tok = lex.getToken();
   }

   protected void eat(Token.T t) throws IOException {
      if (tok.type == t)
         advance();
      else
         error(new Token.T[]{t});
   }

   public abstract T parse() throws IOException;
}
