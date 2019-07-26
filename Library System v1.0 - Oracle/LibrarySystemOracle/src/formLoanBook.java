
import dao.DAO_LoanRecord;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arron
 */


public class formLoanBook extends javax.swing.JFrame {

    double loanLength;
    ArrayList<Document> lstBooks = new ArrayList<>();
    /** Creates new form formLoanBook */
    public formLoanBook() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Image i = null;
        try {
            i = ImageIO.read(getClass().getResource("/icon/book.png"));
        } catch (IOException ex) {
            Logger.getLogger(formHomeMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        setIconImage(i);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtLoanRecordNum = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtStudentNum = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBooks = new javax.swing.JTable();
        btnAddBook = new javax.swing.JButton();
        btnCreateLoan = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnStartOver = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Loan A Book");

        jLabel2.setText("Loan Record Number");

        jLabel3.setText("Student Number");

        jLabel4.setText("ISBN");

        tblBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ISBN", "Book Name"
            }
        ));
        jScrollPane2.setViewportView(tblBooks);
        if (tblBooks.getColumnModel().getColumnCount() > 0) {
            tblBooks.getColumnModel().getColumn(0).setMinWidth(125);
            tblBooks.getColumnModel().getColumn(0).setPreferredWidth(125);
            tblBooks.getColumnModel().getColumn(0).setMaxWidth(125);
            tblBooks.getColumnModel().getColumn(1).setMinWidth(300);
        }
        tblBooks.getAccessibleContext().setAccessibleParent(tblBooks);

        btnAddBook.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnAddBook.setText("Add Book");
        btnAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBookActionPerformed(evt);
            }
        });

        btnCreateLoan.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnCreateLoan.setText("Create Loan");
        btnCreateLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateLoanActionPerformed(evt);
            }
        });

        btnStartOver.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnStartOver.setForeground(new java.awt.Color(255, 50, 0));
        btnStartOver.setText("Start Over");
        btnStartOver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartOverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
                                .addComponent(btnStartOver, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtLoanRecordNum, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                        .addComponent(txtISBN))
                                    .addComponent(txtStudentNum, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnCreateLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(btnAddBook)))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnStartOver))
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtLoanRecordNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtStudentNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(btnAddBook)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnCreateLoan)
                .addGap(153, 153, 153))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBookActionPerformed
        DAO_LoanRecord dao_loanRecord = new DAO_LoanRecord();
        Document book = dao_loanRecord.getBookByIsbn(txtISBN.getText());
        
        //Adding data into GUI table
        DefaultTableModel tbl_model_books = (DefaultTableModel) tblBooks.getModel();
        tbl_model_books.addRow(new Object[]{book.get("Isbn"), book.get("Book_Name")});
            
        //Adding data into a list variable to be used when buy button pressed
        lstBooks.add(book);
        
    }//GEN-LAST:event_btnAddBookActionPerformed

    private void btnCreateLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateLoanActionPerformed
         DAO_LoanRecord dao_loanRecord = new DAO_LoanRecord();
        
        //Document student = dao_loanRecord.getStudent(txtStudentNum.getText());
        
        String formattedDateIssued = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        
        Document loan = new Document() 
            .append("Loan_Record_Num", txtLoanRecordNum.getText())
            .append("Student_Num", txtStudentNum.getText()) 
            .append("Date_Issued", formattedDateIssued); 
                
        loan.append("Loan_Books", lstBooks);
        dao_loanRecord.createLoan(loan);
        
        txtISBN.setText("");
        txtLoanRecordNum.setText("");
        txtStudentNum.setText("");
        DefaultTableModel tbl_model_books = (DefaultTableModel) tblBooks.getModel();
        tbl_model_books.setRowCount(0);
        
        JOptionPane.showMessageDialog(null, "Your loan request has been successful", "Loan Created: ", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnCreateLoanActionPerformed

    private void btnStartOverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartOverActionPerformed
         txtISBN.setText("");
         txtLoanRecordNum.setText("");
         txtStudentNum.setText("");
         DefaultTableModel tbl_model_books = (DefaultTableModel) tblBooks.getModel();
         tbl_model_books.setRowCount(0);
    }//GEN-LAST:event_btnStartOverActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formLoanBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formLoanBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formLoanBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formLoanBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formLoanBook().setVisible(true);
            }
        });
    }
    
    /** 
    This method centres the form on screen 
    **/
    void centerFrame() {
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;    
        setLocation(dx, dy);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnCreateLoan;
    private javax.swing.JButton btnStartOver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblBooks;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtLoanRecordNum;
    private javax.swing.JTextField txtStudentNum;
    // End of variables declaration//GEN-END:variables
    
    

}
