import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.nio.file.NoSuchFileException

object MainParser {
    @JvmStatic
    fun main(args: Array<String>) {
        while (true) {
            println("Input filename with grammar:")
            val line = readLine() ?: "exit"
            if (line == "exit") {
                println("Bye!")
                break
            }
            val charStream: CharStream?
            try {
                charStream = CharStreams.fromFileName(line)
            } catch (e: NoSuchFileException) {
                println("No such file!")
                continue
            }
            try {
                val lexer = GrammarLexer(charStream)
                val commonTokenStream = CommonTokenStream(lexer)
                val parser = GrammarParser(commonTokenStream)

                val parseTree = parser.my_rules();
                val visitor = Visitor()
                val ast = visitor.visit(parseTree)
                //ast.print()
                println()
                val listRules = getRuleList(ast)
                //println(listRules)
                //buildFirst(listRules)
                printFirst(buildFirst(listRules))
                printFollow(buildFollow(listRules))
            } catch (e: ParseException) {
                println("Parse error " + e.message)
            }
        }
    }
}

