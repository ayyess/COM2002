package uk.ac.shef.com2002.grp4.data;

/**
 * A generic model that can be saved to the db
 * Created on 11/11/2016.
 */
public interface Saveable{
	boolean isInDb();
	void update();
	void insert();
	default void save(){
		if(isInDb()){
			update();
		}else{
			insert();
		}
	}
}