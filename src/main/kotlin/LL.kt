

fun buildFirst(rules: List<Rule>, terminals: List<Term>): Map<AST, MutableSet<Term>> {
    val first: MutableMap<AST, MutableSet<Term>> = mutableMapOf()
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
        for(rule in rules) {
            val ruleFirst = mutableSetOf<Term>()
            var isEps = true
            for (item in rule.tail) {
                if (item is Term && item !is Epsilon) {
                    ruleFirst.add(item)
                    isEps = false
                    break
                } else if(item is Nonterminal){
                    ruleFirst.addAll(first[item]?.minus(Epsilon()) ?: emptyList())
                    if (first[item]?.any{it is Epsilon} != true) {
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

fun buildFollow(list: List<Rule>) {
    TODO()
}

fun buildTable(list: List<Rule>) {
    TODO()
}





