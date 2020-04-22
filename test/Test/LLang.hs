module Test.LLang where

-- import           AST
-- import           Combinators      (Result (..), runParser, toStream)
-- import qualified Data.Map         as Map
-- import           Debug.Trace      (trace)
-- import           LLang            (Configuration (..), LAst (..), eval,
--                                    initialConf, parseL, Function (..), Program (..))
-- import           Test.Tasty.HUnit (Assertion, assertBool, (@?=))
-- import           Text.Printf      (printf)

import           Test.Tasty.HUnit    (Assertion, (@?=), assertBool)

import           AST                 (AST (..), Operator (..))
import LLang    (parseFunctionCall, parseDef, parseProg, parseExpr, parseIdent,
                    parseNum, parseAssign, parseRead, parseIf, 
                    parseLLang, parseWrite, parseSeq, parseWhile, 
                    parsePleaseHelpMe, stmt, LAst (..), Program (..), Function (..))
import           Combinators         (Position (..), InputStream (..), toStream, Parser (..), Result (..), runParser,
                                      symbol,  stringCompare)


import           Control.Applicative
-- f x y = read z ; return (x + z * y)
-- g x = if (x) then return x else return x*13
-- {read x; read y; write (f x y); write (g x)}"
--
--
--
--prog =
--  Program
--    [ Function "f" ["x", "y"] (Seq [Read "z", Return (BinOp Plus (Ident "x") (Ident "y"))])
--    , Function "g" ["x"] (If (Ident "x") (Return (Ident "x")) (Return (BinOp Mult (Ident "x") (Num 13))))
--    ]
--    (
--      Seq
--        [ Read "x"
--        , Read "y"
--        , Write (FunctionCall "f" [Ident "x", Ident "y"])
--        , Write (FunctionCall "g" [Ident "x"])
--        ]
--    )
---- -- f x y = read z ; return (x + z * y)
---- -- g x = if (x) then return x else return x*13
---- -- {read x; read y; write (f x y); write (g x)}"
--
---- prog =
----   Program
----     [ Function "f" ["x", "y"] (Seq [Read "z", Return (BinOp Plus (Ident "x") (Ident "y"))])
--     , Function "g" ["x"] (If (Ident "x") (Return (Ident "x")) (Return (BinOp Mult (Ident "x") (Num 13))))
--     ]
--     (
--       Seq
--         [ Read "x"
--         , Read "y"
--         , Write (FunctionCall "f" [Ident "x", Ident "y"])
--         , Write (FunctionCall "g" [Ident "x"])
--         ]
--     )

-- -- read x;
-- -- if (x > 13)
-- -- then { write x }
-- -- else {
-- --     while (x < 42) {
-- --       x := x * 7;
-- --       write (x);
-- --     }
-- -- }
-- stmt1 :: LAst
-- stmt1 =
--   Seq
--     [ Read "x"
--     , If (BinOp Gt (Ident "x") (Num 13))
--          (Seq [(Write (Ident "x"))])
--          (Seq [(While (BinOp Lt (Ident "x") (Num 42))
--                 (Seq [ Assign "x"
--                         (BinOp Mult (Ident "x") (Num 7))
--                      , Write (Ident "x")
--                      ]
--                 )
--          )])
--     ]

-- unit_stmt1 :: Assertion
-- unit_stmt1 = do
--   let xIs n = Map.fromList [("x", n)]
--   eval stmt1 (initialConf [1]) @?= Just (Conf (xIs 49) [] [49, 7])
--   eval stmt1 (initialConf [10]) @?= Just (Conf (xIs 70) [] [70])
--   eval stmt1 (initialConf [42]) @?= Just (Conf (xIs 42) [] [42])


