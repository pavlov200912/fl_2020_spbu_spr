module LLang where

import           AST         (AST (..), Operator (..), Subst (..))
import           Data.List   (intercalate)
import qualified Data.Map    as Map
import           Text.Printf (printf)
import           Combinators (makeError, curPos, InputStream (..), ErrorMsg (..), Parser (..), Result (..), elem', elemSome', fail',
                      satisfy, success,  symbol, stringCompare, satisfySome)
import           Expr (OpType (..), Associativity (..), uberExpr, toOperator, evalExpr)
import           Control.Applicative
import           Data.Char   (digitToInt, isDigit)
import           Control.Monad


--import Data.Text

type Expr = AST

type Var = String

data Configuration = Conf { subst :: Subst, input :: [Int], output :: [Int] }
                   deriving (Show, Eq)

data Program = Program { functions :: [Function], main :: LAst } deriving Eq

data Function = Function { name :: String, args :: [Var], funBody :: LAst } deriving Eq

data LAst
  = If { cond :: Expr, thn :: LAst, els :: LAst }
  | While { cond :: AST, body :: LAst }
  | Assign { var :: Var, expr :: Expr }
  | Read { var :: Var }
  | Write { expr :: Expr }
  | Seq { statements :: [LAst] }
  | Return { expr :: Expr }
  deriving (Eq)


-- Парсер для положительных целых чисел
-- Ведет себя неожиданно, но так и задуманно
parseNum :: Parser String String Int
parseNum = foldl (\acc d -> if d == '.' then acc else 10 * acc + digitToInt d) 0 `fmap` go
    where
        go :: Parser String String String
        go = (++) <$> some (satisfy isDigit) <*>
         (((:) <$> satisfy (=='.') <*> (some (satisfy isDigit))) <|> return [])

--Parse Ident 
parseIdent :: Parser String String String
parseIdent = do
             str <- parseVar
             guard (not $ elem str indentKeywords)
             return str
          where
          parseVar = (:) <$> (letterParser) <*> (many (letterParser <|> digitParser <|> underscoreParser))
            where digitParser = satisfy isDigit
                  letterParser = satisfy (\x -> elem x (['a'..'z'] ++ ['A'..'Z']))
                  underscoreParser = satisfy (== '~')

--Parse Expressions 
plus'   = stringCompare "+" >>= toOperator
minus'  = stringCompare "-" >>= toOperator
mult'   = stringCompare "*" >>= toOperator
pow'    = stringCompare "^" >>= toOperator
equal'  = stringCompare "==" >>= toOperator
nequal' = stringCompare "/=" >>= toOperator
ge'     = stringCompare ">=" >>= toOperator
le'     = stringCompare "<=" >>= toOperator
and'    = stringCompare "&&" >>= toOperator
or'     = stringCompare "||" >>= toOperator
gt'     = stringCompare ">" >>= toOperator
lt'     = stringCompare "<" >>= toOperator
div'    = stringCompare "/" >>= toOperator

