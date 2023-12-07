package Tests;

import Main.Info;

public class TestData {

    public static int continuousFeature = 3;
    public static int categoricalFeature = 1;

    //Training Data
    public static Info a = new  Info (50, 2, 26.2f, 97, 186, 105.4f, 49, 4, 5.0626f, 88, false);
    public static Info b = new  Info(61, 1, 24, 91, 202, 115.4f, 72, 3, 4.2905f, 73, true);
    public static Info c = new  Info(34, 2, 24.7f, 118, 254, 184.2f, 39, 7, 5.037f, 81, false);
    public static Info d = new  Info(47, 1, 30.3f, 109, 207, 100.2f, 70, 3, 5.2149f, 98, false);
    public static Info i = new  Info(51, 1, 23.4f, 87, 220, 108.8f, 93, 2, 4.5109f, 82, true );
    public static Info j = new  Info( 50, 2, 29.2f, 119, 162, 85.2f, 54, 3, 4.7362f, 95, false);
    public static Info k = new  Info( 59, 2, 27.2f, 107, 158, 102, 39, 4, 4.4427f, 93, true);
    public static Info l = new  Info(52, 1, 27, 78.33f, 134, 73, 44, 3.05f, 4.4427f, 69, false);
    public static Info m = new  Info( 69, 2, 24.5f, 108, 243, 136.4f, 40, 6, 5.8081f, 100, false);
    public static Info n = new  Info(53, 1, 24.1f, 105, 184, 113.4f, 46, 4, 4.8122f, 95, true);

    //Testing Data
    public static Info e = new  Info(42, 2, 30.6f, 101, 269, 172.2f, 50, 5, 5.4553f, 106, false);
    public static Info f = new  Info(25, 2, 22.6f, 85, 130, 71, 48, 3, 4.0073f, 81, true);
    public static Info g = new  Info(52, 2, 19.7f, 81, 152, 53.4f, 82, 2, 4.4188f, 82, true);
    public static Info h = new  Info(34, 1, 21.2f, 84, 254, 113.4f, 52, 5, 6.0936f, 92, true);

    //Test Data Set that's used a lot
    public static Info[] data = {a,b,c,d};

}
