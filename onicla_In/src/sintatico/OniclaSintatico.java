package sintatico;


import lexico.OniclaLexico;
import lexico.TipoToken;
import lexico.Token;

public class OniclaSintatico {
    private OniclaLexico lexico;
    private Token token;
    private int scopeCounter = 0;
    private String epsilon = "e";

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

    public void printProduction(String left, String right) {
        String format = "%10s%s = %s";
        System.out.println(String.format(format, "", left, right));
    }

    public void FS() {
        if (checkCategory(TipoToken.PR_INTEGER, TipoToken.PR_FLOAT, TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_CHARRAY)) {
            printProduction("S", "DeclId S");
            fDeclId();
            FS();
        } else if (checkCategory(TipoToken.PR_FUNC)) {
            printProduction("S", "DeclFunction S");
            declFunction();
            FS();
        }  else {
            printProduction("S", epsilon);
        }
    }


    public void fDeclId() {
        if (checkCategory(TipoToken.PR_INTEGER, TipoToken.PR_FLOAT, TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_CHARRAY)) {
            printProduction("DeclId", "Type IdLL ';'");
            fType();
            fLId();

            if (!checkCategory(TipoToken.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        }
    }
    public void fType() {
        if (checkCategory(TipoToken.PR_INTEGER)) {
            printProduction("Type", "'Integer'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_VOID)) {
            printProduction("Type", "'Void'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_FLOAT)) {
            printProduction("Type", "'Float'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_BOOL)) {
            printProduction("Type", "'Bool'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_CHARAC)) {
            printProduction("Type", "'Character'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_CHARRAY)) {
            printProduction("Type", "'Characterarray'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void fLId() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("IdLL", "Id '=' Id_LL");
            fId();
            atrib();
            fLIdr();
        }
    }

    public void fLIdr() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("Id_LL", "',' Id '=' Id_LL");
            System.out.println(token);
            setNextToken();
            fId();
            atrib();
            fLIdr();
        } else {
            printProduction("Id_LL", epsilon);
        }
    }

    public void fId() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("Id", "'id' VectorType");
            System.out.println(token);
            setNextToken();
            vectorType();
        }
    }

    public void atrib() {
        if (checkCategory(TipoToken.OP_ATR)) {
            printProduction("=", "'=' Ec");
            System.out.println(token);
            setNextToken();
            fEc();
        } else {
            printProduction("=", epsilon);
        }
    }

    public void declFunction() {
        if (checkCategory(TipoToken.PR_FUNC)) {
            printProduction("DeclFunction", "'Function' FunctionType NameFunctionId '(' ConstDecl ')' InternalDecl");
            System.out.println(token);
            setNextToken();
            fType();
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
            printProduction("nameFunction", "'id'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_MAIN)) {
            printProduction("nameFunction", "'main'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void paramFunction() {
        if (checkCategory(TipoToken.ID, TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_INT, TipoToken.CTE_CADCHA)) {
            printProduction("ParamFunction", "Ec ParamFunctionLL");
            fEc();
            paramFunctionLL();
        } else {
            printProduction("ParamFunction", epsilon);
        }
    }

    public void paramFunctionLL() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("ParamFunctionLL", "',' Ec ParamFunctionLL");
            System.out.println(token);
            setNextToken();
            fEc();
            paramFunctionLL();
        } else {
            printProduction("ParamFunctionLL", epsilon);
        }
    }

    public void constDecl() {
        if (checkCategory(TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_FLOAT, TipoToken.PR_INTEGER, TipoToken.PR_CHARRAY)) {
            printProduction("ConstDecl", "Type 'id' VectorType ConstDecl_LL");
            fType();
            if (checkCategory(TipoToken.ID)) {
                System.out.println(token);
                setNextToken();
                vectorType();
                constDecl_LL();
            }
        }else {
            printProduction("ConstDecl", epsilon);
        }
    }

    public void constDecl_LL() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("ConstDecl_LL", "',' Type 'id' VectorType ConstDecl_LL");
            System.out.println(token);
            setNextToken();
            fType();
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
            printProduction("VectorType", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (!checkCategory(TipoToken.FEC_COL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("VectorType", epsilon);
        }
    }

    public void internalDecl() {
        if (checkCategory(TipoToken.PR_BEGIN)) {
            ++scopeCounter;
            printProduction("InternalDecl", "'Begin' Instructions 'End'");
            System.out.println(token);
            setNextToken();
            instructions();
            if (!checkCategory(TipoToken.PR_END)) {
            } else {
                System.out.println(token);
                setNextToken();
                --scopeCounter;
            }
        }
    }

    public void instructions() {
        if (checkCategory(TipoToken.PR_INTEGER, TipoToken.PR_FLOAT, TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_CHARRAY)) {
            printProduction("Instructions", "DeclId Instructions");
            fDeclId();
            instructions();
        } else if (checkCategory(TipoToken.PR_PRINT,TipoToken.PR_PRINTL,TipoToken.PR_PRINTNL,TipoToken.PR_INPUT, TipoToken.PR_WHILE, TipoToken.PR_REPEAT, TipoToken.PR_IF)) {
            System.out.println(token.lexema);
            printProduction("Instructions", "Command Instructions");
            fCommand();
            instructions();
        } else if (checkCategory(TipoToken.ID)) {
            printProduction("Instructions", "instructionsLL ';' Instructions");
            instructionsLL();
            if (!checkCategory(TipoToken.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
            instructions();
        } else if (checkCategory(TipoToken.PR_REFOUND)) {
            printProduction("Instructions", "'Refound' Return ';'");
            System.out.println(token);
            setNextToken();
            refound();
            if (!checkCategory(TipoToken.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("Instructions", epsilon);
        }
    }

    public void instructionsLL() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("instructionsLL", "'id' ParamAttr");
            System.out.println(token);
            setNextToken();
            fParamAttr();
        }
    }

    public void fParamAttr() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printProduction("ParamAttrib", "'(' ParamFunction ')'");
            System.out.println(token);
            setNextToken();
            paramFunction();
            if (!checkCategory(TipoToken.FEC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(TipoToken.OP_ATR)) {
            printProduction("Atrib", "'=' Ec Atrib");
            System.out.println(token);
            setNextToken();
            fEc();
            lAtrib();
        } else if (checkCategory(TipoToken.AB_COL)) {
            printProduction("ParamAttrib", "'[' Ea ']' '=' Ec Atrib");
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
            printProduction("Atrib", "',' Id '=' Ec Atrib");
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
            printProduction("Atrib", epsilon);
        }
    }

    public void refound() {
        if (checkCategory(TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_INT, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_CADCHA, TipoToken.ID)) {
            printProduction("Refound", "Ec");
            fEc();
        } else {
            printProduction("Refound", epsilon);
        }
    }

    public void fCommand() {
        if (checkCategory(TipoToken.PR_PRINT ,TipoToken.PR_PRINTNL, TipoToken.PR_PRINTL)) {
            printProduction("Command", "'print' '(' 'CTE_CADCHA' PrintParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                //colocar o char antes
                if (checkCategory(TipoToken.CTE_CADCHA, TipoToken.ID)) {
                    System.out.println(token);
                    setNextToken();
                    printParam();
                    if (checkCategory(TipoToken.FEC_PAR)) {
                        System.out.println(token);
                        setNextToken();
                        if (!checkCategory(TipoToken.TERMINAL)) {
                        } else {
                            System.out.println(token);
                            setNextToken();
                        }
                    }
                }
            }
        } else if (checkCategory(TipoToken.PR_INPUT)) {
            printProduction("Command", "'Input' '(' InputParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                inputParam();
                if (checkCategory(TipoToken.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    if (!checkCategory(TipoToken.TERMINAL)) {
                    } else {
                        System.out.println(token);
                        setNextToken();
                    }
                }
            }
        } else if (checkCategory(TipoToken.PR_WHILE)) {
            printProduction("Command", "'PR_WHILE' '(' Eb ')' InternalDecl");
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
            printProduction("Command", "'Repeat' repeatParams");
            System.out.println(token);
            setNextToken();
            repeat();
        } else if (checkCategory(TipoToken.PR_IF)) {
            printProduction("Command", "'PR_IF' '(' Eb ')' InternalDecl Ifr");
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
                    fIfr();
                }
            }
        }
    }

    public void printParam() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("PrintParam", "',' Ec PrintParam");
            System.out.println(token);
            setNextToken();
            fEc();
            printParam();
        } else {
            printProduction("PrintParam", epsilon);
        }
    }

    public void inputParam() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("InputParam", "'id' VectorType InputParamLL");
            System.out.println(token);
            setNextToken();
            vectorType();
            inputParamLL();
        }
    }

    public void inputParamLL() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("InputParamLL", "',' 'id' VectorType InputParamLL");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.ID)) {
                System.out.println(token);
                setNextToken();
                vectorType();
                inputParamLL();
            }
        } else {
            printProduction("InputParamLL", epsilon);
        }
    }

    public void repeat() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printProduction("Repeat", "'(' Integer 'id' '='  Ea ',' Ea repeatStep ')' InternalDecl");
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
            printProduction("repeatStep", "',' Ea");
            System.out.println(token);
            setNextToken();
            fEa();
        } else {
            printProduction("repeatStep", epsilon);
        }
    }

    public void fIfr() {
        if (checkCategory(TipoToken.PR_ELSE)) {
            printProduction("Ifr", "'PR_ELSE' InternalDecl");
            System.out.println(token);
            setNextToken();
            internalDecl();
        } else {
            printProduction("Ifr", epsilon);
        }
    }

    public void fEc() {
        printProduction("Ec", "Fc EcLL");
        fEb();
        EcLL();
    }

    public void EcLL() {
        //setNextToken();
        if (checkCategory(TipoToken.OP_CONCAT)) {
            printProduction("EcLL", "'OP_CONCAT' Fc EcLL");
            System.out.println(token);
            setNextToken();
            fEb();
            //setNextToken();
            EcLL();
        } else {
            printProduction("EcLL", epsilon);
        }
    }

    public void fEb() {
        printProduction("Eb", "Tb EbLL");
        fTb();
        EbLL();
    }

    public void EbLL() {
        if (checkCategory(TipoToken.PR_OR)) {
            printProduction("EbLL", "'PR_OR' Tb EbLL");
            System.out.println(token);
            setNextToken();
            fTb();
            EbLL();
        } else {
            printProduction("EbLL", epsilon);
        }
    }

    public void fTb() {
        printProduction("Tb", "Fb TbLL");
        fFb();
        TbLL();
    }

    public void TbLL() {
        if (checkCategory(TipoToken.PR_AND)) {
            printProduction("TbLL", "'PR_AND' Fb TbLL");
            System.out.println(token);
            setNextToken();
            fFb();
            TbLL();
        } else {
            printProduction("TbLL", epsilon);
        }
    }

    public void fFb() {
        if (checkCategory(TipoToken.OP_NOT)) {
            printProduction("Fb", "'OP_NOT' Fb");
            System.out.println(token);
            setNextToken();
            fFb();
        } else if (checkCategory(TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_INT, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_CADCHA, TipoToken.ID)) {
            printProduction("Fb", "Ra FbLL");
            fRa();
            FbLL();
        }
    }

    public void FbLL() {
        if (checkCategory(TipoToken.OP_GREATER)) {
            printProduction("FbLL", "'OP_GREAT' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(TipoToken.OP_LESS)) {
            printProduction("FbLL", "'OP_LESS' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(TipoToken.OP_GREATEQ)) {
            printProduction("FbLL", "'OP_GREATEQ' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(TipoToken.OP_LESSEQ)) {
            printProduction("FbLL", "'OP_LESSEQ' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else {
            printProduction("FbLL", epsilon);
        }
    }

    public void fRa() {
        printProduction("Ra", "Ea RaLL");
        fEa();
        RaLL();
    }

    public void RaLL() {
        if (checkCategory(TipoToken.OP_REL)) {
            printProduction("RaLL", "'OP_REL' Ea RaLL");
            System.out.println(token);
            setNextToken();
            fEa();
            RaLL();
        } else {
            printProduction("RaLL", epsilon);
        }
    }

    public void fEa() {
        printProduction("Ea", "Ta EaLL");
        fTa();
        EaLL();
    }

    public void EaLL() {
        if (checkCategory(TipoToken.OP_AD)) {
            printProduction("EaLL", "'OP_AD' Ta EaLL");
            System.out.println(token);
            setNextToken();
            fTa();
            EaLL();
        } else if (checkCategory(TipoToken.OP_SUB)) {
            printProduction("EaLL", "'OP_SUB' Ta EaLL");
            System.out.println(token);
            setNextToken();
            fTa();
            EaLL();
        } else {
            printProduction("EaLL", epsilon);
        }
    }

    public void fTa() {
        printProduction("Ta", "Pa TaLL");
        fPa();
        TaLL();
    }

    public void TaLL() {
        if (checkCategory(TipoToken.OP_MULT)) {
            printProduction("TaLL", "'OP_MULT' Pa TaLL");
            System.out.println(token);
            setNextToken();
            fPa();
            TaLL();
        } else if (checkCategory(TipoToken.OP_DIV)) {
            printProduction("TaLL", "'OP_DIV' Pa TaLL");
            System.out.println(token);
            setNextToken();
            fPa();
            TaLL();
        } else {
            printProduction("TaLL", epsilon);
        }
    }

    public void fPa() {
        printProduction("Pa", "Fa PaLL");
        fFa();
        PaLL();
    }

    public void PaLL() {
        if (checkCategory(TipoToken.OP_RES)) {
            printProduction("PaLL", "'OP_RES' Fa PaLL");
            System.out.println(token);
            setNextToken();
            fFa();
            PaLL();
        } else {
            printProduction("PaLL", epsilon);
        }
    }

    public void fFa() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printProduction("Fa", "'(' Ec ')'");
            System.out.println(token);
            setNextToken();
            fEc();
            if (!checkCategory(TipoToken.FEC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(TipoToken.OP_SUB)) {
            printProduction("Fa", "'OP_SUB' Fa");
            System.out.println(token);
            setNextToken();
            fFa();
        } else if (checkCategory(TipoToken.ID)) {
            idFunctionCall();
        } else if (checkCategory(TipoToken.CTE_BOOL)) {
            printProduction("Fa", "'CTE_BOOL'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_CHAR)) {
            printProduction("Fa", "'CTE_CHAR'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_FLOAT)) {
            printProduction("Fa", "'CTE_FLOAT'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_INT)) {
            printProduction("Fa", "'CTE_INT'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_CADCHA)) {
            printProduction("Fa", "'CTE_CADCHA'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void idFunctionCall() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("IdFunctionCall", "'id' IdFunctionCall_LL");
            System.out.println(token);
            setNextToken();
            idFunctionCall_LL();
        }
    }

    public void idFunctionCall_LL() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printProduction("IdFunctionCall_LL", "'(' ParamFunction ')'");
            System.out.println(token);
            setNextToken();
            paramFunction();
            if (!checkCategory(TipoToken.FEC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(TipoToken.AB_COL)) {
            printProduction("IdFunctionCall_LL", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (!checkCategory(TipoToken.FEC_COL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("IdFunctionCall_LL", epsilon);
        }
    }
}
