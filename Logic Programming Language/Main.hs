module Main where

import Control.Monad (when)
import System.Directory (doesFileExist)
import System.Environment (getArgs)
import Data.Char
import Bird.Parser
import Bird.Printer

                        -- >> TODO list << --
-- [X] simple rules (the form p. for predicate p) and string literal terms
-- [X] queries (don’t need to handle variables yet)
-- [X] atom terms
-- [X] compound terms
-- [X] natural number literal terms
-- [X] list literal terms
-- [X] variable terms
-- [X] complex rules

main :: IO ()
main = do args <- getArgs
          let files = filter ("-p" /=) args
              go | elem "-p" args = goParse
                 | otherwise      = goEval
          when (not (null files)) $
              go  . concat =<< mapM readFile files

goParse, goEval :: String -> IO ()
goParse s = mapM_ (\(a,_) -> putStrLn a) (runParser parseIO (clean (removeComments s)))
goEval s  = mapM_ (\(es,_) -> putStrLn (evalExprs (es,[]))) (runParser parseExprs (clean (removeComments s)))

parseIO :: Parser String
parseIO = do x <- many parseLine
             return (prettyExprs x)

parseLine :: Parser Expr
parseLine = rule <|> query <|> compRule <|> multQuery

parseExprs :: Parser [Expr]
parseExprs = do many parseLine

evalExprs :: ([Expr],[Expr]) -> String
evalExprs ([], rules) = ""
evalExprs ([Rule s ts], rules) = ""
evalExprs ([Query s ts], rules) = evalQuery (Query s ts, rules)
evalExprs ([MultQuery es], rules) = evalMultQuery (MultQuery es, rules)
evalExprs (Rule s ts:es, rules) = evalExprs (es, rules ++ [Rule s ts])
evalExprs (Query s ts:es, rules) = evalQuery (Query s ts, rules) ++ evalExprs (es, rules)
evalExprs (MultQuery es':es, rules) = evalMultQuery (MultQuery es', rules) ++ evalExprs (es, rules)

evalQuery :: (Expr, [Expr]) -> String
evalQuery (Query s ts, rules)
    | null rules = "No.\n"
    | Rule s ts == head rules = "Yes.\n"
    | length rules == 1 = "No.\n"
    | otherwise = evalQuery (Query s ts, tail rules)

evalMultQuery :: (Expr, [Expr]) -> String
evalMultQuery (MultQuery es, rules)
    | null es = "Yes.\n"
    | evalQuery (head es, rules) == "Yes.\n" = evalMultQuery (MultQuery (tail es), rules)
    | otherwise = "No.\n"

-- >>> runParser parseLine "p(zero)?" == runParser parseLine "p(zero)?"
-- True

-- >>> tail [1]
-- []

-- prettyExpr :: Expr -> String
-- prettyExpr (Rule e ts) = e ++ "(" ++ init(init(prettyTerms ts)) ++ ")."

