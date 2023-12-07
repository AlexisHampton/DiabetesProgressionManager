package Main;

//Class Main.DecisionTreeLeaf contains an index and a boolean by which a diseaseProgression prediction can be had in a decision tree traversal
public class DecisionTreeLeaf {

    boolean isDiseaseProgressionGood;
    int index;

    //for printing purposes only
    String name;

    //Accepts a boolean prediction and an index to populate its values at intitialization
    public DecisionTreeLeaf(boolean IDPG, int i)
    {
        index = i;
        isDiseaseProgressionGood = IDPG;
    }


    public void SetName(String n)
    {
        name = n + ".leaves[" + index + "]";
    }

    @Override
    public String toString() {
        return name + " = " + isDiseaseProgressionGood;
    }
}

