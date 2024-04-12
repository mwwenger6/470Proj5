import java.util.*;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class ParserImpl
{
    public static Boolean _debug = true;
    void Debug(String message)
    {
        if(_debug)
            System.out.println(message);
    }

    // This is for chained symbol table.
    // This includes the global scope only at this moment.
    Env env = new Env(null);
    // this stores the root of parse tree, which will be used to print parse tree and run the parse tree
    ParseTree.Program parsetree_program = null;

    Object program____decllist(Object s1) throws Exception
    {
        // 1. check if decllist has main function having no parameters and returns int type
        // 2. assign the root, whose type is ParseTree.Program, to parsetree_program
        ArrayList<ParseTree.FuncDecl> decllist = (ArrayList<ParseTree.FuncDecl>)s1;
        parsetree_program = new ParseTree.Program(decllist);
        return parsetree_program;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object decllist____decllist_decl(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.FuncDecl> decllist = (ArrayList<ParseTree.FuncDecl>)s1;
        ParseTree.FuncDecl                decl = (ParseTree.FuncDecl           )s2;
        decllist.add(decl);
        return decllist;
    }
    Object decllist____eps() throws Exception
    {
        return new ArrayList<ParseTree.FuncDecl>();
    }
    Object decl____funcdecl(Object s1) throws Exception
    {
        return s1;
    }
    Object primtype____NUM(Object s1) throws Exception
    {
        ParseTree.TypeSpec typespec = new ParseTree.TypeSpec("num");
        return typespec;
    }
    Object typespec____primtype(Object s1)
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s1;
        return primtype;
    }
    public Object param____ident_typeof_typespec(Object s1, Object s3) throws Exception {
        String ident = ((Token)s1).lexeme; // Extract identifier
        ParseTree.TypeSpec typespec = (ParseTree.TypeSpec)s3; // Already a TypeSpec object
        return new ParseTree.Param(ident, typespec);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_10X_stmtlist_END(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7, Object s8, Object s9) throws Exception
    {
        // 1. add function_type_info object (name, return type, params) into the global scope of env
        // 2. create a new symbol table on top of env
        // 3. add parameters into top-local scope of env
        // 4. etc.
        return null;
    }
    Object fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_X10_stmtlist_END(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7, Object s8, Object s9, Object s10, Object s11, Object s12) throws Exception
    {
        // 1. check if this function has at least one return type
        // 2. etc.
        // 3. create and return funcdecl node
        Token                            id         = (Token                           )s2;
        ParseTree.TypeSpec               rettype    = (ParseTree.TypeSpec              )s4;
        ArrayList<ParseTree.Param>       params     = (ArrayList<ParseTree.Param>      )s6;
        ArrayList<ParseTree.LocalDecl>   localdecls = (ArrayList<ParseTree.LocalDecl>  )s9;
        ArrayList<ParseTree.Stmt>        stmtlist   = (ArrayList<ParseTree.Stmt>       )s11;
        Token                            end        = (Token                           )s12;
        ParseTree.FuncDecl funcdecl = new ParseTree.FuncDecl(id.lexeme, rettype, params, localdecls, stmtlist);
        return funcdecl;
    }

    Object params____eps() throws Exception 
    {
        return new ArrayList<ParseTree.Param>();
    }

    Object stmtlist____stmtlist_stmt(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.Stmt> stmtlist = (ArrayList<ParseTree.Stmt>)s1;
        ParseTree.Stmt            stmt     = (ParseTree.Stmt           )s2;
        stmtlist.add(stmt);
        return stmtlist;
    }
    Object stmtlist____eps() throws Exception
    {
        return new ArrayList<ParseTree.Stmt>();
    }

    Object stmt____assignstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.AssignStmt);
        return s1;
    }
    Object stmt____returnstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.ReturnStmt);
        return s1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object assignstmt____IDENT_ASSIGN_expr_SEMI(Object s1, Object s2, Object s3, Object s4) throws Exception
    {
        // 1. check if ident.value_type matches with expr.value_type
        // 2. etc.
        // e. create and return node
        Token          id     = (Token         )s1;
        Token          assign = (Token         )s2;
        ParseTree.Expr expr   = (ParseTree.Expr)s3;
        Object id_type = env.Get(id.lexeme);
        // {
        //     // check if expr.type matches with id_type
        //     if(id_type.equals("num")
        //         && (expr instanceof ParseTree.ExprNumLit)
        //         )
        //         {} // ok
        //     else if(id_type.equals("num")
        //         && (expr instanceof ParseTree.ExprFuncCall)
        //         && (env.Get(((ParseTree.ExprFuncCall)expr).ident).equals("num()"))
        //         )
        //     {} // ok
        //     else
        //     {
        //         throw new Exception("semantic error");
        //     }
        // }
        ParseTree.AssignStmt stmt = new ParseTree.AssignStmt(id.lexeme, expr);
        stmt.ident_reladdr = 1;
        return stmt;
    }
    Object returnstmt____RETURN_expr_SEMI(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr.value_type matches with the current function return type
        // 2. etc.
        // 3. create and return node
        ParseTree.Expr expr = (ParseTree.Expr)s2;
        return new ParseTree.ReturnStmt(expr);
    }
    public Object ifstmt____IF_expr_THEN_stmtlist_ELSE_stmtlist_END(Object s1, Object s3, Object s5) throws Exception {
        ParseTree.Expr cond = (ParseTree.Expr)s1; // s1 is the condition expression
        ArrayList<ParseTree.Stmt> thenStmtList = (ArrayList<ParseTree.Stmt>)s3; // s3 is the list of statements for the "then" part
        ArrayList<ParseTree.Stmt> elseStmtList = (ArrayList<ParseTree.Stmt>)s5; // s5 is the list of statements for the "else" part
        return new ParseTree.IfStmt(cond, thenStmtList, elseStmtList);
    }
    public Object whilestmt____WHILE_expr_BEGIN_stmtlist_END(Object s1, Object s3) throws Exception {
        ParseTree.Expr cond = (ParseTree.Expr)s1; // s1 is the condition expression
        ArrayList<ParseTree.Stmt> stmtList = (ArrayList<ParseTree.Stmt>)s3; // s3 is the list of statements to execute in the loop
        return new ParseTree.WhileStmt(cond, stmtList);
    }
    public Object compoundstmt____BEGIN_localdecls_stmtlist_END(Object s1, Object s2) throws Exception {
        ArrayList<ParseTree.LocalDecl> localDecls = (ArrayList<ParseTree.LocalDecl>)s1; // s1 is the list of local declarations
        ArrayList<ParseTree.Stmt> stmtList = (ArrayList<ParseTree.Stmt>)s2; // s2 is the list of statements within the block
        return new ParseTree.CompoundStmt(localDecls, stmtList);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object localdecls____localdecls_localdecl(Object s1, Object s2)
    {
        ArrayList<ParseTree.LocalDecl> localdecls = (ArrayList<ParseTree.LocalDecl>)s1;
        ParseTree.LocalDecl            localdecl  = (ParseTree.LocalDecl           )s2;
        localdecls.add(localdecl);
        return localdecls;
    }
    Object localdecls____eps() throws Exception
    {
        return new ArrayList<ParseTree.LocalDecl>();
    }
    Object localdecl____VAR_IDENT_TYPEOF_typespec_SEMI(Object s1, Object s2, Object s3, Object s4, Object s5)
    {
        Token              id       = (Token             )s2;
        ParseTree.TypeSpec typespec = (ParseTree.TypeSpec)s4;
        ParseTree.LocalDecl localdecl = new ParseTree.LocalDecl(id.lexeme, typespec);
        localdecl.reladdr = 1;
        return localdecl;
    }
    Object args____eps() throws Exception
    {
        return new ArrayList<ParseTree.Expr>();
    }
    public Object printstmt____PRINT_expr_SEMI(Object s1) throws Exception {
        ParseTree.Expr expr = (ParseTree.Expr)s1; // s1 is an expression node
        return new ParseTree.PrintStmt(expr);
    }
    public Object expr____expr_AND_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprAnd(expr1, expr2);
    }
    public Object expr____expr_OR_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprOr(expr1, expr2);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object expr____expr_ADD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        if(expr1.info.getType() != expr2.info.getType())
            throw new Exception("ERROR.");
        
        return new ParseTree.ExprAdd(expr1,expr2);
    }
    Object expr____expr_EQ_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        return new ParseTree.ExprEq(expr1,expr2);
    }
    public Object expr____expr_NE_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprNe(expr1, expr2);
    }
    public Object expr____expr_LE_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprLe(expr1, expr2);
    }
    public Object expr____expr_LT_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprLt(expr1, expr2);
    }
    public Object expr____expr_GE_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprGe(expr1, expr2);
    }
    public Object expr____expr_GT_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprGt(expr1, expr2);
    }
    Object expr____LPAREN_expr_RPAREN(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. create and return node whose value_type is the same to the expr.value_type
        Token          lparen = (Token         )s1;
        ParseTree.Expr expr   = (ParseTree.Expr)s2;
        Token          rparen = (Token         )s3;
        return new ParseTree.ExprParen(expr);
    }
    Object expr____IDENT(Object s1) throws Exception
    {
        // 1. check if id.lexeme can be found in chained symbol tables
        // 2. check if it is variable type
        // 3. etc.
        // 4. create and return node that has the value_type of the id.lexeme
        Token id = (Token)s1;
        ParseTree.ExprIdent expr = new ParseTree.ExprIdent(id.lexeme);
        expr.reladdr = 1;
        return expr;
    }
    Object expr____IDENT_LPAREN_args_RPAREN(Object s1, Object s2, Object s3, Object s4) throws Exception
    {
        // 1. check if id.lexeme can be found in chained symbol tables
        // 2. check if it is function type
        // 3. check if the number and types of env(id.lexeme).params match with those of args
        // 4. etc.
        // 5. create and return node that has the value_type of env(id.lexeme).return_type
        Token                    id   = (Token                   )s1;
        ArrayList<ParseTree.Arg> args = (ArrayList<ParseTree.Arg>)s3;
        Object func_attr = env.Get(id.lexeme);
        // {
        //     // check if argument types match with function param types
        //     if(env.Get(id.lexeme).equals("num()")
        //         && (args.size() == 0)
        //         )
        //     {} // ok
        //     else
        //     {
        //         throw new Exception("semantic error");
        //     }
        // }
        return new ParseTree.ExprFuncCall(id.lexeme, args);
    }
    public Object expr____BOOL_LIT(Object s1) throws Exception {
        Token token = (Token)s1; // Token containing boolean literal
        boolean value = Boolean.parseBoolean(token.lexeme);
        return new ParseTree.ExprBoolLit(value);
    }
    Object expr____NUMLIT(Object s1) throws Exception
    {
        // 1. create and return node that has int type
        Token token = (Token)s1;
        double value = Double.parseDouble(token.lexeme);
        return new ParseTree.ExprNumLit(value);
    }
    public Object expr____NEW_primtype_LBRACKET_expr_RBRACKET(Object s2, Object s4) throws Exception {
        ParseTree.TypeSpec elemType = (ParseTree.TypeSpec)s2; // Element type of the array
        ParseTree.Expr sizeExpr = (ParseTree.Expr)s4; // Size expression
        return new ParseTree.ExprNewArray(elemType, sizeExpr);
    }
    public Object expr____IDENT_LBRACKET_expr_RBRACKET(Object s1, Object s3) throws Exception {
        String ident = ((Token)s1).lexeme; // Array identifier
        ParseTree.Expr indexExpr = (ParseTree.Expr)s3; // Index expression
        return new ParseTree.ExprArrayElem(ident, indexExpr);
    }
    public Object expr____IDENT_DOT_SIZE(Object s1) throws Exception {
        String ident = ((Token)s1).lexeme; // Array identifier
        return new ParseTree.ExprArraySize(ident);
    }
    public Object expr____expr_SUB_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprSub(expr1, expr2);
    }
    public Object expr____expr_MUL_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprMul(expr1, expr2);
    }
    public Object expr____expr_DIV_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprDiv(expr1, expr2);
    }
    public Object expr____expr_MOD_expr(Object s1, Object s3) throws Exception {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1; // Left operand
        ParseTree.Expr expr2 = (ParseTree.Expr)s3; // Right operand
        return new ParseTree.ExprMod(expr1, expr2);
    }
    public Object expr____NOT_expr(Object s2) throws Exception {
        ParseTree.Expr expr = (ParseTree.Expr)s2; // Operand
        return new ParseTree.ExprNot(expr);
    }
    // Handling parameter lists
    public Object params____paramlist(Object s1) throws Exception {
        return s1; // Simply return the passed parameter list
    }

    // Adding a parameter to a parameter list
    public Object paramlist____paramlist_comma_param(Object s1, Object s2) throws Exception {
        @SuppressWarnings("unchecked")
        ArrayList<ParseTree.Param> paramList = (ArrayList<ParseTree.Param>)s1;
        ParseTree.Param param = (ParseTree.Param)s2;
        paramList.add(param);
        return paramList;
    }

    // Creating a new parameter list with a single parameter
    public Object paramlist____param(Object s1) throws Exception {
        ArrayList<ParseTree.Param> paramList = new ArrayList<>();
        paramList.add((ParseTree.Param)s1);
        return paramList;
    }

    // Type specification for array types
    public Object typespec____primtype_array(Object s1) throws Exception {
        ParseTree.TypeSpec elemType = (ParseTree.TypeSpec)s1;
        // Handle as you see fit for arrays; assuming array type extends or wraps elemType
        return new ParseTree.TypeSpec(elemType.typename + "[]");
    }

    // Type specification for boolean
    Object primtype____BOOL(Object s1) throws Exception {
        return new ParseTree.TypeSpec("bool");
    }

    // Print statement
    Object stmt____printstmt(Object s1) throws Exception {
        return s1;
    }

    // If statement
    Object stmt____ifstmt(Object s1) throws Exception {
        return s1; // Assuming s1 is an already constructed IfStmt object
    }

    // While statement
    public Object stmt____whilestmt(Object s1) throws Exception {
        return s1; // Assuming s1 is an already constructed WhileStmt object
    }

    // Compound statement
    public Object stmt____compoundstmt(Object s1) throws Exception {
        return s1; // Assuming s1 is an already constructed CompoundStmt object
    }

    // Assigning to an array element
    public Object assignstmt____IDENT_LBRACKET_expr_RBRACKET_ASSIGN_expr_SEMI(Object s1, Object s3, Object s5) throws Exception {
        Token id = (Token)s1;
        ParseTree.Expr index = (ParseTree.Expr)s3;
        ParseTree.Expr value = (ParseTree.Expr)s5;
        return new ParseTree.AssignStmtForArray(id.lexeme, index, value);
    }

    // Adding an argument to an argument list
    public Object arglist____arglist_comma_expr(Object s1, Object s3) throws Exception {
        @SuppressWarnings("unchecked")
        ArrayList<ParseTree.Arg> argList = (ArrayList<ParseTree.Arg>)s1;
        ParseTree.Expr expr = (ParseTree.Expr)s3;
        argList.add(new ParseTree.Arg(expr));
        return argList;
    }

    // Creating a new argument list with a single expression
    public Object arglist____expr(Object s1) throws Exception {
        ArrayList<ParseTree.Arg> argList = new ArrayList<>();
        ParseTree.Expr expr = (ParseTree.Expr)s1;
        argList.add(new ParseTree.Arg(expr));
        return argList;
    }

}