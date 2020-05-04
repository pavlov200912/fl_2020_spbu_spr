sealed class AST

data class Rules(val left: AST?, val right: AST?): AST()

data class Rule(val head: Nonterminal, val tail: List<AST>): AST()

data class Nonterminal(val name: String): AST()

data class Terminal(val symbol: String): AST()

data class ExtraTerminal(val symbol: String): AST()

// Print methods for AST
fun AST.print() {
    when(this) {
        is Terminal -> print()
        is Nonterminal -> print()
        is Rules -> print()
        is Rule -> print()
        is ExtraTerminal -> print()
    }
}

fun Rules.print() {
    left?.print()
    if (right != null) println()
    right?.print()
}

fun Rule.print() {
    head.print()
    print(" -> ")
    tail.forEach { it.print() }
}

fun Nonterminal.print() {
    print(name)
}

fun Terminal.print() {
    print(symbol)
}

fun ExtraTerminal.print() {
    print("'$symbol'")
}

class ParseException(message:String): RuntimeException(message)
