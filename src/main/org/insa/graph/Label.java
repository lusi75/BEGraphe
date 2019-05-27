package org.insa.graph;
import org.insa.graph.Node;

public class Label implements Comparable<Label>{
    protected Node sommet_courant;

    private boolean marque;

    protected float cout;

    private Arc arc_pere;

    private boolean inTas;
    
    public Label(Node sommet_courant){
        this.sommet_courant = sommet_courant;
        this.marque = false;
        this.cout = Float.POSITIVE_INFINITY;
        this.arc_pere = null;
        this.inTas = false;
    }
    @Override
	    public int compareTo(Label autre) {
	    	int resultat;
	    	if (this.getCoutTotal() < autre.getCoutTotal())
	    		resultat = -1;
	    	else if(this.getCoutTotal() == autre.getCoutTotal())
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

    public float getCout(){
        return this.cout;
    }

    public void setCout(float cout){
        this.cout = cout;
    }

    public Arc getArc_pere(){
        return this.arc_pere;
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
    
    public float getCoutTotal() {
    	return this.cout;
    }
}