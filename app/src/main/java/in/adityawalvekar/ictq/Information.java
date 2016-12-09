package in.adityawalvekar.ictq;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Information extends android.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView.Adapter mAdapter;
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        RecyclerView.LayoutManager mLayoutManager;
        final RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.rcview);
        mRecyclerView.setHasFixedSize(true);
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server").child("info");
        final ArrayList<Info> InfoList = new ArrayList<Info>();
        Toast.makeText(getActivity(),"Added Data",Toast.LENGTH_SHORT);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                InfoList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Info in = postSnapshot.getValue(Info.class);
                    InfoList.add(in);
                    int n = InfoList.size();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("no", n);
                    editor.commit();
                }
                MyAdapter adapter = new MyAdapter(InfoList, getActivity());
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