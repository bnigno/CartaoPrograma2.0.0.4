package pa.pm.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import pa.pm.cartaoprograma2.EnderecoServidor;
import pa.pm.cartaoprograma2.MarkerRide;
import pa.pm.localdb.ConfigDataBaseHelper;

public class SyncAdapter extends AbstractThreadedSyncAdapter{

    public static final String TAG = "SyncAdapter";

    private final ContentResolver mContentResolver;

    ConfigDataBaseHelper configDataBaseHelper = new ConfigDataBaseHelper(getContext());

    private Map<String, String> params;

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    /**
     * Called by the Android system in response to a request to run the sync adapter. The work
     * required to read data from the network, parse it, and store it in the content provider is
     * done here. Extending AbstractThreadedSyncAdapter ensures that all methods within SyncAdapter
     * run on a background thread.
     *
     * <p>The syncResult argument allows you to pass information back to the method that triggered
     * the sync.
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Log.i(TAG, "Beginning network synchronization");

        enviarMarkerRide(configDataBaseHelper.getLastMarkerRide());

        Log.i(TAG, "Network synchronization complete");
    }


    private void enviarMarkerRide(MarkerRide markerRide) {

        params = new HashMap<String, String>();
        params.put("idCard", String.valueOf(markerRide.getIdCard()));
        params.put("lat", String.valueOf(markerRide.getLat()));
        params.put("lng", String.valueOf(markerRide.getLng()));

        int method = Request.Method.POST;
        String url = EnderecoServidor.ENVIAR_ROTA_PERCORRIDA;

        System.out.println("RIDE MARKER SYNC: "+ markerRide.getIdCard() +" ="+ markerRide.getLat() +", "+ markerRide.getLng());
        Log.i("Script", "URL: " + url);

        StringRequest request = new StringRequest(method,
                url,
                new Response.Listener<String>() { // LISTENER QUE TRATA A RESPOSTA DA REQUISIÇÃO
                    @Override
                    public void onResponse(String response) {
                        Log.i("Script", "SUCCESS: " + response);
                    }
                },
                new Response.ErrorListener() { // LISTENER QUE TRATA A FALHA NA REQUISIÇÃO
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Script", "Error: " + error.getMessage());
                    }
                }) {
            // CHAMADO INOLICITAMENTE PARA SETAR OS PARAMETROS DA REQUISIÇÃO
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return (params);
            }
        };

        request.setTag("tagRequest");
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }

}