package package1;


public class Imposable extends Personne {
	
    
    public Revenu revenu; 
    public int taux;
    double impotApayer;
    double fraisDeclares; 
    DonneesHistorique hisorique[];
    
 
    public Imposable(String nom, String prenom, int numSecu, int statut, Personne conjoint, int nombreEnfants, Revenu revenu) {
    	super(nom, prenom, numSecu, statut, conjoint, nombreEnfants);
    	this.revenu = revenu; 
    }
    
    public Imposable(){}
    
    int calculerTaux(){
    	return 0; 
    }
    
    public void enregistrerImpot(double imp){
    	impotApayer= imp; 
    }
    
    public void setFrais(double frais){
    	this.fraisDeclares = frais;
    }
	
    public double getFraisDeclares(){
    	return fraisDeclares; 
    }

}
