package models;

import enums.ExpenseType;

import java.util.List;

public class Expense {
    private String name;
    private ExpenseType type;
    private User creditor;
    private List<User> debtors;
    private Double amount;
    private List<Double> distributionRatio;

    public Expense(String name, ExpenseType type, Double amount) {
        this.name = name;
        this.type = type;
        this.amount = amount;

    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<Double> getDistributionRatio() {
        return distributionRatio;
    }

    public void setDistributionRatio(List<Double> distributionRatio) {
        this.distributionRatio = distributionRatio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public User getCreditor() {
        return creditor;
    }

    public void setCreditor(User creditor) {
        this.creditor = creditor;
    }

    public List<User> getDebtors() {
        return debtors;
    }

    public void setDebtors(List<User> debtors) {
        this.debtors = debtors;
    }
}
