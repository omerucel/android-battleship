package tr.edu.fatih.amiralbatti;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class WinnerActivity extends Activity {

	Game game;
	TextView text;
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winner);
		text = (TextView)findViewById(R.id.winner_text);
		imageView = (ImageView)findViewById(R.id.winner_image);
	}

	@Override
	protected void onResume() {
		super.onResume();

		game = (Game)getIntent().getParcelableExtra("game");
		if (game.isWinnerEnemy())
		{
			text.setText("Kaybettiniz");
			imageView.setImageResource(R.drawable.sad);
		}else{
			text.setText("Tebrikler, kazandõnõz!");
		}
	}

	public void newGame(View view)
	{
		Intent intent = new Intent(this, NewGameActivity.class);
		startActivity(intent);
	}

}
