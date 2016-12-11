package hu.ait.onetwelve.flash.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.ait.onetwelve.flash.AddDeckActivity;
import hu.ait.onetwelve.flash.MainActivity;
import hu.ait.onetwelve.flash.R;
import hu.ait.onetwelve.flash.ViewCardsActivity;
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
        @BindView(R.id.btnEdit)
        Button btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;
    private List<Deck> deckList;
    private List<String> deckKeys;
    private String uId;
    private int lastPosition = -1;
    private DatabaseReference decksRef;
    private DataSnapshot dataSnap;

    public DecksAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;
        this.deckList = new ArrayList<Deck>();
        this.deckKeys = new ArrayList<String>();

        decksRef = FirebaseDatabase.getInstance().getReference("deck");
        decksRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnap = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_deck, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Deck tmpDeck = deckList.get(position);
        holder.tvAuthor.setText(tmpDeck.getAuthor());
        holder.tvTitle.setText(tmpDeck.getTitle());
        holder.btnViewCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewCardsActivity.class);
                intent.putExtra(MainActivity.KEY_DECK,deckList.get(position));
                context.startActivity(intent);
            }
        });

        if(uId.equals(tmpDeck.getUid())) {
            holder.btnEdit.setVisibility(View.VISIBLE);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddDeckActivity.class);
                intent.putExtra(MainActivity.KEY_KEY, deckKeys.get(position));
                intent.putExtra(MainActivity.KEY_DECK, deckList.get(position));
                ((MainActivity) context).startActivity(intent);
            }
        });

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

    public void updateDecks(String uid, DataSnapshot dataSnapshot, boolean myDecks){
        deckKeys.clear();
        deckList.clear();
        notifyDataSetChanged();
        for (DataSnapshot deckSnapshot : dataSnapshot.getChildren()) {
            String key = deckSnapshot.getKey();
            Deck deck = deckSnapshot.getValue(Deck.class);
            if(myDecks) {
                if (uid.equals(deck.getUid())) {
                    Log.d("HELP", "THIS HAPPENED");
                    deckKeys.add(key);
                    deckList.add(deck);
                }
            }else{
                if (!uid.equals(deck.getUid())) {
                    deckKeys.add(key);
                    deckList.add(deck);
                }
            }
        }
        notifyDataSetChanged();
    }
}
