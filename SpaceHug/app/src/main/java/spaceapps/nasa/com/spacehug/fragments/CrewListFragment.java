package spaceapps.nasa.com.spacehug.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import spaceapps.nasa.com.spacehug.R;
import spaceapps.nasa.com.spacehug.data.CrewMember;
import spaceapps.nasa.com.spacehug.data.DataSource;

/**
 * Created by Nic on 4/11/2015.
 */
public class CrewListFragment extends Fragment {


    public CrewListFragment(){}

    View rootView;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_crew_list,container, false);

        list = (ListView)rootView.findViewById(R.id.crew_list);

        return rootView;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }


    private class CrewListAdapter extends BaseAdapter {
        private Context _context;

        private ArrayList<CrewMember> crewList;

        public CrewListAdapter(Context c){
            _context = c;

            crewList = DataSource.getCrewMemberData();

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
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





            return convertView;
        }

        class ViewHolder{
            TextView name, craft;

        }
    }




}
