import java.io.IOException;

public class MyParser extends Parser<Double> {

   public MyParser(Lexer lex) throws IOException {
      super(lex);
   }

   private Double E() throws IOException {
      switch (tok.type) {
         case LPAREN:
         case NUM:
            Double x = T();
            return Eresto(x);
         default:
            throw error(new Token.T[]{Token.T.LPAREN, Token.T.NUM});
      }
   }

   private Double Eresto(Double x) throws IOException {
      switch (tok.type) {
         case PLUS:
            eat(Token.T.PLUS);
            Double a = T();
            return Eresto(x + a);
         case MINUS:
            eat(Token.T.MINUS);
            Double b = T();
            return Eresto(x - b);
         case RPAREN:
         case EOF:
            return x;
         default:
            throw error(new Token.T[]{Token.T.PLUS, Token.T.MINUS, Token.T.RPAREN, Token.T.EOF});
      }
   }

   private Double T() throws IOException {
      switch (tok.type) {
         case LPAREN:
         case NUM:
            Double x = F();
            return Tresto(x);
         default:
            throw error(new Token.T[]{Token.T.LPAREN, Token.T.NUM});
      }
   }

   private Double Tresto(Double x) throws IOException {
      switch (tok.type) {
         case TIMES:
            eat(Token.T.TIMES);
            Double a = F();
            return Tresto(x * a);
         case DIV:
            eat(Token.T.DIV);
            Double b = F();
            return Tresto(x / b);
         case PLUS:
         case MINUS:
         case RPAREN:
         case EOF:
            return x;
         default:
            throw error(new Token.T[]{Token.T.PLUS, Token.T.MINUS, Token.T.TIMES, Token.T.DIV, Token.T.RPAREN, Token.T.EOF});
      }
   }

   private Double F() throws IOException {
      switch (tok.type) {
         case LPAREN:
            eat(Token.T.LPAREN);
            Double x = E();
            eat(Token.T.RPAREN);
            return x;
         case NUM:
            Double a = (Double) tok.val;
            eat(Token.T.NUM);
            return a;
         default:
            throw error(new Token.T[]{Token.T.LPAREN, Token.T.NUM});
      }
   }

   public Double parse() throws IOException {
      Double x = E();
      eat(Token.T.EOF);
      return x;
   }
}
