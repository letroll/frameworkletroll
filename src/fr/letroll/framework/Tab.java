package fr.letroll.framework;


public class Tab {
    public static <T> T[] reverse(T [] valeurs){
        for (int left=0,right=valeurs.length-1; left<right; left++, right--) {
            T temp = valeurs[left]; valeurs[left]  = valeurs[right]; valeurs[right] = temp;
        }
        return valeurs;
    }  
}
