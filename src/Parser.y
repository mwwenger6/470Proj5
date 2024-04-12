/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2001 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * This is a modified version of the example from                          *
 *   http://www.lincom-asg.com/~rjamison/byacc/                            *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
import java.io.*;
%}

%right  ASSIGN
%left   OR
%left   AND
%right  NOT
%left   EQ      NE
%left   LE      LT      GE      GT
%left   ADD     SUB
%left   MUL     DIV     MOD

%token <obj>    EQ   NE   LE   LT   GE   GT
%token <obj>    ADD  SUB  MUL  DIV  MOD
%token <obj>    OR   AND  NOT

%token <obj>    IDENT     NUM_LIT   BOOL_LIT

%token <obj> BOOL  NUM  TYPEOF
%token <obj> FUNC  IF  THEN  ELSE  WHILE  PRINT  RETURN
%token <obj> BEGIN  END  LPAREN  RPAREN  LBRACKET  RBRACKET
%token <obj> ASSIGN  VAR  SEMI  COMMA  NEW  DOT  SIZE


%type <obj> program   decl_list  decl
%type <obj> func_decl  local_decls  local_decl  type_spec  prim_type
%type <obj> params  param_list  param  args  arg_list
%type <obj> stmt_list  stmt  assign_stmt  print_stmt  return_stmt  if_stmt  while_stmt  compound_stmt     
%type <obj> expr

%%
program         : decl_list                                     { $$ = program____decllist($1); }
                ;

decl_list       : decl_list decl                                { $$ = decllist____decllist_decl($1, $2); }
                |                                               { $$ = decllist____eps(); }
                ;

decl            : func_decl                                     { $$ = decl____funcdecl($1); }
                ;

func_decl       : FUNC IDENT TYPEOF type_spec LPAREN params RPAREN BEGIN local_decls{$<obj>$ = fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_10X_stmtlist_END($1,$2,$3,$4,$5,$6,$7,$8,$9            ); }
                                                                       stmt_list END{$$ =      fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_X10_stmtlist_END($1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12); }
                ;

params          : param_list                                   { $$ = params____paramlist($1); }
                |                                              { $$ = params____eps(); }
                ;

param_list      : param_list COMMA param                       { $$ = paramlist____paramlist_comma_param($1, $3); }
                | param                                        { $$ = paramlist____param($1); }
                ;                

param           : IDENT TYPEOF type_spec                       { $$ = param____ident_typeof_typespec($1, $3); }
                ;

type_spec       : prim_type                                     { $$ = typespec____primtype($1); }
                | prim_type LBRACKET RBRACKET                   { $$ = typespec____primtype_array($1); }
                ;

prim_type       : NUM                                           { $$ = primtype____NUM($1); }
                | BOOL                                          { $$ = primtype____BOOL($1); }
                ;

local_decls     : local_decls  local_decl                       { $$ = localdecls____localdecls_localdecl($1,$2); }
                |                                               { $$ = localdecls____eps(); }
                ;

local_decl      : VAR  IDENT  TYPEOF  type_spec  SEMI           { $$ = localdecl____VAR_IDENT_TYPEOF_typespec_SEMI($1,$2,$3,$4,$5); }
                ;

stmt_list       : stmt_list stmt                                { $$ = stmtlist____stmtlist_stmt($1,$2); }
                |                                               { $$ = stmtlist____eps(); }
                ;

stmt            : assign_stmt                                   { $$ = stmt____assignstmt  ($1); }
                | print_stmt                                    { $$ = stmt____printstmt($1); }
                | return_stmt                                   { $$ = stmt____returnstmt  ($1); }
                | if_stmt                                       { $$ = stmt____ifstmt($1); }
                | while_stmt                                    { $$ = stmt____whilestmt($1); }
                | compound_stmt                                 { $$ = stmt____compoundstmt($1); }
                ;

assign_stmt     : IDENT ASSIGN expr SEMI                        { $$ = assignstmt____IDENT_ASSIGN_expr_SEMI($1,$2,$3,$4); }
                | IDENT LBRACKET expr RBRACKET ASSIGN expr SEMI { $$ = assignstmt____IDENT_LBRACKET_expr_RBRACKET_ASSIGN_expr_SEMI($1, $3, $6); }
                ;

