package org.insa.graph;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Node;

public class Label implements Comparable<Label>{
    protected Node sommet_courant;

    protected boolean marque;

    protected double cout;

    protected Arc arc_pere;

    protected boolean inTas;
    
    protected double cout_total;

    public Label(Node sommet_courant){
        this.sommet_courant = sommet_courant;
        this.marque = false;
        this.cout = Float.POSITIVE_INFINITY;
        this.cout_total = Float.POSITIVE_INFINITY;
        this.arc_pere = null;
        this.inTas = false;
    }
    
    public int compareTo(Label autre) {
    	int resultat;
    	if (this.cout_total < autre.getCoutTotal())
    		resultat = -1;
    	else if(this.cout_total == autre.getCoutTotal())
    		resultat = 0;
    	else
    		resultat = 1;
    	return resultat;
    }
    
    public Node getNode(){
        return this.sommet_courant;
    }

    public void setNode(Node sommet_courant){
        this.sommet_courant = sommet_courant;
    }

    public double getCout(){
        return cout;
    }

    public void setCout(double cout, ShortestPathData data){
        this.cout = cout;
        this.cout_total = cout;
    }

    public Arc getArc_pere(){
        return arc_pere;
    }

    public void setArc_pere(Arc arc_pere){
        this.arc_pere = arc_pere;
    }

    public boolean isMarque(){
        return marque;
    }

    public void setMarque(){
        this.marque = true;
    }

    public boolean getInTas(){
        return this.inTas;
    }

    public void setInTas(){
        this.inTas = true;
    }
    
    public double getCoutTotal() {
    	return this.cout_total;
    }
    
    public void setCoutTotal(ShortestPathData data) {
    	this.cout_total = cout;
    }
}