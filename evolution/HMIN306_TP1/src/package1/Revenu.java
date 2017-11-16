package package1;


public class Revenu {
	
	double montant; 
	double revenuSalaire; 
	double revenuFancier;
	double autresRevenus;
	
	public Revenu (){}
	
	public Revenu (double revenuSalaire , double revenuFancier, double autresRevenus){
		this.revenuSalaire = revenuSalaire; 
		this.revenuFancier = revenuFancier;
		this.autresRevenus = autresRevenus; 
		
		montant = (revenuSalaire + 0.7 * revenuFancier + 0.85 *  autresRevenus) * 2/3;
	}
	
	public double calculerRevenu(){
		return (revenuSalaire + 0.7 * revenuFancier + 0.85 *  autresRevenus) * 2/3; 
	}

   double getMontant(){
	   return montant; 
   }

}
