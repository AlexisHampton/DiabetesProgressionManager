import java.util.ArrayList;

public class User {
    private int userID;
    private String userName;
    private String password;

    ArrayList<Patient> patients;

    Patient SearchForPatient(Patient patient) {
        return null;
    }

    void ShowPatients() {
        //all of the patients User has
        ArrayList<PatientInfo> patientInfos = Main.GetInfoFromDB(userID);
    }

    void ShowPatientInfo(int patientID) {
        Info info = Main.GetInfoFromDB(userID, patientID);
    }

    void FillInNewPatientInfo(Patient patient, Info info) {
        Main.AddInfoTODB(userID, patient.patientID, info);
    }

    void ShowPrediction(Patient patient) {

    }


}
