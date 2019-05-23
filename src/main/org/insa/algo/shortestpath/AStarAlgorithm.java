package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.*;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    protected LabelStar newLabelStar (Node node,ShortestPathData data){
        return new LabelStar(node);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        int taillegraph = graph.size();
        boolean DestinationAtteinte = false;

        LabelStar tablabel[] = new LabelStar [taillegraph];

        BinaryHeap<Label> tas =new BinaryHeap<LabelStar>();

        LabelStar debut = newLabel(data.getOrigin(),data);

        tablabel[debut.getNode().getId()] = debut; 
        tas.insert(debut);
        debut.setInTas();
        debut.setCout(0);
        
        notifyOriginProcessed(data.getOrigin());

        while(!tas.isEmpty() && !DestinationAtteinte){
            LabelStar currentLabel = tas.deleteMin();
            currentLabel.setMarque();
            if(currentLabel.getNode() == data.getDestination()){
                DestinationAtteinte = true;
            }
            else{
                Iterator<Arc> arc = currentLabel.getNode().iterator();
                while(arc.hasNext()){
                    Arc arcSuiv = arc.next();

                    if(!data.isAllowed(arcSuiv))
                        continue;
                    
                    Node nodeSuiv = arcSuiv.getDestination();

                    LabelStar labelNodeSuiv = tablabel[nodeSuiv.getId()];

                    if(labelNodeSuiv == null){
                        labelNodeSuiv = newLabel(nodeSuiv,data);
                        tablabel[nodeSuiv.getId()] = labelNodeSuiv;
                        notifyNodeReached(nodeSuiv);
                    }

                    if(!labelNodeSuiv.isMarque()){
                        if((labelNodeSuiv.getCout()>(currentLabel.getCout() + data.getCost(arcSuiv))) || (labelNodeSuiv.getCout() == Float.POSITIVE_INFINITY)){
                            labelNodeSuiv.setCout(currentLabel.getCout() + data.getCost(arcSuiv));
                            labelNodeSuiv.setArc_pere(arcSuiv); 
                            tablabel[labelNodeSuiv.getNode().getId()]=labelNodeSuiv;
                            if(labelNodeSuiv.getInTas())
                                tas.remove(labelNodeSuiv);
                            else   
                                labelNodeSuiv.setInTas();
                            tas.insert(labelNodeSuiv);
                        }
                    }
                }
            }
        }

        if(!DestinationAtteinte)
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        else{
            ArrayList<Arc> arcs = new ArrayList<>();
            Node destination = data.getDestination();
            LabelStar lab = tablabel[destination.getId()];
            Arc arc = lab.getArc_pere();
            arcs.add(arc);
            notifyNodeMarked(arc.getOrigin());
            notifyNodeMarked(data.getDestination());
            Node precedent = arc.getOrigin();
            notifyNodeReached(destination);
            while(precedent.getId() != data.getOrigin().getId()){
                lab = tablabel[precedent.getId()];
                arc =lab.getArc_pere();
                arcs.add(arc);
                notifyNodeMarked(arc.getOrigin());
                precedent = arc.getOrigin();
            }
            notifyDestinationReached(data.getDestination());
            Collections.reverse(arcs);
            solution  = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph,arcs));
        }
        return solution;
    }
}
