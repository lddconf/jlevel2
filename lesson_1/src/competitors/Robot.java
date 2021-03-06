package competitors;

public class Robot implements Competitor {
    int maxRunDistace;
    int height;
    String name;

    public Robot() {
        this("Robot", 3000, 5 );
    }

    public Robot( String name,int maxRunDistace, int height ) {
        this.maxRunDistace = maxRunDistace;
        this.height = height;
        this.name = name;
    }

    @Override
    public boolean run(int distance) {
        if ( distance <= maxRunDistace) {
            System.out.println("The " + name + " ran on " + distance + " meters");
            return true;
        }
        System.out.println("The " + name + " can't run on " + distance + " meters");
        return false;
    }

    @Override
    public boolean jump(int height) {
        if ( height <= this.height ) {
            System.out.println("The " + name + " jumped on " + height + " meters");
            return true;
        }
        System.out.println("The " + name + " can't jump on " + height + " meters");

        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
