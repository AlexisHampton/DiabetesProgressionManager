package Main;

//class Main.PatientInfo constructs an Main.Info with the addition of a patient ID
//used for bringing back data from the database
public class PatientInfo extends Info {
    public int patientID;

    //initializes a Main.Patient info with a patient id and all the data the info class needs
    public PatientInfo(int pid, int age, int sex, float BMI, float bloodPressure,
                       float totalSerumCholesterol, float lowDensityLipoproteins, float highDensityLipoproteins,
                       float totalCholesterol, float possibilityLogOfSerumTriglyceridesLevel, float bloodSugarLevels,
                       boolean isDiseaseProgressionGood) {
        super(age, sex, BMI, bloodPressure, totalSerumCholesterol, lowDensityLipoproteins, highDensityLipoproteins,
                totalCholesterol, possibilityLogOfSerumTriglyceridesLevel, bloodSugarLevels, isDiseaseProgressionGood);
        patientID = pid;

    }

    //returns a string containing the patientID and all the info information
    @Override
    public String toString() {

        return  patientID + " "+ super.toString();
    }
}
