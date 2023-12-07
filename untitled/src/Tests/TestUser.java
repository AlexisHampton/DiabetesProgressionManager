package Tests;

import Main.Patient;
import Main.User;
import org.junit.Test;

public class TestUser {
    @Test
    public void TestPatientFillIn()
    {
        User user = new User(1,"user1", "pass1");
        Patient patient = new Patient(TestData.a, user);
        

    }
}
