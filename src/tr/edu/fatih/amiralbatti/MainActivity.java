package tr.edu.fatih.amiralbatti;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void newGame(View view)
	{
		Intent intent = new Intent(this, NewGameActivity.class);
		startActivity(intent);
	}
}
