import com.jakewharton.fliptables.FlipTable
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.nio.file.NoSuchFileException



class MainCLI {
    var grammarAST: AST? = null
    var grammarRules: List<Rule>? = null
    var grammarFirst: Map<AST, MutableSet<Term>>? = null
    var grammarFollow: Map<AST, MutableSet<Term>>? = null
    var grammarTable:  Map<Pair<Nonterminal, Term>, Rule>? = null
    private fun loadGrammar() {
        val charStream: CharStream?
        println("Введите путь к файлу с грамматикой.")
        val line = readLine() ?: "exit"
        if (line == "exit")
            return
        try {
            charStream = CharStreams.fromFileName(line)
        } catch (e: NoSuchFileException) {
            println("Файл не найден. Попробуйте снова или наберите exit")
            loadGrammar()
            return
        }
        try {
            val lexer = GrammarLexer(charStream)
            val commonTokenStream = CommonTokenStream(lexer)
            val parser = GrammarParser(commonTokenStream)

            val parseTree = parser.my_rules();
            val visitor = Visitor()
            grammarAST = visitor.visit(parseTree)
            grammarRules = getRuleList(grammarAST)
            grammarFirst = grammarRules?.let { buildFirst(it)}
            grammarFollow = grammarRules?.let { buildFollow(it) }
            println("Успех! Грамматика:")
            grammarAST?.print()
            println()
        } catch (e: ParseException) {
            println("Неудалось распарсить грамматику: " + e.message)
            cleanGrammar()
        }
    }

    private fun cleanGrammar() {
        grammarFollow = null
        grammarFirst = null
        grammarAST = null
        grammarRules = null
        grammarTable = null
    }

    private fun help() {
        println("""
        exit - выход
        load - загрузить грамматику
        show - показать текущую грамматику
        parse - парсить файл с данными 
        showFirst - показать множество FIRST_1 для грамматики
        showFollow - показать множество FOLLOW_1 для грамматики
        showTable - показать управляющую таблицу для грамматики
    """.trimIndent())
    }

    private fun parse() {
        println("TODO(parser)")
    }

    private fun unknown() {
        println("Неизвестная команда. Наберите help для просмотра списка команд.")
    }

    private fun showGrammar(){
        if (grammarAST == null) {
            println("Нет загруженной грамматики.")
            return
        }
        println("Грамматика:")
        grammarAST?.print()
        println()
    }

    fun run() {
        println("Привет! Это CLI интерфейс для LL пасера")
        println("Наберите help для просмотра списка команд")
        loop@ while (true) {
            when(readLine() ?: "exit") {
                "exit" -> break@loop
                "help" -> help()
                "load" -> loadGrammar()
                "parse" -> parse()
                "show" -> showGrammar()
                "showFollow" -> showGrammarFollow()
                "showFirst" -> showGrammarFirst()
                "showTable" -> showGrammarTable()
                else -> unknown()
            }
        }
    }

    private fun showGrammarTable() {
        if (grammarRules == null) {
            println("Нет загруженной грамматики.")
        } else {
            try {
                grammarTable = grammarRules?.let { buildTable(it) }
                val headersSet = mutableSetOf<Term>()
                val columnsSet = mutableSetOf<Nonterminal>()
                grammarTable?.forEach {
                    headersSet.add(it.key.second)
                    columnsSet.add(it.key.first)
                }
                val headers = arrayOf("") + headersSet.toTypedArray().map{it.str()}
                val tableAsSet = mutableSetOf<Array<String>>()
                columnsSet.forEach {
                    val list = mutableListOf<String>()
                    list.add(it.name)
                    headersSet.forEach {
                        term ->
                        list.add(grammarTable?.get(Pair(it, term))?.str() ?: "")
                    }
                    tableAsSet.add(list.toTypedArray())
                }
                val data = tableAsSet.toTypedArray()
                println(FlipTable.of(headers, data))
                //println(grammarTable)
            } catch (e: TableException) {
                println(e.message)
            }
        }
    }

    private fun showGrammarFirst() {
        if (grammarFirst == null) {
            println("Нет загруженной грамматики.")
        } else {
            grammarFirst?.let { printFirst(it) }
        }
    }

    private fun showGrammarFollow() {
        if (grammarFollow == null) {
            println("Нет загруженной грамматики.")
        } else {
            grammarFollow?.let { printFollow(it) }
        }
    }
}

object MainParser {
    @JvmStatic
    fun main(args: Array<String>) {
        val cli = MainCLI()
        cli.run()
    }
}

