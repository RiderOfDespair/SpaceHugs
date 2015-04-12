package spaceapps.nasa.com.spacehug;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import spaceapps.nasa.com.spacehug.data.CrewMember;
import spaceapps.nasa.com.spacehug.data.ServerData;


public class CrewList extends FragmentActivity {

    public View listView;
    public ImageView bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_list);

        overridePendingTransition(android.R.anim.bounce_interpolator,android.R.anim.slide_out_right);



    }

    @Override
    protected void onResume(){
        super.onResume();
        bg = (ImageView)findViewById(R.id.bg_img);
        bg.setImageDrawable((getResources().getDrawable(R.drawable.iss)));

        if(listView == null) {
            listView = findViewById(R.id.list);


            ListView list = (ListView) listView;
            final CrewListAdapter adapter = new CrewListAdapter(this);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = ((TextView)view.findViewById(R.id.name)).getText().toString();
                    String craft = ((TextView)view.findViewById(R.id.craft)).getText().toString();

                    Intent intent = new Intent(CrewList.this, DetailsActivity.class);
                    intent.putExtra(DetailsActivity.NAME, name);
                    intent.putExtra(DetailsActivity.CRAFT, craft);
                    startActivity(intent);
                }
            });

        }

    }

    @Override
    protected void onPause(){
        super.onPause();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                bg.setImageBitmap(null);
            }
        },1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crew_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CrewListAdapter extends BaseAdapter {
        private Context _context;
        private ArrayList<CrewMember> crewList;


        private void initialize(){

            crewList = new ArrayList<>();

            ServerData.getCrewData(null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                    try {
                        JSONArray jsonArray = response.getJSONArray("people");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            // Log.d(obj.getString("name"), obj.getString("craft"));
                            crewList.add(new CrewMember(obj.getString("name"), obj.getString("craft")));

                        }

                    } catch (JSONException ex) {

                    }
                    notifyDataSetChanged();
                    //Log.d("data set changed ", crewList.size() + "");
                }
            });

        }


        public CrewListAdapter(Context c){
            _context = c;
            initialize();

        }

        @Override
        public int getCount() {
            return crewList.size();
        }

        @Override
        public CrewMember getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if(convertView == null) {

                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.crew_list_item, null);

                viewHolder = new ViewHolder();
                viewHolder.name = (TextView)convertView.findViewById(R.id.name);
                viewHolder.craft = (TextView)convertView.findViewById(R.id.craft);
                convertView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }


            viewHolder.name.setText( crewList.get(position).getName() );
            viewHolder.craft.setText( crewList.get(position).getCraft() );


            return convertView;
        }

        class ViewHolder{
            TextView name, craft;

        }
    }

}
