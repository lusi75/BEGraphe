package org.insa.graph;
import org.insa.graph.Node;

public class Label implements Comparable<Label>{
    private Node sommet_courant;

    private boolean marque;

    private double cout;
    
    private double estimation;
    
    private double cout_total;

    private Arc arc_pere;

    private boolean inTas;

    public Label(Node sommet_courant){
        this.sommet_courant = sommet_courant;
        this.marque = false;
        this.cout = Float.POSITIVE_INFINITY;
        this.arc_pere = null;
        this.inTas = false;
        this.estimation = 0;
        this.cout_total = 0;
    }
    
    public int compareTo(Label autre) {
    	int resultat;
    	if (this.cout_total < autre.getCout() + autre.estimation)
    		resultat = -1;
    	else if(this.cout_total == autre.cout_total)
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

    public void setCout(double cout){
        this.cout = cout;
    }
    
    public double getEstimation(){
    	return this.estimation;
    }
    
    public void setEstimation() {
    	this.estimation = 0;
    }
    
    public void setTotalCost(double total) {
    	this.cout_total = total; 
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
    
    public void setTotalCost() {
    	this.cout_total = this.cout + this.estimation;
    }
    public double getTotalCost() {
    	return cout_total;
    }
}