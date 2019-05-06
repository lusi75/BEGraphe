package org.insa.algo.shortestpath;
import org.insa.algo.utils.*;
import org.insa.graph.*;
import java.awt.Label;
import java.util.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    protected int SommetsVisites;
    protected int Sommets;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.SommetsVisites = 0;
    }

    /**Création du label*/
    protected Label newLabel (Node node,ShortestPathData data){
        return new Label(node);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        int taillegraph = graph.size();

        Label tablabel[] = new Label [taillegraph];

        BinaryHeap<Label> tas =new BinaryHeap<Label>();

        Label debut = newLabel(data.getOrigin(),data);

        tablabel[debut.getNode().getId()] = debut; 
        notifyOriginProcessed(data.getOrigin());

        while(!tas.isEmpty() && !DestinationAtteinte){
            Label currentLabel = tas.deletMin();
            currentLabel.setMarque();
            if(currentLabel.getNode() == data.getDestination()){
                DestinationAtteinte = true;
            }
            else{
                Iterator<Arc> arc = currentLabel.getNode().Iterator();
                while(arc.hasNext()){
                    Arc arcSuiv = arc.next();

                    if(!data.isAllowed(arcSuiv))
                        continue;
                    
                    Node nodeSuiv = arcSuiv.getDestination();

                    Label labelNodeSuiv = tablabel[nodeSuiv.getId()];

                    if(labelNodeSuiv == null){
                        labelNodeSuiv = newLabel(nodeSuiv,data);
                        tablabel[nodeSuiv.getId()] = labelNodeSuiv;
                        notifyNodeReached(nodeSuiv);
                    }

                    if(!labelNodeSuiv.isMarque()){
                        if(labelNodeSuiv.getCout())
                    }
                }
            }
        }


        return solution;
    }

}
