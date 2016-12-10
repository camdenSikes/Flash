package hu.ait.onetwelve.flash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.onetwelve.flash.model.Deck;

public class AddDeckActivity extends BaseActivity {

    @BindView(R.id.etTitle)
    EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deck);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSend)
    void sendClick() {
        if(!isFormValid()){
            return;
        }

        String key = FirebaseDatabase.getInstance().getReference().child("deck").push().getKey();
        Deck newDeck = new Deck(getUid(), getUserName(),
                etTitle.getText().toString(), null, null);

        FirebaseDatabase.getInstance().getReference().child("deck").child(key).setValue(newDeck);

        Toast.makeText(this, "Deck created", Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean isFormValid() {
        if (TextUtils.isEmpty(etTitle.getText().toString())) {
            etTitle.setError("Required");
            return false;
        } else {
            etTitle.setError(null);
            return true;
        }
    }
}
