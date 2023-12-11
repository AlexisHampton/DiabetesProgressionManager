package Main;

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
                System.out.println("====Main.Patient Search Results for" + currentUser.getUserName() + ":====");

                for (Patient patient : patients) {
                    System.out.println("Main.Patient ID: " + patient.getPatientID() + ", info: " + patient.getInfo());
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

    private static boolean promptBoolean (String message) { 
        System.out.print(message); 
        return scanner.nextBoolean(); 
        } 
         
        
       private static float promptFloat (String message) { 
        System.out.print(message); 
        return scanner.nextFloat(); 
        } 
       



    public static void renderPatientInfo(Patient patient) {
        System.out.println("Main.Patient Information:");
        System.out.println("Main.Patient ID: " + patient.getPatientID());
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
                System.out.println("====  Warning for Main.Patient " + patient.getPatientID() + "==== \n" +  "Suggestions that might help:");
                System.out.println("Exercise regularly" + "\n" +"Eat healthy " + "\n" + "suggested medications" + "\n" );
            } else {
                System.out.println("==== Yaaaay ===== \n" + "patient " + patient.getPatientID() + ": You are doing great ");
                System.out.println("Continue what you have been doing");
            }
        } else {
            System.out.println("Main.Patient information not available.");
        }
    }


   public static void RenderPatientFillIn(Patient patient)
    {
        if (currentUser != null) {
            // Get the user's patients
            ArrayList<Patient> patients = currentUser.getPatients();

            // Calculate the new patient ID based on the existing patients for the user
            int newPatientID = patients.size() > 0 ? patients.get(patients.size() - 1).getPatientID() + 1 : 1;

            System.out.println("==== Register New Patient ====");

            // Collect information for the new patient using getPatientInfo method
            Info newPatientInfo = getPatientInfo();

            // Create a new patient
            Patient newPatient = new Patient(newPatientInfo, currentUser);
            newPatient.setPatientID(newPatientID);

            // Add the new patient to the user's list
            currentUser.addPatient(newPatient);


            // Add the new patient's info to the database
            Main.AddInfoTODB(currentUser.getUserID(), newPatientID, newPatientInfo);

            System.out.println("New patient registered successfully!");
        }
    }

    private static Info getPatientInfo () {
        System.out.println("==== Enter Patient Information ====");

        int age = promptInt("Enter age: ");
        int sex = promptInt("Enter sex (1 for male, 2 for female): ");
        float bmi = promptFloat("Enter BMI: ");
        float bloodPressure = promptFloat("Enter blood pressure: ");
        int totalSerumCholesterol = promptInt("Enter total serum cholesterol: ");
        float lowDensityLipoproteins = promptFloat("Enter low-density lipoproteins: ");
        float highDensityLipoproteins = promptFloat("Enter high-density lipoproteins: ");
        float totalCholesterol = promptFloat("Enter total cholesterol: ");
        float possibilityLogOfSerumTriglycerides = promptFloat("Enter possibility log of serum triglycerides level: ");
        int bloodSugarLevel = promptInt("Enter blood sugar level: ");
        boolean isDiseaseProgressionGood = promptBoolean("Is disease progression good? (true/false): ");

        return new Info(
                age, sex, bmi, bloodPressure, totalSerumCholesterol, lowDensityLipoproteins,
                highDensityLipoproteins, totalCholesterol, possibilityLogOfSerumTriglycerides, bloodSugarLevel,
                isDiseaseProgressionGood
        );
    }

    public static void RenderUpdatePatient () {
        if (currentUser != null) {
            // Get the user's patients
            ArrayList<Patient> patients = currentUser.getPatients();

            // Display patients for the user to choose from
            RenderPatientSearch();

            // Prompt user to select a patient to update
            int patientIDToUpdate = promptInt("Enter Patient ID to update: ");
            Patient patientToUpdate = searchPatient(currentUser, patientIDToUpdate);

            if (patientToUpdate != null) {
                // Collect updated information for the patient
                Info updatedPatientInfo = getPatientInfo();

                // Update the patient's information
                patientToUpdate.setInfo(updatedPatientInfo);

                // Update the patient's information in the database
                Main.AddInfoTODB(currentUser.getUserID(), patientIDToUpdate, updatedPatientInfo);

                System.out.println("Patient information updated successfully!");
            } else {
                System.out.println("Patient not found.");
            }
        }
    }


    public static void RenderDecision(Patient patient, boolean decision)
    {

    }

    public static  void Exit()
    {

    }





}
