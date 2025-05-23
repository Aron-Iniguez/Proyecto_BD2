package modelo;

import java.util.Date;

public class Usuario {
    private int userId;
    private Date dateAdded;
    private String username;
    private String password;
    private String email;
    private int preferredPlatformId;
    private String accessType; // "user" or "admin"

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String username, String password, String email, int preferredPlatformId, String accessType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.preferredPlatformId = preferredPlatformId;
        this.accessType = accessType;
    }

    // Getters y Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPreferredPlatformId() {
        return preferredPlatformId;
    }

    public void setPreferredPlatformId(int preferredPlatformId) {
        this.preferredPlatformId = preferredPlatformId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
}