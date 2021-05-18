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
            printProduction("S", "FunDecl S");
            fFunDecl();
            FS();
        }  else {
            printProduction("S", epsilon);
        }
    }


    public void fDeclId() {
        if (checkCategory(TipoToken.PR_INTEGER, TipoToken.PR_FLOAT, TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_CHARRAY)) {
            printProduction("DeclId", "Type LId ';'");
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
            printProduction("Type", "'CharacterArray'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void fLId() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("LId", "Id AttrOpt LIdr");
            fId();
            fAttrOpt();
            fLIdr();
        }
    }

    public void fLIdr() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("LIdr", "',' Id AttrOpt LIdr");
            System.out.println(token);
            setNextToken();
            fId();
            fAttrOpt();
            fLIdr();
        } else {
            printProduction("LIdr", epsilon);
        }
    }

    public void fId() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("Id", "'id' ArrayOpt");
            System.out.println(token);
            setNextToken();
            fArrayOpt();
        }
    }

    public void fAttrOpt() {
        if (checkCategory(TipoToken.OP_ATR)) {
            printProduction("AttrOpt", "'=' Ec");
            System.out.println(token);
            setNextToken();
            fEc();
        } else {
            printProduction("AttrOpt", epsilon);
        }
    }

    public void fFunDecl() {
        if (checkCategory(TipoToken.PR_FUNC)) {
            printProduction("FunDecl", "'fun' Type FunName '(' LParamDecl ')' Body");
            System.out.println(token);
            setNextToken();
            fType();
            fFunName();
            if (checkCategory(TipoToken.AB_PAR)) {
                setNextToken();
                fLParamDecl();
                System.out.println(token);


                if (checkCategory(TipoToken.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    fBody();
                }
            }
        }
    }

    public void fFunName() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("FunName", "'id'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.PR_MAIN)) {
            printProduction("FunName", "'main'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void fLParamCall() {
        if (checkCategory(TipoToken.ID, TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_INT, TipoToken.CTE_CADCHA)) {
            printProduction("LParamCall", "Ec LParamCallr");
            fEc();
            fLParamCallr();
        } else {
            printProduction("LParamCall", epsilon);
        }
    }

    public void fLParamCallr() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("LParamCallr", "',' Ec LParamCallr");
            System.out.println(token);
            setNextToken();
            fEc();
            fLParamCallr();
        } else {
            printProduction("LParamCallr", epsilon);
        }
    }

    public void fLParamDecl() {
        System.out.println(token.tipoToken);
        if (checkCategory(TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_FLOAT, TipoToken.PR_INTEGER, TipoToken.PR_CHARRAY)) {
            System.out.println("aqui estou");
            printProduction("LParamDecl", "Type 'id' ArrayOpt LParamDeclr");
            fType();
            if (checkCategory(TipoToken.ID)) {
                System.out.println(token);
                setNextToken();
                fArrayOpt();
                fLParamDeclr();
            }
        }else {
            printProduction("LParamDecl", epsilon);
        }
    }

    public void fLParamDeclr() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("LParamDeclr", "',' Type 'id' ArrayOpt LParamDeclr");
            System.out.println(token);
            setNextToken();
            fType();
            if (checkCategory(TipoToken.ID)) {
                System.out.println(token);
                setNextToken();
                fArrayOpt();
                fLParamDeclr();
            }
        }
    }

    public void fArrayOpt() {
        if (checkCategory(TipoToken.AB_COL)) {
            printProduction("ArrayOpt", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (!checkCategory(TipoToken.FEC_COL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("ArrayOpt", epsilon);
        }
    }

    public void fBody() {
        if (checkCategory(TipoToken.PR_BEGIN)) {
            ++scopeCounter;
            printProduction("Body", "'{' BodyPart '}'");
            System.out.println(token);
            setNextToken();
            fBodyPart();
            if (!checkCategory(TipoToken.PR_END)) {
            } else {
                System.out.println(token);
                setNextToken();
                --scopeCounter;
            }
        }
    }

    public void fBodyPart() {
        if (checkCategory(TipoToken.PR_INTEGER, TipoToken.PR_FLOAT, TipoToken.PR_BOOL, TipoToken.PR_CHARAC, TipoToken.PR_CHARRAY)) {
            printProduction("BodyPart", "DeclId BodyPart");
            fDeclId();
            fBodyPart();
        } else if (checkCategory(TipoToken.PR_PRINT,TipoToken.PR_PRINTL,TipoToken.PR_PRINTNL,TipoToken.PR_INPUT, TipoToken.PR_WHILE, TipoToken.PR_REPEAT, TipoToken.PR_IF)) {
            printProduction("BodyPart", "Command BodyPart");
            fCommand();
            fBodyPart();
        } else if (checkCategory(TipoToken.ID)) {
            printProduction("BodyPart", "BodyPartr ';' BodyPart");
            fBodyPartr();
            if (!checkCategory(TipoToken.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
            fBodyPart();
        } else if (checkCategory(TipoToken.PR_REFOUND)) {
            printProduction("BodyPart", "'return' Return ';'");
            System.out.println(token);
            setNextToken();
            fReturn();
            if (!checkCategory(TipoToken.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("BodyPart", epsilon);
        }
    }

    public void fBodyPartr() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("BodyPartr", "'id' ParamAttr");
            System.out.println(token);
            setNextToken();
            fParamAttr();
        }
    }

    public void fParamAttr() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printProduction("ParamAttrib", "'(' LParamCall ')'");
            System.out.println(token);
            setNextToken();
            fLParamCall();
            if (!checkCategory(TipoToken.FEC_PAR)) {
          } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(TipoToken.OP_ATR)) {
            printProduction("ParamAttrib", "'=' Ec LAttr");
            System.out.println(token);
            setNextToken();
            fEc();
            fLAttr();
        } else if (checkCategory(TipoToken.AB_COL)) {
            printProduction("ParamAttrib", "'[' Ea ']' '=' Ec LAttr");
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
                    fLAttr();
                }
            }
        }
    }

    public void fLAttr() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("LAttr", "',' Id '=' Ec LAttr");
            System.out.println(token);
            setNextToken();
            fId();
            if (checkCategory(TipoToken.OP_ATR)) {
                System.out.println(token);
                setNextToken();
                fEc();
                fLAttr();
            }
        } else {
            printProduction("LAttr", epsilon);
        }
    }

    public void fReturn() {
        if (checkCategory(TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_INT, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_CADCHA, TipoToken.ID)) {
            printProduction("Return", "Ec");
            fEc();
        } else {
            printProduction("Return", epsilon);
        }
    }

    public void fCommand() {
        if (checkCategory(TipoToken.PR_PRINT ,TipoToken.PR_PRINTNL, TipoToken.PR_PRINTL)) {
            printProduction("Command", "'print' '(' 'constStr' PrintLParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                //colocar o char antes
                if (checkCategory(TipoToken.CTE_CADCHA)) {
                    System.out.println(token);
                    setNextToken();
                    fPrintLParam();
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
            printProduction("Command", "'scan' '(' ScanLParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fScanLParam();
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
            printProduction("Command", "'whileLoop' '(' Eb ')' Body");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fEb();
                if (checkCategory(TipoToken.AB_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    fBody();
                }
            }
        } else if (checkCategory(TipoToken.PR_REPEAT)) {
            printProduction("Command", "'forLoop' ForParams");
            System.out.println(token);
            setNextToken();
            fForParams();
        } else if (checkCategory(TipoToken.PR_IF)) {
            printProduction("Command", "'condIf' '(' Eb ')' Body Ifr");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fEb();
                if (checkCategory(TipoToken.FEC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    fBody();
                    fIfr();
                }
            }
        }
    }

    public void fPrintLParam() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("PrintLParam", "',' Ec PrintLParam");
            System.out.println(token);
            setNextToken();
            fEc();
            fPrintLParam();
        } else {
            printProduction("PrintLParam", epsilon);
        }
    }

    public void fScanLParam() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("ScanLParam", "'id' ArrayOpt ScanLParamr");
            System.out.println(token);
            setNextToken();
            fArrayOpt();
            fScanLParamr();
        }
    }

    public void fScanLParamr() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("ScanLParamr", "',' 'id' ArrayOpt ScanLParamr");
            System.out.println(token);
            setNextToken();
            if (checkCategory(TipoToken.ID)) {
                System.out.println(token);
                setNextToken();
                fArrayOpt();
                fScanLParamr();
            }
        } else {
            printProduction("ScanLParamr", epsilon);
        }
    }

    public void fForParams() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printProduction("ForParams", "'(' 'typeInt' 'id' '=' '(' Ea ',' Ea ForStep ')' ')' Body");
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
                        if (checkCategory(TipoToken.AB_PAR)) {
                            System.out.println(token);
                            setNextToken();
                            fEa();
                            if (checkCategory(TipoToken.SEP)) {
                                System.out.println(token);
                                setNextToken();
                                fEa();
                                fForStep();
                                if (checkCategory(TipoToken.FEC_PAR)) {
                                    System.out.println(token);
                                    setNextToken();
                                    if (checkCategory(TipoToken.FEC_PAR)) {
                                        System.out.println(token);
                                        setNextToken();
                                        fBody();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void fForStep() {
        if (checkCategory(TipoToken.SEP)) {
            printProduction("ForStep", "',' Ea");
            System.out.println(token);
            setNextToken();
            fEa();
        } else {
            printProduction("ForStep", epsilon);
        }
    }

    public void fIfr() {
        if (checkCategory(TipoToken.PR_ELSE)) {
            printProduction("Ifr", "'condElse' Body");
            System.out.println(token);
            setNextToken();
            fBody();
        } else {
            printProduction("Ifr", epsilon);
        }
    }

    public void fEc() {
        printProduction("Ec", "Fc Ecr");
        fEb();
        fEcr();
    }

    public void fEcr() {
        if (checkCategory(TipoToken.OP_CONCAT)) {
            printProduction("Ecr", "'opConcat' Fc Ecr");
            System.out.println(token);
            setNextToken();
            fEb();
            fEcr();
        } else {
            printProduction("Ecr", epsilon);
        }
    }

    public void fEb() {
        printProduction("Eb", "Tb Ebr");
        fTb();
        fEbr();
    }

    public void fEbr() {
        if (checkCategory(TipoToken.PR_OR)) {
            printProduction("Ebr", "'opOr' Tb Ebr");
            System.out.println(token);
            setNextToken();
            fTb();
            fEbr();
        } else {
            printProduction("Ebr", epsilon);
        }
    }

    public void fTb() {
        printProduction("Tb", "Fb Tbr");
        fFb();
        fTbr();
    }

    public void fTbr() {
        if (checkCategory(TipoToken.PR_AND)) {
            printProduction("Tbr", "'opAnd' Fb Tbr");
            System.out.println(token);
            setNextToken();
            fFb();
            fTbr();
        } else {
            printProduction("Tbr", epsilon);
        }
    }

    public void fFb() {
        if (checkCategory(TipoToken.OP_NOT)) {
            printProduction("Fb", "'opNot' Fb");
            System.out.println(token);
            setNextToken();
            fFb();
        } else if (checkCategory(TipoToken.AB_PAR, TipoToken.OP_SUB, TipoToken.CTE_INT, TipoToken.CTE_BOOL, TipoToken.CTE_CHAR, TipoToken.CTE_FLOAT, TipoToken.CTE_CADCHA, TipoToken.ID)) {
            printProduction("Fb", "Ra Fbr");
            fRa();
            fFbr();
        }
    }

    public void fFbr() {
        if (checkCategory(TipoToken.OP_GREATER)) {
            printProduction("Fbr", "'opGreater' Ra Fbr");
            System.out.println(token);
            setNextToken();
            fRa();
            fFbr();
        } else if (checkCategory(TipoToken.OP_LESS)) {
            printProduction("Fbr", "'opLesser' Ra Fbr");
            System.out.println(token);
            setNextToken();
            fRa();
            fFbr();
        } else if (checkCategory(TipoToken.OP_GREATEQ)) {
            printProduction("Fbr", "'opGreq' Ra Fbr");
            System.out.println(token);
            setNextToken();
            fRa();
            fFbr();
        } else if (checkCategory(TipoToken.OP_LESSEQ)) {
            printProduction("Fbr", "'opLeq' Ra Fbr");
            System.out.println(token);
            setNextToken();
            fRa();
            fFbr();
        } else {
            printProduction("Fbr", epsilon);
        }
    }

    public void fRa() {
        printProduction("Ra", "Ea Rar");
        fEa();
        fRar();
    }

    public void fRar() {
        if (checkCategory(TipoToken.OP_REL)) {
            printProduction("Rar", "'opEquals' Ea Rar");
            System.out.println(token);
            setNextToken();
            fEa();
            fRar();
        } else if (checkCategory(TipoToken.OP_NOT)) {
            printProduction("Rar", "'opNotEqual' Ea Rar");
            System.out.println(token);
            setNextToken();
            fEa();
            fRar();
        } else {
            printProduction("Rar", epsilon);
        }
    }

    public void fEa() {
        printProduction("Ea", "Ta Ear");
        fTa();
        fEar();
    }

    public void fEar() {
        if (checkCategory(TipoToken.OP_AD)) {
            printProduction("Ear", "'opAdd' Ta Ear");
            System.out.println(token);
            setNextToken();
            fTa();
            fEar();
        } else if (checkCategory(TipoToken.OP_SUB)) {
            printProduction("Ear", "'opSub' Ta Ear");
            System.out.println(token);
            setNextToken();
            fTa();
            fEar();
        } else {
            printProduction("Ear", epsilon);
        }
    }

    public void fTa() {
        printProduction("Ta", "Pa Tar");
        fPa();
        fTar();
    }

    public void fTar() {
        if (checkCategory(TipoToken.OP_MULT)) {
            printProduction("Tar", "'opMult' Pa Tar");
            System.out.println(token);
            setNextToken();
            fPa();
            fTar();
        } else if (checkCategory(TipoToken.OP_DIV)) {
            printProduction("Tar", "'opDiv' Pa Tar");
            System.out.println(token);
            setNextToken();
            fPa();
            fTar();
        } else {
            printProduction("Tar", epsilon);
        }
    }

    public void fPa() {
        printProduction("Pa", "Fa Par");
        fFa();
        fPar();
    }

    public void fPar() {
        if (checkCategory(TipoToken.OP_RES)) {
            printProduction("Par", "'opPow' Fa Par");
            System.out.println(token);
            setNextToken();
            fFa();
            fPar();
        } else {
            printProduction("Par", epsilon);
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
            printProduction("Fa", "'opSub' Fa");
            System.out.println(token);
            setNextToken();
            fFa();
        } else if (checkCategory(TipoToken.ID)) {
            fIdOrFunCall();
        } else if (checkCategory(TipoToken.CTE_BOOL)) {
            printProduction("Fa", "'constBool'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_CHAR)) {
            printProduction("Fa", "'constChar'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_FLOAT)) {
            printProduction("Fa", "'constFloat'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_INT)) {
            printProduction("Fa", "'constInt'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(TipoToken.CTE_CADCHA)) {
            printProduction("Fa", "'constStr'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void fIdOrFunCall() {
        if (checkCategory(TipoToken.ID)) {
            printProduction("IdOrFunCall", "'id' IdOrFunCallr");
            System.out.println(token);
            setNextToken();
            fIdOrFunCallr();
        }
    }

    public void fIdOrFunCallr() {
        if (checkCategory(TipoToken.AB_PAR)) {
            printProduction("IdOrFunCallr", "'(' LParamCall ')'");
            System.out.println(token);
            setNextToken();
            fLParamCall();
            if (!checkCategory(TipoToken.FEC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(TipoToken.AB_COL)) {
            printProduction("IdOrFunCallr", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (!checkCategory(TipoToken.FEC_COL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("IdOrFunCallr", epsilon);
        }
    }
}