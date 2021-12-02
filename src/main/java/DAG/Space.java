package DAG;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Space {
    private Coord2D rootPosition;
    private Set<Node> children;

    public Space(Coord2D rootPosition, Set<Node> children) throws DAGConstraintException {
        this.rootPosition = rootPosition;
        setChildren(children);
    }

    /**
     * @return coordinates origin.
     */
    public Coord2D getPosition() {
        return rootPosition;
    }

    /**
     * Sets new origin.
     *
     * @param newPosition new coordinates of origin.
     */
    public void setPosition(Coord2D newPosition) {
        rootPosition = newPosition;
    }

    /**
     * @return list of children.
     */
    public Set<Node> getChildren() {
        return children;
    }

    /**
     * Changes the children array.
     *
     * @param newChildren new children array.
     */
    public void setChildren(Set<Node> newChildren) throws DAGConstraintException {
        children = newChildren;
        if (checkGraphHasCycle()) {
            throw new DAGConstraintException();
        }
    }

    /**
     * @return if graph has a cycle inside.
     */
    public boolean checkGraphHasCycle() {
        for (Node child : children) {
            if (child instanceof Origin && ((Origin) child)
                    .checkGraphHasCycle((Origin) child)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return bound box for the entire graph.
     */
    public BoundBox getBounds() {
        return getBounds(new Coord2D(0, 0));
    }

    /**
     * Internal function for calculating bound box.
     *
     * @param deltaCoord 0, 0 coordinates.
     * @return bound box for the entire graph.
     */
    private BoundBox getBounds(Coord2D deltaCoord) {
        deltaCoord = new Coord2D(deltaCoord.getX() + rootPosition.getX(),
                deltaCoord.getY() + rootPosition.getY());
        double leftX, leftY, rightX, rightY;
        leftX = deltaCoord.getX();
        leftY = deltaCoord.getY();
        rightX = deltaCoord.getX();
        rightY = deltaCoord.getY();
        for (var child : children) {
            BoundBox childBBox = child.getBounds(deltaCoord);
            leftX = Math.min(childBBox.leftBottomPoint.getX(), leftX);
            leftY = Math.min(childBBox.leftBottomPoint.getY(), leftY);
            rightX = Math.max(childBBox.rightUpperPoint.getX(), rightX);
            rightY = Math.max(childBBox.rightUpperPoint.getY(), rightY);
        }
        return new BoundBox(new Coord2D(leftX, leftY),
                new Coord2D(rightX, rightY));
    }
}
