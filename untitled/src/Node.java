import java.util.ArrayList;
public abstract class Node {

    ArrayList<Info> dataPoints = new ArrayList<Info>();
    ArrayList<Node> children = new ArrayList<>();
    ArrayList<Leaf> leaves = new ArrayList<Leaf>();

    void Branch(int featureIndex)//features go up here
    {
        //creates children and or leaves, by splitting on a feature
    }

    int FindBestFeature(){
        //Branches on each feature and calculates impurity for each branch


        return 0; //feature index
    }

    public  float CalculateImpurity()
    {
        return 0;
    }

    float CalculateTotalGiniImpurity(){
        return 0;
    }

    float CalculateScore()
    {
        return 0;
    }

    void CreateLeaf(boolean value)
    {
    }

}
