package lexico;

import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class LeitorDeArquivoText {
    InputStream is;
    public LeitorDeArquivoText(String arquivo) throws FileNotFoundException {
        is = new FileInputStream(new File(arquivo));
    }

    public int lerProxCaractere() {
        int ret = 0;
        try {
            ret = is.read();
            System.out.println((char)ret);
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }
}
