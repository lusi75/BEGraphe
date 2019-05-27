package org.insa.graph;

import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Node;
import org.insa.graph.Point;
import org.insa.algo.AbstractInputData;


public class LabelStar extends Label implements Comparable<Label>{
	
	private float estimation;
	
	public LabelStar(Node sommet_courant, ShortestPathData data) {
		super(sommet_courant);
		
		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
			this.estimation = (float)Point.distance(sommet_courant.getPoint(),data.getDestination().getPoint());
		}
		else {
			int vitesse = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed());
			this.estimation = (float)Point.distance(sommet_courant.getPoint(),data.getDestination().getPoint())/(vitesse*1000.0f/3600.0f);
		}
	}
	
	@Override
	public float getCoutTotal(){
        return this.estimation*1f + this.cout*1f;
	}
}
