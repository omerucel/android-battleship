package tr.edu.fatih.amiralbatti;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewGameActivity extends Activity {
	int shipCounter = 0;
	Button startButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
		startButton = (Button)findViewById(R.id.new_game_button_start);
		startButton.setEnabled(false);
	}

	public void start(View view){
		Game game = new Game();
		game.getMyBoard().init(this);
		game.getEnemyBoard().init();

		Intent intent = new Intent(this, MyBoardActivity.class);
		intent.putExtra("game", game);
		startActivity(intent);
	}

	public void back(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void toggleImage(View view){
		ImageView imageView = (ImageView)view;

		if (imageView.getTag().equals("question"))
		{
			if (shipCounter < 4)
			{
				imageView.setTag("ship");
				imageView.setImageResource(R.drawable.ship);
				shipCounter++;
			}
		}else{
			shipCounter--;
			imageView.setTag("question");
			imageView.setImageResource(R.drawable.question);
		}

		if (shipCounter == 4)
		{
			startButton.setEnabled(true);
		}else{
			startButton.setEnabled(false);
		}
	}
}
