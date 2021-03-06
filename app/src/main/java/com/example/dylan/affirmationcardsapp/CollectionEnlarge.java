package com.example.dylan.affirmationcardsapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CollectionEnlarge extends AppCompatActivity {
    public static final String EXTRA_CARD_ID = "id";
    Drawable d;
    private Card card;
    private LikeButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_enlarge);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Card> cardBox = boxStore.boxFor(Card.class);
        List<Card> cards = cardBox.getAll();
        ImageView cardView = findViewById(R.id.enlargedCard);
        int cardId = (Integer) getIntent().getExtras().get(EXTRA_CARD_ID);
        ImageView heartView = findViewById(R.id.heartView);

        card = cards.get(cardId);
        if (card.isFavorite()) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
        }
        cardView.setImageResource(cards.get(cardId).getImage());


    }


    public void setDrawable(Drawable d) {
        this.d = d;
    }

    public void favorite_button(View view) {
        ImageView heartView = findViewById(R.id.heartView);
        String action;
        if (card.isFavorite()) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsortfavorite_24dp));
            action = "Card removed from favorites";

        } else {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
            action = "Card added to favorites";
        }
        //if the button is clicked, it flips the favorite boolean value
        card.setFavorite(!card.isFavorite());
        TextView tv = findViewById(R.id.tv);

        tv.setText("" + card.isFavorite());

        Toast.makeText(this, action,
                Toast.LENGTH_SHORT).show();
    }

    public void share_button(View view) {
        Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                + "/drawable/" + "ic_launcher");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/jpeg");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }
}





