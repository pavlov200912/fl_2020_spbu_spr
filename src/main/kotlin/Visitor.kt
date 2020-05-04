class Visitor: GrammarBaseVisitor<AST>() {
    override fun visitMy_rules(ctx: GrammarParser.My_rulesContext?): AST {
        if (ctx == null) throw ParseException("panic: context is null")
        return if (ctx.childCount == 1) {
            val rule = visitMy_rule(ctx.getChild(0) as GrammarParser.My_ruleContext)
            Rules(rule, null)
        } else {
            val left = visitMy_rules(ctx.getChild(0) as GrammarParser.My_rulesContext)
            val right = visitMy_rule(ctx.getChild(1) as GrammarParser.My_ruleContext)
            Rules(left, right)
        }
    }

    override fun visitMy_rule(ctx: GrammarParser.My_ruleContext?): AST {
        if (ctx == null) throw ParseException("panic: context is null")
        val start =
            ctx.getChild(0)  as GrammarParser.Start_nonterminalContext
        val head = visitStart_nonterminal(start) as Nonterminal
        val tail = mutableListOf<AST>()
        for (child in ctx.children.drop(2)) {
            val token = when(child) {
                is GrammarParser.NonterminalContext -> visitNonterminal(child)
                is GrammarParser.TerminalContext -> visitTerminal(child)
                is GrammarParser.Extra_terminalContext -> visitExtra_terminal(child)
                is GrammarParser.EndContext -> null
                else -> throw ParseException("panic: unknown AST type in Rule parsing")
            }
            token?.let { tail.add(it) }
        }
        return Rule(head, tail)
    }

    override fun visitNonterminal(ctx: GrammarParser.NonterminalContext?): AST {
        return Nonterminal(ctx?.text ?: "")
    }

    override fun visitTerminal(ctx: GrammarParser.TerminalContext?): AST {
        return Terminal(ctx?.text ?: "")
    }

    override fun visitExtra_terminal(ctx: GrammarParser.Extra_terminalContext?): AST {
        if (ctx?.text == "\'<eps>\'")
            return Epsilon()
        return ExtraTerminal(ctx?.text?.drop(1)?.dropLast(1) ?: "")
    }
}

