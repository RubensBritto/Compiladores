package lexico;

import java.util.Hashtable;

public class KeyWordsHash {

    Hashtable<String, TipoToken> keyWords = new Hashtable<>();

    public KeyWordsHash(){
        keyWords.put("Main", TipoToken.PR_MAIN);
        keyWords.put("Begin", TipoToken.PR_BEGIN);
        keyWords.put("End",TipoToken.PR_END);
        keyWords.put("Function", TipoToken.PR_FUNC);
        keyWords.put("And", TipoToken.PR_AND);
        keyWords.put("Or", TipoToken.PR_OR);
        keyWords.put("Refound", TipoToken.PR_REFOUND);
        keyWords.put("Void", TipoToken.PR_VOID);
        keyWords.put("If", TipoToken.PR_IF);
        keyWords.put("Else", TipoToken.PR_ELSE);
        keyWords.put("While", TipoToken.PR_WHILE);
        keyWords.put("Repeat", TipoToken.PR_REPEAT);
        keyWords.put("Integer", TipoToken.PR_INTEGER);
        keyWords.put("Float",TipoToken.PR_FLOAT);
        keyWords.put( "Characterarray", TipoToken.PR_CHARRAY);
        keyWords.put("Character", TipoToken.PR_CHARAC);
        keyWords.put("Bool", TipoToken.PR_BOOL);
        keyWords.put("Input", TipoToken.PR_INPUT);
        keyWords.put("Print", TipoToken.PR_PRINT);
        keyWords.put("Printl", TipoToken.PR_PRINTL);
        keyWords.put("Printnl", TipoToken.PR_PRINTNL);
        keyWords.put("True", TipoToken.PR_TRUE);
        keyWords.put("False", TipoToken.PR_FALSE);
        keyWords.put("Null", TipoToken.PR_NULL);

    }

}