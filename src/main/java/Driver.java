import Services.SplitwiseGroup;
import enums.ExpenseType;
import models.Expense;
import models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Driver {

    public static void main(String args[]) throws FileNotFoundException {
        SplitwiseGroup group = new SplitwiseGroup();
        group.addUser("u1");
        group.addUser("u2");
        group.addUser("u3");
        group.addUser("u4");

        File file = new File("/Users/b0212222/Downloads/Splitwise/src/main/resources/Input");
        Scanner sc = new Scanner(file);

        while(sc.hasNext()){
            String input = sc.nextLine();

            String[] inputArray = input.split(" ");

            if(inputArray[0].equals("SHOW")){
                if (inputArray.length == 1){
                    group.getAllRecords();
                }
                else{
                    group.getRecordForOneUser(inputArray[1]);
                }
            }
            else{
                String expenseName = inputArray[1];
                String creditorName = inputArray[2];
                User creditor = group.getUser(creditorName);
                if(Objects.isNull(creditor)){

                    continue;
                }
                Double amount = Double.parseDouble(inputArray[3]);
                int numberOfDebtors = Integer.valueOf(inputArray[4]);
                List<User> debtors = new ArrayList<>();
                int j = 5;
                for(int i=0; i<numberOfDebtors; i++){
                    j = 5+i;
                    User debtor = group.getUser(inputArray[5+i]);
                    if(Objects.isNull(debtor)){
                        continue;
                    }
                    debtors.add(debtor);
                }
                ++j;
                String type = inputArray[j];
                ++j;


                ExpenseType expenseType = getExpenseType(type);
                Expense expense = new Expense(expenseName,expenseType, amount);
                expense.setCreditor(creditor);
                expense.setDebtors(debtors);
                List<String> ratio = new ArrayList<>();
                for(; j<inputArray.length; j++){
                    ratio.add(inputArray[j]);
                }
                List<Double> distributionShare = group.getDistributionAmount(expenseType,amount, numberOfDebtors, ratio);

                expense.setDistributionRatio(distributionShare);
                group.addExpense(expense);
            }
        }
    }

    public static ExpenseType getExpenseType(String type){
        switch (type){
            case "EQUAL": return ExpenseType.EQUAL;
            case "PERCENT": return ExpenseType.PERCENT;
            case "EXACT": return ExpenseType.EXACT;
            case "SHARE": return ExpenseType.SHARE;
        }

        return ExpenseType.EXACT;
    }


}
