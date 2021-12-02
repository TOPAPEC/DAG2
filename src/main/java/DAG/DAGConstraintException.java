package DAG;

public class DAGConstraintException extends Exception {
    public String toString() {
        return "DAG.DAGConstraintException: Cycle found! Graph is no longer a DAG.";
    }
}
