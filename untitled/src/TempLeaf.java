public class TempLeaf {
    float yes;
    float no;

    public TempLeaf(int y, int n){
        yes = y;
        no = n;
    }

    public float CalculateImpurity() {
        float total = yes + no;
        if (total == 0) return 0;
       // System.out.println("tli: total: " + total + " y:" + yes + " n:" + no);
        yes /= total;
        no /= total;
       // System.out.println("tli: total: " + total + " y:" + yes + " n:" + no);
        //System.out.println("temp leaf impurity: " + (1- yes*yes - no*no));
        return 1- yes*yes - no*no;
    }

}
