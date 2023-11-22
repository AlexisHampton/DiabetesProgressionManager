//Class DecisionTreeLeaf contains an index and a boolean by which a diseaseProgression prediction can be had in a decision tree traversal
public class DecisionTreeLeaf {

    boolean isDiseaseProgressionGood;
    int index;

    //Accepts a boolean prediction and an index to populate its values at intitialization
    public DecisionTreeLeaf(boolean IDPG, int i)
    {
        index = i;
        isDiseaseProgressionGood = IDPG;
    }



}

