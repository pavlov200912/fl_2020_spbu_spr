// Print methods for AST
fun AST.print() {
    when(this) {
        is Terminal -> print()
        is Nonterminal -> print()
        is Rules -> print()
        is Rule -> print()
        is ExtraTerminal -> print()
        is Epsilon -> print()
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

fun Epsilon.print() {
    print("<eps>")
}

fun isRulesWithStarted(list: List<Rule>): Boolean {
    return list.any { it.head.isStarted() }
}

fun getRuleList(ast: AST?): List<Rule> {
    return when(ast) {
        null -> listOf()
        is Terminal -> listOf()
        is Nonterminal -> listOf()
        is ExtraTerminal -> listOf()
        is Epsilon -> listOf()
        is Rule -> listOf(ast)
        is Rules -> getRuleList(ast.left) + getRuleList(ast.right)
    }
}

fun Nonterminal.isStarted(): Boolean {
    return name == "<S>"
}


fun getTerms(list: List<Rule>): List<Term> {
    val terminals: MutableSet<Term> = mutableSetOf()
    list.forEach { rule ->
        rule.tail.forEach {
            if (it is Term) {
                terminals.add(it)
            }
        }
    }
    return terminals.toList()
}

fun getNonterminals(list: List<Rule>): List<Nonterminal> {
    val nonterminals: MutableSet<Nonterminal> = mutableSetOf()
    list.forEach { rule ->
        nonterminals.add(rule.head)
        rule.tail.forEach {
            if (it is Nonterminal) nonterminals.add(it)
        }
    }
    return nonterminals.toList()
}