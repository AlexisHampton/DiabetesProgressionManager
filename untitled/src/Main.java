import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Info> allInfo = new ArrayList<Info>();

    public static void main(String[] args) {
        //parse diabetes info into info
        ParseData();
/*
        for(int i = 0; i < allInfo.size(); i++)
            System.out.println(allInfo.get(i).toString());
*/
        //create tree
        DecisionTreeManager decisionTreeManager = new DecisionTreeManager(allInfo);
        decisionTreeManager.CreateDecisionTrees();


    }

    public static void ParseData()
    {
        try {
            File file = new File("Assignment3_DiabetesData.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine())
            {
                String data = scanner.nextLine();
                String[] dataArr = data.split("\\s+");
                Info info = new Info(
                        Integer.parseInt(dataArr[0]), //age
                        Integer.parseInt(dataArr[1]), //sex
                        Float.parseFloat(dataArr[2]), //bmi
                        Float.parseFloat(dataArr[3]), //blood pressure
                        Integer.parseInt(dataArr[4]), //total serum cholesterol , s1
                        Float.parseFloat(dataArr[5]), //low-density lipoproteins, s2
                        Float.parseFloat(dataArr[6]), //high-density lipoproteins, s3
                        Float.parseFloat(dataArr[7]), //total cholesterol / HDL, s4
                        Float.parseFloat(dataArr[8]), //possibly log of serum triglycerides level, s5
                        Integer.parseInt(dataArr[9]), //blood sugar level, s6
                        Integer.parseInt(dataArr[10]) < 150 //is disease progression good, y
                );

                allInfo.add(info);
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

    }

}