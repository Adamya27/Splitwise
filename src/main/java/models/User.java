package models;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String name;
    private HashMap<String , Double> financialRelations;
    private List<Expense> expenseList;

    public User(String name) {
        this.name = name;
        this.financialRelations = new HashMap<>();
        this.expenseList = new ArrayList<>();
    }

    public void addTransaction(String userName, Double amount){
        HashMap<String, Double> finances = getFinancialRelations();
        Double currentBalance = finances.get(userName);
        Double total = currentBalance != null ? currentBalance + amount : amount;
//        System.out.println(amount + " " +currentBalance);
        finances.put(userName, total);
    }

    public Double getDuesWithAPerson(String userName){
        HashMap<String, Double> finances = getFinancialRelations();
        return finances.get(userName);
    }

    public Boolean isUserIndebted(List<User> users){
        HashMap<String , Double> recordMap= getFinancialRelations();
        Boolean isOwed = false;
        for(User user: users){
            String userName = user.getName();
            Double amountOwed = recordMap.get(userName);
            if(amountOwed != null && amountOwed > 0){
                isOwed = true;
                break;
            }
        }


        return isOwed;
    }


    public void addExpense(Expense expense){
        List<Expense> expenses = getExpenseList();
        expenses.add(expense);
        setExpenseList(expenses);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Double> getFinancialRelations() {
        return financialRelations;
    }

    public void setFinancialRelations(HashMap<String, Double> financialRelations) {
        this.financialRelations = financialRelations;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }
}
