package hu.ait.onetwelve.flash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hu.ait.onetwelve.flash.model.Deck;
import hu.ait.onetwelve.flash.view.FlipLayout;

/**
 * Created by Brendan on 12/11/16.
 */

public class ViewCardsActivity extends AppCompatActivity {
    private Deck deck;
    private TextView tvFront;
    private TextView tvBack;
    private Button fabCorrect;
    private Button fabIncorrect;
    private int listPos;
    private TextView tvDeckHeader;
    private TextView tvDeckPosition;
    private FlipLayout flipLayout;

    private int score;
    private boolean complete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        deck = (Deck) bd.get(MainActivity.KEY_DECK);
        listPos = 0;
        score = 0;

        if(ViewCardsData.getInstance().isComplete()) {
            setContentView(R.layout.completion_screen);
            score = ViewCardsData.getInstance().getScore();
            showCompleteMessage();
        }
        else {
            setupFlipViews();
        }

        if(ViewCardsData.getInstance().getListPos() != 0) {
            listPos = ViewCardsData.getInstance().getListPos();
        }

        if(ViewCardsData.getInstance().getScore() != 0) {
            score = ViewCardsData.getInstance().getScore();
        }

        if (!ViewCardsData.getInstance().isComplete()) {
            updateCardInfo();
        }
    }

    private void setupFlipViews() {
        setContentView(R.layout.activity_view_cards);
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvFront = (TextView) findViewById(R.id.tvFront);
        fabCorrect = (Button) findViewById(R.id.fabCorrect);
        fabIncorrect = (Button) findViewById(R.id.fabIncorrect);

        fabCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score++;
                ViewCardsData.getInstance().setScore(score);
                flipLayout.reset();
                nextItem();
            }
        });

        fabIncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipLayout.reset();
                nextItem();
            }
        });

        tvDeckHeader = (TextView) findViewById(R.id.tvDeckHeader);
        tvDeckHeader.setText(deck.getTitle().toString()+" by "+deck.getAuthor().toString());
        tvDeckPosition = (TextView) findViewById(R.id.tvDeckPosition);
        flipLayout = (FlipLayout) findViewById(R.id.flipCards);
    }

    public void nextItem() {
        if(listPos == deck.getFronts().size()-1) {
            ViewCardsData.getInstance().setComplete(true);
            ViewCardsData.getInstance().setScore(score);
            setContentView(R.layout.completion_screen);
            showCompleteMessage();
        }
        else {
            listPos++;
            ViewCardsData.getInstance().setListPos(listPos);
            updateCardInfo();
        }
    }

    private void showCompleteMessage() {
        TextView tvCompletion = (TextView) findViewById(R.id.tvCompletion);
        tvCompletion.setText(getString(R.string.score_completion)+
                score+"/"+deck.getFronts().size());
        Button btnCompAccept = (Button) findViewById(R.id.btnCompAccept);
        btnCompAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCardsData.getInstance().setComplete(false);
                ViewCardsData.getInstance().setScore(0);
                ViewCardsData.getInstance().setListPos(0);
                finish();
            }
        });
    }

    public void updateCardInfo() {
        String front = deck.getFronts().get(listPos);
        String back = deck.getBacks().get(listPos);
        tvDeckPosition.setText(Integer.toString(listPos+1)+" / "+deck.getFronts().size());
        tvFront.setText(front);
        tvBack.setText(back);
    }
}
