import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * Created by JFormDesigner on Sat Aug 14 04:33:04 IST 2021
 */



/**
 * @author unknown
 */
public class CompoundInterest extends JFrame {
    // Connection to the Database
    Connection1 con = new Connection1();
    // creating connection object
    Connection conObj = con.connect();

    public CompoundInterest() throws SQLException, ClassNotFoundException {
        initComponents();
    }


    // Github Link = https://github.com/amarpreet2209/Amarpreet_300337710_2175Final
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // creating form object
        CompoundInterest form1 = new CompoundInterest();
        // setting form visibility true
        form1.setVisible(true);
        // calling function to load DB data to table
        form1.updateTable();
    }

    // This function loads data stored in database in the table
    public void updateTable() throws SQLException {
        // Creating query to be executed
        String quer1 = "Select * from savingstable";
        PreparedStatement query = conObj.prepareStatement(quer1);

        // Executing query and getting result in ResultSet object
        ResultSet rs = query.executeQuery();

        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        // taking cursor to the last record
        rs.last();

        // getting number of rows
        int z = rs.getRow();

        // taking cursor pointer before first record
        rs.beforeFirst();


        // initializing 2d array
        String [][] array = new String[0][];
        if(z > 0){
            array = new String[z][5];
        }

        int j =0;

        while(rs.next()){
            array[j][0]= rs.getString("custno");
            array[j][1] = rs.getString("custname");
            array[j][2] = rs.getString("cdep");
            array[j][3] = rs.getString("nyears");
            array[j][4] = rs.getString("savtype");
            ++j;

        }

        // declaring column names
        String[] cols = {"Number", "Name","Deposit","Years","Type of Savings"};

        // creating model using 2d array with data and column names
        DefaultTableModel model = new DefaultTableModel(array, cols);

        // setting table model
        table1.setModel(model);
    }

    // This function gets executed when add button is clicked
    private void btnaddActionPerformed(ActionEvent e) throws SQLException {
        // getting values of textfields

        String custNumber = txtcustnum.getText();
        String custName = txtcustname.getText();
        String initialDeposit = txtinitialdep.getText();
        String numOfYears = txtnumyears.getText();
        String savingsType = (String) combosavings.getSelectedItem();


        // preparing and executing query
        String query1 = "Select * from savingstable where custno=?";
        PreparedStatement query = conObj.prepareStatement(query1);
        query.setString(1,custNumber);
        ResultSet rs = query.executeQuery();

        // if record with same customer number exists
        if (rs.isBeforeFirst()) {
            JOptionPane.showMessageDialog(null, "The record is existing");

            // cleating text fields
            txtcustnum.setText("");
            txtcustname.setText("");
            txtinitialdep.setText("");
            txtnumyears.setText("");
            return;
        }
        // temporarily holding data in object
        Savings savings = new Savings(custNumber,custName,initialDeposit,numOfYears,savingsType);

        // creating wuery
        String query2 = "insert into savingstable values (?,?,?,?,?)";
        query = conObj.prepareStatement(query2);

        query.setString(1,custNumber);
        query.setString(2,custName);
        query.setString(3,initialDeposit);
        query.setString(4,numOfYears);
        query.setString(5,savingsType);

        // executing query
        query.executeUpdate();
        // displaying message
        JOptionPane.showMessageDialog(null, "One record added");
        // updating table
        updateTable();

        // clearing textfields
        txtcustnum.setText("");
        txtcustname.setText("");
        txtnumyears.setText("");
        txtinitialdep.setText("");
    }

    // This function is executed when edit button is clicked
    private void btneditActionPerformed(ActionEvent e) throws SQLException {
        // TODO add your code here
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        // getting textfield values
        String custNumber = txtcustnum.getText();
        String custName = txtcustname.getText();
        String initialDeposit = txtinitialdep.getText();
        String numOfYears = txtnumyears.getText();
        String savingsType = (String) combosavings.getSelectedItem();

        // getting selected row
        int index1 = table1.getSelectedRow();
        String oldvalue = (String) df.getValueAt(index1,0);

        // creating and executing query
        String query1 = "update savingstable set custno = ?,custname=?,cdep=?,nyears=?,savtype=? where custno=?";
        PreparedStatement query = conObj.prepareStatement(query1);

        query.setString(1,custNumber);
        query.setString(2,custName);
        query.setString(3,initialDeposit);
        query.setString(4,numOfYears);
        query.setString(5,savingsType);
        query.setString(6,oldvalue);

        query.executeUpdate();
        // displaying message
        JOptionPane.showMessageDialog(null, "Record edited");
        updateTable();

        // clearing text fields
        txtcustnum.setText("");
        txtcustname.setText("");
        txtnumyears.setText("");
        txtinitialdep.setText("");

    }

    // This function is executed when table1 is clicked
    private void table1MouseClicked(MouseEvent e) {
        // TODO add your code here
        DefaultTableModel df = (DefaultTableModel) table1.getModel();
        int index1 = table1.getSelectedRow();
        txtcustnum.setText((String) df.getValueAt(index1,0));
        txtcustname.setText((String) df.getValueAt(index1,1));
        txtinitialdep.setText((String) df.getValueAt(index1,2));
        txtnumyears.setText((String) df.getValueAt(index1,3));
        combosavings.setSelectedItem((String) df.getValueAt(index1,4));

        String customerNumber = (String) df.getValueAt(index1,0);
        String customerName = (String) df.getValueAt(index1,1);
        String initialDeposit = (String) df.getValueAt(index1,2);
        String numOfYears = (String) df.getValueAt(index1,3);
        String savingsType = (String) df.getValueAt(index1,4);

        if (savingsType=="Savings-Regular") {
           Regular reg = new Regular(customerNumber, customerName, initialDeposit, numOfYears, savingsType);
           reg.generateTable();
        } else {
            Deluxe del = new Deluxe(customerNumber, customerName, initialDeposit, numOfYears, savingsType);
            del.generateTable();
        }
        double startingValue = Double.parseDouble(initialDeposit);

        compoundInterestTable(Integer.parseInt(numOfYears),startingValue,savingsType);
    }
    private void compoundInterestTable(int numOfYears,double startingValue,String savingsType) {
                DefaultTableModel df = (DefaultTableModel) table2.getModel();

        String [][] array = new String[numOfYears][4];
        String[] cols = {"Year", "Starting","Interest","Ending Value"};

        int j=0;
        double interest;
        for (int i = 0; i < numOfYears; i++) {
            array[j][0] = String.valueOf(i+1);
            array[j][1] = String.valueOf(startingValue);

            if (savingsType=="Savings-Regular") {
                interest = Math.round((startingValue*Regular.interestRate*100)/100.00);
            } else interest = Math.round((startingValue*Deluxe.interestRate*100)/100.00);

            array[j][2] = String.valueOf(Math.round(interest*100)/100.0);
            array[j][3] = String.valueOf(Math.round((startingValue+interest)*100)/100.0);
            startingValue = startingValue+interest;
            j++;
        }
        DefaultTableModel model = new DefaultTableModel(array, cols);
        table2.setModel(model);
    }

    // This function is executed when delete button is clicked
    private void btndeleteActionPerformed(ActionEvent e) throws SQLException {
        // confirming delete
        int input = JOptionPane.showConfirmDialog(null,"Do you really want to delete this record?");

        // if yes
        if (input==0) {
            DefaultTableModel df = (DefaultTableModel) table1.getModel();
            // getting customer number
            String custNumber = txtcustnum.getText();


        // creating and executing query
            String query1 = "delete from savingstable where custno=?";
            PreparedStatement query = conObj.prepareStatement(query1);
            query.setString(1,custNumber);

            query.executeUpdate();
            updateTable();

// clearing text fields
            txtcustnum.setText("");
            txtcustname.setText("");
            txtnumyears.setText("");
            txtinitialdep.setText("");
        }

    }




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label4 = new JLabel();
        txtcustnum = new JTextField();
        label5 = new JLabel();
        txtcustname = new JTextField();
        label1 = new JLabel();
        txtinitialdep = new JTextField();
        label2 = new JLabel();
        txtnumyears = new JTextField();
        label3 = new JLabel();
        combosavings = new JComboBox<>();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        btnadd = new JButton();
        btnedit = new JButton();
        btndelete = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[sizegroup 1,fill]" +
            "[sizegroup 1,fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label4 ----
        label4.setText("Enter the Customer Number");
        contentPane.add(label4, "cell 0 0");
        contentPane.add(txtcustnum, "cell 1 0 2 1");

        //---- label5 ----
        label5.setText("Enter the Customer Name");
        contentPane.add(label5, "cell 0 1");
        contentPane.add(txtcustname, "cell 1 1 2 1");

        //---- label1 ----
        label1.setText("Enter the Initial Deposit");
        contentPane.add(label1, "cell 0 2");
        contentPane.add(txtinitialdep, "cell 1 2 2 1");

        //---- label2 ----
        label2.setText("Enter the number of years ");
        contentPane.add(label2, "cell 0 3");
        contentPane.add(txtnumyears, "cell 1 3 2 1");

        //---- label3 ----
        label3.setText("Choose the type of savings");
        contentPane.add(label3, "cell 0 4");

        //---- combosavings ----
        combosavings.setModel(new DefaultComboBoxModel<>(new String[] {
            "Savings-Deluxe",
            "Savings-Regular"
        }));
        contentPane.add(combosavings, "cell 1 4 2 1");

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 5");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(table2);
        }
        contentPane.add(scrollPane2, "cell 1 5 2 1");

        //---- btnadd ----
        btnadd.setText("Add");
        btnadd.addActionListener(e -> {
            try {
                btnaddActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnadd, "cell 0 6");

        //---- btnedit ----
        btnedit.setText("Edit");
        btnedit.addActionListener(e -> {
            try {
                btneditActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnedit, "cell 0 6");

        //---- btndelete ----
        btndelete.setText("Delete");
        btndelete.addActionListener(e -> {
            try {
                btndeleteActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btndelete, "cell 0 6");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label4;
    private JTextField txtcustnum;
    private JLabel label5;
    private JTextField txtcustname;
    private JLabel label1;
    private JTextField txtinitialdep;
    private JLabel label2;
    private JTextField txtnumyears;
    private JLabel label3;
    private JComboBox<String> combosavings;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JButton btnadd;
    private JButton btnedit;
    private JButton btndelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
