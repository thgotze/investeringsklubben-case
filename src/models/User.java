package models;

import java.time.LocalDate;

public final class User implements Comparable<User> {

    private final int userId;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private double initialCashDKK;
    private final LocalDate createdDate;
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

    // Getters & Setters
    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        updateLastUpdated();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        updateLastUpdated();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        updateLastUpdated();
    }

    public double getInitialCashDKK() {
        return initialCashDKK;
    }

    public void setInitialCashDKK(double initialCashDKK) {
        this.initialCashDKK = initialCashDKK;
        updateLastUpdated();
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    private void updateLastUpdated() {
        this.lastUpdated = LocalDate.now();
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
        updateLastUpdated();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        updateLastUpdated();
    }

    @Override
    public String toString() {
        return ("Fulde Navn: " + fullName + ", Email: " + email + ", Admin: " + admin + ", Bruger ID: " + userId);
    }

    @Override
    public int compareTo(User o) {
        return this.userId - o.getUserId();
    }
}