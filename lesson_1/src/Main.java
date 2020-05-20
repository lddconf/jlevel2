import obstacles.Cross;
import obstacles.Obstacle;
import obstacles.Wall;
import competitors.Cat;
import competitors.Competitor;
import competitors.Human;
import competitors.Robot;

public class Main {
    public static void main(String[] args) {

        Obstacle[] obstacles = {
                new Wall(5),
                new Wall(2),
                new Cross(1000),
                new Cross(5000),
                new Cross(6000)
        };

        Competitor[] competitors = {
            new Robot("Rbot", 7000, 5),
            new Human("Human", 5000, 2),
            new Cat("Barsik", 400, 4)
        };

        for ( Competitor c: competitors ) {
            NextCompetitor: for ( Obstacle o : obstacles ) {
                if (!o.doIt(c)) {
                    System.out.println("Competitor " + c.getName() + " left competition");
                    break NextCompetitor; //Понятно, что можно и без метки
                }
            }
        }
    }
}
