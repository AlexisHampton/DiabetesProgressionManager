package Main;

import java.util.ArrayList;

public class User {
    private int userID;
    private String userName;
    private String password;

    ArrayList<Patient> patients;

    public User (int UID, String UName, String password) {
        this.userID=UID;
        this.userName = UName;
        this.password=password;
        this.patients = new ArrayList<>();
    }


    void ShowPatients() {
        //all of the patients Main.User has
        ArrayList<PatientInfo> patientInfos = Main.GetInfoFromDB(userID);
    }

    void ShowPatientInfo(int patientID) {
        Info info = Main.GetInfoFromDB(userID, patientID);
    }

    void FillInNewPatientInfo(Patient patient, Info info)
    {
        if (patient != null && info != null) {
            patient.setInfo(info);
            System.out.println("Main.Patient information filled in successfully.");
        } else {
            System.out.println("Invalid patient information ");
        }


    }

    public static boolean showPrediction (Patient patient) {
        boolean prediction = patient.predictDiseaseProgression();

        if (!prediction){
        System.out.println("Prediction for Patient " + patient.getPatientID() + " using RandomForest is  Bad \n");
        return false;
        }
        else {
            System.out.println("Prediction for Patient " + patient.getPatientID() + " using RandomForest is Good \n");
            return true;
        }
    }


    public String getUserName () {
        return userName;
    }

    public String getPassword () {
        return password;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }


    public ArrayList<Patient> getPatients() {
        return patients;
    }






}
