
import junit.framework.Assert.assertEquals
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.Test
import java.io.File

internal class LLTest {
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
    fun testFirst() {
        // Example from lectures:
        initFile(data = """
            <S> := a<S1>
            <S1> := <A>b<B><S1>
            <S1> := '<eps>'
            <A> := a<A1>
            <A> := '<eps>'
            <A1> := b
            <A1> := a
            <B> := c
            <B> := '<eps>'
        """.trimIndent())
        val ast = createAST()
        val rules = getRuleList(ast)
        val first = buildFirst(rules)

        assert(first.getOrDefault(Nonterminal("<S>"), mutableSetOf())
        == mutableSetOf(Terminal("a")))

        assert(first.getOrDefault(Nonterminal("<S1>"), mutableSetOf())
                == mutableSetOf(Terminal("a"),
                                Terminal("b"), Epsilon()))

        assert(first.getOrDefault(Nonterminal("<A>"), mutableSetOf())
                == mutableSetOf(Terminal("a"),
                                Epsilon()))

        assert(first.getOrDefault(Nonterminal("<A1>"), mutableSetOf())
                == mutableSetOf(Terminal("a"),
                                Terminal("b")))

        assert(first.getOrDefault(Nonterminal("<B>"), mutableSetOf())
                == mutableSetOf(Terminal("c"),
                                Epsilon()))
        clear()
    }

    @Test
    fun testFollow() {
        // Example from lectures:
        initFile(data = """
            <S> := a<S1>
            <S1> := <A>b<B><S1>
            <S1> := '<eps>'
            <A> := a<A1>
            <A> := '<eps>'
            <A1> := b
            <A1> := a
            <B> := c
            <B> := '<eps>'
        """.trimIndent())
        val ast = createAST()
        val rules = getRuleList(ast)
        val follow = buildFollow(rules)

        assert(follow[Nonterminal("<S>")]
        == mutableSetOf(Terminal("$")))

        assert(follow[Nonterminal("<S1>")]
                == mutableSetOf(Terminal("$")))

        assert(follow[Nonterminal("<A>")]
                == mutableSetOf(Terminal("b")))

        assert(follow[Nonterminal("<A1>")]
                == mutableSetOf(Terminal("b")))

        assert(follow[Nonterminal("<B>")]
                == mutableSetOf(Terminal("a"),
                                Terminal("b"),
                                Terminal("$")))

        clear()
    }



}
