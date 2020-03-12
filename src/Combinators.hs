module Combinators where

import           Control.Applicative

data Result error input result
  = Success input result
  | Failure error
  deriving (Show, Eq) 

newtype Parser error input result
  = Parser { runParser :: input -> Result error input result }

instance Functor (Parser error input) where
  -- fmap :: (a -> b) -> Parser e i a -> Parser e i b
  fmap f p = Parser $ \input -> 
    case runParser p input of 
      Success i x -> Success i (f x)
      Failure e -> Failure e

instance Applicative (Parser error input) where
  -- pure :: a -> Parser e i a
  pure x = Parser $ \input -> Success input x
  -- <*> :: f (a -> b) -> f a -> f b 
  p <*> q = Parser $ \input ->
      case runParser p input of
        Failure e1   -> Failure e1
        Success i1 g ->   case runParser q i1 of
             Failure e2   -> Failure e2
             Success i2 y -> Success i2 (g y)

instance Monad (Parser error input) where
  return = pure

  p >>= f = Parser $ \input -> 
    case runParser p input of 
      Success i r -> runParser (f r) i
      Failure e   -> Failure e

instance Monoid error => Alternative (Parser error input) where
  empty = Parser $ \_ -> Failure mempty

  p <|> q = Parser $ \input ->
    case runParser p input of 
      Failure _ -> runParser q input
      x         -> x

-- Принимает последовательность элементов, разделенных разделителем
-- Первый аргумент -- парсер для разделителя
-- Второй аргумент -- парсер для элемента
-- В последовательности должен быть хотя бы один элемент
sepBy1 :: (Monoid e) => Parser e i sep -> Parser e i a -> Parser e i [a]
sepBy1 sep elem = do 
                  x  <- elem 
                  xs <- many (sep *> elem)
                  return (x:xs)


-- Проверяет, что первый элемент входной последовательности -- данный символ
symbol :: Char -> Parser String String Char
symbol c = satisfy (== c)

-- Успешно завершается, если последовательность содержит как минимум один элемент
elem' :: (Show a) => Parser String [a] a
elem' = satisfy (const True)

-- Проверяет, что первый элемент входной последовательности удовлетворяет предикату
satisfy :: Show a => (a -> Bool) -> Parser String [a] a
satisfy p = Parser $ \input ->
  case input of
    (x:xs) | p x -> Success xs x
    _       -> Failure $ "Predicate failed"

-- Успешно парсит пустую строку
epsilon :: Parser e i ()
epsilon = success ()

-- Всегда завершается успехом, вход не читает, возвращает данное значение
success :: a -> Parser e i a
success a = Parser $ \input -> Success input a

-- Всегда завершается ошибкой
fail' :: e -> Parser e i a
fail' = Parser . const . Failure
