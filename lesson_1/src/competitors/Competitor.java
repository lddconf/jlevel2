package competitors;

public interface Competitor {
    boolean run( int distance );
    boolean jump( int height );
    String getName();
}