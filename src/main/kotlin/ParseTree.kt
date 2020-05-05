import java.lang.RuntimeException

interface ParseTree

class NonterminalNode(val parent: NonterminalNode?,
                           val label: String,
                           val children: MutableList<ParseTree>,
                           val childCount: Int): ParseTree

class TerminalNode(val parent: NonterminalNode, val label: String): ParseTree

fun addTerminal(root: NonterminalNode, terminal: Term): NonterminalNode {
    if (root.children.size == root.childCount) {
        if (root.parent == null) {
            // Only happens if there is error in code
            throw ParseTreeException("Error while building ParseTree")
        } else {
            return addTerminal(root.parent, terminal)
        }
    } else {
        val newNode: TerminalNode = TerminalNode(parent = root, label = (terminal as AST).str())
        root.children.add(newNode)
        return root
    }
}

fun addNonterminal(root: NonterminalNode?, nonterminal: Nonterminal, childCount: Int): NonterminalNode{
    if (root == null) {
        return NonterminalNode(null, nonterminal.name, mutableListOf(), childCount)
    } else {
        if (root.children.size == root.childCount) {
            if (root.parent == null) {
                // Only happens if there is error in code
                throw ParseTreeException("Error while building ParseTree")
            } else {
                return addNonterminal(root.parent, nonterminal, childCount)
            }
        } else {
            val newNode = NonterminalNode(root, nonterminal.name, mutableListOf(), childCount)
            root.children.add(newNode)
            return newNode
        }
    }
}

data class ParseTreeException(val msg: String): RuntimeException(msg)

fun NonterminalNode.str(): String {
    val buffer = StringBuilder(1000)
    this.print(buffer, "", "")
    return buffer.toString()
}

private fun NonterminalNode.print(buffer: StringBuilder, prefix: String, childrenPrefix: String) {
    buffer.append(prefix)
    buffer.append(this.label)
    buffer.append('\n')
    val it = children.iterator()
    while (it.hasNext()) {
        val next = it.next()
        when (next) {
            is TerminalNode -> {
                if (it.hasNext()) {
                    next.print(buffer, "$childrenPrefix├── ", "$childrenPrefix│   ")
                } else {
                    next.print(buffer, "$childrenPrefix└── ", "$childrenPrefix    ")
                }
            }
            is NonterminalNode -> {
                if (it.hasNext()) {
                    next.print(buffer, "$childrenPrefix├── ", "$childrenPrefix│   ")
                } else {
                    next.print(buffer, "$childrenPrefix└── ", "$childrenPrefix    ")
                }
            }
        }
    }
}


private fun TerminalNode.print(buffer: StringBuilder, prefix: String, childrenPrefix: String) {
    buffer.append(prefix)
    buffer.append(this.label)
    buffer.append('\n')
}