/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author roylee
 */
public interface Lock {
    public boolean obtainLock();
    public boolean lockExist();
    public boolean releaseLock();
    public boolean retry(int numRetry);
}
