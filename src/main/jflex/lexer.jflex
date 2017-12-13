%%

%public
%class Lexer
%type Token
%function getToken
%line
%column

%{  
    private Token tok(Token.T typ, Object val) {
        return new Token(typ, val, yyline, yycolumn);
    }

    private Token tok(Token.T typ) {
        return tok(typ, null);
    }
    
    private CompilationError error(String format, Object... args) {
    	return CompilationError.error(
    	           yyline, yycolumn,
    	           "lexical error: %s",
    	           String.format(format, args));
    }
%}

%%

[ \t\f\n\r]+          { /* skip white spaces */ }
"#" .*                { /* skip comment */ }

"("                   { return tok(Token.T.LPAREN); }
")"                   { return tok(Token.T.RPAREN); }

"+"                   { return tok(Token.T.PLUS); }
"-"                   { return tok(Token.T.MINUS); }
"*"                   { return tok(Token.T.TIMES); }
"/"                   { return tok(Token.T.DIV); }

[0-9]+ ("." [0-9]+)?  { return tok(Token.T.NUM, new Double(yytext())); }
                        
<<EOF>>               { return tok(Token.T.EOF); }

.                     { throw error("illegal character: [%s]", yytext()); }
