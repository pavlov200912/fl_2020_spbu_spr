
import junit.framework.Assert.assertEquals
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.Test
import java.io.File

internal class GrammarParserTest {
    private fun initFile(data: String) {
        val filename = "test.txt"
        val file  = File(filename)
        file.createNewFile()
        file.writeText(data)
    }

    private fun clear() {
        val filename = "test.txt"
        val file = File(filename)
        file.deleteOnExit()
    }

    private fun createAST(filename: String = "test.txt"): AST {
        val charStream = CharStreams.fromFileName(filename)
        val lexer = GrammarLexer(charStream)
        val commonTokenStream = CommonTokenStream(lexer)
        val parser = GrammarParser(commonTokenStream)
        val parseTree = parser.my_rules();
        val visitor = Visitor()
        return visitor.visit(parseTree)
    }

    @Test
    fun testTerminals() {
        initFile(data = "<S> := abc")
        val ast = createAST()
        assertEquals(Rules(Rule(Nonterminal("<S>"), listOf(
            Terminal("a"),
            Terminal("b"),
            Terminal("c"))),
            null), ast)
        clear()
    }

    @Test
    fun testNonterminals() {
        initFile(data = "<S> := <a><b><c>")
        val ast = createAST()
        assertEquals(Rules(Rule(Nonterminal("<S>"), listOf(
            Nonterminal("<a>"),
            Nonterminal("<b>"),
            Nonterminal("<c>"))),
            null), ast)
        clear()
    }

    @Test
    fun testNonterminalsAndTerminals() {
        initFile(data = "<nonterm> := <a>bc")
        val ast = createAST()
        assertEquals(Rules(Rule(Nonterminal("<nonterm>"), listOf(
            Nonterminal("<a>"),
            Terminal("b"),
            Terminal("c"))),
            null), ast)
        clear()
    }

    @Test
    fun testExtraTerminals() {
        initFile(data = "<nonterm> := '\\n''\\t'' ''<''>'':''='")
        val ast = createAST()
        assertEquals(Rules(Rule(Nonterminal("<nonterm>"), listOf(
            ExtraTerminal("\\n"),
            ExtraTerminal("\\t"),
            ExtraTerminal(" "),
            ExtraTerminal("<"),
            ExtraTerminal(">"),
            ExtraTerminal(":"),
            ExtraTerminal("="))),
            null), ast)
        clear()
    }

    @Test
    fun testOneRule() {
        initFile(data = "<nonterm> := <N1>a<N2>' '")
        val ast = createAST()
        assertEquals(Rules(Rule(Nonterminal("<nonterm>"), listOf(
            Nonterminal("<N1>"),
            Terminal("a"),
            Nonterminal("<N2>"),
            ExtraTerminal(" "))),
            null), ast)
        clear()
    }

    @Test
    fun testManyRules() {
        initFile(data = """
            <S> := a<S>b<S>
            <S> := c<S>d<S>
            <N> := <S><S>
        """.trimIndent())
        val ast = createAST()
        val left = Rules(
            Rules(
                Rule(
                    head = Nonterminal("<S>"),
                    tail = listOf(
                        Terminal("a"),
                        Nonterminal("<S>"),
                        Terminal("b"),
                        Nonterminal("<S>")
                    )
                ),
                null
            ),
            Rule (
                head = Nonterminal("<S>"),
                tail = listOf(
                    Terminal("c"),
                    Nonterminal("<S>"),
                    Terminal("d"),
                    Nonterminal("<S>")
                )
            )
        )
        val right = Rule(
            head = Nonterminal("<N>"),
            tail = listOf(
                Nonterminal("<S>"),
                Nonterminal("<S>")
            )
        )
        assertEquals(Rules(left, right), ast)
        clear()
    }


}