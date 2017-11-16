package package1;


public class Impot {
	
	
	private double calculerNombreParts(Imposable imps){
		if (imps.statut == 1)  
		   return (1+ imps.nombreEnfants + 1);
		else return (1+ imps.nombreEnfants);
		
		
	}
	
	Tranche identifierTranche(Imposable imp){
		if (imp.revenu.getMontant() > 10)
		return new Tranche(1);
		else return new Tranche(2);
	}
	
	double calculerFraisReels(Imposable imp){
		//TODO
		return (imp.getFraisDeclares()* 0.8) ; 
	}
	
	double calculerImpot(Imposable imps){
		Revenu rev= imps.revenu; 
		
		double nombreParts = calculerNombreParts(imps); 
		Tranche tran = identifierTranche(imps);
		double frais = calculerFraisReels(imps);
		
		double MontantRevenu = rev.getMontant() - frais; 
		
		double impot = tran.calculerImpot(MontantRevenu, nombreParts);
		
		return  impot;
	}

}
 