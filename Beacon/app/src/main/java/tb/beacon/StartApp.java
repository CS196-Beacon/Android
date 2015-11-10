/**
 * Created by harrison on 11/8/15.
 */
package tb.beacon;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class StartApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Xt3BGViTJtlp2gWkSxmVBc5xikYi9NWkDRNyT1kA", "k99Fb3x0Ap6r0M0pKyUJcuHSVVGh2KlHt5mkDROU");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}