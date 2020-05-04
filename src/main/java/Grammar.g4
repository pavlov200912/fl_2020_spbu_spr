// Define a grammar called Hello
grammar Grammar;

my_rules : my_rules my_rule | my_rule;

my_rule : start_nonterminal ':=' (nonterminal | terminal | extra_terminal)+;

start_nonterminal: nonterminal;
nonterminal : NAME;
terminal : symbol;
extra_terminal: extra_symbol;
symbol : ANY;
extra_symbol : EXTRA;

NAME: '<'[a-zA-Z0-9]+'>';
EXTRA: ('\' \'' | '\'<\'' | '\'>\'' | '\'\\n\'' | '\'\\t\'' | '\'=\'' | '\':\'' | '\'<eps>\'');
ANY: ~[\n\t\r <>];
WS: [\n\t\r ]+ -> skip;
