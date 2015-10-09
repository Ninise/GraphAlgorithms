package backend;

/**
 * Created by nikitin_ns on 01.10.2015.
 */
public class DistPar {

    public int distance;
    public int parentVertex;

    public DistPar(int pv, int d) {
        this.parentVertex = pv;
        this.distance = d;
    }
}
