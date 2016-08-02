package pa.pm.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import pa.pm.cartaoprograma2.R;
import pa.pm.sync.accounts.GenericAccountService;

/**
 * Static helper methods for working with the sync framework.
 */
public class SyncUtils {
    private static final String TAG = "SyncUtils";
    private static final long SYNC_FREQUENCY = 60; // 1 minuto
    public static final String CONTENT_AUTHORITY    = String.valueOf(R.string.content_authority);
    private static final String ACCOUNT_TYPE = String.valueOf(R.string.auth_type);
    public static final String ACCOUNT_NAME = "CartaoPrograma2";
    private static final String PREF_SETUP_COMPLETE = "setup_complete";

    public static void CreateSyncAccount(Context context) {

        boolean newAccount = false;
        boolean setupComplete = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

        // Create account, if it's missing. (Either first run, or user has deleted account.)
        Account account = GenericAccountService.GetAccount();
        //AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(account, null, null)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, new Bundle(),SYNC_FREQUENCY);
            newAccount = true;
        }

        // Schedule an initial sync if we detect problems with either our account or our local
        // data has been deleted. (Note that it's possible to clear app data WITHOUT affecting
        // the account list, so wee need to check both.)
        if (newAccount || !setupComplete) {
            TriggerRefresh();
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean(PREF_SETUP_COMPLETE, true).commit();
        }

    }


    public static void TriggerRefresh() {
        Bundle b = new Bundle();
        // Disable sync backoff and ignore sync preferences. In other words...perform sync NOW!
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                GenericAccountService.GetAccount(),      // Sync account
                CONTENT_AUTHORITY, // Content authority
                b);                                      // Extras
    }
}