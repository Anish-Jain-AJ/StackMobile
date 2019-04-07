package aj.stackmobile;

import java.util.ArrayList;

public class TagItem {

    public boolean has_more;
    public int quota_max;
    public int quota_remaining;

    public ArrayList<tagdetails> items = new ArrayList<>();

    public ArrayList<tagdetails> getItems() {
        return items;
    }
}

    class tagdetails{
        public boolean has_synonyms;
        public boolean is_moderator_only;
        public boolean is_required;
        public int count;
        public String name;

        public String getName() {
            return name;
        }
    }

