module Test.LLang where

import           Test.Tasty.HUnit    (Assertion, (@?=), assertBool)

import           AST                 (AST (..), Operator (..))
import LLang    (parseExpr, parseIdent,
                    parseNum, parseAssign, parseRead, parseIf, 
                    parseLLang, parseWrite, parseSeq, parseWhile, 
                    parsePleaseHelpMe, stmt, LAst (..))
import           Combinators         (Parser (..), Result (..), runParser,
                                      symbol, sepBy1, stringCompare)



isFailure (Failure _) = True
isFailure  _          = False


unit_parsePleaseHelpMe :: Assertion
unit_parsePleaseHelpMe = do 
    runParser parsePleaseHelpMe "please " @?= 
        Success "" ""
    runParser parsePleaseHelpMe "please()" @?= 
        Success "" ""
    runParser parsePleaseHelpMe "please(x)" @?= 
        Success "" ""
    runParser parsePleaseHelpMe "please(x+1)" @?= 
        Success "" ""


unit_parseIdent :: Assertion
unit_parseIdent = do
    runParser parseIdent "abc def" @?= Success " def" "abc"
    runParser parseIdent "AbC dEf" @?= Success " dEf" "AbC"
    runParser parseIdent "a~b~c d~e" @?= Success " d~e" "a~b~c"
    runParser parseIdent "x~ " @?= Success " " "x~"
    runParser parseIdent "abc123" @?= Success "" "abc123"
    runParser parseIdent "abc*1" @?= Success "*1" "abc"
    assertBool "" $ isFailure $ runParser parseIdent "123abc"
    assertBool "" $ isFailure $ runParser parseIdent "123"
    assertBool "" $ isFailure $ runParser parseIdent ""
    assertBool "" $ isFailure $ runParser parseIdent "_123"
    assertBool "" $ isFailure $ runParser parseIdent "_"


unit_parseNum :: Assertion
unit_parseNum = do
    runParser parseNum "7" @?= Success "" 7
    runParser parseNum "12+3" @?= Success "+3" 12
    runParser parseNum "007" @?= Success "" 7
    runParser parseNum "0.123" @?= Success "" 123
    runParser parseNum "124.124" @?= Success "" 124124
    assertBool "" $ isFailure (runParser parseNum "+3")
    assertBool "" $ isFailure (runParser parseNum "a")

unit_parseAssign :: Assertion
unit_parseAssign = do
    runParser parseAssign "ident := 123;" @?= Success "" (Assign "ident" (Num 123))
    runParser parseAssign "kek123 := 12*2;" @?= Success "" (Assign "kek123" (BinOp Mult (Num 12) (Num 2)))
    runParser parseAssign "    ident~ :=    12;" @?= Success "" (Assign "ident~" (Num 12))
    assertBool "" $ isFailure (runParser parseAssign " esle := 123;")
    assertBool "" $ isFailure (runParser parseAssign " poka := 123;")
    assertBool "" $ isFailure (runParser parseAssign " ident := 12 3;")
    assertBool "" $ isFailure (runParser parseAssign " ident := 123")
    assertBool "" $ isFailure (runParser parseAssign " ident:= 123")
    assertBool "" $ isFailure (runParser parseAssign " ident :=123")
   
unit_parseWhile :: Assertion
unit_parseWhile = do
    runParser parseWhile " poka (x>0) {};" @?= Success "" (While (BinOp Gt (Ident "x") (Num 0)) (Seq []))
    runParser parseWhile " poka (x==0) {x := x+1; print(x);};" @?= 
        Success "" (While (BinOp Equal (Ident "x") (Num 0))
         (Seq [Assign "x" (BinOp Plus (Ident "x") (Num 1)), Write $ Ident "x"]))
    runParser parseWhile "poka (koronavirus==1) {print(stay~at~home);};" @?= 
        Success "" (While (BinOp Equal (Ident "koronavirus") (Num 1)) (Seq [Write $ Ident "stay~at~home"]))
    assertBool "" $ isFailure (runParser parseWhile "poka (x==0) {x := x+1; print(x);}")
    assertBool "" $ isFailure (runParser parseWhile "poka (x!=0) {x := x+1; print(x);};")

