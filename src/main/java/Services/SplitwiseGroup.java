package Services;

import enums.ExpenseType;
import models.Expense;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SplitwiseGroup {
    private List<User> users;
    private List<Expense> expenseList;
    private Boolean duesPending;

    public SplitwiseGroup() {
        this.users = new ArrayList<>();
        this.expenseList = new ArrayList<>();
        this.duesPending = false;
    }

    public void addUser(String name){
        User newUser = new User(name);
        List<User> userList = getUsers();
        userList.add(newUser);
    }

    public User getUser(String name){
        List<User> userList = getUsers();
        for(User user: userList){
            if(user.getName().equals(name)){
                return user;
            }
        }

        System.out.println(" INVALID EXPENSE: User" + name + " doesn't exist");
        return null;
    }

    public void addExpense(Expense expense){
        User debtor = expense.getCreditor();
        List<User> creditors = expense.getDebtors();
        List<Double> distributionRatio = expense.getDistributionRatio();
        for(int i=0; i<creditors.size(); i++){
            Double share = distributionRatio.get(i);
            User creditor = creditors.get(i);
            creditor.addTransaction(debtor.getName(), share);
            creditor.addExpense(expense);
            debtor.addTransaction(creditor.getName(), -share);
        }
        checkBalance();
        System.out.println("EXPENSE SUCCESSFULLY ADDED");
    }

    public void checkBalance(){
        List<User> users = getUsers();
        for (User user: users){
            if(user.isUserIndebted(users)){
                setDuesPending(true);
                return;
            }
        }
        setDuesPending(false);
    }

    public List<Double> getDistributionAmount(ExpenseType type, Double amount, int numberOfUsers, List<String> ratio){
        List<Double> shares = new ArrayList<>();
        if(type == ExpenseType.EQUAL){
            Double eachShare = amount/numberOfUsers;
            for(int i=0; i<numberOfUsers; i++){
                Double share = i == 0? amount - eachShare*(numberOfUsers - 1) : eachShare;
                shares.add(share);
            }
        }
        else if (type == ExpenseType.EXACT){
            Double total = 0.0;
            for (int i=0; i<numberOfUsers; i++){
                Double share = Double.parseDouble(ratio.get(i));
//                System.out.println(share);
                total += share;
                shares.add(share);
            }

            if(!total.equals(amount)){
                System.out.println("INVALID EXPENSE: Expense sharing doesn't add up to the amount");
                return null;

            }

        }
        else if(type == ExpenseType.PERCENT){
            Double total = 0.0;
            for (int i=0; i<numberOfUsers; i++){
                Double share = Double.parseDouble(ratio.get(i));
                total += share;
                shares.add(share * amount / 100);
            }
            if( !total.equals(100.0)){
                System.out.println("INVALID EXPENSE: Distribution doesn't add up to 100%");
                return null;
            }
        }
        return shares;

    }

    public Boolean getUserRecords(String name){
        List<User> users = getUsers();
        User currentUser = null;
        for(User user: users){
            if(user.getName().equals(name)){
                currentUser = user;
            }
        }

        if(Objects.isNull(currentUser)){
            System.out.println("Records of user Not found");
            return false;
        }
        HashMap<String,Double> recordMap = currentUser.getFinancialRelations();
        if(recordMap.isEmpty()){
            return false;
        }
        if(!currentUser.isUserIndebted(users)){
            return false;
        }
        recordMap.forEach((key,value) -> {
            printBalance(name, key, value);
        });

        return true;
    }

    public void getAllRecords(){
        if(getDuesPending() == false){
            printNoBalance();
            return;
        }
        List<User> users = getUsers();
        for (User user: users){
            getUserRecords(user.getName());
        }
    }

    public void getRecordForOneUser(String name){
        if(!getUserRecords(name)){
            printNoBalance();
        }
    }


    public void printNoBalance(){
        System.out.println("No Balances");
    }

    public void printBalance(String debtor, String creditor, Double value){

        if(value > 0){
            System.out.println(debtor + " owes " + creditor + " " + value);
        }

    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public Boolean getDuesPending() {
        return duesPending;
    }

    public void setDuesPending(Boolean duesPending) {
        this.duesPending = duesPending;
    }
}
