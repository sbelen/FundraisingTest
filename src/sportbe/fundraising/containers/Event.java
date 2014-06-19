package sportbe.fundraising.containers;

/**
 * Created by stevenbelen on 04/12/13.
 */
public class Event {

    int Event_ID;
    String Description;
    boolean Active;


    public Event(int event_ID, String description, boolean active) {
        Event_ID = event_ID;
        Description = description;
        Active = active;
    }

    public int getEvent_ID() {
        return Event_ID;
    }

    public void setEvent_ID(int event_ID) {
        Event_ID = event_ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
