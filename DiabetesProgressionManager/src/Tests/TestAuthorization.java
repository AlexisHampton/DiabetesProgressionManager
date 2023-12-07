package Tests;

import Main.Authorization;
import org.junit.Assert;
import org.junit.Test;

public class TestAuthorization {
    @Test
    public void TestUserLogin()
    {
        boolean value = Authorization.login("user1", "pass1") == null;
        Assert.assertEquals(false, value);
    }

    @Test
    public void TestUserLogin2(){
        boolean value = Authorization.login("user2", "pass1") == null;
        Assert.assertEquals(true, value);
    }

}
