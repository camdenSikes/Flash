package hu.ait.onetwelve.flash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hu.ait.onetwelve.flash.MainActivity;
import hu.ait.onetwelve.flash.R;
import hu.ait.onetwelve.flash.adapter.DecksAdapter;

public class MyDecksFragment extends Fragment {
    private DecksAdapter decksAdapter;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deck_list, null);
        uid = ((MainActivity) container.getContext()).getUid();

        decksAdapter = new DecksAdapter(container.getContext(), uid);
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

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                decksAdapter.updateMyDecks(uid, dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
