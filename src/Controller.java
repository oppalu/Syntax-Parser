import lexer.FileUtil;
import lexer.Lexer;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by phoebegl on 2016/11/4.
 */
public class Controller {

    private FileUtil fileUtil;
    private ArrayList<String> tokens;
    private Stack<String> stack;
    private LLtable table;

    public Controller(String path) {
        fileUtil = new FileUtil(path);
        Lexer lex = new Lexer(fileUtil);
        lex.analyse();
        tokens = lex.getTokens();
        stack = new Stack<>();
        table = new LLtable();
    }

    public void analyse() {
        stack.push("$");
        stack.push("E");
        int i = 0;
        String input = tokens.get(i);
        while(!input.equals(-1)) {
            String s = stack.peek();
            //最终$L与$R匹配,则表示成功
            if(s.equals("$") && input.equals("$")) {
                fileUtil.write("匹配成功,完成! ");
                break;
            } else if(s.equals(")") && input.equals(")")){
                //右括号的问题(仍存在疑问)
                stack.pop();
                i++;
                input = tokens.get(i);
            } else {
                String cfg = table.getCFG(s,input);
                if(cfg.equals("error")){
                    System.out.println("未找到对应的非终结符!");
                    break;
                } else if(cfg.equals("")) {
                    //栈顶非终结符和下一个输入的不匹配(为空)
                    System.out.println("错误,不匹配!");
                    break;
                } else {
                    String[] split = table.getSplit(cfg);
                    //若第一个终结符匹配,则直接将栈顶以及该终结符弹出,压入文法后部分
                    if(split[0].equals(input)) {
                        fileUtil.write(cfg+"  ");
                        fileUtil.write("匹配: "+input+"\n");
                        stack.pop();
                        for(int j = split.length-1;j>0;j--) {
                            stack.push(split[j]);
                        }
                        i++;
                        input = tokens.get(i);
                    }else {
                        //否则压栈,从右向左压。若是推出ε,则直接弹出栈顶
                        fileUtil.write(cfg+"\n");
                        stack.pop();
                        if(!split[0].equals("ε")) {
                            for(int j = split.length-1;j>=0;j--) {
                                stack.push(split[j]);
                            }
                        }

                    }
                }
            }
        }
    }
}
