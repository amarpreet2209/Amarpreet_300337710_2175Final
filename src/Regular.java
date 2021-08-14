import javax.swing.*;

public class Regular extends Savings implements Compound_Interest{
    public Regular(String customerNumber, String customerName, String initialDeposit, String numOfYears, String typeOfSavings) {
        super(customerNumber, customerName, initialDeposit, numOfYears, typeOfSavings);
    }
    public static double interestRate = 0.10;

    public void generateTable() {

    }
}
