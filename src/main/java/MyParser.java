import java.io.IOException;

public class MyParser extends Parser<Object> {
   private void S() throws IOException {
      switch (tok.type) {
         case IF:
            eat(Token.T.IF);
            E();
            eat(Token.T.THEN);
            S();
            eat(Token.T.ELSE);
            S();
            break;
         /* complete */
         default:
            error(new Token.T[]{Token.T.IF, Token.T.BEGIN, Token.T.PRINT});
      }
   }

   private void E() throws IOException {
      switch (tok.type) {
       /* complete */
         default:
            error(new Token.T[]{ /* complete */});
      }
   }


   /* complete */

   public Object parse() throws IOException {
      S();
      eat(Token.T.EOF);
   }
}
