package in.adityawalvekar.ictq;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notif extends android.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView.Adapter mAdapter;
        View v = inflater.inflate(R.layout.fragment_notif, container, false);
        RecyclerView.LayoutManager mLayoutManager;
        final RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.rcview3);
        mRecyclerView.setHasFixedSize(true);
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("notif");
        final ArrayList<Noti> NotiList = new ArrayList<Noti>();
        Toast.makeText(getActivity(),"Added Data",Toast.LENGTH_SHORT);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                NotiList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Noti sc = postSnapshot.getValue(Noti.class);
                    NotiList.add(sc);
                }
                NotifAdapter adapter = new NotifAdapter(NotiList, getActivity());
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(),"Failed to Load Data",Toast.LENGTH_SHORT);
            }
        });

        Toast.makeText(getActivity(),"After adapter",Toast.LENGTH_SHORT);
        mLayoutManager  = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return v;
    }

}