package fr.letroll.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Classe de gestion de fichiers
 * 
 * @author Julien Quiévreux
 * @version 0.0.1
 */
public class FileLt {
    /**
     * Génére une chaine de caractère à partir d'un flux rentrant.
     * 
     * @param stream
     *            le flux rentrant
     * @return la chaine de caractère obtenu
     */
    public static String generateString(InputStream stream) {
        return generateStringBuilder(stream).toString();
    }

    /**
     * Génére une chaine de caractère à partir d'un flux rentrant.
     * 
     * @param stream
     *            le flux rentrant
     * @return la chaine de caractère obtenu
     */
    public static StringBuilder generateStringBuilder(InputStream stream) {
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader buffer = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        try {
            String cur;
            while ((cur = buffer.readLine()) != null) {
                sb.append(cur).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }
    
    /**
     * Supprime fichier et dossier.
     * 
     * @param file
     *            fichier à supprimer
     * @return vrais si la suppression est réussi
     */

    public static boolean recursiveDelete(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (int x = 0; x < files.length; x++) {
                File childFile = files[x];
                if (childFile.isDirectory()) {
                    if (!recursiveDelete(childFile)) {
                        return false;
                    }
                } else {
                    if (!childFile.delete()) {
                        return false;
                    }
                }
            }
        }
        if (!file.delete()) {
            return false;
        }
        return true;
    }

    /**
     * Supprime fichier et dossier contenu dans un dossier.
     * 
     * @param file
     *            fichier à supprimer
     * @return vrais si la suppression est réussi
     */

    public static boolean recursiveDeleteContent(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (int x = 0; x < files.length; x++) {
                File childFile = files[x];
                if (childFile.isDirectory()) {
                    if (!recursiveDelete(childFile)) {
                        return false;
                    }
                } else {
                    if (!childFile.delete()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Supprime fichier et dossier contenu dans un dossier.
     * 
     * @param file
     *            fichier à supprimer
     * @return vrais si la suppression est réussi
     */
    public static boolean removeFile(String file) {
        File itemFile = new File(file);
        return itemFile.delete();
    }

    /**
     * Recupere l'extension d'un fichier.
     * 
     * @param filename
     *            fichier concerné
     * @return l'extension
     */
    public static String getExt(String filename) {
        int dotposition = filename.lastIndexOf(".");
        return filename.substring(dotposition + 1, filename.length());
    }

    /**
     * Recupere le nom d'un fichier sans son extension.
     * 
     * @param filename
     *            fichier concerné
     * @return Retourne le nom sans son extension
     */
    public String getFilenameWithoutExt(String filename) {
        int dotposition = filename.lastIndexOf(".");
        return filename.substring(0, dotposition);
    }

    /**
     * Copie un fichier.
     * 
     * @param source
     *            adresse du fichier concerné
     * @param filename
     *            fichier de destination
     */
    public static boolean copyFile(String source, String destination) {
        try {
            FileInputStream src = new FileInputStream(source);
            BufferedReader reader = new BufferedReader(new InputStreamReader(src), 8092);
            File destFile = new File(destination);
            
            Notification.log("FileIO",destination);
            if (destFile.exists())
                destFile.renameTo(new File(destination + ".copy"));
            destFile.createNewFile();
            FileOutputStream destStream = new FileOutputStream(destFile);
            int piece;
            while ((piece = reader.read()) >= 0) {
                destStream.write(piece);
            }
            return true;
        } catch (IOException e) {
            Notification.log("FileIO", "Problem copying the source.");
            Notification.log("FileIO", e.toString());
            return false;
        }
    }

    
    public static boolean copyFile(String source, String destination,Boolean showTime) {
        long startnow;
        long endnow;
        Boolean result;
        startnow = android.os.SystemClock.uptimeMillis();
        result=copyFile(source, destination);
        if(showTime){
            endnow = android.os.SystemClock.uptimeMillis();
            Notification.log("MYTAG", "Excution time: "+(endnow-startnow)+" ms");
            endnow=endnow-startnow;
            int seconds = (int) (endnow / 1000) % 60 ;
            Notification.log("MYTAG", "Excution time: "+seconds+" sec");
        }
        return result; 
    }
    
    /**
     * Copie un fichier.
     * 
     * @param source
     *            adresse du fichier concerné
     * @param filename
     *            fichier de destination
     */
    public static boolean copyFile(InputStream source, String destination) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(source), 8092);
            File destFile = new File(destination);
            
            Notification.log("FileIO",destination);
            if (destFile.exists())
                destFile.renameTo(new File(destination + ".copy"));
            destFile.createNewFile();
            FileOutputStream destStream = new FileOutputStream(destFile);
            int piece;
            while ((piece = reader.read()) >= 0) {
                destStream.write(piece);
            }
            return true;
        } catch (IOException e) {
            Notification.log("FileIO", "Problem copying the source.");
            Notification.log("FileIO", e.toString());
            return false;
        }
    }

    /**
     * Déplacement d'un fichier.
     * 
     * @param source
     *            adresse du fichier concerné
     * @param filename
     *            fichier de destination
     */
    public static boolean moveFile(String source, String destination) {
        try {
            File srcFile = new File(source);
            FileInputStream src = new FileInputStream(source);
            BufferedReader reader = new BufferedReader(new InputStreamReader(src), 8092);
            File destFile = new File(destination + "/" + source.substring(source.lastIndexOf("/")));
            destFile.createNewFile();
            FileOutputStream destStream = new FileOutputStream(destFile);

            int piece;
            while ((piece = reader.read()) >= 0) {
                destStream.write(piece);
            }

            // now that the source is copied over to the destination remove it from the source.
            srcFile.delete();
            return true;
        } catch (IOException e) {
            Notification.log("FileIO", "Problem copying the source.");
            Notification.log("FileIO", e.toString());
            return false;
        }
    }
    
    public void dirChecker(String dir) { 
        File f = new File(dir); 
     
        if(!f.isDirectory()) { 
          f.mkdirs(); 
        } 
      } 

}
