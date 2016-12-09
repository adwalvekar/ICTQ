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

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.MyViewHolder> {
    private ArrayList<Noti> mDataset;
    Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView2;
        public TextView title;
        public Button link;
        public MyViewHolder(View v) {
            super(v);

            mCardView2 = (CardView) v.findViewById(R.id.card_view3);
            title = (TextView) v.findViewById(R.id.Tview3);
            link  = (Button) v.findViewById(R.id.notiB);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotifAdapter(ArrayList<Noti> myDataset, Context mContext) {
        mDataset = myDataset;
        context = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotifAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_notif, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        int i = position;
        holder.title.setText(mDataset.get(i).Title);
        holder.link.setOnClickListener(new View.OnClickListener() {
            int i = position;
            @Override
            public void onClick(View v) {
                holder.link.setText("DONE");
                int color = holder.mCardView2.getContext().getResources().getColor(R.color.green);
                holder.mCardView2.setCardBackgroundColor(color);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
