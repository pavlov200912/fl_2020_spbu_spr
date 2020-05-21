# 1. Иван Олейник

Получилось составить такую грамматику.

Парсер грамматик Ивана ее успешно принял. Класс!!
```
!S >=> !GRAMMAR
!GRAMMAR >=> !SOMERULES
!SOMERULES >=> !RULE | !RULE !LINE !SOMERULES
!LINE >=> :n:
!RULE >=> !LEFT !ARROW !RIGHT
!LEFT >=> !NONTERM
!RIGHT >=> !NONTERM !RIGHT | !TERM !RIGHT | :!eps:
!TERM >=> !DDOT !LOWER !DDOT
!DDOT >=> :d:
!NONTER >=> !SIGN !UPPER !UPPERS
!UPPERS >=> !UPPER !UPPERS | :!eps:
!SIGN >=> :s: 
!LOWER >=> :a: | :b: | :c: | :d: | :e: | :f: | :g: | :h: | :i: | :j: | :k: | :l: | :m: | :n: | :o: | :p: | :q: | :r: | :s: | :t: | :u: | :v: | :w: | :x: | :y: | :z:
!UPPER >=> :a: | :b: | :c: | :d: | :e: | :f: | :g: | :h: | :i: | :j: | :k: | :l: | :m: | :n: | :o: | :p: | :q: | :r: | :s: | :t: | :u: | :v: | :w: | :x: | :y: | :z:
```
Получилось выразить не всё, т.к Ваня не добавил никаких спец. символов в алфавит терминалов, даже заглавных букв нет. Пришлось переиспользовать прописные буквы в нескольких целях. Понятно, что даже если Ваня напишет парсер, пропарсить саму себя такой грамматикой не получится. 

Мне __очень__ понравилось, что Иван сумел поддержать парсинг альтернативы для грамматик. Очень здорово, я так не смог. Также красивые цвета при выводе на питоне, хвалю. 

# 2. Аля Новикова

Получилась вот такая грамматика.

Парсер грамматик Али ее успешно принял. Класс!!

```
@S := @grammar
@grammar := @somerules
@somerules := @rule 
@somerules := @rule + @line + @somerules
@rule := @left + @arrow + @right
@left := @nonterm
@right := 
@right := @rightsome
@rightsome := @nonterm
@rightsome := @term 
@rightsome := @nonterm + "+" + @rightsome
@rightsome := @term + "+" + @rightsome 
@right := @term + @right 
@term :=  "-" + @letter + @letters + "-" 
@nonterm := @dog + @letter + @letters
@letters := @letter + @letters
@letters := 
@letter := @lower
@letter := @upper
@dog := "*" 
@line := "/n"
@arrow := ":="
@lower := "a"
@lower := "b"
@lower := "c"
@lower := "d"
@lower := "e"
@lower := "f"
@lower := "g"
@lower := "h"
@lower := "i"
@lower := "j"
@lower := "k"
@lower := "l"
@lower := "m"
@lower := "n"
@lower := "o"
@lower := "p"
@lower := "q"
@lower := "r"
@lower := "s"
@lower := "t"
@lower := "u"
@lower := "v"
@lower := "w"
@lower := "x"
@lower := "y"
@lower := "z"
@upper := "A"
@upper := "B"
@upper := "C"
@upper := "D"
@upper := "E"
@upper := "F"
@upper := "G"
@upper := "H"
@upper := "I"
@upper := "J"
@upper := "K"
@upper := "L"
@upper := "M"
@upper := "N"
@upper := "O"
@upper := "P"
@upper := "Q"
@upper := "R"
@upper := "S"
@upper := "T"
@upper := "U"
@upper := "V"
@upper := "W"
@upper := "X"
@upper := "Y"
@upper := "Z"
```

Жалко конечно, что нет альтернатив. Но в целом грамматика очень выразительная и может выразить саму себя без особых ухищрений. (Ну только '\n' нехватает, но это мелочь).

 Аля - молодец.

 Про CYK. Я попытался пропарсить этой грамматику саму себя. При этом нужно было сделать некоторые замены в ее тексте (python помог):
 ```
\n -> /n
@ -> *
' ' -> ''
" -> -
 ```

 Последняя замена нужна, потому что у меня лично не получилось пропарсить выражения по типу `@S:="a"`. Он точно понимает `"`? Я не уверен, но разбираться конечно не буду. 

 Так вот, после такой замены мы получаем. 

 ```
 *S:=*grammar/n*grammar:=*somerules/n*somerules:=*rule/n*somerules:=*rule+*line+*somerules/n*rule:=*left+*arrow+*right/n*left:=*nonterm/n*right:=/n*right:=*rightsome/n*rightsome:=*nonterm/n*rightsome:=*term/n*rightsome:=*nonterm+*rightsome/n*rightsome:=*term+*rightsome/n*right:=*term+*right/n*term:=*letter+*letters/n*nonterm:=*dog+*letter+*letters/n*letters:=*letter+*letters/n*letters:=/n*letter:=*lower/n*letter:=*upper/n*lower:=-a-/n*lower:=-b-/n*lower:=-c-/n*lower:=-d-/n*lower:=-e-/n*lower:=-f-/n*lower:=-g-/n*lower:=-h-/n*lower:=-i-/n*lower:=-j-/n*lower:=-k-/n*lower:=-l-/n*lower:=-m-/n*lower:=-n-/n*lower:=-o-/n*lower:=-p-/n*lower:=-q-/n*lower:=-r-/n*lower:=-s-/n*lower:=-t-/n*lower:=-u-/n*lower:=-v-/n*lower:=-w-/n*lower:=-x-/n*lower:=-y-/n*lower:=-z-/n*upper:=-A-/n*upper:=-B-/n*upper:=-C-/n*upper:=-D-/n*upper:=-E-/n*upper:=-F-/n*upper:=-G-/n*upper:=-H-/n*upper:=-I-/n*upper:=-J-/n*upper:=-K-/n*upper:=-L-/n*upper:=-M-/n*upper:=-N-/n*upper:=-O-/n*upper:=-P-/n*upper:=-Q-/n*upper:=-R-/n*upper:=-S-/n*upper:=-T-/n*upper:=-U-/n*upper:=-V-/n*upper:=-W-/n*upper:=-X-/n*upper:=-Y-/n*upper:=-Z-/n
```

Иииии... Длина этой строки 1100, т.е если CYK работает кубическое время, то это уже долго, а если еще учитывать python, да и не думаю, что Аля гналась за эффективностью... 

В общем, оно зависает и я не дождался ответа. Уверен, что когда-нибудь он справится!

Я попробовал пропарсить строчки по типу:
```
*S:=*S+-a-/n*N:=*S
```
Что эквивалентно
```
@S := @S + "a"
@N := @S
```

И все парсится, даже дерево строит. (Мне только дерево не очень понравилось, как сделано, но это от библиотек всё-таки зависит, вот на kotlin можно красивее сделать!!!)

В итоге, Аля - красава, Ваня тоже, другой Ваня тоже. Я бы всем зачет поставил. (Ване особенно)