package appExplorer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Test {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String pathToExplore = "/home/yassine/workspace/HMIN306_TP1/";
		DiskFileExplorer diskFileExplorer = new DiskFileExplorer(pathToExplore);
		
		Long start = System.currentTimeMillis();

		diskFileExplorer.doStats(pathToExplore + "src/");
		diskFileExplorer.doStatsOnMethods(pathToExplore + "bin/");
		int x = 5;
		
		System.out.println("1- Nombre de classes dans l'application : " + diskFileExplorer.getClassCount());
		System.out.println("2- Nombre de lignes de code dans l'application : " + diskFileExplorer.getLineCount());
		System.out.println("3- Nombre de méthodes dans l'application : " + diskFileExplorer.getMethodeCount());
		System.out.println("4- Nombre de packages dans l'application : " + diskFileExplorer.getPackageCount());
		System.out.println("5- Nombre moyen de méthodes par classe : " + diskFileExplorer.NbrMoyMethodClass());
		
		//#TODO
		System.out.println("6- Nombre moyen de ligne de code par méthode : ");
		
		System.out.println("7- Nombre moyen d'attributs par classe : " + diskFileExplorer.nbrMoyAttributClass());
		System.out.println("8- Les 10% des classes qui possèdent le plus grand nombre de méthodes : \n" + diskFileExplorer.maxMethodClass());
		System.out.println("9- Les 10% des classes qui possèdent le plus grand nombre d'attributs : \n" + diskFileExplorer.maxAttributClass());
		
		//TODO
		System.out.println("10- Les classes qui appartiennent aux deux catégories précédentes : ");
		
		System.out.println("11- Les classes qui possèdent plus de " + x + " méthodes : \n" + diskFileExplorer.maxMethodClass(x));
		
		//TODO
		System.out.println("12- Les 10% de méthodes qui possèdent le plus grand nombre de ligne de code par classe : ");
		
		System.out.println("13- Le nombre maximal de paramètres par rapport à toutes les classes de l'application est : \n" + diskFileExplorer.getBigAttributeClass());
		
		
		System.out.println("********************************************************************");
		
	}

}
