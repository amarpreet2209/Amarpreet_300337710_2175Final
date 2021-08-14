import javax.swing.table.DefaultTableModel;

public class Deluxe extends Savings implements Compound_Interest{
    public Deluxe(String customerNumber, String customerName, String initialDeposit, String numOfYears, String typeOfSavings) {
        super(customerNumber, customerName, initialDeposit, numOfYears, typeOfSavings);
    }

    private double interestRate = 0.15;


    public void generateTable() {
//        DefaultTableModel df = (DefaultTableModel) table2.getModel();
//
//        String [][] array = new String[numOfYears][4];
//        String[] cols = {"Year", "Starting","Interest","Ending Value"};
//
//        int j=0;
//        double interest;
//        for (int i = 0; i < numOfYears; i++) {
//            array[j][0] = String.valueOf(i+1);
//            array[j][1] = String.valueOf(startingValue);
//
//            if (savingsType=="Savings-Regular") {
//                interest = Math.round((startingValue*0.10*100)/100.00);
//            } else interest = Math.round((startingValue*0.15*100)/100.00);
//
//            array[j][2] = String.valueOf(Math.round(interest*100)/100.0);
//            array[j][3] = String.valueOf(Math.round((startingValue+interest)*100)/100.0);
//            startingValue = startingValue+interest;
//            j++;
//        }
//        DefaultTableModel model = new DefaultTableModel(array, cols);
//        table2.setModel(model);
    }
}
