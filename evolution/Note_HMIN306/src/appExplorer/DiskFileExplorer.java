/**
 * @author ybenabderrah
 */
package appExplorer;

import java.beans.MethodDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.text.html.HTMLDocument.Iterator;

public class DiskFileExplorer {
	 
    private String initialpath = "";
    private int classCount = 0;
    private int packageCount = 0;
    private int lineCount = 0;
    private int methodeCount = 0;
    private int attributsCount = 0;
    private String packName = "";
    private Map<String, Integer> classMethod;
    private Map<String, Integer> classAttribute;
    private String bigAttributeClass = "";
 
	/**
	 * Constructeur
	 * @param path chemin du répertoire
	 * @param subFolder analyse des sous dossiers
	 */
    public DiskFileExplorer(String path) {
        super();
        this.initialpath = path;
        this.classMethod = new TreeMap<String, Integer>();
        this.classAttribute = new TreeMap<String, Integer>();
    }
    
    /*
     * Une méthode qui :
     * Calcule le nombre de classes dans toutes l'application,
     * Calcule le nombre de packages dans toutes l'application,
     * Calcule le nombre de lignes de codes dans toutes l'application.
     * Le paramètre pathTo permet de faire un parcours récursif sur les sous dossiers/
     */
    public void doStats(String pathTo) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    	File file = new File(pathTo);
        File[] files = file.listFiles();
        if (files != null) {
        	for (int i = 0; i < files.length; i++) {
        		if (files[i].isDirectory() == true) {
        			this.packageCount++;
        		}
        		else {
        			this.classCount++;
        			this.lineCount += this.countLines(files[i].getAbsolutePath());
        		}
        		if (files[i].isDirectory() == true) {
                    this.doStats(files[i].getAbsolutePath());
                }
        	}
        }
    }
    
    /*
     * Une méthode qui fait des stats sur les méthodes d'une application
     * - Retourn le nombre de méthodes de l'application
     * #TODO Calcule le nombre moyen de ligne de code par méthode
     */
    public void doStatsOnMethods(String pathTo) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    	File file = new File(pathTo);
        File[] files = file.listFiles();
        
        if (files != null) {
        	for (int i = 0; i < files.length; i++) {
        		if (files[i].isDirectory() == true) {
        			this.packName = files[i].getName().toString();
        		}
        		else {
        			URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {
        				       new URL(
        				           "file://" + pathTo.replace(this.packName, "")
        				       )
        				});

        			Class c = urlClassLoader.loadClass(this.packName + "." + files[i].getName().split("\\.")[0]);
        			
        			//calcule du nombre d'attributs dans toute l'application
        			this.attributsCount += c.getDeclaredFields().length;

        			
        			//récupération pour chaque classe le nombre de méthodes qu'elle implémente.
        			//Utilisé pour avoir les 10% de classes qui possèdent le plus grand nombre de méthode
        			this.classMethod.put(files[i].getName().split("\\.")[0], c.getDeclaredMethods().length);
        			
        			//récupération pour chaque classe le nombre d'attributs qu'elle déclare.
        			this.classAttribute.put(files[i].getName().split("\\.")[0],c.getDeclaredFields().length);

        			Method[] m = c.getDeclaredMethods();
        			
        			this.methodeCount += m.length;
//        	        for (int j = 0; j < m.length; j++)
//        	        	System.out.println(m[j]);
        		}
        		if (files[i].isDirectory() == true) {
                    this.doStatsOnMethods(files[i].getAbsolutePath());
                }
        	}
        }
    }

    /*
     * Une méthode qui prend en paramètre le lien absolu d'un fichier et qui calcul le nombre de ligne de code.
     * Cette méthode pour des raisons de rapidité d'exécution fait un parcours par char et non pas par ligne.
     * Stats : sur un fichier de 150Mo => 0.35s VS 2.40s avec une lecture ligne par ligne.
     */
	public int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
	
	/*
	 * Une méthode qui retourne le nombre moyen de méthode par classe
	 */
	public double NbrMoyMethodClass() {
//		return (double)this.getMethodeCount() / (double)this.getClassCount();
		return Double.valueOf(new DecimalFormat("####0.00").format((double)this.getMethodeCount() / (double)this.getClassCount()));
	}
	
	/*
	 * Une méthode qui retourne le nombre moyen d'attributs par classe
	 */
	public double nbrMoyAttributClass() {
		return Double.valueOf(new DecimalFormat("####0.00").format((double)this.attributsCount / (double)this.classCount));
	}
	
	/*
	 * Une méthode qui retourne les 10% des classes qui contiennent le plus grand nombre de méthodes.
	 */
	public String maxMethodClass(){
		int ten = (int) Math.ceil((float)(this.classCount * 10) / 100);
		String result = "";
		
		for (Entry<String, Integer> obj : entriesSortedByValues(this.classMethod)) {
			if (ten > 0) {
				result += "- " + obj.getKey() + " avec " + obj.getValue() + " méthode(s)\n";
				ten--;
			}
		}
		
		return result;
	}
	
	//Une surchage de la méthode pour retourner les classes qui possèdent plus de x méthodes.
	public String maxMethodClass(int x){
		String result = "";
		
		for (Entry<String, Integer> obj : entriesSortedByValues(this.classMethod)) {
			if (obj.getValue() >= x) {
				result += "- " + obj.getKey() + " avec " + obj.getValue() + " méthode(s)\n";
			}
		}
		
		return result;
	}
	
	/*
	 * Une méthode qui retourne les 10% des classes qui contiennent le plus grand nombre d'attributs.
	 */
	public String maxAttributClass(){
		int ten = (int) Math.ceil((float)(this.classCount * 10) / 100);
		String result = "";
		boolean test = true;
		
		for (Entry<String, Integer> obj : entriesSortedByValues(this.classAttribute)) {
			if (ten > 0) {
				if (test) {
					this.bigAttributeClass = "- " + obj.getValue() + " de la classe " + obj.getKey();
					test = false;
				}
				result += "- " + obj.getKey() + " avec " + obj.getValue() + " Attribut(s)\n";
				ten--;
			}
		}
		
		return result;
	}
	


	/*
	 * Une méthode pour ordoner la TreeMap par les valeurs et nom pas par Key
	 * Merci stackoverflow
	 */
	private static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(Collections.reverseOrder(//pour inverser l'ordre
            new Comparator<Map.Entry<K,V>>() {
                @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1; // Special fix to preserve items with equal values
                }
            }
        ));
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

	/**************************************** Getters & Setters **************************************/
	
	public String getInitialpath() {
		return initialpath;
	}

	public void setInitialpath(String initialpath) {
		this.initialpath = initialpath;
	}

	public int getClassCount() {
		return classCount;
	}

	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}

	public int getPackageCount() {
		return packageCount;
	}

	public void setPackageCount(int packageCount) {
		this.packageCount = packageCount;
	}

	public int getMethodeCount() {
		return methodeCount;
	}

	public void setMethodeCount(int methodeCount) {
		this.methodeCount = methodeCount;
	}
    
    public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
    
	public String getBigAttributeClass() {
		return bigAttributeClass;
	}

	public void setBigAttributeClass(String bigAttributeClass) {
		this.bigAttributeClass = bigAttributeClass;
	}
    
}
