# Отчет о языках программирования

## Андрей Кислицын

В прошлый раз я хвалил язык Андрея. Этого больше не повториться. Я написал три функции, не очень осмысленные, но уже умер. Пожалуйста, больше никогда не заставляйте меня делать это, я не выдержу. 

Функция, которая зависает f(x) = f(x) + 1:
```
~SHUE_PPSH~{#VIZNACH#_ruSU_(@rus@){#ROBIT#{#ZVYAZATI#@ussr@:$NOL$:}}{#VIDDAI#:$CELKOVIY$+_ruSU_(@rus@):}}{#ROBIT#{#ZVYAZATI#@uusr@:$NOL$:}{#NAPISATNABERESTU#:_ruSU_(@ussr@):}}
```

Табов и переносов строк **НЕТ** читать язык невозможно, все сообщения об ошибках - predicate failed. 


Фибоначчи
```
~SHUE_PPSH~{#VIZNACH#_ruSU_(@rus@){#ROBIT#{#KOLI#:@rus@>=$NOL$:#TADI#{#ZVYAZATI#@ussr@:$CELKOVIY$:}#PO-INOMU#{#ROBIT#{#ZVYAZATI#@rus@:@rus@+$CELKOVIY$:}{#ZVYAZATI#@ussr@:_ruSU_(@rus@):}{#ZVYAZATI#@rus@:@rus@+$CELKOVIY$:}{#ZVYAZATI#@ruSS@:_ruSU_(@rus@):}{#ZVYAZATI#@ussr@:@uusr@+@ruSS@:}}}}{#VIDDAI#:@uusr@:}}{#ROBIT#{#CHITATSBERESTI#@uusr@}{#NAPISATNABERESTU#:_ruSU_(@ussr@):}}
```

Если прочитать этот пример (лучше не надо), можно заметить, что нет возможности передавать в качестве аргумента в вызов функции выражение,а не идентификатор. Ужасно неудобно! Непонятнo, почему в таком неудобном языкe хотя бы это нельзя было поддержать. 

Факториал:
```
~SHUE_PPSH~{#VIZNACH#_ruSU_(@rus@){#ROBIT#{#KOLI#:@rus@==$NOL$:#TADI#{#ZVYAZATI#@ussr@:$CELKOVIY$:}#PO-INOMU#{#ROBIT#{#ZVYAZATI#@s@:@rus@-$CELKOVIY$:}{#ZVYAZATI#@ussr@:_ruSU_(@s@):}{#ZVYAZATI#@ussr@:@rus@*@ussr@:}}}}{#VIDDAI#:@uusr@:}}{#ROBIT#{#CHITATSBERESTI#@uusr@}{#NAPISATNABERESTU#:_ruSU_(@ussr@):}}
```

Если поначалу это был концептуальный язык с древними русами, то теперь это просто какой-то бурелом из капса. Я уже не понимаю откуда берутся новые ключевые слова, что такое _SHUE_PPSH_? Почему _VIDDAY_, а не _VIDAY_? И почему нельзя было всё-таки добавить пробелы и переносы строк?

Кроме всего прочего, в документации нет ни одного примера использования функций и не описано, что такое FunctionCall.  

## Ольга Шиманская

Спасибо Оле, что не любит древних русов.

Синтаксис опять похож на запись AST, но уже меньше. Немного непоняно откуда идет потребность обрамлять название функции скобками. Это хоть как-то помогает парсингу?

Факториал:

 ```
 Def (fact) (n) 
    (Seq 
    {Assign (i) (1);
     While (n > 0) 
     (Seq 
        {Assign (i) (n*i); 
        Assign (n) (n-1);}
     );
     }) 
     Return (i) 
Seq {
    Read (n); 
    Write (fact(n));
}
 ```

Фибоначчи:

```
"Def (fib) (n) 
(Seq {
    Assign (ans) (1);
     If (n == 0 || n == 1) 
     (Seq {}) 
     (Seq {
         Assign (ans) (fib(ans-1) + fib(ans-1));
         });
    }) 
    Return (ans) 
Seq {
     Read (n); 
     Write (fib(n));
     }"
```

Быстрое возведение в степень:
```
Def (fastpow) (n, x) 
(Seq {
    Assign (ans) (1); 
    If (n == 0) 
    (Seq {}) 
    (Seq {
        If (2*(n/2) == n)
         (Seq {
             Assign (ans) (fastpow(n/2, x*x));
             }) 
        (Seq {
            Assign (ans) (x*fastpow(n/2, x*x));
        });
    });
    }) 
    Return (ans) 
Seq { 
    Read (n); 
    Write (fastpow(n, 0)); 
}
```

Намного удобнее, чем язык Андрея. Потратил на написание этих программ намного меньше времени и нервов. Также у Оли хорошо поддержаны сообщения об ошибках, это помогает. 