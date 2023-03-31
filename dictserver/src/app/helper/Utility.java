package app.helper;

public class Utility {
    public static void callErrorStop(Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
        System.exit(1);
    }
}
