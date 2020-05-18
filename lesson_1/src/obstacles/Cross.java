package obstacles;

import competitors.Competitor;

public class Cross implements Obstacle {
    int distace;

    public Cross(int distace) {
        this.distace = distace;
    }

    @Override
    public boolean doIt(Competitor competitor) {
        boolean result = competitor.run(distace);

        if ( result ) {
            System.out.println("The " + competitor.getName() + " succeed ran cross!");
        } else {
            System.out.println("The " + competitor.getName() + " failed cross!");
        }

        return result;
    }
}
