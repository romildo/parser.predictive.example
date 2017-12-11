
import java_cup.runtime.Symbol;
import javaslang.collection.List;
import org.assertj.core.api.JUnitSoftAssertions;
import parse.Lexer;
import parse.Terminals;
import parse.Tokens;

import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class LexerTest {

  private String run(String input) throws IOException {
    Lexer lexer = new Lexer("test", new StringReader(input));
    Symbol token;
    StringBuilder builder = new StringBuilder();
    List<String> list = List.empty();
    do {
      token = lexer.next_token();
      builder.append(Tokens.dumpToken(token)).append('\n');
      list = list.append(Tokens.dumpToken(token));
    } while (token.sym != Terminals.EOF);
    return builder.toString();
    //return list;
  }

  private void trun(String input, String... output) throws IOException {
    StringBuilder builder = new StringBuilder();
    for (String x : output)
      builder.append(x).append('\n');
    softly.assertThat(run(input))
          .as("%s", input)
          .isEqualTo(builder.toString());
  }

  private void erun(String input, String message) throws IOException {
    softly.assertThatThrownBy(() -> run(input))
          .as("%s", input)
          .isInstanceOf(errormsg.Error.class)
          .hasToString(message);
  }

  @Rule
  public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

  @Test
  public void lexerTest1() throws IOException {

    // whitespaces
    trun("    \t\n\n\n\t\r\n\r\n  ", "6:3-6:3 EOF");

    // comments
    trun("// a comment\n", "2:1-2:1 EOF");
    trun("// a comment\n", "2:1-2:1 EOF");
    trun("/* a block comment */\n", "2:1-2:1 EOF");
    trun("/*multiline\ncomment\n*/\n", "4:1-4:1 EOF");
    trun("/* outer /* inner */ outer */\n", "2:1-2:1 EOF");
    erun("/* outer /* inner /* inside */ inner again\n", "test-2:1-2:1: lexical error: unclosed comment");

//    // punctuation
//    softly.assertThat(run(":"))  .isEqualTo("1:1-1:3 COLON\n1:3-1:3 EOF\n");
//    softly.assertThat(run("="))  .isEqualTo("1:1-1:3 EQ\n1:3-1:3 EOF\n");
//    softly.assertThat(run("("))  .isEqualTo("1:1-1:3 LPAREN\n1:3-1:3 EOF\n");
//    softly.assertThat(run(")"))  .isEqualTo("1:1-1:3 RPAREN\n1:3-1:3 EOF\n");
//    softly.assertThat(run("["))  .isEqualTo("1:1-1:3 LBRACK\n1:3-1:3 EOF\n");
//    softly.assertThat(run("]"))  .isEqualTo("1:1-1:3 RBRACK\n1:3-1:3 EOF\n");
//    softly.assertThat(run("{"))  .isEqualTo("1:1-1:3 LBRACE\n1:3-1:3 EOF\n");
//    softly.assertThat(run("}"))  .isEqualTo("1:1-1:3 RBRACE\n1:3-1:3 EOF\n");
//    softly.assertThat(run(","))  .isEqualTo("1:1-1:3 COMMA\n1:3-1:3 EOF\n");
//    softly.assertThat(run(";"))  .isEqualTo("1:1-1:3 SEMI\n1:3-1:3 EOF\n");
//    softly.assertThat(run(":=")) .isEqualTo("1:1-1:3 ASSIGN\n1:3-1:3 EOF\n");
//
//    // operators
//    softly.assertThat(run("+"))  .isEqualTo("1:1-1:3 PLUS\n1:3-1:3 EOF\n");
//    softly.assertThat(run("-"))  .isEqualTo("1:1-1:3 MINUS\n1:3-1:3 EOF\n");
//    softly.assertThat(run("*"))  .isEqualTo("1:1-1:3 TIMES\n1:3-1:3 EOF\n");
//    softly.assertThat(run("/"))  .isEqualTo("1:1-1:3 DIV\n1:3-1:3 EOF\n");
//    softly.assertThat(run("%"))  .isEqualTo("1:1-1:3 MOD\n1:3-1:3 EOF\n");
//    softly.assertThat(run("^"))  .isEqualTo("1:1-1:3 POWER\n1:3-1:3 EOF\n");
//    softly.assertThat(run("<>")) .isEqualTo("1:1-1:3 NEQ\n1:3-1:3 EOF\n");
//    softly.assertThat(run("<"))  .isEqualTo("1:1-1:3 LT\n1:3-1:3 EOF\n");
//    softly.assertThat(run("<=")) .isEqualTo("1:1-1:3 LE\n1:3-1:3 EOF\n");
//    softly.assertThat(run(">"))  .isEqualTo("1:1-1:3 GT\n1:3-1:3 EOF\n");
//    softly.assertThat(run(">=")) .isEqualTo("1:1-1:3 GE\n1:3-1:3 EOF\n");
//    softly.assertThat(run("@"))  .isEqualTo("1:1-1:3 AT\n1:3-1:3 EOF\n");
//
//    // boolean literals
//    softly.assertThat(run("true"))  .isEqualTo("1:1-1:3 BOOLLIT(true)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("false")) .isEqualTo("1:1-1:3 BOOLLIT(false)\n1:3-1:3 EOF\n");
//
//    // integer literals
//    softly.assertThat(run("26342"))  .isEqualTo("1:1-1:3 INTLIT(26342)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("0"))      .isEqualTo("1:1-1:3 INTLIT(0)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("000234")) .isEqualTo("1:1-1:3 INTLIT(234)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("19ok"))   .isEqualTo("1:1-1:3 INTLIT(19)\n1:3-1:3 EOF\n");
//
//    // real literals
//    softly.assertThat(run("12.345"))   .isEqualTo("1:1-1:3 REALLIT(12.345)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("1234."))    .isEqualTo("1:1-1:3 REALLIT(1234.0)\n1:3-1:3 EOF\n");
//    softly.assertThat(run(".21"))      .isEqualTo("1:1-1:3 REALLIT(0.21)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("1.2e100"))  .isEqualTo("1:1-1:3 REALLIT(1.2E100)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("1.E+100"))  .isEqualTo("1:1-1:3 REALLIT(1.0E100)\n1:3-1:3 EOF\n");
//    softly.assertThat(run(".55e-100")) .isEqualTo("1:1-1:3 REALLIT(0.55E-100)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("2106E2"))   .isEqualTo("1:1-1:3 REALLIT(210600)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("56e-234"))  .isEqualTo("1:1-1:3 REALLIT(56E-234)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("710E+18"))  .isEqualTo("1:1-1:3 REALLIT(710E18)\n1:3-1:3 EOF\n");
//
//    // char literals
//    softly.assertThat(run("'A'")).isEqualTo("1:1-1:3 CHARLIT(A)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("'b'")).isEqualTo("1:1-1:3 CHARLIT(b)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("'*'")).isEqualTo("1:1-1:3 CHARLIT(*)\n1:3-1:3 EOF\n");
//
//    // string literals
//    softly.assertThat(run("\"A\"")).isEqualTo("1:1-1:3 STRINGLIT(A)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("\"b\"")).isEqualTo("1:1-1:3 STRINGLIT(b)\n1:3-1:3 EOF\n");
//    softly.assertThat(run("\"*\"")).isEqualTo("1:1-1:3 STRINGLIT(*)\n1:3-1:3 EOF\n");
//
//    // keywords
//    softly.assertThat(run("if"))       .isEqualTo("1:1-1:3 IF\n1:5-1:5 EOF\n");
//    softly.assertThat(run("then"))     .isEqualTo("1:1-1:3 THEN\n1:3-1:3 EOF\n");
//    softly.assertThat(run("else"))     .isEqualTo("1:1-1:3 ELSE\n1:3-1:3 EOF\n");
//    softly.assertThat(run("let"))      .isEqualTo("1:1-1:3 LET\n1:3-1:3 EOF\n");
//    softly.assertThat(run("in"))       .isEqualTo("1:1-1:3 IN\n1:3-1:3 EOF\n");
//    softly.assertThat(run("var"))      .isEqualTo("1:1-1:3 VAR\n1:3-1:3 EOF\n");
//    softly.assertThat(run("function")) .isEqualTo("1:1-1:3 FUNCTION\n1:3-1:3 EOF\n");
//    softly.assertThat(run("type"))     .isEqualTo("1:1-1:3 TYPE\n1:3-1:3 EOF\n");
//
//    // identifiers
//    softly.assertThat(run("nome")).isEqualTo("1:1-1:3 IF\n1:3-1:3 EOF\n");
//    softly.assertThat(run("camelCase")).isEqualTo("1:1-1:3 IF\n1:3-1:3 EOF\n");
//    softly.assertThat(run("with_underscore")).isEqualTo("1:1-1:3 IF\n1:3-1:3 EOF\n");
//    softly.assertThat(run("A1B2C33")).isEqualTo("1:1-1:3 IF\n1:3-1:3 EOF\n");
//    //softly.assertThat(run("x'")).isEqualTo("1:1-1:3 IF\n1:3-1:3 EOF\n");
//    softly.assertThat(run("45var")).isEqualTo("1:1-1:3 IF\n1:3-1:3 EOF\n");
//    //softly.assertThat(run("_invalid")).isEqualTo("1:1-1:3 IF\n1:3-1:3 EOF\n");

  }

}
