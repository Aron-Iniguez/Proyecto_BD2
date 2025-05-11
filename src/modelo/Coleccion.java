package modelo;

import java.time.LocalDateTime;

public class Coleccion {
    private int collectionId;
    private int userId;
    private int gameId;
    private LocalDateTime dateAdded;
    private int rating;

    // Constructores
    public Coleccion() {
    }

    public Coleccion(int userId, int gameId, int rating) {
        this.userId = userId;
        this.gameId = gameId;
        this.rating = rating;
    }

    public Coleccion(int collectionId, int userId, int gameId, LocalDateTime dateAdded, int rating) {
        this.collectionId = collectionId;
        this.userId = userId;
        this.gameId = gameId;
        this.dateAdded = dateAdded;
        this.rating = rating;
    }

    // Getters y Setters
    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Para mostrar información útil
    @Override
    public String toString() {
        return "Coleccion{" +
                "collectionId=" + collectionId +
                ", userId=" + userId +
                ", gameId=" + gameId +
                ", dateAdded=" + dateAdded +
                ", rating=" + rating +
                '}';
    }
}