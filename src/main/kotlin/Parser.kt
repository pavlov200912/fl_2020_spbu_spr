import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

object MainParser {
    @JvmStatic
    fun main(args: Array<String>) {
        val charStream = CharStreams.fromFileName("./text.txt")
        val lexer = GrammarLexer(charStream)
        val commonTokenStream = CommonTokenStream(lexer)
        val parser = GrammarParser(commonTokenStream)

        val parseTree = parser.my_rules();
        val visitor = Visitor()
        val ast = visitor.visit(parseTree)
        ast.print()

    }
}

