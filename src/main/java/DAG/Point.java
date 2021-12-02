package DAG;

public class Point extends Node {
    private Coord2D position;

    public Point(Coord2D position) {
        this.position = position;
    }

    /**
     * @return position of this point relative to its ancestors.
     */
    @Override
    public Coord2D getPosition() {
        return position;
    }

    /**
     * Sets relative position of point to its ancestors.
     *
     * @param newPosition Coord2D object with new relative coordinates of point.
     */
    @Override
    public void setPosition(Coord2D newPosition) {
        position = newPosition;
    }

    /**
     * Returns zero square bound box formed by points coordinates.
     *
     * @param deltaCoord delta in absolute coordinates.
     * @return bound box of the point.
     */
    @Override
    public BoundBox getBounds(Coord2D deltaCoord) {
        var deltedCoord = new Coord2D(position.getX() +
                deltaCoord.getX(), position.getY() + deltaCoord.getY());
        return new BoundBox(deltedCoord, deltedCoord);
    }

    /**
     * Returns if points form a cycle.
     *
     * @param searchOrigin origin to search for.
     * @return always false.
     */
    @Override
    public boolean checkGraphHasCycle(Origin searchOrigin) {
        return false;
    }
}
