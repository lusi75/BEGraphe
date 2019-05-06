package org.insa.graph;

public class Label{
    private Node sommet_courant;

    private boolean marque;

    private double count;

    private Arc arc_pere;

    public Label(Node sommet_courant, boolean marque, double cout, Arc arc_pere){
        this.sommet_courant = sommet_courant;
        this.marque = marque;
        this.cout = cout;
        this.arc_pere = arc_pere;
    }

    public double getSommet_courant(){
        return this.sommet_courant;
    }

    public double setSommet_courant(Node sommet_courant){
        this.sommet_courant = sommet_courant;
    }

    public double getCout(){
        return cout;
    }

    public double setCout(double cout){
        return this.cout = cout;
    }

    public double getArc_pere(){
        return arc_pere;
    }

    public double setArc_pere(Arc arc_pere){
        this.arc_pere = arc_pere;
    }

    public boolean isMarque(){
        return marque;
    }

    public void setMarque(){
        this.marque = true;
    }
}