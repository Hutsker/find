# PL/SQL to JSON antlr4
Парсинг PL/SQL в JSON при помощи готового файла грамматики и библиотеки ANTLR4

Работа через командную строку
---
`-i` - input (папка или sql файл)<br>
`-o` - output (папка или json файл)


**Работа с файлами:**
- `java -jar plsqlParser-1.0-jar-with-dependencies.jar -i /home/mur101/repositories/ertkWork/prod_oracle_repositories/engine/MVNO/mvno_mgf_funcs.sql -o /home/mur101/file.json` <br>
    Генерация JSON файлов в указанный файл после ключа `-o`.
- Использование только одного параметра недопустимо.

Работа с папками:
- `java parserPLSQL -i G:/path/to/input/Folder` <br>
    При указании флага `-i` и одного параметра (путь до папки
    с sql файлами) рядом с Folder создастся FolderJSON,
    в которой будут находиться все сгенерированные JSON файлы.
- `java parserJSON -i G:/path/to/input/Folder -o G:/output/Folder` <br>
    При указании флага `-i` и двух параметров генерация JSON
    файлов будет происходить в указанную папку после ключа `-o`.
    


Возможные ошибки
---
IDEA может не воспринимать PLSQLParser.java как класс и будет видеть
его как Java file. 

Чтобы это исправить:
- Help > Edit Custom Properties...
- Вставляем туда это `idea.max.intellisense.filesize=25000`
- Перезапускаем IDEA

P.S. Данная ошибка никак не влияет на компиляцию

Просмотр дерева в IDEA
---
Чтобы просмотреть построенное дерево:
- Заходим в файл грамматики (PlSqlParser.g4)
- Правый клик по `sql_script()` > Test Rule sqlscript
- В ANTLR Preview выбираем путь до .sql файла 
(важно чтобы весь код в .sql файле был написан в верхнем регистре, таковы особенности грамматики)

Dependencies
---
- [Grammars](https://github.com/antlr/grammars-v4/tree/master/sql/plsql)
- [PlSqlLexerBase.java and PlSqlParserBase.java](https://github.com/antlr/grammars-v4/tree/master/sql/plsql/Java)
- [antlr4 4.9](https://mvnrepository.com/artifact/org.antlr/antlr4/4.9)
- [Gson 2.8.6](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.6)
- [Apache Commons CLI 1.4](https://mvnrepository.com/artifact/commons-cli/commons-cli/1.4)
- [Apache Commons IO 2.7](https://mvnrepository.com/artifact/commons-io/commons-io/2.7)
