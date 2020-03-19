module Expr where

import           AST         (AST (..), Operator (..))
import           Combinators (Parser (..), Result (..), elem', fail',
                             satisfy, success, sepBy1, symbol)
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
parseExpr :: Parser String String AST
parseExpr = uberExpr [(plus <|> minus, LeftAssoc), (mult <|> div', LeftAssoc), (pow, RightAssoc)]
                      (Num <$> parseNum <|> symbol '(' *> parseExpr <* symbol ')')
                      BinOp


pow   = symbol '^' >>= toOperator
plus  = symbol '+' >>= toOperator
minus = symbol '-' >>= toOperator
mult  = symbol '*' >>= toOperator
div'  = symbol '/' >>= toOperator

-- Парсер для натуральных чисел с 0
parseNum :: Parser String String Int
parseNum = foldl (\acc d -> 10 * acc + digitToInt d) 0 <$> go
  where
    go :: Parser String String String
    go = some (satisfy isDigit)

-- Парсер для операторов
parseOp :: Parser String String Operator
parseOp = elem' >>= toOperator

-- Преобразование символов операторов в операторы
toOperator :: Char -> Parser String String Operator
toOperator '+' = success Plus
toOperator '*' = success Mult
toOperator '-' = success Minus
toOperator '/' = success Div
toOperator '^' = success Pow
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
compute (BinOp Div x y)   = compute x ^ compute y
