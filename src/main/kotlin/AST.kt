sealed class AST

data class Rules(val left: AST?, val right: AST?): AST()

data class Rule(val head: Nonterminal, val tail: List<AST>): AST()

data class Nonterminal(val name: String): AST()

sealed class Term: AST()

data class Terminal(val symbol: String): Term()

data class ExtraTerminal(val symbol: String): Term()

data class Epsilon(val epsilon: String = ""): Term()

class ParseException(message:String): RuntimeException(message)
