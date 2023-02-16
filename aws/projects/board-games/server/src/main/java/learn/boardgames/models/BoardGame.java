package learn.boardgames.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardGame {
    private int id;
    private String name;
    private String imageUrl;
    private double rating;
    private int minimumPlayers;
    private int maximumPlayers;
    private boolean inPrint;

    private List<Category> categories = new ArrayList<>();

    public BoardGame() { }

    public BoardGame(int id, String name, String imageUrl, double rating, int minimumPlayers, int maximumPlayers, boolean inPrint) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.minimumPlayers = minimumPlayers;
        this.maximumPlayers = maximumPlayers;
        this.inPrint = inPrint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public void setMinimumPlayers(int minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public void setMaximumPlayers(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    public boolean isInPrint() {
        return inPrint;
    }

    public void setInPrint(boolean inPrint) {
        this.inPrint = inPrint;
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "BoardGame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", rating=" + rating +
                ", minimumPlayers=" + minimumPlayers +
                ", maximumPlayers=" + maximumPlayers +
                ", inPrint=" + inPrint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGame boardGame = (BoardGame) o;
        return id == boardGame.id && Double.compare(boardGame.rating, rating) == 0 && minimumPlayers == boardGame.minimumPlayers && maximumPlayers == boardGame.maximumPlayers && inPrint == boardGame.inPrint && name.equals(boardGame.name) && imageUrl.equals(boardGame.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, rating, minimumPlayers, maximumPlayers, inPrint);
    }
}
