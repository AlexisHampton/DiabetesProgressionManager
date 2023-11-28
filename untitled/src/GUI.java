import java.util.ArrayList;
import java.util.Scanner;
public class GUI {
    
    private User currentUser;
    private static Authorization authorization;
    private static Scanner scanner = new Scanner(System.in);

    public User getCurrentUser() {
        return currentUser;
    }


    public GUI(Authorization authorization) {
        this.authorization = authorization;
    }


     
    public  boolean RenderLogin() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("==== Login ====");
            System.out.print("Please enter your username: ");
            String enteredUsername = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String enteredPassword = scanner.nextLine();
            currentUser = authorization.login(enteredUsername, enteredPassword);

            if (currentUser!=null) {
                System.out.println("====Login successful!====");
                //scanner.close();
                return true;
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
    }


    public void RenderPatientSearch() {
        if (currentUser != null) {
            ArrayList<Patient> patients = currentUser.getPatients();

            if (patients != null && !patients.isEmpty()) {
                System.out.println("====Patient Search Results for" + currentUser.getUserName() + ":====");

                for (Patient patient : patients) {
                    System.out.println("Patient ID: " + patient.getPatientID() + ", info: " + patient.getInfo());
                }
            } 
            else {
                System.out.println("No patients found for  " + currentUser.getUserName());
            }
        }
    }

    


    public Patient searchPatient(User user, int patientID) {
        ArrayList<Patient> patients = user.getPatients();

        if (patients != null && !patients.isEmpty()) {
            for (Patient patient : patients) {
                if (patient.getPatientID() == patientID) {
                    return patient;
                }
            }
        }

        return null;
    }


    public static int promptInt (String message) {
        System.out.print(message);
        return scanner.nextInt();
    }



    public static void renderPatientInfo(Patient patient) {
        System.out.println("Patient Information:");
        System.out.println("Patient ID: " + patient.getPatientID());
        System.out.println("Age: " + patient.getInfo().age);
        System.out.println("Sex: " + patient.getInfo().sex);
        System.out.println("BMI: " + patient.getInfo().BMI);
        System.out.println("bloodPressure: " + patient.getInfo().bloodPressure);
        System.out.println("totalSerumCholesterol: " + patient.getInfo().totalSerumCholesterol);
        System.out.println("lowDensityLipoproteins: " + patient.getInfo().lowDensityLipoproteins);
        System.out.println("highDensityLipoproteins: " + patient.getInfo().highDensityLipoproteins);
        System.out.println("totalCholesterol: " + patient.getInfo().totalCholesterol);
        System.out.println("possibilityLogOfSerumTriglyceridesLevel: " + patient.getInfo().possibilityLogOfSerumTriglyceridesLevel);
        System.out.println("bloodSugarLevels: " + patient.getInfo().bloodSugarLevels);
        System.out.println("isDiseaseProgressionGood: " + patient.getInfo().isDiseaseProgressionGood);

        System.out.println();
    }

    public static void renderWarning(Patient patient) {
        Info patientInfo = patient.getInfo();

        if (patientInfo != null) {

            //boolean progression= patient.predictDiseaseProgression();

            if (!User.showPrediction(patient)) {
                System.out.println("====  Warning for Patient " + patient.getPatientID() + "==== \n" +  "Suggestions that might help:");
                System.out.println("Exercise regularly" + "\n" +"Eat healthy " + "\n" + "suggested medications" + "\n" );
            } else {
                System.out.println("==== Yaaaay ===== \n" + "patient " + patient.getPatientID() + ": You are doing great ");
                System.out.println("Continue what you have been doing");
            }
        } else {
            System.out.println("Patient information not available.");
        }
    }


   public static void RenderPatientFillIn(Patient patient)
    {

    }

    public static void RenderDecision(Patient patient, boolean decision)
    {

    }

    public static  void Exit()
    {

    }





}
