package hu.ait.onetwelve.flash.fragments;

import android.content.Context;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.util.Pair;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.text.TextUtils;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.EditText;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import butterknife.OnClick;
        import hu.ait.onetwelve.flash.MainActivity;
        import hu.ait.onetwelve.flash.R;
        import hu.ait.onetwelve.flash.adapter.DecksAdapter;
        import hu.ait.onetwelve.flash.model.Deck;
        import hu.ait.onetwelve.flash.stringsimilarity.ComparePairs;
        import hu.ait.onetwelve.flash.stringsimilarity.LongestCommonSubsequence;

public class SearchFragment extends Fragment {
    private DecksAdapter decksAdapter;
    private String uid;
    private LongestCommonSubsequence lcs;
    private String query;
    private DataSnapshot snapshot;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deck_search, null);
        uid = ((MainActivity) container.getContext()).getUid();
        ButterKnife.bind(this,rootView);

        lcs = new LongestCommonSubsequence();

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

    @OnClick(R.id.btnSearch)
    void search(){
        if(!isSearchValid()){
            return;
        }

        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

        query = etSearch.getText().toString();
        orderDecks(snapshot);
    }

    private boolean isSearchValid() {
            if (TextUtils.isEmpty(etSearch.getText().toString())) {
                etSearch.setError("Required");
                return false;
            } else {
                etSearch.setError(null);
                return true;
            }
    }

    private void initDecksListener() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("deck");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                snapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void orderDecks(DataSnapshot dataSnapshot) {
        List<Pair<Double, DataSnapshot>> list = new ArrayList<>();

        for (DataSnapshot deckSnapshot : dataSnapshot.getChildren()) {
            Deck deck = deckSnapshot.getValue(Deck.class);
            String title = deck.getTitle();
            double length = lcs.length(title, query);
            list.add(Pair.create(length, deckSnapshot));
        }

        Collections.sort(list, new ComparePairs());
        decksAdapter.clear();
        for (Pair<Double,DataSnapshot> pair:list) {
            Deck deck = pair.second.getValue(Deck.class);
            String key = pair.second.getKey();
            decksAdapter.addDeck(deck,key);
        }
    }
}
