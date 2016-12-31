package hu.ait.onetwelve.flash;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
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
    @BindView(R.id.recyclerAddedCards)
    RecyclerView recyclerAddedCards;
    @BindView(R.id.switchPriv)
    CompoundButton switchPriv;
    AddedCardsAdapter addedCardsAdapter;
    private Bundle bd;
    private String front;
    private String back;
    private boolean editingCard = false;
    Activity activity = this;

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
            switchPriv.setChecked(editDeck.isPrivate());
            addedCardsAdapter = new AddedCardsAdapter(this, editDeck.getFronts(), editDeck.getBacks());


        }else {
            addedCardsAdapter = new AddedCardsAdapter(this);
        }
        recyclerAddedCards.setAdapter(addedCardsAdapter);
    }


    @OnClick(R.id.btnAddCard)
    void addCardClick() {
        addCard();
    }

    public void addCard(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_add_card,null);
        final EditText etCardFront = (EditText) dialog.findViewById(R.id.etCardFront);
        final EditText etCardBack = (EditText) dialog.findViewById(R.id.etCardBack);
        final String oldFront = front;
        final String oldBack = back;
        if(editingCard){
            etCardFront.setText(front);
            etCardBack.setText(back);
        }

        builder.setTitle("Add Card")
                .setView(dialog)
                .setPositiveButton("Add Card", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        front = etCardFront.getText().toString();
                        back = etCardBack.getText().toString();
                        if(isAddFormValid()) {
                            addedCardsAdapter.addCard(front, back);
                            dialogInterface.dismiss();
                        }
                        else {
                            Toast.makeText(AddDeckActivity.this, R.string.empty_card_error, Toast.LENGTH_SHORT).show();
                        }
                        editingCard = false;
                    }

                    private boolean isAddFormValid() {
                        if (TextUtils.isEmpty(etCardFront.getText().toString())) {
                            return false;
                        }
                        if (TextUtils.isEmpty(etCardBack.getText().toString())) {
                            return false;
                        }
                        return true;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editingCard){
                            addedCardsAdapter.addCard(oldFront, oldBack);
                        }
                        editingCard = false;
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alert = builder.create();

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                View view = activity.getCurrentFocus();
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        alert.show();
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
                addedCardsAdapter.getBackList(), switchPriv.isChecked());

        FirebaseDatabase.getInstance().getReference().child("deck").child(key).setValue(newDeck);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        finish();
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

    public void setEditingCard(boolean editingCard){
        this.editingCard = editingCard;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public void setBack(String back) {
        this.back = back;
    }
}
