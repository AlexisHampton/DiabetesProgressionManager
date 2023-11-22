import java.util.ArrayList;

public class Info {
    public int age;
    public int sex;
    public float BMI;
    public float bloodPressure;
    public float totalSerumCholesterol;
    public float lowDensityLipoproteins;
    public float highDensityLipoproteins;
    public float totalCholesterol;
    public float possibilityLogOfSerumTriglyceridesLevel;
    public float bloodSugarLevels;
    public boolean isDiseaseProgressionGood;

    //every variable but in a list
    public ArrayList<Float> data = new ArrayList<Float>();

    //Initializes an Info class based on all of the data neccessary for a patient
    public Info(int age, int sex, float BMI, float bloodPressure,
                float totalSerumCholesterol, float lowDensityLipoproteins, float highDensityLipoproteins,
                float totalCholesterol, float possibilityLogOfSerumTriglyceridesLevel, float bloodSugarLevels,
                boolean isDiseaseProgressionGood)
    {
        //variables might be used in User and Patient classes
        this.age = age;
        this.sex = sex;
        this.BMI  = BMI;
        this.bloodPressure = bloodPressure;
        this.totalSerumCholesterol = totalSerumCholesterol;
        this.lowDensityLipoproteins = lowDensityLipoproteins;
        this.highDensityLipoproteins = highDensityLipoproteins;
        this.totalCholesterol = totalCholesterol;
        this.possibilityLogOfSerumTriglyceridesLevel = possibilityLogOfSerumTriglyceridesLevel;
        this.bloodSugarLevels = bloodSugarLevels;
        this.isDiseaseProgressionGood = isDiseaseProgressionGood;

        //data is used by the decisionTree class
        data.add((float) age);
        data.add((float) sex);
        data.add( BMI);
        data.add(bloodPressure);
        data.add(totalCholesterol);
        data.add(lowDensityLipoproteins);
        data.add(highDensityLipoproteins);
        data.add(totalCholesterol);
        data.add( possibilityLogOfSerumTriglyceridesLevel);
        data.add(bloodSugarLevels);
    }

    //Replaces each 0 with an average
    public void FillMissingData()
    {
        for(int i = 0; i < data.size();i++)
            if(data.get(i) == 0)
                data.set(i, RandomForest.GetAverage(i));
    }

    //Returns a string of the class as a string
    //useful for writing to files
    @Override
    public String toString() {
        return age + " " + sex + " " + BMI + " " + bloodPressure + " " + totalSerumCholesterol + " " + lowDensityLipoproteins
                + " " + highDensityLipoproteins + " " + totalCholesterol + " " + possibilityLogOfSerumTriglyceridesLevel +
                " " + bloodSugarLevels + " " + isDiseaseProgressionGood;
    }
}
