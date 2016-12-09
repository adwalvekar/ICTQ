package in.adityawalvekar.ictq;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Info> mDataset;
    Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView title;
        public TextView subtext;
        public TextView body;
        public Button button;
        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            title = (TextView) v.findViewById(R.id.headerText);
            subtext = (TextView) v.findViewById(R.id.subText);
            body = (TextView) v.findViewById(R.id.desc);
            button = (Button) v.findViewById(R.id.cvbutton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Info> myDataset, Context mContext) {
        mDataset = myDataset;
        context = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
                int i = position;
                holder.title.setText(mDataset.get(i).Title);
                holder.subtext.setText(mDataset.get(i).SubText);
                holder.body.setText(mDataset.get(i).Body);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    int i = position;
                    @Override
                    public void onClick(View v) {
                        String url = mDataset.get(i).url;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
