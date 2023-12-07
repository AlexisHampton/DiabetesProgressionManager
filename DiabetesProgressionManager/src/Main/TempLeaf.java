package Main;

//Class Main.TempLeaf creates a temporary leaf, which counts the amount of yes's and no's for a particular branch of a Main.DecisionTreeNode
//Used to calculate the impurity of the branch and nothing more
public class TempLeaf {

    float yes;
    float no;

    //Initializes Main.TempLeaf with a yes and no value
    public TempLeaf(int y, int n){
        yes = y;
        no = n;
    }

    //Returns the impurity of the Leaf by using the Gini Impurity Formula
    public float CalculateImpurity() {
        float total = yes + no;

        //stops branches that are split in an unbalanced manner to report NaN
        if (total == 0) return 0;
        yes /= total;
        no /= total;
        return 1- yes*yes - no*no;
    }

}
