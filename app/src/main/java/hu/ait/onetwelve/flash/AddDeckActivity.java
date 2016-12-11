package hu.ait.onetwelve.flash;

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

import static android.R.attr.onClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deck);
        ButterKnife.bind(this);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerAddedCards.setLayoutManager(mLayoutManager);
        //TODO: If edit button was pressed, set title and call other adapter constructor
        addedCardsAdapter = new AddedCardsAdapter();
        recyclerAddedCards.setAdapter(addedCardsAdapter);
    }


    @OnClick(R.id.btnAddCard)
    void addCardClick() {
        if(!isAddFormValid()){
            return;
        }
        addedCardsAdapter.addCard(etCardFront.getText().toString(),etCardBack.getText().toString());
    }

    @OnClick(R.id.btnSend)
    void sendClick() {
        if(!isSendFormValid()){
            return;
        }

        //TODO: Maybe have to do something different when editing
        String key = FirebaseDatabase.getInstance().getReference().child("deck").push().getKey();
        Deck newDeck = new Deck(getUid(), getUserName(),
                etTitle.getText().toString(), addedCardsAdapter.getFrontList(),
                addedCardsAdapter.getBackList());

        FirebaseDatabase.getInstance().getReference().child("deck").child(key).setValue(newDeck);

        Toast.makeText(this, "Deck created", Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean isAddFormValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etCardFront.getText().toString())) {
            etTitle.setError("Required");
            result = false;
        } else {
            etTitle.setError(null);
        }
        if (TextUtils.isEmpty(etTitle.getText().toString())) {
            etTitle.setError("Required");
            result = false;
        } else {
            etTitle.setError(null);
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
}
