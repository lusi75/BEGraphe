package org.insa.graph;
import org.insa.graph.Node;

public class LabelStar extends Label{
	private LabelStar(Node sommet_courant) {
		super(sommet_courant);
		
	}
	
	public double setEstimation(ShortestPathData data) {
		Point point;
		point = sommet_courant.getPoint();
		this.estimation = distanceTo(data.getDestination().getPoint());
	}
	
	public double getEstimation() {
		return(this.estimaion)
	}
}