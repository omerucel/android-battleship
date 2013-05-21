package tr.edu.fatih.amiralbatti;

import java.io.Serializable;

public class Point implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8264362531455406236L;

	enum Type{
		QUESTION, SHIP, HIT, EMPTY
	};
	Type type;
	int index = 0;

	public Point(int index, Type type) {
		this.index = 0;
		this.type = type;
	}

	public Point(int index, String type){
		this.index = index;

		if(type.equals("hit")){
			this.type = Type.HIT;
		}else if(type.equals("question")){
			this.type = Type.QUESTION;
		}else if(type.equals("ship")){
			this.type = Type.SHIP;
		}else{
			this.type = Type.EMPTY;
		}
	}

	public String getType(){
		if(Type.HIT == type){
			return "hit";
		}else if(Type.QUESTION == type){
			return "question";
		}else if(Type.SHIP == type){
			return "ship";
		}else{
			return "empty";
		}
	}

	public int getIndex()
	{
		return index;
	}

	public void changeType(Type type)
	{
		this.type = type;
	}

	public int getDrawable()
	{
		if(Type.HIT == type){
			return R.drawable.hit;
		}else if(Type.QUESTION == type){
			return R.drawable.question;
		}else if(Type.SHIP == type){
			return R.drawable.ship;
		}else{
			return R.drawable.empty;
		}
	}

	public Boolean is(Type checkType)
	{
		return checkType == type;
	}

	public Boolean isAvailable()
	{
		return is(Type.QUESTION) || is(Type.SHIP);
	}
}
