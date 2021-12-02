package DAG;

public class BoundBox {
    public final DAG.Coord2D leftBottomPoint;
    public final DAG.Coord2D rightUpperPoint;

    public BoundBox(DAG.Coord2D leftBottomPoint, DAG.Coord2D rightUpperPoint) {
        this.leftBottomPoint = leftBottomPoint;
        this.rightUpperPoint = rightUpperPoint;
    }
}
