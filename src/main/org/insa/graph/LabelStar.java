package org.insa.graph;
import org.insa.algo.shortestpath.ShortestPathData;
//import org.insa.graph.*;

public class LabelStar extends Label implements Comparable<Label>{
	
	
	public LabelStar(Node sommet_courant) {
		super(sommet_courant);
	}
	@Override
	public void setCoutTotal(ShortestPathData data){
        this.cout_total = cout + sommet_courant.getPoint().distanceTo(data.getDestination().getPoint());
    }
	
	@Override
    public void setCout(double cout, ShortestPathData data){
        this.cout = cout;
	}
}
