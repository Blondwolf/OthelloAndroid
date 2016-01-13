package hearc.othello.network;

import java.io.Serializable;

/**
 * Created by Kevin on 09.01.2016.
 */
public class WifiItem implements Serializable{
    private String APName;
    private String AdresseMac;
    private int ForceSignal;

    public String getAPName() {
        return APName;
    }
    public void setAPName(String aPName) {
        APName = aPName;
    }
    public String getAdresseMac() {
        return AdresseMac;
    }
    public void setAdresseMac(String adresseMac) {
        AdresseMac = adresseMac;
    }
    public int getForceSignal() {
        return ForceSignal;
    }
    public void setForceSignal(int forceSignal) {
        ForceSignal = forceSignal;
    }
}
