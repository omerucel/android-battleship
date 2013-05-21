package tr.edu.fatih.amiralbatti;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MyBoardActivity extends Activity {

	private Game game;
	private ProgressDialog loadingDialog;
	private Button attackButton;
	private int lastAttackIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_board);
		attackButton = (Button)findViewById(R.id.my_board_button_attack);
	}

	@Override
	protected void onResume() {
		super.onResume();
		attackButton.setEnabled(true);
		game = (Game)getIntent().getParcelableExtra("game");
		game.getMyBoard().refreshImages(this);

		// Oyunun tamamlanma durumu kontrol ediliyor.
		if (game.isComplete())
		{
			openWinnerActivity();
			return;
		}

		if (!game.isMyTurn())
			enemyAttack();
	}

	public void exit(View view)
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void openWinnerActivity()
	{
		Intent intent = new Intent(this, WinnerActivity.class);
		intent.putExtra("game", game);
		startActivity(intent);
	}

	protected void enemyAttack()
	{
		loadingDialog = ProgressDialog.show(this, "", "Düşman saldırıyor...", true);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				MyBoardActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						int index = MyBoardActivity.this.game.newEnemyAttack();
						if (index > -1)
						{
							MyBoardActivity.this.game.getMyBoard().newAttack(MyBoardActivity.this, index);
							MyBoardActivity.this.loadingDialog.dismiss();

							if (MyBoardActivity.this.game.isComplete())
							{
								MyBoardActivity.this.openWinnerActivity();
								return;
							}
						}

						MyBoardActivity.this.game.isMyTurn(true);
					}
				});
			}
		}, 3000);
	}

	public void attack(View view)
	{
		attackButton.setEnabled(false);
		game.getEnemyBoard().refreshImages(this, true);
	}

	public void sendBomb(View view)
	{
		ImageView imageView = (ImageView)view;
		int index = 0;
		for(; index < Board.SIZE; index++)
		{
			int id = this.getResources().getIdentifier("checkBox" + index, "id", this.getPackageName());
			if (imageView.getId() == id)
				break;
		}
		lastAttackIndex = index;

		loadingDialog = ProgressDialog.show(this, "", "Düşmana saldırılıyor...", true);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				MyBoardActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						MyBoardActivity.this.game.getEnemyBoard().newAttack(MyBoardActivity.this, MyBoardActivity.this.lastAttackIndex);
						MyBoardActivity.this.loadingDialog.dismiss();

						if (MyBoardActivity.this.game.isComplete())
						{
							MyBoardActivity.this.openWinnerActivity();
							return;
						}

						MyBoardActivity.this.game.isMyTurn(false);
						MyBoardActivity.this.attackButton.setEnabled(true);
						MyBoardActivity.this.game.getMyBoard().refreshImages(MyBoardActivity.this);
						MyBoardActivity.this.enemyAttack();
					}
				});
			}
		}, 3000);
	}
}
