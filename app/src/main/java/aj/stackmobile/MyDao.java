package aj.stackmobile;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addItem(itemsave i);

    @Query("select * from i")
    public List <itemsave> getItems();

    @Query("select * from i where question_id = :id")
    public List <itemsave> check(int id);

}
