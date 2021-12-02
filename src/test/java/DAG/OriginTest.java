package DAG;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OriginTest {

    @Test
    void getPosition() throws DAGConstraintException {
        var origin = new Origin(new Coord2D(2.0, 3.0), new HashSet<Node>());
        assertTrue(origin.getPosition().getX() == 2.0 && origin.getPosition().getY() == 3.0);
    }

    @Test
    void setPosition() throws DAGConstraintException {
        var origin = new Origin(null, new HashSet<Node>());
        origin.setPosition(new Coord2D(2.0, 3.0));
        assertTrue(origin.getPosition().getX() == 2.0 && origin.getPosition().getY() == 3.0);
    }

    @Test
    void setChildren() throws DAGConstraintException {
        var orig1 = new Origin(new Coord2D(0, 1), new HashSet<Node>(Arrays.asList(
                new Point(new Coord2D(-1, -1)), new Point(new Coord2D(-11, 1)))));
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)), new Point(new Coord2D(-1, -110)))));
        orig2.setChildren(new HashSet<Node>(Arrays.asList(orig1)));
        assertTrue(orig2.getChildren().iterator().next() == orig1 && orig2.getChildren().size() == 1);
    }

    @Test
    void getChildren() throws DAGConstraintException {
        var orig1 = new Origin(new Coord2D(0, 1), new HashSet<Node>(Arrays.asList(
                new Point(new Coord2D(-1, -1)), new Point(new Coord2D(-11, 1)))));
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)), new Point(new Coord2D(-1, -110)))));
        orig2.setChildren(new HashSet<Node>(Arrays.asList(orig1)));
        assertSame(orig2.getChildren().iterator().next(), orig1);
    }

    @Test
    void getBoundBox() throws DAGConstraintException {
        var orig = new Origin(new Coord2D(0, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(1, -4)), new Point(new Coord2D(2, -5)))));
        var orig1 = new Origin(new Coord2D(0, 1), new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-7, -33)), orig)));
        BoundBox boundBox = orig1.getBounds();
        System.out.println(boundBox.leftBottomPoint.getX() + " " +
                boundBox.leftBottomPoint.getY() + " " +
                boundBox.rightUpperPoint.getX() + " " +
                boundBox.rightUpperPoint.getY());
        assertTrue(
                boundBox.leftBottomPoint.getX() == -7.0 &&
                        boundBox.leftBottomPoint.getY() == -32.0 &&
                        boundBox.rightUpperPoint.getX() == 2.0 &&
                        boundBox.rightUpperPoint.getY() == 6.0
        );
    }

    @Test
    void serialiseToString() throws DAGConstraintException {
        String etalon = "1.0#1.0#{1.0#5.0#{1.0#5.0#{{-2.0,55.0}}}}";
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)))));
        var orig3 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(orig2)));
        var orig = new Origin(new Coord2D(1, 1), new HashSet<Node>(Arrays.asList(orig3)));
        assertEquals(DAGUtils.exportToString(orig), etalon);
    }

    @Test
    void parseSerialised() throws DAGConstraintException {
        String etalon = "1.0#1.0#{1.0#5.0#{1.0#5.0#{{-2.0,55.0}}}}";
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)))));
        var orig3 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(orig2)));
        var orig = new Origin(new Coord2D(1, 1), new HashSet<Node>(Arrays.asList(orig3)));
        System.out.println(DAGUtils.exportToString(orig));
        assertEquals(DAGUtils.exportToString(orig), etalon);
    }
}