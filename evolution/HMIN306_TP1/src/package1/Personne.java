package package1;


public abstract class Personne {
	
	public String nom;
    public String prenom; 
    public int numSecu; 
    public int statut;
    public Personne conjoint; 
    public int nombreEnfants;
    
    
    public Personne(String nom, String prenom, int numSecu, int statut, Personne conjoint, int nombreEnfants){
    	this.nom = nom; 
    	this.prenom = prenom; 
    	this.numSecu = numSecu; 
    	this.statut= statut;
    	this.conjoint = conjoint; 
    	this.nombreEnfants= nombreEnfants;
    }
    
    public Personne(){}
}