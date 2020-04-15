module Test.Combinators where

import           Test.Tasty.HUnit    (Assertion, assertBool, (@?=))
import           Combinators      (Parser, Result (..), elem', runParser,
                                   satisfy, sepBy1, symbol, toStream)
import           Control.Applicative

testFailure = assertBool "" . isFailure

isFailure (Failure _) = True
isFailure _           = False

digit :: Parser String String Char
digit = satisfy (`elem` "0123456789")

unit_satisfy :: Assertion
unit_satisfy = do
    testFailure $ runParser (satisfy (/= '1')) "1234"
    runParser (satisfy (== '1')) "1234" @?= Success (toStream "234" 1) '1'
    runParser digit "1234" @?= Success (toStream "234" 1) '1'
    testFailure $ runParser digit "blah"

unit_many :: Assertion
unit_many = do
    runParser (many $ symbol '1') "234" @?= Success "234" ""
    runParser (many $ symbol '1') "134" @?= Success "34" "1"
    runParser (many $ symbol '1') "114" @?= Success "4" "11"
    runParser (many $ symbol '1') "111" @?= Success "" "111"

unit_some :: Assertion
unit_some = do
    runParser (some $ symbol '1') "234" @?= Failure predErrMsg
    runParser (some $ symbol '1') "134" @?= Success "34" "1"
    runParser (some $ symbol '1') "114" @?= Success "4" "11"
    runParser (some $ symbol '1') "111" @?= Success "" "111"

unit_sepBy :: Assertion
unit_sepBy = do
    runParser (sepBy1 (symbol ',') digit) "" @?= Failure predErrMsg
    runParser (sepBy1 (symbol ',') digit) "1" @?= Success "" ['1']
    runParser (sepBy1 (symbol ',') digit) "1,4," @?= Success "," ['1', '4']
    runParser (sepBy1 (symbol ',') digit) "1,2,4," @?= Success "," ['1', '2', '4']
    runParser (sepBy1 (symbol ',') digit) "1,2,3,4," @?= Success "," ['1', '2', '3','4']
    runParser (sepBy1 (symbol ',') digit) "1,2,3,4,5" @?= Success "" ['1', '2', '3','4','5']
    runParser (sepBy1 (symbol ',') digit) "1,2,3,4," @?= Success "," ['1','2','3','4']
    