prettyExprs :: [Expr] -> String
prettyExprs [] = []
prettyExprs [Rule s ts] = s ++ "(" ++ prettyTerms ts ++ ")."
prettyExprs [Query s ts] = s ++ "(" ++ prettyTerms ts ++ ")?"
prettyExprs [ComplexRule e es] = init(prettyExprs [e]) ++ " :- " ++ rulesSepByCommas es
prettyExprs [MultQuery es] = rulesSepByCommas es
prettyExprs (MultQuery es':es) = rulesSepByCommas es' ++ prettyExprs es
prettyExprs (Rule s ts:es) = s ++ "(" ++ prettyTerms ts ++ ").\n" ++ prettyExprs es
prettyExprs (Query s ts:es) = s ++ "(" ++ prettyTerms ts ++ ")?\n" ++ prettyExprs es
prettyExprs (ComplexRule e es':es) = init(prettyExprs [e]) ++ " :- " ++ rulesSepByCommas es' ++ prettyExprs es

rulesSepByCommas :: [Expr] -> String
rulesSepByCommas [] = []
rulesSepByCommas [Query s ts] = s ++ "(" ++ prettyTerms ts ++ ")?\n"
rulesSepByCommas [Rule s ts] = s ++ "(" ++ prettyTerms ts ++ ").\n"
rulesSepByCommas (Rule s ts:es) = s ++ "(" ++ prettyTerms ts ++ "), " ++ rulesSepByCommas es
rulesSepByCommas (Query s ts:es) = s ++ "(" ++ prettyTerms ts ++ "), " ++ rulesSepByCommas es
rulesSepByCommas (MultQuery es':es) = []
rulesSepByCommas [ComplexRule e es] = []
rulesSepByCommas (ComplexRule e es':es) = []

-- prettyTerm :: Term -> String
-- prettyTerm (Atom s) = s
-- prettyTerm (Variable s) = s
-- prettyTerm (Str s) = s
-- prettyTerm (Compound s ts) = s ++ "(" ++ prettyTerms ts ++ ")"

prettyTerms :: [Term] -> String
prettyTerms [] = []
prettyTerms [Atom s] = s
prettyTerms [Variable s] = s
prettyTerms [Compound a b] = a ++ "(" ++ prettyTerms b ++ ")"
prettyTerms [Str s] = "\""++ s ++ "\""
prettyTerms (Atom s:ts) = s ++ ", " ++ prettyTerms ts
prettyTerms (Variable s:ts) = s ++ ", " ++ prettyTerms ts
prettyTerms (Str s:ts) = "\""++ s ++ "\"" ++ ", " ++ prettyTerms ts
prettyTerms (Compound s ts':ts) = s ++ "(" ++ prettyTerms ts' ++ ")," ++ prettyTerms ts
-- prettyTerms 

-- >>> runParser parseLine "parent(lee,virginia)."
-- [(Rule "parent" [Atom "lee",Atom "virginia"],"")]

-- >>> runParser query "P(X)?"
-- []

-- =/= PARSING/DATATYPES =/= --

data Term = Atom String | Variable String | Str String | Compound String [Term] deriving (Eq, Show)
data Expr = Rule String [Term] | Query String [Term] | MultQuery [Expr] | ComplexRule Expr [Expr] deriving (Eq, Show)

term :: Parser Term
term = comp <|> atom <|> var <|> str <|> peano

terms :: Parser Term
terms = do a <- term
           char ','
           return a

compRule :: Parser Expr -- "p(f):-p(e),p(a)."
compRule = do predHead <- many1 lower
              _ <- satisfy (=='(')
              ts <- many terms
              t <- term
              string "):-"
              es <- many subRule
              e <- rule
              return (ComplexRule (Rule predHead (ts++[t])) (es++[e]))

subRule :: Parser Expr
subRule = do predHead <- many1 lower
             _ <- satisfy (=='(')
             ts <- many terms
             t <- term
             string "),"
             return (Rule predHead (ts++[t]))

multQuery :: Parser Expr -- p(zero), p(one,two,three)?
multQuery = do es <- many1 subQuery
               e <- query
               return (MultQuery (es++[e]))

subQuery :: Parser Expr
subQuery = do qHead <- many1 alpha
              char '('
              ts <- many terms
              t <- term
              string "),"
              return (Query qHead (ts++[t]))

-- >>> runParser (many1 subQuery) "p(zero),"
-- [([Query "p" [Atom "zero"]],"")]

-- >>> runParser multQuery "p(a), pP(B),pPpp(stringstuff),qQ(x,y),pP(x,y),pPPPPP(x,y)?"
-- []

-- >>> runParser query "p(one,two,three)?"
-- [(Query "p" [Atom "one",Atom "two",Atom "three"],"")]

-- >>> runParser parseLine "lang(v("x"))."['l','a','n','g','(','v','(','"','x','"',')',')']
-- Variable not in scope: x

alpha :: Parser Char
alpha = satisfy isAlpha

atom :: Parser Term
atom = do as <- many1 lower
          return (Atom as)

var :: Parser Term
var = do a <- upper
         as <- many lower
         return (Variable (a:as))

str :: Parser Term
str = do _ <- satisfy (=='"')
         str <- many1 (satisfy (/='"'))
         _ <- satisfy (=='"')
         return (Str str)

--- >>> runParser term "length([])" 
-- [(Compound "length" [Atom "nil"],"")]

comp = comp1 <|> comp4 <|> comp3 <|> comp2 <|> comp5 <|> comp6

comp1 :: Parser Term
comp1 = do
          x <- many lower
          char '('
          ys <- many terms
          z <- term
          char ')'
          return (Compound x (ys++[z]) )
comp4 :: Parser Term
comp4 = do
    x <- many lower
    char '('
    y <- term
    char ')'
    return (Compound x [y])


comp2 :: Parser Term
comp2 = do
          _ <- many lower
          char '['
          y <- term
          char ']'
          return (compList [y] )
comp3 :: Parser Term
comp3 = do
          _ <- many lower
          char '['
          ys <- many terms
          z <- term
          char ']'
          return (compList (ys++[z]))
comp5 :: Parser Term
comp5 = do
          _ <- many lower
          char '['
          char ']'
          return (compList [])
comp6 :: Parser Term
comp6 = do
          char '['
          xs <- many terms
          y <- term
          char '|'
          z <- term
          char ']'
          return (compList2(xs++[y]) z)

compList:: [Term] -> Term
compList t
    | null t = Atom "nil"
    | head t == Atom "" = Atom "nil"
    | head t == Str "" = Atom "nil"
    | otherwise = Compound "cons" (head t : [compList(tail t)])

compList2 :: [Term] -> Term-> Term
compList2 t x
    | null t = x
    | otherwise = Compound "cons" (head t : [compList2(tail t)x])

rule :: Parser Expr
rule = do predHead <- many1 alpha
          _ <- satisfy (=='(')
          ts <- many terms
          t <- term
          string ")."
          return (Rule predHead (ts++[t]))

query :: Parser Expr
query = do qHead <- many1 alpha
           char '('
           ts <- many terms
           t <- term
           string ")?"
           return (Query qHead (ts++[t]))

-- >>> runParser query "p(x)?"
-- [(Query "p" [Atom "x"],"")]

peano :: Parser Term
peano = do d <- many1 digit
           return (makeTheBigSucc (joiner d))

makeTheBigSucc :: Int -> Term
makeTheBigSucc i
    | i == 0    = Atom "zero"
    | otherwise = Compound "succ" [makeTheBigSucc (i-1)]

joiner :: [Int] -> Int
joiner = read . concatMap show


-- >>> fst((runParser term (show "hell"))!!0)
-- Str "hell"

-- >>> runParser term "thisclasssucks"
-- [(Atom "thisclasssucks","")]

-- >>> runParser term "Pog"
-- [(Variable "Pog","")]

-- >>> runParser comp "succ(succ(zero))"
-- [(Compound "succ" [Compound "succ" [Atom "zero"]],"")]

-- >>> runParser rule "parent(lee,virginia)."
-- [(Rule "parent" [Atom "lee",Atom "virginia"],"")]

-- >>> runParser query "p(x)?"
-- [(Query "p" [Atom "x"],"")]

-- >>> runParser peano "100"
-- [(Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Compound "succ" [Atom "zero"]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]],"")]

-- =/= CLEAN UP METHODS =/= --

removeComments :: String -> String
removeComments ('%':xs) = removeLineComment xs
removeComments('"':xs) = '"':saveString xs
removeComments(' ':xs) = removeComments xs
removeComments (x:xs) = x : clean(removeComments xs)
removeComments [] = []

saveString :: String -> String
saveString ('"':xs) = '"': removeComments xs
saveString (x:xs) = x:saveString xs
saveString[] = []


removeLineComment :: String -> String
removeLineComment ('\n':xs) = removeComments (xs)
removeLineComment (_:xs) = removeLineComment xs
removeLineComment [] = []

removeNewLines :: String -> String
removeNewLines ('\n':xs) = removeNewLines xs
removeNewLines (x:xs) = x : removeNewLines xs
removeNewLines [] = []

removeSpaces :: String -> String
removeSpaces (' ':xs) = removeSpaces xs
removeSpaces (x:xs) = x : removeSpaces xs
removeSpaces [] = []

clean :: String -> String
clean = s . s
 where s = reverse . dropWhile isSpace