parseExpr :: Parser String String AST
parseExpr = uberExpr [
                      (or', Binary RightAssoc),
                      (and', Binary RightAssoc),
                      (equal' <|> nequal' <|> ge' <|> le' <|> gt' <|> lt', Binary NoAssoc),
                      (plus' <|> minus', Binary LeftAssoc),
                      (mult' <|> div', Binary LeftAssoc),
                      (pow', Binary RightAssoc)
                     ]
                     (Num <$> parseNum <|> 
                     parseFunctionCall <|> 
                     Ident <$> parseIdent <|> 
                     symbol '(' *> parseExpr <* symbol ')')
                     BinOp
                     UnaryOp

parseFunctionCall :: Parser String String AST
parseFunctionCall = do
                    name <- parseIdent
                    symbol '('
                    params <- parseParams <|> return []
                    symbol ')' 
                    return $ FunctionCall name params
                where
                parseExprSpaces = do
                                  parseManySpaces
                                  expr <- parseExpr
                                  parseManySpaces
                                  return $ expr
                parseParams :: Parser String String [AST]  
                parseParams = do
                              top   <- parseExprSpaces
                              other <- many (symbol ',' *> parseExprSpaces)
                              return (top:other) 

parseSomeSpaces :: Parser String String String
parseSomeSpaces = some (symbol ' ' <|> symbol '\n')

parseManySpaces = many (symbol ' ' <|> symbol '\n')

-- keywords that shouldn't be parsed like Var
indentKeywords = ["esle", "poka", "read", "print", "please", "help", "me", "fun"]

parsePleaseHelpMe :: Parser String String String
parsePleaseHelpMe = do
                    parseManySpaces
                    keywordsParser
                    parseSomeSpaces <|> parseBrackets <|> stringCompare ";" <|> parseSeqEnd
                    return ""
                    where
  keywordsParser = stringCompare "please" <|> stringCompare "help" <|> stringCompare "me"
  parseBrackets = (stringCompare "()") <|> (do
                                            symbol '('
                                            parseExpr
                                            symbol ')'
                                            return "")
  parseSeqEnd = Parser $ helper where
    helper :: InputStream String -> Result String String String
    helper input@(InputStream ('}':xs) n) = Success input ""
    helper input = Failure [makeError mempty (curPos input)]
 
parseIf :: Parser String String LAst
parseIf = do
          parseManySpaces
          stringCompare "esle"
          parseSomeSpaces 
          symbol '('
          expr <- parseExpr
          symbol ')'
          parseSomeSpaces
          stringCompare "then"
          parseSomeSpaces 
          seqTrue <- parseSeq
          parseSomeSpaces
          stringCompare "else"
          parseSomeSpaces 
          seqFalse <- parseSeq
          symbol ';'
          return $ If expr seqTrue seqFalse

parseAssign :: Parser String String LAst
parseAssign = do
           parseManySpaces 
           ident <- parseIdent
           parseSomeSpaces
           stringCompare ":="
           parseSomeSpaces
           expr <- parseExpr
           symbol ';'
           return $ Assign ident expr


parseWhile :: Parser String String LAst
parseWhile = do
             parseManySpaces
             stringCompare "poka"
             parseSomeSpaces
             symbol '('
             expr <- parseExpr
             symbol ')'
             parseSomeSpaces
             seq <- parseSeq
             symbol ';'
             return $ While expr seq

parseRead :: Parser String String LAst
parseRead = do
            parseManySpaces
            stringCompare "read"
            symbol '('
            var <- parseIdent
            symbol ')'
            symbol ';'
            return $ Read var



parseWrite :: Parser String String LAst
parseWrite = do
              parseManySpaces
              stringCompare "print"
              symbol '('
              expr <- parseExpr
              symbol ')'
              symbol ';'
              return $ Write expr

parseReturn :: Parser String String LAst
parseReturn = do
              parseManySpaces
              stringCompare "return"
              parseSomeSpaces
              expr <- parseExpr
              symbol ';'
              return $ Return expr

 
parseSeq :: Parser String String LAst
parseSeq = do
           parseManySpaces
           symbol '{'
           list <- many (parseInstruction) 
           many parsePleaseHelpMe -- In case there is no instructions in Seq, but there is please help me
           parseManySpaces
           symbol '}'
           return $ Seq list

parseInstruction = do
                   many parsePleaseHelpMe 
                   parseAssign <|> parseIf <|> parseWhile <|> parseRead <|> parseWrite <|> parseReturn

parseLLang :: Parser String String LAst
parseLLang = do 
             parseManySpaces
             last <- parseSeq <|> parseInstruction
             parseManySpaces
             return last 
stmt :: LAst
stmt =
  Seq
    [ Read "x"
    , If (BinOp Gt (Ident "x") (Num 13))
         (Seq [(Write (Ident "x"))])
         (Seq [(While (BinOp Lt (Ident "x") (Num 42))
                (Seq [ Assign "x"
                        (BinOp Mult (Ident "x") (Num 7))
                     , Write (Ident "x")
                     ]
                )
         )])
    ]

-- i like parseLLang more
parseL :: Parser String String LAst
parseL = parseLLang


parseArgs :: Parser String String [Var]
parseArgs = do
            top <- parseIdentSpaces
            other <- many (symbol ',' *> parseIdentSpaces)
            return (top:other)
      where
      parseIdentSpaces = do
        parseManySpaces
        ident <- parseIdent
        parseManySpaces
        return ident           

parseDef :: Parser String String Function
parseDef = do
           parseManySpaces
           stringCompare "fun"
           parseSomeSpaces
           name <- parseIdent
           symbol '('
           args <- parseArgs <|> return []
           symbol ')'
           parseManySpaces
           body <- parseSeq
           symbol ';'
           parseManySpaces
           return $ Function name args body


parseProg :: Parser String String Program
parseProg = do
            parseManySpaces
            functions <- many parseDef
            parseManySpaces
            main <- parseLLang
            return $ Program functions main

initialConf :: [Int] -> Configuration
initialConf input = Conf Map.empty input []

instance Show Function where
  show (Function name args funBody) =
    printf "%s(%s) =\n%s" name (intercalate ", " $ map show args) (unlines $ map (identation 1) $ lines $ show funBody)

instance Show Program where
  show (Program defs main) =
    printf "%s\n\n%s" (intercalate "\n\n" $ map show defs) (show main)

instance Show LAst where
  show =
      go 0
    where
      go n t =
        let makeIdent = identation n in
        case t of
          If cond thn els -> makeIdent $ printf "if %s\n%sthen\n%s\n%selse\n%s" (flatShowExpr cond) (makeIdent "") (go (ident n) thn) (makeIdent "") (go (ident n) els)
          While cond body -> makeIdent $ printf "while %s\n%sdo\n%s" (flatShowExpr cond) (makeIdent "") (go (ident n) body)
          Assign var expr -> makeIdent $ printf "%s := %s" var (flatShowExpr expr)
          Read var        -> makeIdent $ printf "read %s" var
          Write expr      -> makeIdent $ printf "write %s" (flatShowExpr expr)
          Seq stmts       -> intercalate "\n" $ map (go n) stmts
          Return expr     -> makeIdent $ printf "return %s" (flatShowExpr expr)
      flatShowExpr (BinOp op l r) = printf "(%s %s %s)" (flatShowExpr l) (show op) (flatShowExpr r)
      flatShowExpr (UnaryOp op x) = printf "(%s %s)" (show op) (flatShowExpr x)
      flatShowExpr (Ident x) = x
      flatShowExpr (Num n) = show n
      flatShowExpr (FunctionCall name args) = printf "%s(%s)" name (intercalate ", " $ map flatShowExpr args)


ident = (+1)

identation n = if n > 0 then printf "%s|_%s" (concat $ replicate (n - 1) "| ") else id
eval (If cond thn els) conf@(Conf subst input output) = do
                                                cond_res <- evalExpr subst cond
                                                if (cond_res == 0) then
                                                  eval els conf
                                                else
                                                  eval thn conf

eval while@(While cond body) conf@(Conf subst input output) = do
                                                cond_res <- evalExpr subst cond
                                                if (cond_res == 0) then
                                                  return conf
                                                else
                                                  do 
                                                  body_res <- eval body conf
                                                  eval while body_res
eval (Assign var expr) conf@(Conf subst input output) = do
                                                        expr_res <- evalExpr subst expr
                                                        return $ Conf (Map.insert var expr_res subst) input output 
eval (Read var) conf@(Conf subst [] output) = Nothing 
eval (Read var) conf@(Conf subst (token:input) output) = return $ Conf (Map.insert var token subst) input output 

eval (Write expr) conf@(Conf subst input output) = do 
                                                   expr_res <- evalExpr subst expr
                                                   return $ Conf subst input (expr_res:output)

eval (Seq []) conf = Just conf  
eval (Seq (x:xs)) conf = do
                         first <- eval x conf
                         eval (Seq xs) first  

--data LAst
--  = If { cond :: Expr, thn :: LAst, els :: LAst }
--  | While { cond :: AST, body :: LAst }
--  | Assign { var :: Var, expr :: Expr }
--  | Read { var :: Var }
--  | Write { expr :: Expr }
--  | Seq { statements :: [LAst] }
--  deriving (Show, Eq)
