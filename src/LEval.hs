module LEval where

import qualified Data.Map    as Map
import Combinators (InputStream (..), Result (..), runParser)
import LLang (eval, Program (..), Configuration (..), Function (..), parseProg)

evalProg :: Program -> [Int] -> Maybe Configuration
evalProg (Program functions main) input = 
    eval main (Conf Map.empty input [] 
    (Map.fromList $ helper <$> functions))
    where helper f@(Function name _ _ _) = (name, f) 

parseAndEvalProg :: String -> [Int] -> Maybe Configuration
parseAndEvalProg code input = do
    (Success (InputStream _ _) prog) <- return $ runParser parseProg code
    evalProg prog input