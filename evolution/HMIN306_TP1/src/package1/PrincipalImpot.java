package package1;


public class PrincipalImpot {
	
	public static void main(String[] params){
		Revenu revenu1 = new Revenu(10, 0, 5); 
		Imposable impos1 = new Imposable("Dupond", "Bernard", 345, 1, null, 3, revenu1);
		impos1.setFrais(4); 
		//impos1.revenu=revenu1;
		Impot imp= new Impot();
		double montantImpot = imp.calculerImpot(impos1);
		impos1.enregistrerImpot(montantImpot);
		(new FeuilleImpot(2)).editer(5);
		System.out.println(montantImpot); 
	}

}


