package obstacles;

import competitors.Competitor;

public class Wall implements Obstacle {
    int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public boolean doIt(Competitor competitor) {
        boolean result = competitor.jump(height);

        if ( result ) {
            System.out.println("The " + competitor.getName() + " jump succeed!");
        } else {
            System.out.println("The " + competitor.getName() + " jump failed!");
        }
        return result;
    }
}