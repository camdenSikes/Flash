package hu.ait.onetwelve.flash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.ait.onetwelve.flash.R;
import hu.ait.onetwelve.flash.model.Deck;

/**
 * Created by Camden Sikes on 12/10/2016.
 */

public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvAuthor)
        TextView tvAuthor;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.btnViewCards)
        Button btnViewCards;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    private Context context;
    private List<Deck> deckList;
    private List<String> deckKeys;
    private String uId;
    private int lastPosition = -1;
    private DatabaseReference decksRef;

    public DecksAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;
        this.deckList = new ArrayList<Deck>();
        this.deckKeys = new ArrayList<String>();

        decksRef = FirebaseDatabase.getInstance().getReference("posts");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_deck, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Deck tmpPost = deckList.get(position);
        holder.tvAuthor.setText(tmpPost.getAuthor());
        holder.tvTitle.setText(tmpPost.getTitle());
        holder.btnViewCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Show deck view
            }
        });

        if(uId.equals(tmpPost.getUid())) {
            holder.itemView.setVisibility(View.GONE);
        }

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }

    public void addDeck(Deck deck, String key) {
        deckList.add(deck);
        deckKeys.add(key);
        notifyDataSetChanged();
    }

    public void removeDeck(int index) {
        decksRef.child(deckKeys.get(index)).removeValue();
        deckList.remove(index);
        deckKeys.remove(index);
        notifyItemRemoved(index);
    }

    public void removeDeckByKey(String key) {
        int index = deckKeys.indexOf(key);
        if (index != -1) {
            deckList.remove(index);
            deckKeys.remove(index);
            notifyItemRemoved(index);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