unit_parseWrite :: Assertion
unit_parseWrite = do
    runParser parseWrite "print(123+2);" @?= Success "" (Write $ BinOp Plus (Num 123) (Num 2))
    runParser parseWrite "print(hello~world);" @?= 
        Success "" (Write $ Ident "hello~world")
    runParser parseWrite "print(0);" @?= Success "" (Write $ Num 0)
    assertBool "" $ isFailure (runParser parseWrite "print(1+esle);")
    assertBool "" $ isFailure (runParser parseWrite "print( 1);")
    assertBool "" $ isFailure (runParser parseWrite "print(123)")
    assertBool "" $ isFailure (runParser parseWrite "pritn(123);")

unit_parseRead :: Assertion
unit_parseRead = do
    runParser parseRead "read(x123);" @?= Success "" (Read "x123")
    runParser parseRead "read(hello~world);" @?= 
        Success "" (Read "hello~world")
    assertBool "" $ isFailure (runParser parseRead "read(0);")
    assertBool "" $ isFailure (runParser parseRead "read(esle);")
    assertBool "" $ isFailure (runParser parseRead "raed(x);")
    assertBool "" $ isFailure (runParser parseRead "read(y)")
    assertBool "" $ isFailure (runParser parseRead "read(x_y);")

unit_parseSeq :: Assertion
unit_parseSeq = do 
    runParser parseSeq "{}" @?= Success "" (Seq [])
    runParser parseSeq "{      }" @?= Success "" (Seq [])
    runParser parseSeq "{x := 1;}" @?= Success "" (Seq [Assign "x" (Num 1)])
    runParser parseSeq "{x := 1;x := 2;}" @?= Success "" (Seq [Assign "x" (Num 1), Assign "x" (Num 2)])
    runParser parseSeq "{read(x); print(12);}" @?= 
        Success "" (Seq [Read "x", Write $ Num 12])
    runParser parseSeq "{esle (0) then {} else {};}" @?= 
        Success ""  (Seq [If (Num 0) (Seq []) (Seq [])])
    runParser parseSeq "{esle (0) then {} else {};  print(12);  read(x);  }" @?= 
        Success ""  (Seq [If (Num 0) (Seq []) (Seq []), Write $ Num 12, Read "x"])

unit_parseIf :: Assertion
unit_parseIf = do
    runParser parseIf "esle (0) then {} else {};" @?= Success "" (If (Num 0) (Seq []) (Seq []))
    runParser parseIf "esle (x==0) then {print(x);} else {read(x);};" @?= 
        Success "" (If (BinOp Equal (Ident "x") (Num 0)) (Seq [Write $ Ident "x"]) (Seq [Read "x"]))
    assertBool "" $ isFailure (runParser parseRead "else (0) then {} else {};")
    assertBool "" $ isFailure (runParser parseRead "esle (0) then {} else {}")
    assertBool "" $ isFailure (runParser parseRead "esle (0) then {} esle {}")
    assertBool "" $ isFailure (runParser parseRead "esle (0) then print(0); else {};")

unit_parseLLang :: Assertion
unit_parseLLang = do
    runParser parseLLang 
            "{ \
               \ read(x); \
               \ esle (x>13) \
               \ then { \ 
               \     print(x); \
               \     please help me \
               \ } \
               \ else { \
               \     poka (x<42) { \
               \         x := x*7; \
               \         print(x);\ 
               \     };\ 
               \ }; \
            \}"  @?= 
                Success "" stmt
    runParser parseLLang "{read(x);print(xplease);}" @?=
        Success "" (Seq [Read "x", Write (Ident "xplease")])

    runParser parseLLang "{ please help me }" @?=
        Success "" (Seq [])

    runParser parseLLang "{ please(x) me help please read(x);please help }" @?=
        Success "" (Seq [Read "x"])

    runParser parseLLang "{ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \ please help me \
    \}" @?= Success "" (Seq [])