package DAG;

import java.util.Arrays;
import java.util.HashSet;

public class Main {

    /**
     * For testing purposes.
     *
     * @param args
     * @throws DAGConstraintException
     */
    public static void main(String[] args) throws DAGConstraintException {
        var orig1 = new Origin(new Coord2D(0, 1), new HashSet<Node>(
                Arrays.asList(new Point(new Coord2D(-1, -1)),
                        new Point(new Coord2D(-11, 1)))));

        var orig2 = new Origin(new Coord2D(1, 5), new HashSet<Node>(
                Arrays.asList(new Point(new Coord2D(-2, 55)),
                        new Point(new Coord2D(-1, -110)))));

        var orig3 = new Origin(new Coord2D(1, 5), new HashSet<Node>(
                Arrays.asList(orig2, new Point(new Coord2D(-1, 110)))));

        var space = new Space(new Coord2D(1, 1), new HashSet<Node>(
                Arrays.asList(orig1, orig3)));
        var serialised = DAGUtils.exportToString(space);
        System.out.println(serialised);
        Space space2;
        space2 = DAGUtils.importFromString(serialised);
        System.out.println(DAGUtils.exportToString(space2));
        System.out.println(orig3.getBounds().leftBottomPoint.getX());
    }
}
