module LLang where

import AST (AST (..), Operator (..))
import Combinators (Parser (..), Result (..), elem', elemSome', fail',
                      satisfy, success, sepBy1, symbol, stringCompare, satisfySome)
import Expr (OpType (..), Associativity (..), uberExpr, toOperator)
import           Control.Applicative
import           Data.Char   (digitToInt, isDigit)
import Control.Monad

--import Data.Text

type Expr = AST

type Var = String

data LAst
  = If { cond :: Expr, thn :: LAst, els :: LAst }
  | While { cond :: AST, body :: LAst }
  | Assign { var :: Var, expr :: Expr }
  | Read { var :: Var }
  | Write { expr :: Expr }
  | Seq { statements :: [LAst] }
  deriving (Show, Eq)


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
                     (Num <$> parseNum <|> Ident <$> parseIdent <|> symbol '(' *> parseExpr <* symbol ')')
                     BinOp
                     UnaryOp


parseSomeSpaces :: Parser String String String
parseSomeSpaces = some (symbol ' ' <|> symbol '\n')

parseManySpaces = many (symbol ' ' <|> symbol '\n')

-- keywords that shouldn't be parsed like Var
indentKeywords = ["esle", "poka", "read", "print", "please", "help", "me"]

-- please help me - блок обязан быть отделен пробелами от другого кода
parsePleaseHelpMe :: Parser String String String
parsePleaseHelpMe = do
                    parseSomeSpaces
                    parsePleaseBlock
                    return ""
                    where
  keywordsParser = stringCompare "please" <|> stringCompare "help" <|> stringCompare "me"
  parsePleaseBlock = do
                     keywordsParser
                     many (do 
                       parseSomeSpaces
                       keywordsParser
                       return "")
                     return "" 

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
                   parseAssign <|> parseIf <|> parseWhile <|> parseRead <|> parseWrite

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