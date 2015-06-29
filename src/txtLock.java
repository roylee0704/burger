/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author roylee
 */
import java.io.*;
public class txtLock implements Lock{
private File flag;

    public txtLock(String lockName){
        flag = new File(lockName);//"C:\\flag"
    }

    public boolean lockExist(){
        return(flag.exists());
    }

    public boolean obtainLock(){
        try{
            return(flag.createNewFile());
        }catch(Exception ex){
            return false;
        }
    }

    @SuppressWarnings("static-access")
    public boolean retry(int numRetry){
        boolean success = true;
        int counter = 0;
        while(counter < numRetry){
            if(lockExist()){
                System.out.println("retry!");
                try{
                    new Thread().sleep(10);//0.01sec
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else
                break;
            counter++;
        }
        if(counter >= numRetry)
            success = false;

        return success;
    }

    public boolean releaseLock(){
        flag.delete();
        return true;
    }
}
