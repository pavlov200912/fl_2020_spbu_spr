grammar Grammar;

// options/header/tokens

@parser::members
{
    @Override
    public void notifyErrorListeners(Token offendingToken, String msg, RecognitionException ex)
    {

     throw new ParseException("(line: " + offendingToken.getLine() +
              ", pos: " + offendingToken.getCharPositionInLine()
              + ") message: " + msg);
    }
}

@lexer::members
{
    @Override
    public void recover(RecognitionException ex)
    {
        if (ex == null) {
            throw  new ParseException("Unknow error");
        }
        throw new ParseException(ex.getMessage() + ex.getCause());
    }

}

my_rules : my_rules my_rule| my_rule;

my_rule : start_nonterminal ':=' (nonterminal | terminal | extra_terminal)+ end;

end: new_line | eof;

new_line: LINE;
eof: EOF;

start_nonterminal: nonterminal;
nonterminal : NAME;
terminal : symbol;
extra_terminal: extra_symbol;
symbol : ANY;
extra_symbol : EXTRA;

NAME: '<'[a-zA-Z0-9]+'>';
EXTRA: ('\' \'' | '\'<\'' | '\'>\'' | '\'\\n\'' | '\'\\t\'' | '\'=\'' | '\':\'' | '\'<eps>\'');
ANY: ~[\n\t\r <>];
WS: [\t\r ]+ -> skip;
LINE: [\n]+;
