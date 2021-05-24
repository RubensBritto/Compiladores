package src.sintatico;


import src.lexico.OniclaLexico;
import src.lexico.TipoToken;
import src.lexico.Token;

public class OniclaSintatico {
    private OniclaLexico lexico;
    private Token token;
    private String epsilon = "epsilon";

    public OniclaSintatico(String a) {
        lexico = new OniclaLexico(a);
        setNextToken ();
        FS();
    }

    public void setNextToken() {
        token = lexico.nextToken();
        if(token.tipoToken == TipoToken.EOF) {
            System.out.println(token);
            return ;
        }
    }

    public boolean checkCategory(TipoToken... categories) {
        for (TipoToken category: categories) {
            if (token.tipoToken == category) {
                return true;
            }
        }
        return false;
    }

    public void printGrammar(String left, String right) {
        String format = "%10s%s = %s";
        System.out.println(String.format(format, "", left, right));
    }

    public void FS() {
        if (checkCategory(TipoToken.PR_INTEGER, TipoToken.PR_FLOAT, TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_CHARRAY)) {
            printGrammar("S", "DeclId S");
            declId();
            FS();
        } else if (checkCategory(TipoToken.PR_FUNC)) {
            printGrammar("S", "DeclFunction S");
            declFunction();
            FS();
        }  else {
            printGrammar("S", epsilon);
        }
    }


