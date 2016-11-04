import lexer.FileUtil;
import lexer.Lexer;

import java.util.ArrayList;

/**
 * Created by phoebegl on 2016/11/4.
 */
public class Main {

    public static void main(String[] args) {
        Controller con = new Controller("input.txt");
        con.analyse();
        FileUtil.finish();
    }
}
