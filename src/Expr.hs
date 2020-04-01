module Expr where

import           AST         (AST (..), Operator (..))
import           Combinators (Parser (..), Result (..), elem', elemSome', fail',
                             satisfy, success, sepBy1, symbol, stringCompare, satisfySome)
import           Data.Char   (digitToInt, isDigit)
import           Control.Applicative
import           Data.Function

data Associativity
  = LeftAssoc  -- 1 @ 2 @ 3 @ 4 = (((1 @ 2) @ 3) @ 4)
  | RightAssoc -- 1 @ 2 @ 3 @ 4 = (1 @ (2 @ (3 @ 4))
  | NoAssoc    -- Может быть только между двумя операндами: 1 @ 2 -- oк; 1 @ 2 @ 3 -- не ок

data OpType = Binary Associativity
            | Unary

uberExpr :: Monoid e
         => [(Parser e i op, OpType)] -- список операций с их арностью и, в случае бинарных, ассоциативностью
         -> Parser e i ast            -- парсер элементарного выражения
         -> (op -> ast -> ast -> ast) -- конструктор узла дерева для бинарной операции
         -> (op -> ast -> ast)        -- конструктор узла для унарной операции
         -> Parser e i ast
uberExpr [] eParser _ _ = eParser  
uberExpr opList eParser binNode unNode = foldr helper eParser opList where
  helper (opParser, Unary) exprParser = (
                                        do 
                                        op   <- opParser
                                        term <- exprParser
                                        return $ unNode op term
                                        ) <|> exprParser 
  helper (opParser, Binary NoAssoc) exprParser = do
                                                 term1 <- exprParser
                                                 (do
                                                  op    <- opParser
                                                  term2 <- exprParser
                                                  return $ binNode op term1 term2
                                                  ) <|> return term1
  helper (opParser, Binary LeftAssoc) exprParser = do
                                                   term1 <- exprParser
                                                   foldl (&) term1 <$> many (
                                                    do 
                                                    op <- opParser
                                                    term2 <- exprParser
                                                    return $ (flip $ binNode op) term2
                                                    )
  helper (opParser, Binary RightAssoc) exprParser = 
    let list = many (do 
                    term <- exprParser
                    op   <- opParser
                    return $ binNode op term) in (do
                                                  xs <- list
                                                  term <- exprParser
                                                  return $ foldr ($) term xs) <|> exprParser
                                                    

-- Парсер для выражений над +, -, *, /, ^ (возведение в степень)
-- с естественными приоритетами и ассоциативностью над натуральными числами с 0.
-- В строке могут быть скобки
parseExpr :: Parser String String AST
parseExpr = uberExpr [
                      (or', Binary RightAssoc),
                      (and', Binary RightAssoc),
                      (not', Unary),
                      (equal' <|> nequal' <|> ge' <|> le' <|> gt' <|> lt', Binary NoAssoc),
                      (plus' <|> minus', Binary LeftAssoc),
                      (mult' <|> div', Binary LeftAssoc),
                      (minus', Unary),
                      (pow', Binary RightAssoc)
                     ]
                     (Num <$> parseNum <|> Ident <$> parseIdent <|> symbol '(' *> parseExpr <* symbol ')')
                     BinOp
                     UnaryOp

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
not'    = stringCompare "!" >>= toOperator

-- Парсер для целых чисел
parseNegNum :: Parser String String Int
parseNegNum = foldl func 0 <$> go
  where
    go :: Parser String String String
    go = some (satisfy isDigit) <|> ((flip (++)) <$> many (symbol '-') <*> some (satisfy isDigit)) 
    func  acc ('-') = -acc 
    func  acc   d   = (digitToInt d) + 10 * acc
       
-- Парсер для положительных целых чисел
parseNum :: Parser String String Int
parseNum = foldl (\acc d -> 10 * acc + digitToInt d) 0 `fmap` go
    where
        go :: Parser String String String
        go = some (satisfy isDigit)

parseIdent :: Parser String String String
parseIdent = (:) <$> (letterParser <|> underscoreParser) <*> (many (letterParser <|> digitParser <|> underscoreParser))
  where digitParser = satisfy isDigit
        letterParser = satisfy (\x -> elem x (['a'..'z'] ++ ['A'..'Z']))
        underscoreParser = satisfy (== '_')

-- Парсер для операторов

parseOp :: Parser String String Operator
parseOp = elemSome' operators >>= toOperator

operators = ["+", "*", "-", "^", "/", ">=", "<=", ">", "<", "&&", "||", "==", "/="]


--parseOp = ((:[]) <$> (satisfy (const True)) >>= toOperator) <|>
--            (((:) <$> satisfy (const True) <*>((:[]) <$> (satisfy (const True))) >>= toOperator))



-- Преобразование символов операторов в операторы
toOperator :: String -> Parser String String Operator
toOperator "+" = success Plus
toOperator "*" = success Mult
toOperator "-" = success Minus
toOperator "/" = success Div
toOperator "^" = success Pow
toOperator "==" = success Equal
toOperator "/=" = success Nequal
toOperator ">" = success Gt
toOperator ">=" = success Ge
toOperator "<=" = success Le
toOperator "<" = success Lt
toOperator "&&" = success And
toOperator "||" = success Or
toOperator "!"  = success Not
toOperator _   = fail' "Failed toOperator"



evaluate :: String -> Maybe Int
evaluate input = do
  case runParser parseExpr input of
    Success rest ast | null rest -> return $ compute ast
    _                            -> Nothing

compute :: AST -> Int
compute (Num x)           = x
compute (BinOp Plus x y)  = compute x + compute y
compute (BinOp Mult x y)  = compute x * compute y
compute (BinOp Minus x y) = compute x - compute y
compute (BinOp Div x y)   = compute x `div` compute y
compute (BinOp Pow x y)   = compute x ^ compute y
compute (BinOp Equal x y)  = boolInt $ compute x == compute y
compute (BinOp Nequal x y) = boolInt $ compute x /= compute y
compute (BinOp Ge x y)     = boolInt $ compute x >= compute y
compute (BinOp Le x y)     = boolInt $ compute x <= compute y
compute (BinOp Gt x y)     = boolInt $ compute x < compute y
compute (BinOp Lt x y)     = boolInt $ compute x > compute y
compute (BinOp And x y)    = case compute x of
                                0 -> 0
                                _ -> compute y
compute (BinOp Or x y)     = case compute x of
                                0 -> compute y
                                x -> x

boolInt :: Bool -> Int
boolInt True = 1
boolInt False = 0