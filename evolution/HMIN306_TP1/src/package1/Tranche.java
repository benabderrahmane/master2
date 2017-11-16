package package1;


public class Tranche {
	
	public int identifiant; 
	
	Tranche(int ident) {
		identifiant = ident; 
	}
	
	
	double calculerImpot(double montantRevenu, double nombreParts) {
		if ( identifiant == 1) 
			return montantRevenu/nombreParts;
		else 
			return (montantRevenu * 0.6)/nombreParts;
	}
}
