package aj.stackmobile;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class QuestionObject {

    public ArrayList<item> items = new ArrayList<>();
    public boolean has_more;
    public int quota_max;
    public int quota_remaining;

}


class item{
    public List<String> tags;
    public Owner owner;
    public boolean is_answered;
    public int view_count;
    public int closed_date;
    public int accepted_answer_id;
    public int answer_count;
    public int score;
    public int last_Activity_date;
    public int creation_date;
    public int question_id;
    public String link;
    public String closed_reason;
    public String title;
    public String last_edit_date;

    public String getTitle() {
        return title;
    }
}

class Owner{
    public int reputation;
    public int user_id;
    public String user_type;
    public int accept_rate;
    public String profile_image;
    public String display_name;
    public String link;
}

@Entity(tableName = "i")
class itemsave{
    @NonNull
    @PrimaryKey
    public int question_id;
    public String link;
    public String title;

    public itemsave(@NonNull int question_id, String link, String title) {
        this.question_id = question_id;
        this.link = link;
        this.title = title;
    }
}