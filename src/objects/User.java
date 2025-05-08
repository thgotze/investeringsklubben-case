package objects;

import java.time.LocalDate;

public class User implements Comparable<User> {

    private int userId;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private double initialCashDKK;
    private LocalDate createdDate;
    private LocalDate lastUpdated;

    // Egne tilføjelser
    private boolean admin;
    private String password;

    // Constructor
    public User(int userId, String fullName, String email, LocalDate birthDate, double initialCashDKK, LocalDate createdDate, LocalDate lastUpdated, boolean admin, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.initialCashDKK = initialCashDKK;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;

        // Egne tilføjelser
        this.admin = admin;
        this.password = password;
    }


    // Setters & Getters
    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public double getInitialCashDKK() {
        return initialCashDKK;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setInitialCashDKK(double initialCashDKK) {
        this.initialCashDKK = initialCashDKK;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ("Fulde Navn: " + fullName + ", Email: " + email + ", Saldo: " + initialCashDKK);
    }

    @Override
    public int compareTo(User o) {
        return this.userId - o.getUserId();
    }
}