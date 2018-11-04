package catalog.statistics;

public enum Rating {
    WORST(1),
    VERY_POOR(2),
    SIGNIFICALLY_BELOW_AVERAGE(3),
    BELOW_AVERAGE(4),
    AVERAGE(5),
    ABOVE_AVERAGE(6),
    SIGNIFICALLY_ABOVE_AVERAGE(7),
    GOOD(8),
    VERY_GOOD(9),
    BEST(10);

    private final int rate;

    Rating(int rate) {
        this.rate = rate;
    }

    public static Rating getRating (int rate){
        Rating ratingToRet = null;
        for (Rating rating : Rating.values()) {
            if (rating.rate==rate){
                ratingToRet= rating;
            }
        }
        return ratingToRet;
    }

}