-- -- read x;
-- -- if (x)
-- -- then {
-- --   while (x) {
-- --     x := x - 2;
-- --     write (x);
-- --   }
-- -- else {}
-- stmt2 :: LAst
-- stmt2 =
--   Seq
--     [ Read "x"
--     , If (Ident "x")
--          (Seq [(While (Ident "x")
--                 (Seq
--                    [ (Assign "x" (BinOp Minus (Ident "x") (Num 2)))
--                    , (Write (Ident "x"))
--                    ]
--                 )
--          )])
--          (Seq [])
--     ]

-- unit_stmt2 :: Assertion
-- unit_stmt2 = do
--   let xIs n = Map.fromList [("x", n)]
--   eval stmt2 (initialConf [0]) @?= Just (Conf (xIs 0) [] [])
--   eval stmt2 (initialConf [2]) @?= Just (Conf (xIs 0) [] [0])
--   eval stmt2 (initialConf [42]) @?= Just (Conf (xIs 0) [] (filter even [0 .. 40]))

-- -- read x;
-- -- read y;
-- -- write (x == y);
-- stmt3 :: LAst
-- stmt3 =
--   Seq
--     [ Read "x"
--     , Read "y"
--     , Write (BinOp Equal (Ident "x") ((Ident "y")))
--     ]

-- unit_stmt3 :: Assertion
-- unit_stmt3 = do
--   let subst x y = Map.fromList [("x", x), ("y", y) ]
--   eval stmt3 (initialConf [0, 2]) @?= Just (Conf (subst 0 2) [] [0])
--   eval stmt3 (initialConf [2, 2]) @?= Just (Conf (subst 2 2) [] [1])
--   eval stmt3 (initialConf [42]) @?= Nothing

-- -- read n;
-- -- if (n == 1 || n == 2)
-- -- then {
-- --   write 1;
-- -- }
-- -- else {
-- --   i := 2;
-- --   cur := 1
-- --   prev := 1
-- --   while (i < n) {
-- --     temp := cur + prev;
-- --     prev := cur;
-- --     cur := temp;
-- --     i := i + 1;
-- --   }
-- --   write (cur);
-- -- }
-- stmt4 :: LAst
-- stmt4 =
--   Seq
--     [ Read "n"
--     , If (BinOp Or (BinOp Equal (Ident "n") (Num 1)) (BinOp Equal (Ident "n") (Num 2)))
--          (Seq [(Write (Num 1))])
--          (Seq
--             [ Assign "i" (Num 2)
--             , Assign "cur" (Num 1)
--             , Assign "prev" (Num 1)
--             , While (BinOp Lt (Ident "i") (Ident "n"))
--                      (Seq
--                         [ Assign "temp" (BinOp Plus (Ident "cur") (Ident "prev"))
--                         , Assign "prev" (Ident "cur")
--                         , Assign "cur" (Ident "temp")
--                         , Assign "i" (BinOp Plus (Ident "i") (Num 1))
--                         ]
--                      )
--             , Write (Ident "cur")
--             ]
--          )
--     ]

--unit_stmt4 :: Assertion
--unit_stmt4 = do
--  let subst n i cur prev temp = Map.fromList [("n", n), ("i", i), ("cur", cur), ("prev", prev), ("temp", temp)]
--  let subst' n = Map.fromList [("n", n)]
--  eval stmt4 (initialConf [1]) @?= Just (Conf (subst' 1) [] [1])
--  eval stmt4 (initialConf [2]) @?= Just (Conf (subst' 2) [] [1])
--  eval stmt4 (initialConf [10]) @?= Just (Conf (subst 10 10 55 34 55) [] [55] )
--  eval stmt4 (initialConf []) @?= Nothing

unit_parsePleaseHelpMe :: Assertion
unit_parsePleaseHelpMe = do 
    assertParser parsePleaseHelpMe "please "  ""
    assertParser parsePleaseHelpMe "please()"  ""
    assertParser parsePleaseHelpMe "please(x)"  ""
    assertParser parsePleaseHelpMe "please;" ""
    assertParser (many parsePleaseHelpMe) "please;help;me;" ["", "", ""]

assertParser :: (Eq a, Show a) => Parser String String a -> String -> a -> Assertion
assertParser p str a = do
  runParser p str @?= Success (toStream "" (length str)) a 


assertPosParser :: (Eq a, Show a) => Parser String String a -> String -> a -> (Int, Int) -> Assertion 
assertPosParser p str a (x, y) = do
    runParser p str @?= Success (InputStream "" (Position x y)) a

isFailure (Failure _) = True
isFailure  _          = False

unit_parseIdent :: Assertion
unit_parseIdent = do
    assertBool "" $ isFailure $ runParser parseIdent "123abc"
    assertBool "" $ isFailure $ runParser parseIdent "123"
    assertBool "" $ isFailure $ runParser parseIdent ""
    assertBool "" $ isFailure $ runParser parseIdent "_123"
    assertBool "" $ isFailure $ runParser parseIdent "_"


unit_parseNum :: Assertion
unit_parseNum = do
    assertParser parseNum "7"  7
    assertParser parseNum "12" 12
    assertParser parseNum "007" 7
    assertParser parseNum "0.123" 123
    assertParser parseNum "124.124" 124124
    assertBool "" $ isFailure (runParser parseNum "+3")
    assertBool "" $ isFailure (runParser parseNum "a")


unit_parseAssign :: Assertion
unit_parseAssign = do
    assertParser parseAssign "ident := 123;"  (Assign "ident" (Num 123))
    assertParser parseAssign "kek123 := 12*2;" (Assign "kek123" (BinOp Mult (Num 12) (Num 2)))
    assertParser parseAssign "    ident~ :=    12;" (Assign "ident~" (Num 12))
    assertBool "" $ isFailure (runParser parseAssign " esle := 123;")
    assertBool "" $ isFailure (runParser parseAssign " poka := 123;")
    assertBool "" $ isFailure (runParser parseAssign " ident := 12 3;")
    assertBool "" $ isFailure (runParser parseAssign " ident := 123")
    assertBool "" $ isFailure (runParser parseAssign " ident:= 123")
    assertBool "" $ isFailure (runParser parseAssign " ident :=123")
   
unit_parseWhile :: Assertion
unit_parseWhile = do
    assertParser parseWhile " poka (x>0) {};"  (While (BinOp Gt (Ident "x") (Num 0)) (Seq []))
    assertParser parseWhile " poka (x==0) {x := x+1; print(x);};"  
        (While (BinOp Equal (Ident "x") (Num 0))
         (Seq [Assign "x" (BinOp Plus (Ident "x") (Num 1)), Write $ Ident "x"]))
    assertParser parseWhile "poka (koronavirus==1) {print(stay~at~home);};"
          (While (BinOp Equal (Ident "koronavirus") (Num 1)) (Seq [Write $ Ident "stay~at~home"]))
    assertBool "" $ isFailure (runParser parseWhile "poka (x==0) {x := x+1; print(x);}")
    assertBool "" $ isFailure (runParser parseWhile "poka (x!=0) {x := x+1; print(x);};")

unit_parseWrite :: Assertion
unit_parseWrite = do
    assertParser parseWrite "print(123+2);"  (Write $ BinOp Plus (Num 123) (Num 2))
    assertParser parseWrite "print(hello~world);" (Write $ Ident "hello~world")
    assertParser parseWrite "print(0);"(Write $ Num 0)
    assertBool "" $ isFailure (runParser parseWrite "print(1+esle);")
    assertBool "" $ isFailure (runParser parseWrite "print( 1);")
    assertBool "" $ isFailure (runParser parseWrite "print(123)")
    assertBool "" $ isFailure (runParser parseWrite "pritn(123);")

unit_parseRead :: Assertion
unit_parseRead = do
    assertParser parseRead "read(x123);" (Read "x123")
    assertParser parseRead "read(hello~world);"  (Read "hello~world")
    assertBool "" $ isFailure (runParser parseRead "read(0);")
    assertBool "" $ isFailure (runParser parseRead "read(esle);")
    assertBool "" $ isFailure (runParser parseRead "raed(x);")
    assertBool "" $ isFailure (runParser parseRead "read(y)")
    assertBool "" $ isFailure (runParser parseRead "read(x_y);")

unit_parseSeq :: Assertion
unit_parseSeq = do 
    assertParser parseSeq "{}" (Seq [])
    assertParser parseSeq "{      }"  (Seq [])
    assertParser parseSeq "{x := 1;}" (Seq [Assign "x" (Num 1)])
    assertParser parseSeq "{x := 1;x := 2;}" (Seq [Assign "x" (Num 1), Assign "x" (Num 2)])
    assertParser parseSeq "{read(x); print(12);}" (Seq [Read "x", Write $ Num 12])
    assertParser parseSeq "{esle (0) then {} else {};}"  (Seq [If (Num 0) (Seq []) (Seq [])])
    assertParser parseSeq "{esle (0) then {} else {};  print(12);  read(x);  }" (Seq [If (Num 0) (Seq []) (Seq []), Write $ Num 12, Read "x"])

unit_parseIf :: Assertion
unit_parseIf = do
    assertParser parseIf "esle (0) then {} else {};"  (If (Num 0) (Seq []) (Seq []))
    assertParser parseIf "esle (x==0) then {print(x);} else {read(x);};"  (If (BinOp Equal (Ident "x") (Num 0)) (Seq [Write $ Ident "x"]) (Seq [Read "x"]))
    assertBool "" $ isFailure (runParser parseRead "else (0) then {} else {};")
    assertBool "" $ isFailure (runParser parseRead "esle (0) then {} else {}")
    assertBool "" $ isFailure (runParser parseRead "esle (0) then {} esle {}")
    assertBool "" $ isFailure (runParser parseRead "esle (0) then print(0); else {};")

unit_parseLLang :: Assertion
unit_parseLLang = do
    assertParser parseLLang 
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
            \}" stmt
    assertParser parseLLang "{read(x);print(xplease);}" (Seq [Read "x", Write (Ident "xplease")])

    assertParser parseLLang "{ please help me }"  (Seq [])

    assertParser parseLLang "{ please(x) me help please read(x);please help }" (Seq [Read "x"])

    assertParser parseLLang "{read(x);please }" (Seq [Read "x"])

    assertParser parseLLang "{read(x);please;}" (Seq [Read "x"])
    
    assertParser parseLLang "{read(x);please}" (Seq [Read "x"])

    assertParser parseLLang "{read(x);please;help}" (Seq [Read "x"])

    assertParser parseLLang "{read(x);please(x)}"  (Seq [Read "x"])


unit_parseFunctionCall :: Assertion
unit_parseFunctionCall = do
  assertParser parseFunctionCall "func()" (FunctionCall "func" [])
  assertParser parseFunctionCall "func(1, 2, 3)" (FunctionCall "func" [Num 1, Num 2, Num 3])
  assertParser parseFunctionCall "func(fib(15))" (FunctionCall "func" [FunctionCall "fib" [Num 15]])

unit_parseDef :: Assertion
unit_parseDef = do 
  assertParser parseDef "fun kek() {return 0;};" (Function "kek" [] (Seq []) (Num 0))
  assertParser parseDef "fun kek(x1) {return 0;};" (Function "kek" ["x1"] (Seq []) (Num 0))
  assertParser parseDef "fun kek(x1, x2) {return 0;};" (Function "kek" ["x1", "x2"] (Seq []) (Num 0))
  assertParser parseDef "fun kek(x1, x2   , x3   ) {return 0;};" (Function "kek" ["x1", "x2", "x3"] (Seq []) (Num 0))
  assertParser parseDef "fun kek(x) {print(x); please help me return 1;};" (Function "kek" ["x"] (Seq [Write (Ident "x")]) (Num 1))
  assertParser parseDef "fun succ(x) {return x+1;};" (Function "succ" ["x"] (Seq []) (BinOp Plus (Ident "x") (Num 1)))
  assertParser parseDef ("fun fib(n) \
                                \{ \
                                \ ret := 0; \
                                \ esle (n==1||n==0) \ 
                                \ then {ret := 1;} \
                                \ else {ret := fib(n-1)+fib(n-2);}; \
                                \ return ret; \
                                \};") (Function "fib" ["n"] (Seq [
                                    Assign "ret" (Num 0), 
                                  If (BinOp Or (BinOp Equal (Ident "n") (Num 1)) (BinOp Equal (Ident "n") (Num 0))) 
                                  (Seq [Assign "ret" (Num 1)]) (Seq [Assign "ret" (BinOp Plus (
                                    FunctionCall "fib" [BinOp Minus (Ident "n") (Num 1)]
                                  ) (
                                    FunctionCall "fib" [BinOp Minus (Ident "n") (Num 2)]
                                  ))])
                                ]) (Ident "ret")) 
--
unit_parseProg :: Assertion
unit_parseProg = do
  assertParser parseProg "{read(x);print(xplease);}" (Program [] (Seq [Read "x", Write (Ident "xplease")]))
 
  assertParser parseProg "fun succ(x) {return x+1;}; {x := 0; read(x); print(succ(x));}"  (Program [Function "succ" ["x"] (Seq []) ((BinOp Plus (Ident "x") (Num 1)))] (Seq [Assign "x" (Num 0), Read "x", 
      Write (FunctionCall "succ" [Ident "x"])]))
  assertParser parseProg  
                "fun fib(n) \
                    \{ \
                    \ ret := 0; \
                    \ esle (n==1||n==0) \ 
                    \ then {ret := 1;} \
                    \ else {ret := fib(n-1)+fib(n-2);}; \
                    \ return ret; \
                    \}; \
              \ { \
              \ read(x); \
              \ print(fib(x)); \
              \ }"
                (Program [(Function "fib" ["n"] (Seq [
                                    Assign "ret" (Num 0), 
                                  If (BinOp Or (BinOp Equal (Ident "n") (Num 1)) (BinOp Equal (Ident "n") (Num 0))) 
                                  (Seq [Assign "ret" (Num 1)]) (Seq [Assign "ret" (BinOp Plus (
                                    FunctionCall "fib" [BinOp Minus (Ident "n") (Num 1)]
                                  ) (
                                    FunctionCall "fib" [BinOp Minus (Ident "n") (Num 2)]
                                  ))])
                                ]) (Ident "ret"))  ] 
                (Seq [Read "x", Write (FunctionCall "fib" [Ident "x"])]))


unit_lines::Assertion
unit_lines = do
    assertPosParser parseLLang ("{\n" ++
                               "help; me; please; \n" ++
                               "please;\n" ++ 
                               "}") (Seq []) (3, 1)
    assertPosParser parseLLang ("{\n" ++
                               "please;\n" ++ 
                               "}") (Seq []) (2, 1)

    assertPosParser parseLLang ("{\n" ++
                               "please;\n" ++ 
                               "help;\n" ++ 
                               "me;\n" ++
                               "print(auktyon~are~showing~their~new~albom~now~so~this~is~the~last~test~sorry);\n" ++
                               "}") (Seq [Write (Ident "auktyon~are~showing~their~new~albom~now~so~this~is~the~last~test~sorry")]) 
                               (5, 1)