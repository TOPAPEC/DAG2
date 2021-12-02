package DAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DAGUtils {
    /**
     * Parses graph string representation into object.
     *
     * @param serialised serialised string.
     * @return constructed Space object.
     * @throws DAGConstraintException if constucted object has cycles.
     */
    public static Space importFromString(String serialised)
            throws DAGConstraintException {
        var splitOuter = SerialisedParser.splitOuter(serialised);
        Coord2D coord;
        if (splitOuter.length != 3) {
            for (var word : splitOuter) {
                System.out.println(word);
            }
            throw new IllegalArgumentException("Serialised data is corrupted");
        } else {
            coord = new Coord2D(Double.parseDouble(splitOuter[0]),
                    Double.parseDouble(splitOuter[1]));
        }
        var splitSerialised = SerialisedParser.cutString(splitOuter[2]);
        List<Node> listDeserialised = new ArrayList<Node>();
        for (var word : splitSerialised) {
            listDeserialised.add(DAGUtils.parseSerialised(word));
        }
        return new Space(coord, new HashSet<Node>(listDeserialised));
    }

    /**
     * Turns serialised origin or point into object.
     *
     * @param serialised string representation of origin.
     * @return Point or Origin object depending on serialised content.
     * @throws DAGConstraintException if deserialised graph has loop in it.
     */
    public static Node parseSerialised(String serialised)
            throws DAGConstraintException {
        var splitOuter = SerialisedParser.splitOuter(serialised);
        Coord2D coord;
        String[] splitSerialised;
        if (splitOuter.length == 1) {
            splitSerialised = SerialisedParser.cutString(splitOuter[0]);
            return new Point(new Coord2D(Double.parseDouble(splitSerialised[0]),
                    Double.parseDouble(splitSerialised[1])));
        } else {
            splitSerialised = SerialisedParser.cutString(splitOuter[2]);
            coord = new Coord2D(Double.parseDouble(splitOuter[0]),
                    Double.parseDouble(splitOuter[1]));
        }

        List<Node> listDeserialised = new ArrayList<Node>();
        for (var word : splitSerialised) {
            listDeserialised.add(DAGUtils.parseSerialised(word));
        }
        return new Origin(coord, new HashSet<Node>(listDeserialised));
    }

    /**
     * @return graph represented as a string.
     */
    public static String exportToString(Space space) {
        var str = new StringBuilder("{");
        int i = 0;
        for (var child : space.getChildren()) {
            if (child instanceof Origin) {
                str.append(DAGUtils.exportToString((Origin) child));
            } else {
                str.append(DAGUtils.exportToString((Point) child));
            }
            if (i != space.getChildren().size() - 1) {
                str.append(",");
            }
            ++i;
        }
        str.append("}");
        return space.getPosition().getX() + "#" + space.getPosition().getY()
                + "#" + str.toString();
    }

    /**
     * @return string representation of the origin and its children.
     */
    public static String exportToString(Origin orig) {
        var str = new StringBuilder("{");
        int i = 0;
        for (var child : orig.getChildren()) {
            if (child instanceof Origin) {
                str.append(DAGUtils.exportToString((Origin) child));
            } else {
                str.append(DAGUtils.exportToString((Point) child));
            }
            if (i != orig.getChildren().size() - 1) {
                str.append(",");
            }
            ++i;
        }
        str.append("}");
        return orig.getPosition().getX() + "#" + orig.getPosition().getY() + "#" + str.toString();
    }

    /**
     * @return converts Point to its string representation.
     */
    public static String exportToString(Point point) {
        return "{" + point.getPosition().getX() + "," + point.getPosition().getY() + "}";
    }


}
