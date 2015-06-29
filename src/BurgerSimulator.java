
/*
 * tryExam.java
 *
 * Created on Apr 30, 2010, 4:45:00 PM
 */
/**
 *
 * @author roylee
 */
import java.sql.*;

public class BurgerSimulator extends javax.swing.JFrame {
private Connection con = null;

private Lock dbLock;
    /** Creates new form tryExam */
    public BurgerSimulator(){
       
        initComponents();
        connectDB();
        dbLock = new dbLock(con,"flag");
        dbLock.releaseLock();
        if(getTotalBurgers() >= 0)
            txtInitial.setText(Integer.toString(getTotalBurgers()));
        else
            txtInitial.setText("0");
        controlButtonProduce();
    }
    public void connectDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam","root","root");
  
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void closeDB(){
        try{
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public int proBurger(){
        int numProduced = 0;
        int numBurg = 0;
        PreparedStatement pstmt = null;
        String sqlUpdate = "UPDATE BURGERINV SET NUMBURGERS = ?";
        try{
            pstmt = con.prepareStatement(sqlUpdate);
            do{
                if(dbLock.obtainLock()){
                    numBurg = getTotalBurgers();
                    if(numBurg > 0){
                        pstmt.setInt(1, --numBurg);
                        pstmt.executeUpdate();
                        dbLock.releaseLock();
                        numProduced++;
                    }
                    else{
                        dbLock.releaseLock();//clear flag after finshed producing
                        break;
                    }
                }
             }while(dbLock.retry(1000));//break out after 1000 failed retries
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return numProduced;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNumBurger = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnProduce = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtInitial = new javax.swing.JTextField();
        txtProcess = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Burger Outlet Simulator");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Inventory"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabel1.setText("Number of Bugers:");

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNumBurger, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnAdd)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNumBurger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Manufacture"));

        btnProduce.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnProduce.setText("Produce Burger");
        btnProduce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProduceActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabel2.setText(" Initial available burgers: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12));
        jLabel3.setText("Total processed burgers:");

        txtInitial.setEditable(false);

        txtProcess.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(14, 14, 14)
                        .addComponent(txtInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(14, 14, 14)
                        .addComponent(txtProcess)))
                .addGap(18, 18, 18)
                .addComponent(btnProduce, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInitial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduce)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtProcess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        System.out.println("Print");
        System.out.println("There are " + getTotalBurgers() + " burgers.");
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnProduceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProduceActionPerformed
        txtProcess.setText(Integer.toString(proBurger()));
        btnProduce.setEnabled(false);
    }//GEN-LAST:event_btnProduceActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeDB();
    }//GEN-LAST:event_formWindowClosing
    private void insert(){
        String sqlInsert = "INSERT INTO BURGERINV VALUES(?)";
        String sqlUpdate = "UPDATE BURGERINV SET NUMBURGERS = ?";
        PreparedStatement pstmtIns = null;
        PreparedStatement pstmtUpd = null;
        int totalInv = 0;
        int totalInsert = 0;
        try{
            totalInv = getTotalBurgers();
            if(totalInv == -1){
                pstmtIns = con.prepareStatement(sqlInsert);
                pstmtIns.setInt(1, Integer.parseInt(txtNumBurger.getText()));
                pstmtIns.executeUpdate();
            }
            else{
                pstmtUpd = con.prepareStatement(sqlUpdate);
                pstmtUpd.setInt(1, Integer.parseInt(txtNumBurger.getText()));
                pstmtUpd.executeUpdate();
            }

            totalInsert = totalInv + Integer.parseInt(txtNumBurger.getText());
            System.out.println("add " + txtNumBurger.getText());
            System.out.println(totalInsert + " burgers inserted.");
            txtInitial.setText(txtNumBurger.getText());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        controlButtonProduce();
    }
    private void controlButtonProduce(){
            if( getTotalBurgers() > 0)
                btnProduce.setEnabled(true);
            else
                btnProduce.setEnabled(false);
    }
    private int getTotalBurgers(){
        String sqlGetTotal = "SELECT * FROM BURGERINV";
        int totalInv = 0;
        Statement stmt = null;
        try{
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetTotal);
            if(rs.next())
                totalInv = rs.getInt(1);
            else
                totalInv = -1;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return totalInv;
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BurgerSimulator().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnProduce;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtInitial;
    private javax.swing.JTextField txtNumBurger;
    private javax.swing.JTextField txtProcess;
    // End of variables declaration//GEN-END:variables
}
