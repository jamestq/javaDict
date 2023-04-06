/**
 * Name: Uy Thinh Quang
 * Surname: Quang
 * StudentID: 1025981
 */
package app.helper;

public class Utility {
    public static void callErrorStop(Exception e, int exitCode){
        System.out.println(e.getMessage());
        e.printStackTrace();
        if(exitCode==0) System.exit(exitCode);
    }
}
