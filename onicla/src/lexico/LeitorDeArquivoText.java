package lexico;

import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class LeitorDeArquivoText {
    private final static int TAMANHO_BUFFER = 20;
    int[] bufferReader;
    int ponteiro;
    int bufferAtual;
    int initLexema;
    private String lexema;
    InputStream is;

    public LeitorDeArquivoText(String arquivo) {
        try {
            is = new FileInputStream(new File(arquivo));
            inicializarBuffer();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }
    private void inicializarBuffer(){
        bufferAtual = 2;
        initLexema = 0;
        lexema = "";
        bufferReader = new int[TAMANHO_BUFFER * 2];
        ponteiro = 0;
        reloadBuffer1();
    }

    private void incrementarPonteiro(){
        ponteiro++;
        if (ponteiro == TAMANHO_BUFFER){
            reloadBuffer2();
        }else if(ponteiro == TAMANHO_BUFFER * 2){
            reloadBuffer1();
            ponteiro = 0;
        }
    }

    private void reloadBuffer1(){
        if(bufferAtual == 2) {
            bufferAtual = 1;
            try{
                for (int i = 0; i < TAMANHO_BUFFER; i++) {

                    bufferReader[i] = is.read();
                    if (bufferReader[i] == -1) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void reloadBuffer2(){
        if (bufferAtual == 1) {
           bufferAtual = 2;
            try {
                for (int i = TAMANHO_BUFFER; i < TAMANHO_BUFFER * 2; i++) {

                    bufferReader[i] = is.read();
                    if (bufferReader[i] == -1) {
                        break;
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private int readCaracterBuffer(){
        int ret = bufferReader[ponteiro];
        incrementarPonteiro();
        return ret;
    }
    public int lerProxCaractere() {
        int c = readCaracterBuffer();
        lexema += (char)c;
        return c;
    }

    public void rectroceder(){
        ponteiro--;
        lexema = lexema.substring(0,lexema.length()-1);  //subtrai do lexema
        if(ponteiro < 0){
            ponteiro = TAMANHO_BUFFER * 2 - 1;
        }
    }

    public void zerar(){
        ponteiro = initLexema;
        lexema = "";
    }
    public void confirmar(){
        initLexema = ponteiro;
        lexema = "";
    }

    public String getLexema(){
        return lexema;
    }
}
