// Generated from C.g4 by ANTLR 4.5.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#incList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncList(CParser.IncListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#directiveDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirectiveDefinition(CParser.DirectiveDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#declList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclList(CParser.DeclListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#funcList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncList(CParser.FuncListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(CParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#identList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentList(CParser.IdentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(CParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(CParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(CParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#compoundStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStmt(CParser.CompoundStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#stmtList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtList(CParser.StmtListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(CParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#assignStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(CParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(CParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#callStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStmt(CParser.CallStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(CParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#retStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetStmt(CParser.RetStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(CParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(CParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(CParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#switchStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStmt(CParser.SwitchStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#caseList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseList(CParser.CaseListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(CParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(CParser.ArgListContext ctx);
}