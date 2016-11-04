/**
 * Created by phoebegl on 2016/11/3.
 */
public class LLtable {

    public LLtable() {}
    /*
    由文法生成的预测分析表
     */
    public String[][] ppt = {
            //id      number      +          *          (        )       $
            {"E->TE'","E->TE'"   ,""        ,""        ,"E->TE'",""     ,""     },//E
            {""      ,""         ,"E'->+TE'",""        ,""      ,"E'->ε","E'->ε"},//E'
            {"T->FT'","T->FT'"   ,""        ,""        ,"T->FT'",""     ,""     },//T
            {""      ,""         ,"T'->ε"   ,"T'->*FT'",""      ,"T'->ε","T'->ε"},//T'
            {"F->id" ,"F->number",""        ,""        ,"F->(E)",""     ,""     }//F
    };

    /*
    对每个CFG箭头后方的部分进行拆分方便压栈
     */
    public String[][] rightSplit = {
            {"T","E'"},
            {"+","T","E'"},
            {"ε"},
            {"F","T'"},
            {"ε"},
            {"*","F","T'"},
            {"id"},
            {"number"},
            {"(","E",")"}
    };
    public String[] CFGs = {
            "E->TE'","E'->+TE'","E'->ε","T->FT'","T'->ε","T'->*FT'","F->id","F->number","F->(E)"
    };

    public String getCFG(String stack,String input) {
        int vn = getNonTerminal(stack);
        int vt = getTerminal(input);
        if(vn == -1 || vt == -1)
            return "error";
        return ppt[vn][vt];
    }

    public String[] getSplit(String cfg) {
        int index = -1;
        for(int i = 0;i<CFGs.length;i++) {
            if(CFGs[i].equals(cfg)){
                index = i;
                break;
            }
        }
        return rightSplit[index];
    }

    private int getNonTerminal(String s) {
        String[] vns = {"E","E'","T","T'","F"};
        for(int i = 0;i<vns.length;i++) {
            if(s.equals(vns[i])) {
                return i;
            }
        }
        return -1;
    }

    private int getTerminal(String s) {
        String[] vts = {"id","number","+","*","(",")","$"};
        for(int i = 0;i<vts.length;i++) {
            if(s.equals(vts[i])) {
                return i;
            }
        }
        return -1;
    }
}
