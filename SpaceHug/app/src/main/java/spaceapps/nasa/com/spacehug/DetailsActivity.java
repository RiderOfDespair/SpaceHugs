package spaceapps.nasa.com.spacehug;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import spaceapps.nasa.com.spacehug.data.CrewMember;
import spaceapps.nasa.com.spacehug.data.ServerData;

/**
 * Created by Nic on 4/11/2015.
 */
public class DetailsActivity extends Activity{

    public TextView name;
    public TextView craft;


    public static final String NAME = "name";
    public static final String CRAFT = "craft";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name = (TextView)findViewById(R.id.name_detail);
        craft = (TextView)findViewById(R.id.craft_detail);

        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);

        Intent intent = getIntent();

        if(intent.hasExtra(NAME) && intent.hasExtra(CRAFT)){
             CrewMember member = new CrewMember(intent.getStringExtra(NAME), intent.getStringExtra(CRAFT));
            setDetails(member);
        }else{
            finish();
        }


    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }



    public void setDetails(final CrewMember member){


        String memberName = member.getName();
        String memberCraft = member.getCraft();
        name.setText(memberName);
        craft.setText(memberCraft);
        RequestParams params = new RequestParams("query", memberName + " " + memberCraft);


        ServerData.getCrewMemberDetails(params, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);

                try {
                    JSONObject parent = response.getJSONObject("responseData");

                    JSONArray array = parent.getJSONArray("results");

                    JSONObject node = array.getJSONObject(0);
                    String url = node.getString("url");

                   // Log.d("url", url);

                    new DownloadImageTask().execute(url);

                    JSONObject cursor = parent.getJSONObject("cursor");
                    //Log.d("more url", cursor.getString("moreResultsUrl"));
                    setButton(cursor.getString("moreResultsUrl"));




                } catch (JSONException ex) {
                    Log.e("json error", ex.getMessage());
                    ex.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
                super.onFailure(statusCode, e, errorResponse);
                Log.d("error", errorResponse.toString());
            }


        });
    }

    private void setButton(final String url){
        Button btn = (Button)findViewById(R.id.more_detail);
        Log.d("setting button", "detail");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri address = Uri.parse(url);
                Intent browser = new Intent(Intent.ACTION_VIEW, address);
                startActivity(browser);
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap icon = null;
            try {

                InputStream in = new java.net.URL(url).openStream();

                icon = BitmapFactory.decodeStream(in);

                Log.d("extracted", "detail");
            } catch (Exception e) {

                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return icon;
        }

        protected void onPostExecute(Bitmap result) {
            Log.d("post exectue", "detail");

            View progress = findViewById(R.id.progress_detail);
            progress.setVisibility(View.GONE);

            ImageView img = (ImageView)findViewById(R.id.img_detail);
            if(result == null){
                img.setImageDrawable( getResources().getDrawable(android.R.drawable.ic_menu_report_image));
            }else {
                img.setImageBitmap(result);
            }
        }
    }




}
