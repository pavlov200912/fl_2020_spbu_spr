module Regexp where

import Prelude hiding (seq)

data Regexp = Empty
            | Epsilon
            | Char Char
            | Seq Regexp Regexp
            | Alt Regexp Regexp
            | Star Regexp
            deriving (Show, Eq, Ord)

match :: Regexp -> String -> Bool
match r s = nullable (foldl (flip derivative) r s)

derivative :: Char -> Regexp -> Regexp
derivative _ Empty = Empty
derivative _ Epsilon = Empty
derivative c (Char b) | b == c    = Epsilon
                      | otherwise = Empty
derivative c (Alt r1 r2) = Alt (derivative c r1) (derivative c r2)
derivative c (Seq r1 r2) | nullable r1 = Alt (Seq (derivative c r1) r2) (derivative c r2)
                         | otherwise   = Seq (derivative c r1) r2
derivative c (Star r) = Seq (derivative c r) (Star r)

nullable :: Regexp -> Bool
nullable (Star _ )= True
nullable Epsilon = True
nullable (Seq r1 r2) = (nullable r1) && (nullable r2)
nullable (Alt r1 r2) = (nullable r1) || (nullable r2)
nullable (Char _) = False
nullable Empty = False

