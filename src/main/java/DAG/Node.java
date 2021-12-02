package DAG;

abstract public class Node {
    abstract public BoundBox getBounds(Coord2D deltaCoord);
    abstract public Coord2D getPosition();
    abstract public void setPosition(Coord2D newPosition);
    abstract public boolean checkGraphHasCycle(Origin searchOrigin);
}
