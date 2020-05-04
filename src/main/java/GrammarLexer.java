// Generated from Grammar.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, NAME=2, EXTRA=3, ANY=4, WS=5, LINE=6;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "NAME", "EXTRA", "ANY", "WS", "LINE"
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
	    public void recover(RecognitionException ex)
	    {
	        if (ex == null) {
	            throw  new ParseException("Unknow error");
	        }
	        throw new ParseException(ex.getMessage() + ex.getCause());
	    }



	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\bH\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\2\3\3\3\3\6\3\25\n\3"+
		"\r\3\16\3\26\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\5\49\n\4\3\5\3\5\3\6\6\6>\n\6\r\6\16\6?\3\6\3\6\3\7\6\7E\n\7\r\7\16\7"+
		"F\2\2\b\3\3\5\4\7\5\t\6\13\7\r\b\3\2\6\5\2\62;C\\c|\7\2\13\f\17\17\"\""+
		">>@@\5\2\13\13\17\17\"\"\3\2\f\f\2Q\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\3\17\3\2\2\2\5\22\3\2\2\2\78\3"+
		"\2\2\2\t:\3\2\2\2\13=\3\2\2\2\rD\3\2\2\2\17\20\7<\2\2\20\21\7?\2\2\21"+
		"\4\3\2\2\2\22\24\7>\2\2\23\25\t\2\2\2\24\23\3\2\2\2\25\26\3\2\2\2\26\24"+
		"\3\2\2\2\26\27\3\2\2\2\27\30\3\2\2\2\30\31\7@\2\2\31\6\3\2\2\2\32\33\7"+
		")\2\2\33\34\7\"\2\2\349\7)\2\2\35\36\7)\2\2\36\37\7>\2\2\379\7)\2\2 !"+
		"\7)\2\2!\"\7@\2\2\"9\7)\2\2#$\7)\2\2$%\7^\2\2%&\7p\2\2&9\7)\2\2\'(\7)"+
		"\2\2()\7^\2\2)*\7v\2\2*9\7)\2\2+,\7)\2\2,-\7?\2\2-9\7)\2\2./\7)\2\2/\60"+
		"\7<\2\2\609\7)\2\2\61\62\7)\2\2\62\63\7>\2\2\63\64\7g\2\2\64\65\7r\2\2"+
		"\65\66\7u\2\2\66\67\7@\2\2\679\7)\2\28\32\3\2\2\28\35\3\2\2\28 \3\2\2"+
		"\28#\3\2\2\28\'\3\2\2\28+\3\2\2\28.\3\2\2\28\61\3\2\2\29\b\3\2\2\2:;\n"+
		"\3\2\2;\n\3\2\2\2<>\t\4\2\2=<\3\2\2\2>?\3\2\2\2?=\3\2\2\2?@\3\2\2\2@A"+
		"\3\2\2\2AB\b\6\2\2B\f\3\2\2\2CE\t\5\2\2DC\3\2\2\2EF\3\2\2\2FD\3\2\2\2"+
		"FG\3\2\2\2G\16\3\2\2\2\7\2\268?F\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}