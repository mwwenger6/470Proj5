/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%%

%class Lexer
%byaccj
%line
%column

%{

  public Parser   parser;
  public int      lineno;
  public int      column;

  public Lexer(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
    this.lineno = 1;
    this.column = 1;
  }
  public int getLine() {
	  return yyline + 1; 
  }

  public int getColumn() {
    return yycolumn + 1; 
  }
%}

num          = [0-9]+("."[0-9]+)?
identifier   = [a-zA-Z][a-zA-Z0-9_]*
newline      = \n
whitespace   = [ \t\r]+
linecomment  = "//".*
blockcomment = "{"[^]*"}"

%%
"="                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.EQ         ; }
"<>"                                { parser.yylval = new ParserVal(new Token(yytext())); return Parser.NE         ; }
"<="                                { parser.yylval = new ParserVal(new Token(yytext())); return Parser.LE         ; }
"<"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.LT         ; }
">="                                { parser.yylval = new ParserVal(new Token(yytext())); return Parser.GE         ; }
">"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.GT         ; }

"+"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.ADD        ; }
"-"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.SUB        ; }
"*"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.MUL        ; }
"/"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.DIV        ; }
"%"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.MOD        ; }

"or"                                { parser.yylval = new ParserVal(new Token(yytext())); return Parser.OR         ; }
"and"                               { parser.yylval = new ParserVal(new Token(yytext())); return Parser.AND        ; }
"not"                               { parser.yylval = new ParserVal(new Token(yytext())); return Parser.NOT        ; }

"num"                               { parser.yylval = new ParserVal(new Token(yytext())); return Parser.NUM        ; }
"bool"                              { parser.yylval = new ParserVal(new Token(yytext())); return Parser.BOOL       ; }
"::"                                { parser.yylval = new ParserVal(new Token(yytext())); return Parser.TYPEOF        ; }

"func"                              { parser.yylval = new ParserVal(new Token(yytext())); return Parser.FUNC       ; }
"if"                                { parser.yylval = new ParserVal(new Token(yytext())); return Parser.IF         ; }
"then"                              { parser.yylval = new ParserVal(new Token(yytext())); return Parser.THEN       ; }
"else"                              { parser.yylval = new ParserVal(new Token(yytext())); return Parser.ELSE       ; }
"while"                             { parser.yylval = new ParserVal(new Token(yytext())); return Parser.WHILE      ; }
"print"                             { parser.yylval = new ParserVal(new Token(yytext())); return Parser.PRINT      ; }
"return"                            { parser.yylval = new ParserVal(new Token(yytext())); return Parser.RETURN     ; }

"begin"                             { parser.yylval = new ParserVal(new Token(yytext())); return Parser.BEGIN      ; }
"end"                               { parser.yylval = new ParserVal(new Token(yytext())); return Parser.END        ; }
"("                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.LPAREN     ; }
")"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.RPAREN     ; }
"["                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.LBRACKET      ; }
"]"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.RBRACKET      ; }

":="                                { parser.yylval = new ParserVal(new Token(yytext())); return Parser.ASSIGN     ; }
"var"                               { parser.yylval = new ParserVal(new Token(yytext())); return Parser.VAR        ; }
";"                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.SEMI       ; }
","                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.COMMA      ; }
"new"                               { parser.yylval = new ParserVal(new Token(yytext())); return Parser.NEW        ; }
"."                                 { parser.yylval = new ParserVal(new Token(yytext())); return Parser.DOT        ; }
"size"                              { parser.yylval = new ParserVal(new Token(yytext())); return Parser.SIZE       ; }


{num}                               { parser.yylval = new ParserVal(new Token(yytext())); return Parser.NUM_LIT    ; }
"true"|"false"                      { parser.yylval = new ParserVal(new Token(yytext())); return Parser.BOOL_LIT      ; }
{identifier}                        { parser.yylval = new ParserVal(new Token(yytext())); return Parser.IDENT      ; }

{linecomment}                       { /* skip */ }
{newline}                           { /* skip */ }
{whitespace}                        { /* skip */ }
{blockcomment}                      { /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
