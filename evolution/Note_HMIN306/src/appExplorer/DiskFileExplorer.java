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
import java.util.ArrayList;
import java.util.HashMap;

public class DiskFileExplorer {
	 
    private String initialpath = "";
    private int classCount = 0;
    private int packageCount = 0;
    private int lineCount = 0;
    private int methodeCount = 0;
    private String packName = "";
 
	/**
	 * Constructeur
	 * @param path chemin du répertoire
	 * @param subFolder analyse des sous dossiers
	 */
    public DiskFileExplorer(String path) {
        super();
        this.initialpath = path;
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
		return (double)this.getMethodeCount() / (double)this.getClassCount();
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
    
    
}
