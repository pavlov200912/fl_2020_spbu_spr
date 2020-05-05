import java.lang.RuntimeException
import java.util.*

fun buildFirst(rules: List<Rule>): Map<AST, MutableSet<Term>> {
    val first: MutableMap<AST, MutableSet<Term>> = mutableMapOf()
    val terminals = getTerms(rules)
    terminals.forEach {
        first[it] = mutableSetOf(it as Term)
    }
    rules.filter {
        it.tail == listOf(Epsilon() as Term)
    }.forEach {
        first[it.head] = mutableSetOf(Epsilon() as Term)
    }
    var isChanging: Boolean = false
    do {
        isChanging = false
        for (rule in rules) {
            val ruleFirst = mutableSetOf<Term>()
            var isEps = true
            for (item in rule.tail) {
                if (item is Term && item !is Epsilon) {
                    ruleFirst.add(item)
                    isEps = false
                    break
                } else if (item is Nonterminal) {
                    ruleFirst.addAll(first[item]?.minus(Epsilon()) ?: emptyList())
                    if (first[item]?.any { it is Epsilon } != true) {
                        isEps = false
                        break
                    }
                }
            }
            if (!(first[rule.head] ?: mutableSetOf()).containsAll(ruleFirst)) {
                isChanging = true
            }
            first[rule.head] = ruleFirst.plus(first[rule.head] ?: mutableSetOf()).toMutableSet()
            if (isEps) first[rule.head]?.add(Epsilon())
        }
    } while (isChanging)
    return first
}

fun buildFollow(rules: List<Rule>): Map<AST, MutableSet<Term>> {
    val follow: MutableMap<AST, MutableSet<Term>> = mutableMapOf()

    val first = buildFirst(rules)

    // FOLLOW(S) = {$}
    follow[Nonterminal("<S>")] = mutableSetOf(Terminal("$") as Term)

    rules.forEach { rule ->
        // A -> aBb => FOLLOW(B) U= FIRST(b) \ <eps>
        for (i in 0 until rule.tail.size - 1) {
            val item = rule.tail[i]
            if (item is Nonterminal) {
                val ruleFollow = first[rule.tail[i + 1]]?.minus(Epsilon())?.toMutableSet() ?: mutableSetOf()
                if (follow[item] != null) {
                    follow[item]?.addAll(ruleFollow)
                } else {
                    follow[item] = ruleFollow
                }
            }
        }
    }
    var isChanging: Boolean
    do {
        isChanging = false
        // A -> aB, then FOLLOW(B) U= FOLLOW(A)
        rules.forEach {
            if (it.tail.last() is Nonterminal) {
                val previous = follow.getOrDefault(it.tail.last(), mutableSetOf())
                follow[it.tail.last()] = follow.getOrDefault(it.tail.last(), mutableSetOf())
                    .plus(follow[it.head] ?: mutableSetOf()).toMutableSet()
                if (previous != follow.getOrDefault(it.tail.last(), mutableSetOf()))
                    isChanging = true
            }
        }

        // A -> aBb, where eps \in First(b), then FOLLOW(B) U= FOLLOW(A)
        rules.forEach { rule ->
            for (i in 0 until rule.tail.size - 1) {
                val item = rule.tail[i]
                if (item is Nonterminal) {
                    val tail = rule.tail.drop(i + 1)
                    val previous = follow.getOrDefault(item, mutableSetOf())
                    if (tail.all { ast ->
                            when (ast) {
                                is Nonterminal -> first[ast]?.contains(Epsilon()) == true
                                else -> false
                            }
                        }) {
                        follow[item] = follow.getOrDefault(item, mutableSetOf())
                            .plus(follow[rule.head] ?: mutableSetOf()).toMutableSet()
                    }
                    if (previous != follow.getOrDefault(item, mutableSetOf()))
                        isChanging = true
                }
            }
        }
    } while (isChanging)
    return follow
}

fun printFirst(first: Map<AST, MutableSet<Term>>) {
    println("First:")
    first.filterKeys { it is Nonterminal }.forEach { (ast, set) ->
        print("First(")
        (ast as Nonterminal).print()
        print(") := {")
        set.first().print()
        set.drop(1).forEach {
            print(", ")
            it.print()
        }
        print("}")
        println()
    }
}

