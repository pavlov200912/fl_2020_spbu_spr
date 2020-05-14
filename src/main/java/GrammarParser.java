// Generated from Grammar.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, NAME=2, EXTRA=3, ANY=4, WS=5, LINE=6;
	public static final int
		RULE_my_rules = 0, RULE_my_rule = 1, RULE_end = 2, RULE_new_line = 3, 
		RULE_eof = 4, RULE_start_nonterminal = 5, RULE_nonterminal = 6, RULE_terminal = 7, 
		RULE_extra_terminal = 8, RULE_symbol = 9, RULE_extra_symbol = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"my_rules", "my_rule", "end", "new_line", "eof", "start_nonterminal", 
			"nonterminal", "terminal", "extra_terminal", "symbol", "extra_symbol"
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
			null, null, "NAME", "EXTRA", "ANY", "WS", "LINE"
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
	    public void notifyErrorListeners(Token offendingToken, String msg, RecognitionException ex)
	    {

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
			setState(23);
			my_rule();
			}
			_ctx.stop = _input.LT(-1);
			setState(29);
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
					setState(25);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(26);
					my_rule();
					}
					} 
				}
				setState(31);
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
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			start_nonterminal();
			setState(33);
			match(T__0);
			setState(37); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(37);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NAME:
					{
					setState(34);
					nonterminal();
					}
					break;
				case ANY:
					{
					setState(35);
					terminal();
					}
					break;
				case EXTRA:
					{
					setState(36);
					extra_terminal();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(39); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NAME) | (1L << EXTRA) | (1L << ANY))) != 0) );
			setState(41);
			end();
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

	public static class EndContext extends ParserRuleContext {
		public New_lineContext new_line() {
			return getRuleContext(New_lineContext.class,0);
		}
		public EofContext eof() {
			return getRuleContext(EofContext.class,0);
		}
		public EndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_end; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterEnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitEnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitEnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndContext end() throws RecognitionException {
		EndContext _localctx = new EndContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_end);
		try {
			setState(45);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LINE:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				new_line();
				}
				break;
			case EOF:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				eof();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class New_lineContext extends ParserRuleContext {
		public TerminalNode LINE() { return getToken(GrammarParser.LINE, 0); }
		public New_lineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_new_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterNew_line(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitNew_line(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitNew_line(this);
			else return visitor.visitChildren(this);
		}
	}

	public final New_lineContext new_line() throws RecognitionException {
		New_lineContext _localctx = new New_lineContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_new_line);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(LINE);
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

	public static class EofContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(GrammarParser.EOF, 0); }
		public EofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eof; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterEof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitEof(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitEof(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EofContext eof() throws RecognitionException {
		EofContext _localctx = new EofContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(EOF);
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
		enterRule(_localctx, 10, RULE_start_nonterminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
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
		enterRule(_localctx, 12, RULE_nonterminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
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
		enterRule(_localctx, 14, RULE_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
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
		enterRule(_localctx, 16, RULE_extra_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
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
		enterRule(_localctx, 18, RULE_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
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
		enterRule(_localctx, 20, RULE_extra_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\bB\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\3\2\3\2\3\2\7\2\36\n\2\f\2\16\2!\13\2\3\3\3\3\3\3\3\3\3"+
		"\3\6\3(\n\3\r\3\16\3)\3\3\3\3\3\4\3\4\5\4\60\n\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\2\3\2\r\2\4\6\b\n\f\16"+
		"\20\22\24\26\2\2\2;\2\30\3\2\2\2\4\"\3\2\2\2\6/\3\2\2\2\b\61\3\2\2\2\n"+
		"\63\3\2\2\2\f\65\3\2\2\2\16\67\3\2\2\2\209\3\2\2\2\22;\3\2\2\2\24=\3\2"+
		"\2\2\26?\3\2\2\2\30\31\b\2\1\2\31\32\5\4\3\2\32\37\3\2\2\2\33\34\f\4\2"+
		"\2\34\36\5\4\3\2\35\33\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 "+
		"\3\3\2\2\2!\37\3\2\2\2\"#\5\f\7\2#\'\7\3\2\2$(\5\16\b\2%(\5\20\t\2&(\5"+
		"\22\n\2\'$\3\2\2\2\'%\3\2\2\2\'&\3\2\2\2()\3\2\2\2)\'\3\2\2\2)*\3\2\2"+
		"\2*+\3\2\2\2+,\5\6\4\2,\5\3\2\2\2-\60\5\b\5\2.\60\5\n\6\2/-\3\2\2\2/."+
		"\3\2\2\2\60\7\3\2\2\2\61\62\7\b\2\2\62\t\3\2\2\2\63\64\7\2\2\3\64\13\3"+
		"\2\2\2\65\66\5\16\b\2\66\r\3\2\2\2\678\7\4\2\28\17\3\2\2\29:\5\24\13\2"+
		":\21\3\2\2\2;<\5\26\f\2<\23\3\2\2\2=>\7\6\2\2>\25\3\2\2\2?@\7\5\2\2@\27"+
		"\3\2\2\2\6\37\')/";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}