package hearc.othello.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kevin on 09.01.2016.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {

    private WifiManager wifiManager;
    private WifiAdapter wifiAdapter;
    private List<WifiItem> listWifiItem;

    public WifiBroadcastReceiver(WifiManager wifiManager, WifiAdapter wifiAdapter, List<WifiItem> listWifiItem){
        this.wifiManager = wifiManager;
        this.wifiAdapter = wifiAdapter;
        this.listWifiItem = listWifiItem;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // On vérifie que notre objet est bien instancié
        if (wifiManager != null) {

            // On vérifie que le WiFi est allumé
            if (wifiManager.isWifiEnabled()) {
                // On récupère les scans
                List<ScanResult> listeScan = wifiManager.getScanResults();

                // On vide notre liste
                listWifiItem.clear();

                // Pour chaque scan
                for (ScanResult scanResult : listeScan) {
                    WifiItem item = new WifiItem();

                    item.setAdresseMac(scanResult.BSSID);
                    item.setAPName(scanResult.SSID);
                    item.setForceSignal(scanResult.level);

                    Log.d("FormationWifi", scanResult.SSID + " LEVEL " + scanResult.level);

                    listWifiItem.add(item);
                }

                // On rafraichit la liste
                wifiAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Vous devez activer votre WiFi",
                        Toast.LENGTH_SHORT);
            }
        }

    }

}
