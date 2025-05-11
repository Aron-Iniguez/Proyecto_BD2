package modelo;

public class Juego {
    private int gameId;
    private String gameName;
    private int platformId;
    private int yearReleased;
    private String imageUrl;

    public Juego() {}

    public Juego(String gameName, int platformId, int yearReleased, String imageUrl) {
        this.gameName = gameName;
        this.platformId = platformId;
        this.yearReleased = yearReleased;
        this.imageUrl = imageUrl;
    }

    // Getters y setters
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public int getPlatformId() { return platformId; }
    public void setPlatformId(int platformId) { this.platformId = platformId; }

    public int getYearReleased() { return yearReleased; }
    public void setYearReleased(int yearReleased) { this.yearReleased = yearReleased; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
