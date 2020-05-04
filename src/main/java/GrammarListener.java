// Generated from Grammar.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#my_rules}.
	 * @param ctx the parse tree
	 */
	void enterMy_rules(GrammarParser.My_rulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#my_rules}.
	 * @param ctx the parse tree
	 */
	void exitMy_rules(GrammarParser.My_rulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#my_rule}.
	 * @param ctx the parse tree
	 */
	void enterMy_rule(GrammarParser.My_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#my_rule}.
	 * @param ctx the parse tree
	 */
	void exitMy_rule(GrammarParser.My_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#end}.
	 * @param ctx the parse tree
	 */
	void enterEnd(GrammarParser.EndContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#end}.
	 * @param ctx the parse tree
	 */
	void exitEnd(GrammarParser.EndContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#new_line}.
	 * @param ctx the parse tree
	 */
	void enterNew_line(GrammarParser.New_lineContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#new_line}.
	 * @param ctx the parse tree
	 */
	void exitNew_line(GrammarParser.New_lineContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#eof}.
	 * @param ctx the parse tree
	 */
	void enterEof(GrammarParser.EofContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#eof}.
	 * @param ctx the parse tree
	 */
	void exitEof(GrammarParser.EofContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#start_nonterminal}.
	 * @param ctx the parse tree
	 */
	void enterStart_nonterminal(GrammarParser.Start_nonterminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#start_nonterminal}.
	 * @param ctx the parse tree
	 */
	void exitStart_nonterminal(GrammarParser.Start_nonterminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#nonterminal}.
	 * @param ctx the parse tree
	 */
	void enterNonterminal(GrammarParser.NonterminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#nonterminal}.
	 * @param ctx the parse tree
	 */
	void exitNonterminal(GrammarParser.NonterminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 */
	void enterTerminal(GrammarParser.TerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 */
	void exitTerminal(GrammarParser.TerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#extra_terminal}.
	 * @param ctx the parse tree
	 */
	void enterExtra_terminal(GrammarParser.Extra_terminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#extra_terminal}.
	 * @param ctx the parse tree
	 */
	void exitExtra_terminal(GrammarParser.Extra_terminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#symbol}.
	 * @param ctx the parse tree
	 */
	void enterSymbol(GrammarParser.SymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#symbol}.
	 * @param ctx the parse tree
	 */
	void exitSymbol(GrammarParser.SymbolContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#extra_symbol}.
	 * @param ctx the parse tree
	 */
	void enterExtra_symbol(GrammarParser.Extra_symbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#extra_symbol}.
	 * @param ctx the parse tree
	 */
	void exitExtra_symbol(GrammarParser.Extra_symbolContext ctx);
}