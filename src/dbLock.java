/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author roylee
 */

import java.sql.*;
public class dbLock implements Lock {
private Connection con;
private String lockName;

    public dbLock(Connection con,String lockName){
        this.con = con;
        this.lockName = lockName;
    }

    public boolean lockExist(){
        String sqlCount = "SELECT COUNT(*) FROM LOCKTBL";
        Statement stmt = null;
        int val = 0;
        try{
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCount);
            if(rs.next())
                val = rs.getInt(1);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        if(val != 0)//someone is inside.
            return true;
        else
            return false;
    }

    public boolean obtainLock(){
        String sqlInsert = "INSERT INTO LOCKTBL VALUES(?)";
        PreparedStatement pstmt = null;
        try{
            pstmt = con.prepareStatement(sqlInsert);
            pstmt.setString(1, lockName);
            pstmt.executeUpdate();
        }catch(SQLException ex){
            return false;
        }
        return true;
    }

    @SuppressWarnings("static-access")
    public boolean retry(int numRetry){
        boolean success = true;
        int counter = 0;
        while(counter < numRetry){
            if(lockExist()){
                System.out.println("retry!");
                try{
                    new Thread().sleep(1);//0.001sec
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
        String sqlRemove = "DELETE FROM LOCKTBL";
        Statement stmt = null;
        try{
            stmt = con.createStatement();
            stmt.execute(sqlRemove);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
}
