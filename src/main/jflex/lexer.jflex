/* -*-Mode: java-*- */

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
    
    private void error(String msg) {
    	ErrorMsg.error(yyline, yycolumn, "lexical error", msg);
    }
%}

%%

[ \t\f\n\r]+          { /* skip white spaces */ }
"#" .*                { /* skip comment */ }

"if"                  { return tok(Token.T.IF); }
"then"                { return tok(Token.T.THEN); }
"else"                { return tok(Token.T.ELSE); }
"while"               { return tok(Token.T.WHILE); }
"do"                  { return tok(Token.T.DO); }

"("                   { return tok(Token.T.LPAREN); }
")"                   { return tok(Token.T.RPAREN); }
"{"                   { return tok(Token.T.LBRACE); }
"}"                   { return tok(Token.T.RBRACE); }
";"                   { return tok(Token.T.SEMI); }

":="                  { return tok(Token.T.ASSIGN); }
"=="                  { return tok(Token.T.EQ); }
"+"                   { return tok(Token.T.PLUS); }
"*"                   { return tok(Token.T.MINUS); }

[0-9]+                { return tok(Token.T.NUM, new Long(yytext())); }
                        
[a-zA-Z][a-zA-Z0-9_]+ { return tok(Token.T.ID, yytext()); }
                        
<<EOF>>               { return tok(Token.T.EOF); }

.                     { error("illegal character: " + yytext()); }
