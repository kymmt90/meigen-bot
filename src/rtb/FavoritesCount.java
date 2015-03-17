package rtb;

class FavoritesCount {
    private int favoritesCount;
    
    FavoritesCount(int favoritesCount) {
        if (favoritesCount < 0) throw new IllegalArgumentException();
        this.favoritesCount = favoritesCount;
    }
    
    void addTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        builder.append("[")
               .append(favoritesCount)
               .append(" fav]");
    }
    
    int toValue() {
        return favoritesCount;
    }
}
