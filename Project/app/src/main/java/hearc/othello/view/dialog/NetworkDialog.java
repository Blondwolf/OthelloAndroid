package hearc.othello.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import hearc.othello.R;
import hearc.othello.network.WifiAdapter;
import hearc.othello.network.WifiBroadcastReceiver;
import hearc.othello.network.WifiItem;

/**
 * Created by Kevin on 13.11.2015.
 */
public class NetworkDialog extends MainDialog implements View.OnClickListener, ListView.OnItemClickListener {

    WifiBroadcastReceiver broadcastReceiver;
    WifiAdapter wifiAdapter;
    WifiManager wifiManager;

    List<WifiItem> networks;
    ListView listView;
    Button cancel;
    Button search;

    public NetworkDialog(Context context, DialogListener listener) {
        super(context, listener, R.layout.layout_dialog_network, "Choose player");

        // Init wifi spec.
        networks = new ArrayList<WifiItem>();
        wifiManager = (WifiManager) getOwnerActivity().getSystemService(Context.WIFI_SERVICE);
        wifiAdapter = new WifiAdapter(getOwnerActivity(), networks);
        broadcastReceiver = new WifiBroadcastReceiver(wifiManager, wifiAdapter, networks);

        // Attach
        getOwnerActivity().registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    protected void initGraphicElement() {
        listView = (ListView) findViewById(R.id.wifi_list);
        listView.setAdapter(wifiAdapter);
        listView.setOnItemClickListener(this);

        search = (Button) findViewById(R.id.search);
        cancel = (Button) findViewById(R.id.cancel);

        search.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == search) {
            //TODO
        }
        else if(v == cancel) {
            dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("network", networks.get(position));

        dismiss();
        callListener(bundle);
    }
}
