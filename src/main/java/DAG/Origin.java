package DAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Origin extends Node {
    private Coord2D position;
    private Set<Node> children;

    public Origin(Coord2D position, Set<Node> children)
            throws DAGConstraintException {
        this.position = position;
        setChildren(children);
    }

    /**
     * @return coordinates of origin.
     */
    @Override
    public Coord2D getPosition() {
        return position;
    }

    /**
     * Let user set origin position.
     * @param newPosition position which will be set.
     */
    @Override
    public void setPosition(Coord2D newPosition) {
        position = newPosition;
    }

    /**
     * @return children array of origin.
     */
    public Set<Node> getChildren() {
        return children;
    }

    /**
     * @param searchOrigin link to node we are searching for.
     * @return if graph has a cycle.
     */
    @Override
    public boolean checkGraphHasCycle(Origin searchOrigin) {
        for (Node child : children) {
            if (child == searchOrigin) {
                return true;
            }
            if (child.checkGraphHasCycle(searchOrigin)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Allows user to set array of children for the origin.
     * @param newChildren new children array.
     * @throws DAGConstraintException if new children array
     * forms a cycle with the graph.
     */
    public void setChildren(Set<Node> newChildren) throws DAGConstraintException {
        children = newChildren;
        if (checkGraphHasCycle(this)) {
            throw new DAGConstraintException();
        }
    }

    /**
     * @return bound box formed by the origin and its children.
     */
    public BoundBox getBounds() {
        return getBounds(new Coord2D(0, 0));
    }

    /**
     * Internal method for calculating bound box.
     * @param deltaCoord delta in coordinats from ancestors.
     * @return bound box formed by origin and its children.
     */
    @Override
    public BoundBox getBounds(Coord2D deltaCoord) {
        deltaCoord = new Coord2D(deltaCoord.getX() + position.getX(),
                deltaCoord.getY() + position.getY());
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
