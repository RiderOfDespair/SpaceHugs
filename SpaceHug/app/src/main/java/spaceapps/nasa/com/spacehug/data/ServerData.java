package spaceapps.nasa.com.spacehug.data;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Nic on 4/11/2015.
 */
public class ServerData {

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static final String CREW_MEMBERS_JSON = "http://niclinscott.com/php/space-hugs/getCrewData.php";

    private static final String IMAGE_SEARCH_DATA = "http://niclinscott.com/php/space-hugs/getGoogleImages.php";

    private static final String PI = "www.google.com";

    public static void getCrewData(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(CREW_MEMBERS_JSON, params, responseHandler);
    }

    public static void getCrewMemberDetails(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(IMAGE_SEARCH_DATA, params, responseHandler);
    }

    public static void postHug(AsyncHttpResponseHandler handler){
        client.post(CREW_MEMBERS_JSON, handler);
    }
}
