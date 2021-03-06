package competitors;

public class Cat implements Competitor  {

    String name;
    int maxRunDistace;
    int height;

    public Cat() {
        this("Cat", 400, 4);
    }

    public Cat(String name, int maxRunDistace, int height) {
        this.name = name;
        this.maxRunDistace = maxRunDistace;
        this.height = height;
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