    public void declId() {
        if (checkCategory(TipoToken.PR_INTEGER, TipoToken.PR_FLOAT, TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_CHARRAY)) {
            printGrammar("DeclId", "Type IdLL ';'");
            FunctionType();
            fLId();

            if (checkCategory(TipoToken.TERMINAL)) {
                System.out.println(token);
                setNextToken();
            }
        }
    }
    public void FunctionType() {
        if (checkCategory(TipoToken.PR_INTEGER)) {
            printGrammar("Type", "'Integer'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_VOID)) {
            printGrammar("Type", "'Void'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_FLOAT)) {
            printGrammar("Type", "'Float'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_BOOL)) {
            printGrammar("Type", "'Bool'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_CHARAC)) {
            printGrammar("Type", "'Character'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_CHARRAY)) {
            printGrammar("Type", "'Characterarray'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void fLId() {
        if (checkCategory(TipoToken.ID)) {
            printGrammar("IdLL", "Id '=' Id_LL");
            fId();
            atrib();
            Id_LL();
        }
    }

    public void Id_LL() {
        if (checkCategory(TipoToken.SEP)) {
            printGrammar("Id_LL", "',' Id '=' Id_LL");
            System.out.println(token);
            setNextToken();
            fId();
            atrib();
            Id_LL();
        } else {
            printGrammar("Id_LL", epsilon);
        }
    }

    public void fId() {
        if (checkCategory(TipoToken.ID)) {
            printGrammar("Id", "'id' VectorType");
            System.out.println(token);
            setNextToken();
            vectorType();
        }
    }

    public void atrib() {
        if (checkCategory(TipoToken.OP_ATR)) {
            printGrammar("=", "'=' Ec");
            System.out.println(token);
            setNextToken();
            fEc();
        } else {
            printGrammar("=", epsilon);
        }
    }

    public void declFunction() {
        if (checkCategory(TipoToken.PR_FUNC)) {
            printGrammar("DeclFunction", "'Function' FunctionType NameFunctionId '(' ConstDecl ')' InternalDecl");
            System.out.println(token);
            setNextToken();
            FunctionType();
            nameFunction();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                constDecl();
                if (checkCategory(TipoToken.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    internalDecl();
                }
            }
        }
    }

    public void nameFunction() {
        if (checkCategory(TipoToken.ID)) {
            printGrammar("nameFunction", "'id'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_MAIN)) {
            printGrammar("nameFunction", "'main'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void paramFunction() {
        if (checkCategory(TipoToken.ID, TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_INT, TipoToken.CTE_CADCHA)) {
            printGrammar("ParamFunction", "Ec ParamFunctionLL");
            fEc();
            paramFunctionLL();
        } else {
            printGrammar("ParamFunction", epsilon);
        }
    }

    public void paramFunctionLL() {
        if (checkCategory(TipoToken.SEP)) {
            printGrammar("ParamFunctionLL", "',' Ec ParamFunctionLL");
            System.out.println(token);
            setNextToken();
            fEc();
            paramFunctionLL();
        } else {
            printGrammar("ParamFunctionLL", epsilon);
        }
    }

    public void constDecl() {
        if (checkCategory(TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_FLOAT, TipoToken.PR_INTEGER, TipoToken.PR_CHARRAY)) {
            printGrammar("ConstDecl", "Type 'id' VectorType ConstDecl_LL");
            FunctionType();
            if (checkCategory(TipoToken.ID)) {
                System.out.println(token);
                setNextToken();
                vectorType();
                constDecl_LL();
            }
        }else {
            printGrammar("ConstDecl", epsilon);
        }
    }

    public void constDecl_LL() {
        if (checkCategory(TipoToken.SEP)) {
            printGrammar("ConstDecl_LL", "',' Type 'id' VectorType ConstDecl_LL");
            System.out.println(token);
            setNextToken();
            FunctionType();
            if (checkCategory(TipoToken.ID)) {
                System.out.println(token);
                setNextToken();
                vectorType();
                constDecl_LL();
            }
        }
    }

    public void vectorType() {
        if (checkCategory(TipoToken.AB_COL)) {
            printGrammar("VectorType", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (checkCategory(TipoToken.FEC_COL)) {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printGrammar("VectorType", epsilon);
        }
    }

    public void internalDecl() {
        if (checkCategory(TipoToken.PR_BEGIN)) {
            printGrammar("InternalDecl", "'Begin' Instructions 'End'");
            System.out.println(token);
            setNextToken();
            instructions();
            if (checkCategory(TipoToken.PR_END)) {
                System.out.println(token);
                setNextToken();
            }
        }
    }

    public void instructions() {
        if (checkCategory(TipoToken.PR_INTEGER, TipoToken.PR_FLOAT, TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_CHARRAY)) {
            printGrammar("Instructions", "DeclId Instructions");
            declId();
            instructions();
        } else if (checkCategory(TipoToken.PR_PRINT,TipoToken.PR_PRINTL,TipoToken.PR_PRINTNL,TipoToken.PR_INPUT, TipoToken.PR_WHILE, TipoToken.PR_REPEAT, TipoToken.PR_IF)) {
            printGrammar("Instructions", "Command Instructions");
            command();
            instructions();
        } else if (checkCategory(TipoToken.ID)) {
            printGrammar("Instructions", "instructionsLL ';' Instructions");
            instructionsLL();
            if (checkCategory(TipoToken.TERMINAL)) {
                System.out.println(token);
                setNextToken();
            }
            instructions();
        } else if (checkCategory(TipoToken.PR_REFOUND)) {
            printGrammar("Instructions", "'Refound' Return ';'");
            System.out.println(token);
            setNextToken();
            refound();
            if (checkCategory(TipoToken.TERMINAL)) {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printGrammar("Instructions", epsilon);
        }
    }

    public void instructionsLL() {
        if (checkCategory(TipoToken.ID)) {
            printGrammar("instructionsLL", "'id' ParamAtrib");
            System.out.println(token);
            setNextToken();
            paramAtrib();
        }
    }

    public void paramAtrib() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printGrammar("ParamAtrib", "'(' ParamFunction ')'");
            System.out.println(token);
            setNextToken();
            paramFunction();
            if (checkCategory(TipoToken.FEC_PAR)) {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(TipoToken.OP_ATR)) {
            printGrammar("Atrib", "'=' Ec Atrib");
            System.out.println(token);
            setNextToken();
            fEc();
            lAtrib();
        } else if (checkCategory(TipoToken.AB_COL)) {
            printGrammar("ParamAtrib", "'[' Ea ']' '=' Ec Atrib");
            System.out.println(token);
            setNextToken();
            fEa();
            if (checkCategory(TipoToken.FEC_COL)) {
                System.out.println(token);
                setNextToken();
                if (checkCategory(TipoToken.OP_ATR)) {
                    System.out.println(token);
                    setNextToken();
                    fEc();
                    lAtrib();
                }
            }
        }
    }

    public void lAtrib() {
        if (checkCategory(TipoToken.SEP)) {
            printGrammar("Atrib", "',' Id '=' Ec Atrib");
            System.out.println(token);
            setNextToken();
            fId();
            if (checkCategory(TipoToken.OP_ATR)) {
                System.out.println(token);
                setNextToken();
                fEc();
                lAtrib();
            }
        } else {
            printGrammar("Atrib", epsilon);
        }
    }

    public void refound() {
        if (checkCategory(TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_INT, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_CADCHA, TipoToken.ID)) {
            printGrammar("Refound", "Ec");
            fEc();
        } else {
            printGrammar("Refound", epsilon);
        }
    }

    public void command() {
        if (checkCategory(TipoToken.PR_PRINT ,TipoToken.PR_PRINTNL, TipoToken.PR_PRINTL)) {
            printGrammar("Command", "'print' '(' 'CTE_CADCHA' PrintParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                
                if (checkCategory(TipoToken.CTE_CHAR, TipoToken.ID)) {
                    System.out.println(token);
                    setNextToken();
                    printParam();
                    if (checkCategory(TipoToken.FEC_PAR)) {
                        System.out.println(token);
                        setNextToken();
                        if (checkCategory(TipoToken.TERMINAL)) {
                            System.out.println(token);
                            setNextToken();
                        }
                    }
                }
                if (checkCategory(TipoToken.CTE_CADCHA, TipoToken.ID)) {
                    System.out.println(token);
                    setNextToken();
                    printParam();
                    if (checkCategory(TipoToken.FEC_PAR)) {
                        System.out.println(token);
                        setNextToken();
                        if (checkCategory(TipoToken.TERMINAL)) {
                            System.out.println(token);
                            setNextToken();
                        }
                    }
                }
            }
        } else if (checkCategory(TipoToken.PR_INPUT)) {
            printGrammar("Command", "'Input' '(' InputParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                inputParam();
                if (checkCategory(TipoToken.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    if (checkCategory(TipoToken.TERMINAL)) {
                        System.out.println(token);
                        setNextToken();
                    }
                }
            }
        } else if (checkCategory(TipoToken.PR_WHILE)) {
            printGrammar("Command", "'PR_WHILE' '(' Eb ')' InternalDecl");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fEb();
                if (checkCategory(TipoToken.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    internalDecl();
                }
            }
        } else if (checkCategory(TipoToken.PR_REPEAT)) {
            printGrammar("Command", "'Repeat' repeatParams");
            System.out.println(token);
            setNextToken();
            repeat();
        } else if (checkCategory(TipoToken.PR_IF)) {
            printGrammar("Command", "'PR_IF' '(' Eb ')' InternalDecl IfLL");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fEb();
                if (checkCategory(TipoToken.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    internalDecl();
                    fIfLL();
                }
            }
        }
    }

    public void printParam() {
        if (checkCategory(TipoToken.SEP)) {
            printGrammar("PrintParam", "',' Ec PrintParam");
            System.out.println(token);
            setNextToken();
            fEc();
            printParam();
        } else {
            printGrammar("PrintParam", epsilon);
        }
    }

    public void inputParam() {
        if (checkCategory(TipoToken.ID)) {
            printGrammar("InputParam", "'id' VectorType InputParamLL");
            System.out.println(token);
            setNextToken();
            vectorType();
            inputParamLL();
        }
    }

    public void inputParamLL() {
        if (checkCategory(TipoToken.SEP)) {
            printGrammar("InputParamLL", "',' 'id' VectorType InputParamLL");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.ID)) {
                System.out.println(token);
                setNextToken();
                vectorType();
                inputParamLL();
            }
        } else {
            printGrammar("InputParamLL", epsilon);
        }
    }

    public void repeat() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printGrammar("Repeat", "'(' Integer 'id' '='  Ea ',' Ea repeatStep ')' InternalDecl");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.PR_INTEGER)) {
                System.out.println(token);
                setNextToken();
                if (checkCategory(TipoToken.ID)) {
                    System.out.println(token);
                    setNextToken();
                    if (checkCategory(TipoToken.OP_ATR)) {
                        System.out.println(token);
                        setNextToken();
                        fEa();
                        if (checkCategory(TipoToken.SEP)) {
                            System.out.println(token);
                            setNextToken();
                            fEa();
                            repeatStep();
                            if (checkCategory(TipoToken.FEC_PAR)) {
                                System.out.println(token);
                                setNextToken();
                                internalDecl();
                            }
                        }
                    }
                }
            }
        }
    }

    public void repeatStep() {
        if (checkCategory(TipoToken.SEP)) {
            printGrammar("repeatStep", "',' Ea");
            System.out.println(token);
            setNextToken();
            fEa();
        } else {
            printGrammar("repeatStep", epsilon);
        }
    }

    public void fIfLL() {
        if (checkCategory(TipoToken.PR_ELSE)) {
            printGrammar("IfLL", "'PR_ELSE' InternalDecl");
            System.out.println(token);
            setNextToken();
            internalDecl();
        } else {
            printGrammar("IfLL", epsilon);
        }
    }

    public void fEc() {
        printGrammar("Ec", "Fc EcLL");
        fEb();
        EcLL();
    }

    public void EcLL() {
        //setNextToken();
        if (checkCategory(TipoToken.OP_CONCAT)) {
            printGrammar("EcLL", "'OP_CONCAT' Fc EcLL");
            System.out.println(token);
            setNextToken();
            fEb();
            //setNextToken();
            EcLL();
        } else {
            printGrammar("EcLL", epsilon);
        }
    }

    public void fEb() {
        printGrammar("Eb", "Tb EbLL");
        fTb();
        EbLL();
    }

    public void EbLL() {
        if (checkCategory(TipoToken.PR_OR)) {
            printGrammar("EbLL", "'PR_OR' Tb EbLL");
            System.out.println(token);
            setNextToken();
            fTb();
            EbLL();
        } else {
            printGrammar("EbLL", epsilon);
        }
    }

    public void fTb() {
        printGrammar("Tb", "Fb TbLL");
        fFb();
        TbLL();
    }

    public void TbLL() {
        if (checkCategory(TipoToken.PR_AND)) {
            printGrammar("TbLL", "'PR_AND' Fb TbLL");
            System.out.println(token);
            setNextToken();
            fFb();
            TbLL();
        } else {
            printGrammar("TbLL", epsilon);
        }
    }

    public void fFb() {
        if (checkCategory(TipoToken.OP_NOT)) {
            printGrammar("Fb", "'OP_NOT' Fb");
            System.out.println(token);
            setNextToken();
            fFb();
        } else if (checkCategory(TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_INT, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_CADCHA, TipoToken.ID)) {
            printGrammar("Fb", "Ra FbLL");
            fRa();
            FbLL();
        }
    }

    public void FbLL() {
        if (checkCategory(TipoToken.OP_GREATER)) {
            printGrammar("FbLL", "'OP_GREAT' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(TipoToken.OP_LESS)) {
            printGrammar("FbLL", "'OP_LESS' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(TipoToken.OP_GREATEQ)) {
            printGrammar("FbLL", "'OP_GREATEQ' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(TipoToken.OP_LESSEQ)) {
            printGrammar("FbLL", "'OP_LESSEQ' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else {
            printGrammar("FbLL", epsilon);
        }
    }

    public void fRa() {
        printGrammar("Ra", "Ea RaLL");
        fEa();
        RaLL();
    }

    public void RaLL() {
        if (checkCategory(TipoToken.OP_REL)) {
            printGrammar("RaLL", "'OP_REL' Ea RaLL");
            System.out.println(token);
            setNextToken();
            fEa();
            RaLL();
        } else {
            printGrammar("RaLL", epsilon);
        }
    }

    public void fEa() {
        printGrammar("Ea", "Ta EaLL");
        fTa();
        EaLL();
    }

    public void EaLL() {
        if (checkCategory(TipoToken.OP_AD)) {
            printGrammar("EaLL", "'OP_AD' Ta EaLL");
            System.out.println(token);
            setNextToken();
            fTa();
            EaLL();
        } else if (checkCategory(TipoToken.OP_SUB)) {
            printGrammar("EaLL", "'OP_SUB' Ta EaLL");
            System.out.println(token);
            setNextToken();
            fTa();
            EaLL();
        } else {
            printGrammar("EaLL", epsilon);
        }
    }

    public void fTa() {
        printGrammar("Ta", "Pa TaLL");
        fPa();
        TaLL();
    }

    public void TaLL() {
        if (checkCategory(TipoToken.OP_MULT)) {
            printGrammar("TaLL", "'OP_MULT' Pa TaLL");
            System.out.println(token);
            setNextToken();
            fPa();
            TaLL();
        } else if (checkCategory(TipoToken.OP_DIV)) {
            printGrammar("TaLL", "'OP_DIV' Pa TaLL");
            System.out.println(token);
            setNextToken();
            fPa();
            TaLL();
        } else {
            printGrammar("TaLL", epsilon);
        }
    }

    public void fPa() {
        printGrammar("Pa", "Fa PaLL");
        fFa();
        PaLL();
    }

    public void PaLL() {
        if (checkCategory(TipoToken.OP_RES)) {
            printGrammar("PaLL", "'OP_RES' Fa PaLL");
            System.out.println(token);
            setNextToken();
            fFa();
            PaLL();
        } else {
            printGrammar("PaLL", epsilon);
        }
    }

    public void fFa() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printGrammar("Fa", "'(' Ec ')'");
            System.out.println(token);
            setNextToken();
            fEc();
            if (checkCategory(TipoToken.FEC_PAR)) {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(TipoToken.OP_SUB)) {
            printGrammar("Fa", "'OP_SUB' Fa");
            System.out.println(token);
            setNextToken();
            fFa();
        } else if (checkCategory(TipoToken.ID)) {
            idFunctionCall();
        } else if (checkCategory(TipoToken.CTE_BOOL)) {
            printGrammar("Fa", "'CTE_BOOL'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_CHAR)) {
            printGrammar("Fa", "'CTE_CHAR'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_FLOAT)) {
            printGrammar("Fa", "'CTE_FLOAT'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_INT)) {
            printGrammar("Fa", "'CTE_INT'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_CADCHA)) {
            printGrammar("Fa", "'CTE_CADCHA'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void idFunctionCall() {
        if (checkCategory(TipoToken.ID)) {
            printGrammar("IdFunctionCall", "'id' IdFunctionCall_LL");
            System.out.println(token);
            setNextToken();
            idFunctionCall_LL();
        }
    }

    public void idFunctionCall_LL() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printGrammar("IdFunctionCall_LL", "'(' ParamFunction ')'");
            System.out.println(token);
            setNextToken();
            paramFunction();
            if (checkCategory(TipoToken.FEC_PAR)) {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(TipoToken.AB_COL)) {
            printGrammar("IdFunctionCall_LL", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (checkCategory(TipoToken.FEC_COL)) {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printGrammar("IdFunctionCall_LL", epsilon);
        }
    }
}