fun printFollow(follow: Map<AST, MutableSet<Term>>) {
    println("Follow:")
    follow.forEach { ast, set ->
        print("Follow(")
        (ast as Nonterminal).print()
        print(") := {")
        set.first().print()
        set.drop(1).forEach {
            print(", ")
            it.print()
        }
        print("}")
        println()
    }
}

fun buildTable(rules: List<Rule>): Map<Pair<Nonterminal, Term>, Rule> {
    val table: MutableMap<Pair<Nonterminal, Term>, Rule> = mutableMapOf()
    val first = buildFirst(rules)
    val follow = buildFollow(rules)
    rules.forEach { rule ->
        // A -> y where y != eps, in (A, a) put (A->y), where a \in FIRST(y)
        val ruleFirst = mutableSetOf<Term>()
        var isEps = true
        for (item in rule.tail) {
            if (item is Term && item !is Epsilon) {
                ruleFirst.add(item)
                isEps = false
                break
            } else if (item is Nonterminal) {
                ruleFirst.addAll(first[item]?.minus(Epsilon()) ?: emptyList())
                if (first[item]?.any { it is Epsilon } != true) {
                    isEps = false
                    break
                }
            }
        }
        ruleFirst.filter { it !is Epsilon }.forEach {
            if (table.contains(Pair(rule.head, it))) {
                throw TableException(
                    TableException.formatMessage(
                        rule.head,
                        it,
                        table[Pair(rule.head, it)],
                        rule
                    )
                )
            }
            table[Pair(rule.head, it)] = rule
        }
        if (isEps) {
            follow[rule.head]?.forEach {
                if (table.contains(Pair(rule.head, it))) {
                    throw TableException(
                        TableException.formatMessage(
                            rule.head,
                            it,
                            table[Pair(rule.head, it)],
                            rule
                        )
                    )
                }
                table[Pair(rule.head, it)] = rule
            }
        }
    }
    return table
}

fun parseString(data: String, table: Map<Pair<Nonterminal, Term>, Rule>) {
    val mainStack: Stack<AST> = Stack()
    // TODO: Check if there is starting rule
    var pos = 0
    mainStack.add(Terminal("$"))
    mainStack.add(Nonterminal("<S>"))
    while (true) {
        if (pos == data.length) {
            if (mainStack.size == 1 && mainStack.peek() == Terminal("$"))
                return
            else
                throw ParseException("Parse error at pos: $pos, End of data, but stack isn't empty: ${mainStack}")
        }
        val peek = mainStack.peek()
        when(peek) {
            is Epsilon -> mainStack.pop()
            is Terminal -> {
                if (data[pos] == peek.symbol[0]) {
                    pos += 1
                    mainStack.pop()
                } else {
                    throw ParseException("Parse error at pos: $pos expected by data ${data[pos]} but got on stack ${peek.symbol}}")
                }
            }
            is ExtraTerminal -> {
                if (data[pos].toString() == peek.symbol.drop(1).dropLast(1)) {
                    pos += 1
                    mainStack.pop()
                } else {
                    throw ParseException("Parse error at pos: $pos expected by data ${data[pos]} but got on stack ${peek.symbol}}")
                }
            }
            is Nonterminal -> {
                val listExtra = listOf<Char>('\n', '\t', ' ', '<', '>', ':', '=')
                val rule = if (data[pos] in listExtra) {
                    table[Pair(peek, ExtraTerminal(data[pos].toString()))]
                } else {
                    table[Pair(peek, Terminal(data[pos].toString()))]
                }
                if (rule == null) {
                    throw ParseException("Parse error at pos: $pos Can't find rule for (${peek.name}, ${data[pos]})")
                } else {
                    mainStack.pop()
                    rule.tail.reversed().forEach {
                        mainStack.push(it)
                    }
                }
            }
        }
    }
}


data class TableException(val msg: String) : RuntimeException(msg) {
    companion object {
        fun formatMessage(first: Nonterminal, second: Term, rule1: Rule?, rule2: Rule?): String {
            return """
                    Error with parsing table
                    ($first, $second) already contains $rule1
                    Tried to rewrite with $rule2
                    """.trimIndent()
        }
    }
}





