package DAG;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void getPosition() {
        Coord2D coord = new Coord2D(1, -1);
        var point = new Point(coord);
        assertTrue(point.getPosition().getX() == coord.getX() &&
                point.getPosition().getY() == coord.getY());
    }

    @Test
    void setPosition() {
        var point = new Point(null);
        Coord2D coord = new Coord2D(1, -1);
        point.setPosition(coord);
        assertTrue(point.getPosition().getX() == coord.getX() &&
                point.getPosition().getY() == coord.getY());
    }

    @Test
    void getBounds() {
        Coord2D coord = new Coord2D(1, -1);
        var point = new Point(coord);
        var bbox = point.getBounds(new Coord2D(1, 1));
        assertTrue(bbox.rightUpperPoint.getX() == 2 && bbox.rightUpperPoint.getY() == 0);
    }

    @Test
    void serialiseToString() {
        Coord2D coord = new Coord2D(1, -1);
        var point = new Point(coord);
        assertEquals(DAGUtils.exportToString(point), "{1.0,-1.0}");
    }

}