print_stmt      : PRINT expr SEMI                               { $$ = printstmt____PRINT_expr_SEMI($2); }
                ;

return_stmt     : RETURN expr SEMI                              { $$ = returnstmt____RETURN_expr_SEMI($1,$2,$3); }
                ;

if_stmt         : IF expr THEN stmt_list ELSE stmt_list END     { $$ = ifstmt____IF_expr_THEN_stmtlist_ELSE_stmtlist_END($2, $4, $6); }
                ;

while_stmt      : WHILE expr BEGIN stmt_list END                { $$ = whilestmt____WHILE_expr_BEGIN_stmtlist_END($2, $4); }
                ;

compound_stmt   : BEGIN local_decls stmt_list END               { $$ = compoundstmt____BEGIN_localdecls_stmtlist_END($2, $3); }
                ;               

args            : arg_list
                |                                               { $$ = args____eps(); }
                ;

arg_list        : arg_list COMMA expr                           { $$ = arglist____arglist_comma_expr($1, $3); }
                | expr                                          { $$ = arglist____expr($1); }
                ;

expr            : expr ADD expr                                 { $$ = expr____expr_ADD_expr($1,$2,$3); }
                | expr SUB expr                                 { $$ = expr____expr_SUB_expr($1,$2, $3); }
                | expr MUL expr                                 { $$ = expr____expr_MUL_expr($1,$2, $3); }
                | expr DIV expr                                 { $$ = expr____expr_DIV_expr($1,$2, $3); }
                | expr MOD expr                                 { $$ = expr____expr_MOD_expr($1,$2, $3); }
                | expr EQ  expr                                 { $$ = expr____expr_EQ_expr($1,$2,$3); }
                | expr NE  expr                                 { $$ = expr____expr_NE_expr($1,$2, $3); }
                | expr LE  expr                                 { $$ = expr____expr_LE_expr($1,$2, $3); }
                | expr LT  expr                                 { $$ = expr____expr_LT_expr($1,$2, $3); }
                | expr GE  expr                                 { $$ = expr____expr_GE_expr($1,$2, $3); }
                | expr GT  expr                                 { $$ = expr____expr_GT_expr($1,$2, $3); }
                | expr AND expr                                 { $$ = expr____expr_AND_expr($1,$2, $3); }
                | expr OR expr                                  { $$ = expr____expr_OR_expr($1,$2, $3); }
                | NOT expr                                      { $$ = expr____NOT_expr($1, $2); }
                | LPAREN expr RPAREN                            { $$ = expr____LPAREN_expr_RPAREN($1,$2,$3); }
                | IDENT                                         { $$ = expr____IDENT($1); }
                | NUM_LIT                                       { $$ = expr____NUMLIT($1);}
                | BOOL_LIT                                      { $$ = expr____BOOL_LIT($1); }
                | IDENT LPAREN args RPAREN                      { $$ = expr____IDENT_LPAREN_args_RPAREN($1,$2,$3,$4); }
                | NEW prim_type LBRACKET expr RBRACKET          { $$ = expr____NEW_primtype_LBRACKET_expr_RBRACKET($2, $4); }
                | IDENT LBRACKET expr RBRACKET                  { $$ = expr____IDENT_LBRACKET_expr_RBRACKET($1, $3); }
                | IDENT DOT SIZE                                { $$ = expr____IDENT_DOT_SIZE($1); }
                ;


%%
    private Lexer lexer;
    private Token last_token;

    private int yylex () {
        int yyl_return = -1;
        try {
            yylval = new ParserVal(0);
            yyl_return = lexer.yylex();
            last_token = (Token)yylval.obj;
        }
        catch (IOException e) {
            System.out.println("IO error :"+e);
        }
        return yyl_return;
    }


    public void yyerror (String error) {
        //System.out.println ("Error message for " + lexer.lineno+":"+lexer.column +" by Parser.yyerror(): " + error);
        int last_token_lineno = 0;
        int last_token_column = 0;
        System.out.println ("Error message by Parser.yyerror() at near " + last_token_lineno+":"+last_token_column + ": " + error);
    }


    public Parser(Reader r, boolean yydebug) {
        this.lexer   = new Lexer(r, this);
        this.yydebug = yydebug;
    }
