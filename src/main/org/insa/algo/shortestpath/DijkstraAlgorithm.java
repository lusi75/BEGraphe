package org.insa.algo.shortestpath;
import org.insa.algo.utils.*;
import org.insa.graph.*;
//import org.insa.graph.Label;
import java.util.*;
import org.insa.algo.AbstractSolution.Status;
import java.util.Iterator;



public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
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
        boolean DestinationAtteinte = false;

        Label tablabel[] = new Label [taillegraph];

        BinaryHeap<Label> tas =new BinaryHeap<Label>();

        Label debut = newLabel(data.getOrigin(),data);

        tablabel[debut.getNode().getId()] = debut; 
        tas.insert(debut);
        debut.setInTas();
        debut.setCout(0,data);
        
        notifyOriginProcessed(data.getOrigin());

        while(!tas.isEmpty() && !DestinationAtteinte){
            Label currentLabel = tas.deleteMin();
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

                    Label labelNodeSuiv = tablabel[nodeSuiv.getId()];

                    if(labelNodeSuiv == null){
                        labelNodeSuiv = newLabel(nodeSuiv,data);
                        tablabel[nodeSuiv.getId()] = labelNodeSuiv;
                        notifyNodeReached(nodeSuiv);
                    }

                    if(!labelNodeSuiv.isMarque()){
                        if((labelNodeSuiv.getCoutTotal()>(currentLabel.getCoutTotal() + data.getCost(arcSuiv))) || (labelNodeSuiv.getCoutTotal() == Float.POSITIVE_INFINITY)){
                            labelNodeSuiv.setCout(currentLabel.getCoutTotal() + data.getCost(arcSuiv),data);
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
            Label lab = tablabel[destination.getId()];
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
