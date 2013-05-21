package tr.edu.fatih.amiralbatti;

import java.util.ArrayList;
import java.util.Random;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Game implements Parcelable{
	private Board myBoard;
	private Board enemyBoard;
	private Boolean isMyTurn = false;
	private ArrayList<Integer> myAttack;
	private ArrayList<Integer> enemyAttack;

	public Game(){
		isMyTurn = new Random().nextBoolean();
		myBoard = new Board();
		enemyBoard = new Board();
		myAttack = new ArrayList<Integer>();
		enemyAttack = new ArrayList<Integer>();
	}

	private Game(Parcel in){
		isMyTurn = Boolean.getBoolean(in.readString());
		myBoard = in.readParcelable(Board.class.getClassLoader());
		enemyBoard = in.readParcelable(Board.class.getClassLoader());
		in.readList(myAttack, Integer.class.getClassLoader());
		in.readList(enemyAttack, Integer.class.getClassLoader());
	}

	public Board getMyBoard()
	{
		return this.myBoard;
	}

	public Board getEnemyBoard()
	{
		return this.enemyBoard;
	}

	public Boolean isMyTurn()
	{
		return isMyTurn;
	}

	public void isMyTurn(Boolean status)
	{
		isMyTurn = status;
	}

	public int newEnemyAttack()
	{
		ArrayList<Integer> indexes = myBoard.getAvailablePointIndexes();
		if (indexes.size() == 0)
		{
			return -1;
		}else if(indexes.size() == 1){
			return indexes.get(0);
		}else{
			return indexes.get(new Random().nextInt(indexes.size()-1));
		}
	}

	public Boolean isComplete()
	{
		return !myBoard.hasAvailableShip() || !enemyBoard.hasAvailableShip();
	}

	public Boolean isWinnerEnemy()
	{
		return enemyBoard.hasAvailableShip();
	}

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
		public Game createFromParcel(Parcel in) {
		    return new Game(in);
		}

		public Game[] newArray(int size) {
		    return new Game[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(isMyTurn.toString());
		dest.writeParcelable(myBoard, flags);
		dest.writeParcelable(enemyBoard, flags);
		dest.writeList(myAttack);
		dest.writeList(enemyAttack);
	}
}
