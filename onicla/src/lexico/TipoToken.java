package lexico;

public enum TipoToken {
    EOF,
    ID,
    CTE_FLOAT,
    CTE_INT,
    CTE_CHAR,
    CTE_CADCHA,
    OP_ATR,
    OP_REL,
    OP_AD,
    OP_SUB,
    OP_MULT,
    OP_DIV,
    OP_RES,
    OP_CONCAT,
    OP_GREATER,
    OP_LESS,
    OP_GREATEQ,
    OP_LESSEQ,
    OP_NOT,
    OP_NOTUN,
    PR_AND,
    PR_OR,
    PR_FUNC,
    PR_REFOUND,
    PR_IF,
    PR_ELSE,
    PR_WHILE,
    PR_REPEAT,
    PR_INTEGER,
    PR_FLOAT,
    PR_CHARRAY,
    PR_CHARAC,
    PR_BOOL,
    PR_INPUT,
    PR_PRINT,
    PR_PRINTNL,
    PR_TRUE,
    PR_FALSE,
    PR_NULL,
    PR_BEGIN,
    PR_END,
    PR_VOID,
    PR_MAIN,
    AB_PAR,
    FEC_PAR,
    AB_COL,
    FEC_COL,
    TERMINAL,
    SEP,
    ER_ID,
    ER_CHAR,
    ER_NUMBER,
    ER_SYMBOL;
}
