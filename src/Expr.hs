module Expr where

import           AST         (AST (..), Operator (..))
import           Combinators (Parser (..), Result (..), elem', elemSome', fail',
                             satisfy, success, sepBy1, symbol, stringCompare, satisfySome)
import           Data.Char   (digitToInt, isDigit)
import           Control.Applicative


data Associativity
  = LeftAssoc  -- 1 @ 2 @ 3 @ 4 = (((1 @ 2) @ 3) @ 4)
  | RightAssoc -- 1 @ 2 @ 3 @ 4 = (1 @ (2 @ (3 @ 4))
  | NoAssoc    -- Может быть только между двумя операндами: 1 @ 2 -- oк; 1 @ 2 @ 3 -- не ок

-- Универсальный парсер выражений
uberExpr :: Monoid e
         => [(Parser e i op, Associativity)] -- список парсеров бинарных операторов с ассоциативностями в порядке повышения приоритета
         -> Parser e i ast -- парсер для элементарного выражения
         -> (op -> ast -> ast -> ast) -- функция для создания абстрактного синтаксического дерева для бинарного оператора
         -> Parser e i ast
uberExpr [] elem _ = elem 
uberExpr ((p, assoc):ps) elem f = let recursive  = uberExpr ps elem f in
                                  let leftSide   = (,) <$> recursive <*> (many ((,) <$> p <*> recursive)) in
                                  let rightSide  = (,) <$> (many ((,) <$> recursive <*> p)) <*> recursive in
                                  (
                                  case assoc of
                                    NoAssoc -> do
                                              l <- recursive
                                              op <- p
                                              r <- recursive
                                              return $ f op l r
                                    LeftAssoc ->  do
                                                  (term, xs) <- leftSide
                                                  return $ foldl (\left (op, right) -> f op left right) term xs   
                                    RightAssoc -> do 
                                                  (term, xs) <- rightSide
                                                  return $ foldr (\(right, op) left -> f op right left) xs term        
                                  )
                                  <|> 
                                  recursive



-- Парсер для выражений над +, -, *, /, ^ (возведение в степень)
-- с естественными приоритетами и ассоциативностью над натуральными числами с 0.
-- В строке могут быть скобки
parseExpr = uberExpr [
                      (or', RightAssoc),
                      (and', RightAssoc),
                      (equal' <|> nequal' <|> ge' <|> le' <|> gt' <|> lt', NoAssoc),
                      (plus' <|> minus', LeftAssoc),
                      (mult' <|> div', LeftAssoc),
                      (pow', RightAssoc)
                     ]
                     (Num <$> parseNum <|> Ident <$> parseIdent <|> symbol '(' *> parseExpr <* symbol ')')
                     BinOp


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


-- Парсер для целых чисел
parseNum :: Parser String String Int
parseNum = foldl func 0 <$> go
  where
    go :: Parser String String String
    go = some (satisfy isDigit) <|> ((flip (++)) <$> many (symbol '-') <*> some (satisfy isDigit)) 
    func  acc ('-') = -acc 
    func  acc   d   = (digitToInt d) + 10 * acc
       


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