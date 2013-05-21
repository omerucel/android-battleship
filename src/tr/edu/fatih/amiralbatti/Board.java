package tr.edu.fatih.amiralbatti;

import java.util.ArrayList;
import java.util.Random;

import tr.edu.fatih.amiralbatti.Point.Type;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Board implements Parcelable{
	ArrayList<Point> points;

	public static int SIZE = 16;

	public Board() {
		points = new ArrayList<Point>();
	}

	private Board(Parcel in)
	{
		this();
		in.readList(points, Point.class.getClassLoader());
	}

	public void init()
	{
		points.clear();

		Boolean isShip = false;
		int shipCounter = 0;
		for(int i=0;i<SIZE;i++)
		{
			if (shipCounter == 4)
			{
				isShip = false;
			}else{
				isShip = new Random().nextBoolean();					
			}

			if (isShip)
			{
				points.add(new Point(i, "ship"));
				shipCounter++;
			}else{
				points.add(new Point(i, "question"));
			}
		}

		if (shipCounter != 4)
			init();
	}

	public void init(Activity activity){
		int id = 0;
		ImageView imageView;
		points.clear();

		for(int i=0;i<SIZE;i++)
		{
			id = activity.getResources().getIdentifier("checkBox" + i, "id", activity.getPackageName());
			imageView = (ImageView)activity.findViewById(id);
			points.add(new Point(i, imageView.getTag().toString()));
		}
	}

	public void refreshImages(Activity activity){
		int id = 0;
		ImageView imageView;

		for(Point point : points)
		{
			id = activity.getResources().getIdentifier("checkBox" + point.getIndex(), "id", activity.getPackageName());
			imageView = (ImageView)activity.findViewById(id);
			imageView.setTag(point.getType());
			imageView.setImageResource(point.getDrawable());
		}
	}

	public void refreshImages(Activity activity, Boolean hideShip){
		int id = 0;
		ImageView imageView;

		for(Point point : points)
		{
			id = activity.getResources().getIdentifier("checkBox" + point.getIndex(), "id", activity.getPackageName());
			imageView = (ImageView)activity.findViewById(id);
			imageView.setTag(point.getType());
			if (point.is(Type.SHIP) && hideShip)
			{
				imageView.setImageResource(R.drawable.question);
			}else{
				imageView.setImageResource(point.getDrawable());
			}
		}
	}

	public ArrayList<Integer> getAvailablePointIndexes()
	{
		ArrayList<Integer> temp = new ArrayList<Integer>();

		for(Point point : points)
		{
			if (point.isAvailable())
				temp.add(point.getIndex());
		}

		return temp;
	}

	public void newAttack(Activity activity, int index)
	{
		Point point = points.get(index);
		if (point.is(Type.SHIP))
		{
			point.changeType(Type.HIT);
		}else{
			point.changeType(Type.EMPTY);
		}

		int id = activity.getResources().getIdentifier("checkBox" + index, "id", activity.getPackageName());
		ImageView imageView = (ImageView)activity.findViewById(id);
		imageView.setTag(point.getType());
		imageView.setImageResource(point.getDrawable());
	}

	public Boolean hasAvailableShip()
	{
		for(Point point : points)
		{
			if (point.is(Type.SHIP))
				return true;
		}

		return false;
	}

    public static final Parcelable.Creator<Board> CREATOR = new Parcelable.Creator<Board>() {
		public Board createFromParcel(Parcel in) {
		    return new Board(in);
		}

		public Board[] newArray(int size) {
		    return new Board[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(points);
	}
}
