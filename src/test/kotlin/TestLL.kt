
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
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
    fun testFirst2() {
        initFile(data = """
            <S> := <Y> z
            <S> := a
            <Y> := b <Z>
            <Y> := '<eps>'
            <Z> := '<eps>'
        """.trimIndent())
        val ast = createAST()
        val rules = getRuleList(ast)
        val first = buildFirst(rules)

        assert(first.getOrDefault(Nonterminal("<S>"), mutableSetOf())
                == mutableSetOf(Terminal("a"), Terminal("b"), Terminal("z")))

        assert(first.getOrDefault(Nonterminal("<Y>"), mutableSetOf())
                == mutableSetOf(Terminal("b"),
            Epsilon()))

        assert(first.getOrDefault(Nonterminal("<Z>"), mutableSetOf())
                == mutableSetOf(Epsilon()))

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

    @Test
    fun testFollow2() {
        initFile(data = """
            <S> := <Y> z
            <S> := a
            <Y> := b <Z>
            <Y> := '<eps>'
            <Z> := '<eps>'
        """.trimIndent())
        val ast = createAST()
        val rules = getRuleList(ast)
        val follow = buildFollow(rules)

        assert(follow[Nonterminal("<S>")]
                == mutableSetOf(Terminal("$")))

        assert(follow[Nonterminal("<Y>")]
                == mutableSetOf(Terminal("z")))

        assert(follow[Nonterminal("<Z>")]
                == mutableSetOf(Terminal("z")))

        clear()
    }

    @Test
    fun testTable() {
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
        val table = buildTable(rules)

        assertEquals(table.size,
            12)

        assertEquals(
            table[Pair(Nonterminal("<S>"), Terminal("a"))]?.str(),
            "<S> -> a<S1>"
        )

        assertEquals(
            table[Pair(Nonterminal("<S1>"), Terminal("a"))]?.str(),
            "<S1> -> <A>b<B><S1>"
        )
        assertEquals(
            table[Pair(Nonterminal("<S1>"), Terminal("b"))]?.str(),
            "<S1> -> <A>b<B><S1>"
        )

        assertEquals(
            table[Pair(Nonterminal("<S1>"), Terminal("$"))]?.str(),
            "<S1> -> <eps>"
        )

        assertEquals(
            table[Pair(Nonterminal("<A>"), Terminal("a"))]?.str(),
            "<A> -> a<A1>"
        )
        assertEquals(
            table[Pair(Nonterminal("<A>"), Terminal("b"))]?.str(),
            "<A> -> <eps>"
        )

        assertEquals(
            table[Pair(Nonterminal("<A1>"), Terminal("b"))]?.str(),
            "<A1> -> b"
        )

        assertEquals(
            table[Pair(Nonterminal("<A1>"), Terminal("a"))]?.str(),
            "<A1> -> a"
        )
        assertEquals(
            table[Pair(Nonterminal("<B>"), Terminal("c"))]?.str(),
            "<B> -> c"
        )
        assertEquals(
            table[Pair(Nonterminal("<B>"), Terminal("a"))]?.str(),
            "<B> -> <eps>"
        )
        assertEquals(
            table[Pair(Nonterminal("<B>"), Terminal("b"))]?.str(),
            "<B> -> <eps>"
        )
        assertEquals(
            table[Pair(Nonterminal("<B>"), Terminal("$"))]?.str(),
            "<B> -> <eps>"
        )

        clear()
    }

    @Test
    fun testTable2() {
        // Example from lectures:
        initFile(data = """
            <S> := <Y> z
            <S> := a
            <Y> := b <Z>
            <Y> := '<eps>'
            <Z> := '<eps>'
        """.trimIndent())
        val ast = createAST()
        val rules = getRuleList(ast)
        val table = buildTable(rules)

        assertEquals(table.size,
            6)

        assertEquals(
            table[Pair(Nonterminal("<S>"), Terminal("a"))]?.str(),
            "<S> -> a"
        )

        assertEquals(
            table[Pair(Nonterminal("<S>"), Terminal("b"))]?.str(),
            "<S> -> <Y>z"
        )

        assertEquals(
            table[Pair(Nonterminal("<S>"), Terminal("z"))]?.str(),
            "<S> -> <Y>z"
        )

        assertEquals(
            table[Pair(Nonterminal("<Y>"), Terminal("b"))]?.str(),
            "<Y> -> b<Z>"
        )

        assertEquals(
            table[Pair(Nonterminal("<Y>"), Terminal("z"))]?.str(),
            "<Y> -> <eps>"
        )

        assertEquals(
            table[Pair(Nonterminal("<Z>"), Terminal("z"))]?.str(),
            "<Z> -> <eps>"
        )


        clear()
    }

    @Test
    fun testParse() {
        // Example from lectures:
        initFile(data = """
            <S> := a<S>b<S>
            <S> := '<eps>'
        """.trimIndent())
        val ast = createAST()
        val rules = getRuleList(ast)
        val table = buildTable(rules)
        val data = "abab"
        val result = parseString(data, table)
        assertEquals(result.label ,"<S>")
        assertEquals(result.childCount, 5)
        assertNull(result.parent)

        assert(result.children[0] is MyTerminalNode)
        assertEquals((result.children[0] as MyTerminalNode).label , "a")

        assert(result.children[1] is NonterminalNode)
        assertEquals((result.children[1] as NonterminalNode).label , "<S>")
        assertEquals((result.children[1] as NonterminalNode).childCount , 1)

        assert(result.children[2] is MyTerminalNode)
        assertEquals((result.children[2] as MyTerminalNode).label , "b")

        assert(result.children[3] is NonterminalNode)
        assertEquals((result.children[3] as NonterminalNode).label , "<S>")
        assertEquals((result.children[3] as NonterminalNode).childCount , 4)

        assert(result.children[4] is MyTerminalNode)
        assertEquals((result.children[4] as MyTerminalNode).label , "$")

    }


}
