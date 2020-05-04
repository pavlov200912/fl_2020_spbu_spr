// Generated from Grammar.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#my_rules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMy_rules(GrammarParser.My_rulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#my_rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMy_rule(GrammarParser.My_ruleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#end}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnd(GrammarParser.EndContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#new_line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNew_line(GrammarParser.New_lineContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#eof}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEof(GrammarParser.EofContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#start_nonterminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart_nonterminal(GrammarParser.Start_nonterminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#nonterminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonterminal(GrammarParser.NonterminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal(GrammarParser.TerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#extra_terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtra_terminal(GrammarParser.Extra_terminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#symbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol(GrammarParser.SymbolContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#extra_symbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtra_symbol(GrammarParser.Extra_symbolContext ctx);
}