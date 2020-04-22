{-# LANGUAGE FlexibleInstances #-}

module Combinators where

import           Control.Applicative
import           Data.List           (nub, sortBy)

data Result error input result
  = Success (InputStream input) result
  | Failure [ErrorMsg error]


data Position = Position {line:: Int, col:: Int} deriving (Show, Eq)

newtype Parser error input result
  = Parser { runParser' :: (InputStream input) -> Result error input result }

data InputStream a = InputStream { stream :: a, curPos :: Position }
                   deriving (Show, Eq)

data ErrorMsg e = ErrorMsg { errors :: [e], pos :: Position }
                
instance Ord Position where
  (Position a b) < (Position a' b') = if a == a' then b < b' else a < a'
  a <= a' = a < a' || a == a'

makeError e p = ErrorMsg [e] p

initPosition = Position 0 0 

runParser :: Parser error input result -> input -> Result error input result
runParser parser input = runParser' parser (InputStream input initPosition)

toStream :: a -> Int -> InputStream a
toStream a x = InputStream a (Position 0 x)

incrPos :: Char -> InputStream String -> InputStream String
incrPos '\n' (InputStream str (Position x y)) = InputStream str (Position (x + 1) (0))
incrPos '\t' (InputStream str (Position x y)) = InputStream str (Position (x) (y + 4))
incrPos _    (InputStream str (Position x y)) = InputStream str (Position (x) (y + 1))

instance Functor (Parser error input) where
  -- fmap :: (a -> b) -> Parser e i a -> Parser e i b
  fmap f (Parser runp) = Parser $ \input -> 
    case runp input of 
      Success i x -> Success i (f x)
      Failure e -> Failure e

instance Applicative (Parser error input) where
  -- pure :: a -> Parser e i a
  pure x = Parser $ \input -> Success input x
  -- <*> :: f (a -> b) -> f a -> f b 
  (Parser runp) <*> (Parser runq) = Parser $ \input ->
      case runp input of
        Failure e1   -> Failure e1
        Success i1 g ->   case runq i1 of
             Failure e2   -> Failure e2
             Success i2 y -> Success i2 (g y)

instance Monad (Parser error input) where
  return = pure

  (Parser runp) >>= f = Parser $ \input -> 
    case runp input of 
      Success i r -> runParser' (f r) i
      Failure e   -> Failure e

instance Monoid error => Alternative (Parser error input) where
  empty = Parser $ \input -> Failure [makeError mempty (curPos input)]

  Parser a <|> Parser b = Parser $ \input ->
    case a input of
      Success input' r -> Success input' r
      Failure e ->
        case b input of
          Failure e' -> Failure $ mergeErrors e e'
          x          -> x

mergeErrors :: (Monoid e) => [ErrorMsg e] -> [ErrorMsg e] -> [ErrorMsg e]
mergeErrors e e' =
    merge (sortBy sorting e) (sortBy sorting e')
  where
    merge [] s = s
    merge s [] = s
    merge (ErrorMsg e p : xs) (ErrorMsg e' p' : xs') | p == p' = ErrorMsg (e <> e') p : merge xs xs'
    merge (ErrorMsg e p : xs) e'@(ErrorMsg _ p' : _) | p < p' = ErrorMsg e p : merge xs e'
    merge e@(ErrorMsg _ p : _) (ErrorMsg e' p' : xs) | p > p' = ErrorMsg e' p' : merge xs e

    sorting x y = pos x `compare` pos y

infixl 1 <?>
(<?>) :: Monoid error => error -> Parser error input a -> Parser error input a
(<?>) msg (Parser p) = Parser $ \input ->
    case p input of
      Failure err -> Failure $ mergeErrors [makeError msg (maximum $ map pos err)] err
      x -> x

-- Проверяет, что первый элемент входной последовательности -- данный символ
symbol :: Char -> Parser String String Char
symbol c = ("Expected symbol: " ++ show c) <?> satisfy (== c)

eof :: Parser String String ()
eof = Parser $ \input -> if null $ stream input then Success input () else Failure [makeError "Not eof" (curPos input)]
stringCompare :: String -> Parser String String String
stringCompare [] = success []
stringCompare (x:xs) = (:) <$> satisfy (== x) <*> stringCompare xs  
 

-- Успешно завершается, если последовательность содержит как минимум один элемент
elem' :: Parser String String Char
elem' = satisfy (const True)

elemSome' :: [String] -> Parser String String String
elemSome' []     = return ""
elemSome' (x:xs) = stringCompare x <|> elemSome' xs
-- Проверяет, что первый элемент входной последовательности удовлетворяет предикату
satisfy :: (Char -> Bool) -> Parser String String Char
satisfy p = Parser $ \(InputStream input pos) ->
  case input of
    (x:xs) | p x -> Success (incrPos x $ InputStream xs pos) x
    input        -> Failure [makeError "Predicate failed" pos]
    

satisfySome :: (Char -> Bool) -> Parser String String String
satisfySome p = some (satisfy p) 

-- Успешно парсит пустую строку
epsilon :: Parser e i ()
epsilon = success ()

-- Всегда завершается успехом, вход не читает, возвращает данное значение
success :: a -> Parser e i a
success a = Parser $ \input -> Success input a

-- Всегда завершается ошибкой
fail' :: e -> Parser e i a
fail' msg = Parser $ \input -> Failure [makeError msg (curPos input)]

word :: String -> Parser String String String
word w = Parser $ \(InputStream input (Position x y)) ->
  let (pref, suff) = splitAt (length w) input in
  if pref == w
  then Success (InputStream suff (Position x (y + length w))) w
  else Failure [makeError ("Expected " ++ show w) (Position x y)]

instance Show (ErrorMsg String) where
  show (ErrorMsg e pos) = "at position " ++ show pos ++ ":\n" ++ (unlines $ map ('\t':) (nub e))

instance (Show input, Show result) => Show (Result String input result) where
  show (Failure e) = "Parsing failed\n" ++ unlines (map show e)
  show (Success i r) = "Parsing succeeded!\nResult:\n" ++ show r ++ "\nSuffix:\t" ++ show i

instance Eq (ErrorMsg String) where
  e1 == e2 = (show e1) == (show e2)

instance (Show input, Show result) => Eq (Result String input result) where
  e1 == e2 = (show e1) == (show e2)
