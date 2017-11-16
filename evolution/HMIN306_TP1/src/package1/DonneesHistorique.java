package package1;


public class DonneesHistorique {
	 double revenu;
	 double fraisDeclares; 
	 double fraisRetenus; 
	 double montantImpot; 
	 String remarques; 
	 
	 public DonneesHistorique(double revenu, double fraisDeclares, double fraisRetenus, double montantImpot, String remarque){
		 this.revenu = revenu; 
		 this.fraisDeclares = fraisDeclares;
		 this.fraisRetenus = fraisRetenus; 
		 this.montantImpot = montantImpot; 
		 this.remarques = remarque; 
	 }
	 
	 
	 DonneesHistorique(){}
	 

}
