package hu.ait.onetwelve.flash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.onetwelve.flash.adapter.AddedCardsAdapter;
import hu.ait.onetwelve.flash.model.Deck;

public class AddDeckActivity extends BaseActivity {

    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etCardFront)
    EditText etCardFront;
    @BindView(R.id.etCardBack)
    EditText etCardBack;
    @BindView(R.id.recyclerAddedCards)

    RecyclerView recyclerAddedCards;
    AddedCardsAdapter addedCardsAdapter;
    private Bundle bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deck);
        ButterKnife.bind(this);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerAddedCards.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        bd = intent.getExtras();
        if(bd != null) {
            Deck editDeck = (Deck) bd.get(MainActivity.KEY_DECK);
            etTitle.setText(editDeck.getTitle());
            addedCardsAdapter = new AddedCardsAdapter(this, editDeck.getFronts(), editDeck.getBacks());


        }else {
            addedCardsAdapter = new AddedCardsAdapter(this);
        }
        recyclerAddedCards.setAdapter(addedCardsAdapter);
    }


    @OnClick(R.id.btnAddCard)
    void addCardClick() {
        if(!isAddFormValid()){
            return;
        }
        addedCardsAdapter.addCard(etCardFront.getText().toString(),etCardBack.getText().toString());
        etCardFront.getText().clear();
        etCardBack.getText().clear();
    }

    @OnClick(R.id.btnSend)
    void sendClick() {
        if(!isSendFormValid()){
            return;
        }

        String key;
        String message;
        if(bd != null){
            key = (String) bd.get(MainActivity.KEY_KEY);
            message = getString(R.string.deck_edited);
        }
        else {
            key = FirebaseDatabase.getInstance().getReference().child("deck").push().getKey();
            message = getString(R.string.deck_created);
        }
        Deck newDeck = new Deck(getUid(), getUserName(),
                etTitle.getText().toString(), addedCardsAdapter.getFrontList(),
                addedCardsAdapter.getBackList());

        FirebaseDatabase.getInstance().getReference().child("deck").child(key).setValue(newDeck);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean isAddFormValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etCardFront.getText().toString())) {
            etCardFront.setError("Required");
            result = false;
        } else {
            etCardFront.setError(null);
        }
        if (TextUtils.isEmpty(etCardBack.getText().toString())) {
            etCardBack.setError("Required");
            result = false;
        } else {
            etCardBack.setError(null);
        }
        return result;
    }

    private boolean isSendFormValid() {
        if (TextUtils.isEmpty(etTitle.getText().toString())) {
            etTitle.setError("Required");
            return false;
        } else {
            etTitle.setError(null);
            return true;
        }
    }

    public void setCardFrontText(String cardFrontText) {
        etCardFront.setText(cardFrontText);
    }

    public void setCardBackText(String cardBackText) {
        etCardBack.setText(cardBackText);
    }
}
