// Generated from Grammar.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, NAME=2, EXTRA=3, ANY=4, WS=5;
	public static final int
		RULE_my_rules = 0, RULE_my_rule = 1, RULE_start_nonterminal = 2, RULE_nonterminal = 3, 
		RULE_terminal = 4, RULE_extra_terminal = 5, RULE_symbol = 6, RULE_extra_symbol = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"my_rules", "my_rule", "start_nonterminal", "nonterminal", "terminal", 
			"extra_terminal", "symbol", "extra_symbol"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, "NAME", "EXTRA", "ANY", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


    @Override
    public void notifyErrorListeners(Token offendingToken, String msg, RecognitionException ex) {

        throw new ParseException("(line: " + offendingToken.getLine() +
                ", pos: " + offendingToken.getCharPositionInLine()
                + ") message: " + msg);
    }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class My_rulesContext extends ParserRuleContext {
		public My_ruleContext my_rule() {
			return getRuleContext(My_ruleContext.class,0);
		}
		public My_rulesContext my_rules() {
			return getRuleContext(My_rulesContext.class,0);
		}
		public My_rulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_my_rules; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterMy_rules(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitMy_rules(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitMy_rules(this);
			else return visitor.visitChildren(this);
		}
	}

	public final My_rulesContext my_rules() throws RecognitionException {
		return my_rules(0);
	}

	private My_rulesContext my_rules(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		My_rulesContext _localctx = new My_rulesContext(_ctx, _parentState);
		My_rulesContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_my_rules, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(17);
			my_rule();
			}
			_ctx.stop = _input.LT(-1);
			setState(23);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new My_rulesContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_my_rules);
					setState(19);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(20);
					my_rule();
					}
					} 
				}
				setState(25);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class My_ruleContext extends ParserRuleContext {
		public Start_nonterminalContext start_nonterminal() {
			return getRuleContext(Start_nonterminalContext.class,0);
		}
		public List<NonterminalContext> nonterminal() {
			return getRuleContexts(NonterminalContext.class);
		}
		public NonterminalContext nonterminal(int i) {
			return getRuleContext(NonterminalContext.class,i);
		}
		public List<TerminalContext> terminal() {
			return getRuleContexts(TerminalContext.class);
		}
		public TerminalContext terminal(int i) {
			return getRuleContext(TerminalContext.class,i);
		}
		public List<Extra_terminalContext> extra_terminal() {
			return getRuleContexts(Extra_terminalContext.class);
		}
		public Extra_terminalContext extra_terminal(int i) {
			return getRuleContext(Extra_terminalContext.class,i);
		}
		public My_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_my_rule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterMy_rule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitMy_rule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitMy_rule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final My_ruleContext my_rule() throws RecognitionException {
		My_ruleContext _localctx = new My_ruleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_my_rule);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			start_nonterminal();
			setState(27);
			match(T__0);
			setState(31); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(31);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case NAME:
						{
						setState(28);
						nonterminal();
						}
						break;
					case ANY:
						{
						setState(29);
						terminal();
						}
						break;
					case EXTRA:
						{
						setState(30);
						extra_terminal();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(33); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Start_nonterminalContext extends ParserRuleContext {
		public NonterminalContext nonterminal() {
			return getRuleContext(NonterminalContext.class,0);
		}
		public Start_nonterminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start_nonterminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterStart_nonterminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitStart_nonterminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitStart_nonterminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Start_nonterminalContext start_nonterminal() throws RecognitionException {
		Start_nonterminalContext _localctx = new Start_nonterminalContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_start_nonterminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			nonterminal();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonterminalContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(GrammarParser.NAME, 0); }
		public NonterminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonterminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterNonterminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitNonterminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitNonterminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonterminalContext nonterminal() throws RecognitionException {
		NonterminalContext _localctx = new NonterminalContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_nonterminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TerminalContext extends ParserRuleContext {
		public SymbolContext symbol() {
			return getRuleContext(SymbolContext.class,0);
		}
		public TerminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminalContext terminal() throws RecognitionException {
		TerminalContext _localctx = new TerminalContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			symbol();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Extra_terminalContext extends ParserRuleContext {
		public Extra_symbolContext extra_symbol() {
			return getRuleContext(Extra_symbolContext.class,0);
		}
		public Extra_terminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extra_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterExtra_terminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitExtra_terminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitExtra_terminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Extra_terminalContext extra_terminal() throws RecognitionException {
		Extra_terminalContext _localctx = new Extra_terminalContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_extra_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			extra_symbol();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SymbolContext extends ParserRuleContext {
		public TerminalNode ANY() { return getToken(GrammarParser.ANY, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterSymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitSymbol(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitSymbol(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(ANY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Extra_symbolContext extends ParserRuleContext {
		public TerminalNode EXTRA() { return getToken(GrammarParser.EXTRA, 0); }
		public Extra_symbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extra_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterExtra_symbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitExtra_symbol(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitExtra_symbol(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Extra_symbolContext extra_symbol() throws RecognitionException {
		Extra_symbolContext _localctx = new Extra_symbolContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_extra_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(EXTRA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return my_rules_sempred((My_rulesContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean my_rules_sempred(My_rulesContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\7\62\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2\3"+
		"\2\7\2\30\n\2\f\2\16\2\33\13\2\3\3\3\3\3\3\3\3\3\3\6\3\"\n\3\r\3\16\3"+
		"#\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\2\3\2\n\2\4\6\b"+
		"\n\f\16\20\2\2\2-\2\22\3\2\2\2\4\34\3\2\2\2\6%\3\2\2\2\b\'\3\2\2\2\n)"+
		"\3\2\2\2\f+\3\2\2\2\16-\3\2\2\2\20/\3\2\2\2\22\23\b\2\1\2\23\24\5\4\3"+
		"\2\24\31\3\2\2\2\25\26\f\4\2\2\26\30\5\4\3\2\27\25\3\2\2\2\30\33\3\2\2"+
		"\2\31\27\3\2\2\2\31\32\3\2\2\2\32\3\3\2\2\2\33\31\3\2\2\2\34\35\5\6\4"+
		"\2\35!\7\3\2\2\36\"\5\b\5\2\37\"\5\n\6\2 \"\5\f\7\2!\36\3\2\2\2!\37\3"+
		"\2\2\2! \3\2\2\2\"#\3\2\2\2#!\3\2\2\2#$\3\2\2\2$\5\3\2\2\2%&\5\b\5\2&"+
		"\7\3\2\2\2\'(\7\4\2\2(\t\3\2\2\2)*\5\16\b\2*\13\3\2\2\2+,\5\20\t\2,\r"+
		"\3\2\2\2-.\7\6\2\2.\17\3\2\2\2/\60\7\5\2\2\60\21\3\2\2\2\5\31!#";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}