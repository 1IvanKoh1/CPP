public class MetroCard {
    private String serNum;
    private User user;
    private String college;
    private double balance;

    public MetroCard(String serNum, User user, String college, double balance) {
        this.serNum = serNum;
        this.user = user;
        this.college = college;
        this.balance = balance;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "No: " + serNum + "\nUser: " + user +
                "\nCollege: " + college +
                "\nBalance: " + balance;
    }
}
