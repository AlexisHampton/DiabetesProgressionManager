public class Info {
    public int age;
    public int sex;
    public float bloodSugarLevels;
    public float BMI;
    public float bloodPressure;
    public float totalSerumCholesterol;
    public float lowDensityLipoproteins;
    public float highDensityLipoproteins;
    public float totalCholesterol;
    public float possibilityLogOfSerumTriglyceridesLevel;
    public boolean isDiseaseProgressionGood;

    public Info(int age, int sex, float BMI, float bloodPressure,
                float totalSerumCholesterol, float lowDensityLipoproteins, float highDensityLipoproteins,
                float totalCholesterol, float possibilityLogOfSerumTriglyceridesLevel, float bloodSugarLevels,
                boolean isDiseaseProgressionGood)
    {
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
    }

    @Override
    public String toString() {
        return age + " " + sex + " " + BMI + " " + bloodPressure + " " + totalSerumCholesterol + " " + lowDensityLipoproteins
                + " " + highDensityLipoproteins + " " + totalCholesterol + " " + possibilityLogOfSerumTriglyceridesLevel +
                " " + bloodSugarLevels + " " + isDiseaseProgressionGood;
    }
}
