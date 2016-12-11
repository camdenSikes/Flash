package hu.ait.onetwelve.flash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import hu.ait.onetwelve.flash.model.Deck;

/**
 * Created by Brendan on 12/11/16.
 */

public class ViewCardsActivity extends AppCompatActivity {
    private Deck deck;
    private TextView tvFront;
    private TextView tvBack;
    private FloatingActionButton fabCorrect;
    private FloatingActionButton fabIncorrect;
    private int listPos;
    private TextView tvDeckName;
    private TextView tvDeckPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cards);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        deck = (Deck) bd.get(MainActivity.KEY_DECK);
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvFront = (TextView) findViewById(R.id.tvFront);
        fabCorrect = (FloatingActionButton) findViewById(R.id.fabCorrect);
        fabIncorrect = (FloatingActionButton) findViewById(R.id.fabIncorrect);
        listPos = 0;
        tvDeckName = (TextView) findViewById(R.id.tvDeckName);
        tvDeckName.setText(deck.getTitle().toString());
        tvDeckPosition = (TextView) findViewById(R.id.tvDeckPosition);
        updateCardInfo();

        fabCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextItem();
                //TODO: Somehow gather data on what was pressed
            }
        });

        fabIncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextItem();
            }
        });
    }

    public void nextItem() {
        if(listPos == deck.getFronts().size()-1) {
            //TODO: Perform end of list action??
        }
        else {
            listPos++;
            updateCardInfo();
        }
    }

    public void updateCardInfo() {
        String front = deck.getFronts().get(listPos);
        String back = deck.getBacks().get(listPos);
        tvDeckPosition.setText(Integer.toString(listPos)+" / "+deck.getFronts().size());
        tvFront.setText(front);
        tvBack.setText(back);
    }

}
