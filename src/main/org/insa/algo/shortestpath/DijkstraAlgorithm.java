package org.insa.algo.shortestpath;
import org.insa.algo.utils.*;
import org.insa.graph.*;
import java.awt.Label;
import java.util.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        List<Node> noeud = data.getGraph().getNodes();
        List<Soluce> solution = 0;
        
        // TODO:
        if(this.ShortestPathSolution){
            solution.append(noeud);
            this.noeud = this.ShortestPathSolution;
        }
        return solution;
    }

}
