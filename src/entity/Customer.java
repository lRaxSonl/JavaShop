package entity;

public class Customer {
    private String username;
    private String password;
    private double balance;
    private int rating;

    public Customer(String username, String password, double balance, int rating) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}


