package DAG;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {


    @Test
    @DisplayName("Acyclic check test")
    void checkIfGraphCorrectlyHandlesAcyclicity() throws DAGConstraintException {
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)),
                        new Point(new Coord2D(-1, -110)))));
        var orig3 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(orig2, new Point(new Coord2D(-1, 110)))));
        DAGConstraintException thrown = assertThrows(
                DAGConstraintException.class,
                () -> orig2.setChildren(new HashSet<Node>(Arrays.asList(orig3))),
                "Expected DAGConstraintException to be thrown, but there wasn't any"
        );
    }

    @Test
    @DisplayName("Check setChilden")
    void setChildrenCheck() throws DAGConstraintException {
        var orig1 = new Origin(new Coord2D(0, 1), new HashSet<Node>(Arrays.asList(
                new Point(new Coord2D(-1, -1)), new Point(new Coord2D(-11, 1)))));
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)),
                        new Point(new Coord2D(-1, -110)))));
        orig2.setChildren(new HashSet<Node>(Arrays.asList(orig1)));
        assertTrue(orig2.getChildren().iterator().next() ==
                orig1 && orig2.getChildren().size() == 1);
    }

    @Test
    void getPosition() throws DAGConstraintException {
        var space = new Space(new Coord2D(2.0, 3.0), new HashSet<Node>());
        assertTrue(space.getPosition().getX() == 2.0 && space.getPosition().getY() == 3.0);
    }

    @Test
    void setPosition() throws DAGConstraintException {
        var space = new Space(null, new HashSet<Node>());
        space.setPosition(new Coord2D(2.0, 3.0));
        assertTrue(space.getPosition().getX() == 2.0 && space.getPosition().getY() == 3.0);
    }

    @Test
    void getChildren() throws DAGConstraintException {
        var orig1 = new Origin(new Coord2D(0, 1), new HashSet<Node>(Arrays.asList(
                new Point(new Coord2D(-1, -1)), new Point(new Coord2D(-11, 1)))));
        var space = new Space(new Coord2D(1, 5), new HashSet<Node>(Arrays.asList(orig1)));
        space.setChildren(new HashSet<Node>(Arrays.asList(orig1)));
        assertSame(space.getChildren().iterator().next(), orig1);
    }

    @Test
    void setChildren() throws DAGConstraintException {
        var orig1 = new Origin(new Coord2D(0, 1), new HashSet<Node>(Arrays.asList(
                new Point(new Coord2D(-1, -1)), new Point(new Coord2D(-11, 1)))));
        var space = new Space(new Coord2D(1, 5), new HashSet<Node>(Arrays.asList(orig1)));
        space.setChildren(new HashSet<Node>(Arrays.asList(orig1)));
        assertTrue(space.getChildren().iterator().next() ==
                orig1 && space.getChildren().size() == 1);
    }

    @Test
    void serialiseToString() throws DAGConstraintException {
        String etalon = "1.0#1.0#{1.0#5.0#{1.0#5.0#{{-2.0,55.0}}}}";
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)))));
        var orig3 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(orig2)));
        var space = new Space(new Coord2D(1, 1),
                new HashSet<Node>(Arrays.asList(orig3)));
        assertEquals(DAGUtils.exportToString(space), etalon);
    }

    @Test
    void exportFromString() throws DAGConstraintException {
        String etalon = "1.0#1.0#{1.0#5.0#{1.0#5.0#{{-2.0,55.0}}}}";
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)))));
        var orig3 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(orig2)));
        var space = new Space(new Coord2D(1, 1),
                new HashSet<Node>(Arrays.asList(orig3)));
        System.out.println(DAGUtils.exportToString(space));
        assertEquals(DAGUtils.exportToString(space), etalon);
    }

    @Test
    void getBoundBox() throws DAGConstraintException {
        var orig = new Origin(new Coord2D(0, 5),
                new HashSet<Node>(Arrays.asList(new Point(
                        new Coord2D(1, -4)), new Point(new Coord2D(2, -5)))));
        var space = new Space(new Coord2D(0, 1),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-7, -33)), orig)));
        BoundBox boundBox = space.getBounds();
        assertTrue(
                boundBox.leftBottomPoint.getX() == -7.0 &&
                        boundBox.leftBottomPoint.getY() == -32.0 &&
                        boundBox.rightUpperPoint.getX() == 2.0 &&
                        boundBox.rightUpperPoint.getY() == 6.0
        );
    }

    @Test
    void checkGraphHasCycle() throws DAGConstraintException {
        var orig2 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(new Point(new Coord2D(-2, 55)),
                        new Point(new Coord2D(-1, -110)))));
        var orig3 = new Origin(new Coord2D(1, 5),
                new HashSet<Node>(Arrays.asList(orig2, new Point(
                        new Coord2D(-1, 110)))));
        var space = new Space(new Coord2D(0, 0),
                new HashSet<Node>(Arrays.asList(orig2, orig3)));
        try {
            orig2.setChildren(new HashSet<Node>(Arrays.asList(orig3)));
        } catch (DAGConstraintException e) {
            assertTrue(space.checkGraphHasCycle());
        }
    }

}