package hu.ait.onetwelve.flash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.ait.onetwelve.flash.MainActivity;
import hu.ait.onetwelve.flash.R;
import hu.ait.onetwelve.flash.adapter.DecksAdapter;
import hu.ait.onetwelve.flash.model.Deck;

/**
 * Created by Camden Sikes on 12/10/2016.
 */

public class MyDecksFragment extends Fragment {
    private DecksAdapter decksAdapter;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shareddecks, null);
        uid = ((MainActivity) container.getContext()).getUid();

        decksAdapter = new DecksAdapter(container.getContext().getApplicationContext(), uid);
        RecyclerView recyclerViewPlaces = (RecyclerView) rootView.findViewById(
                R.id.recyclerSharedDecks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerViewPlaces.setLayoutManager(layoutManager);
        recyclerViewPlaces.setAdapter(decksAdapter);

        initDecksListener();


        return rootView;
    }

    private void initDecksListener() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("deck");
        ref.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Deck newDeck = dataSnapshot.getValue(Deck.class);
                if(uid.equals(newDeck.getUid())) {
                    decksAdapter.addDeck(newDeck, dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
