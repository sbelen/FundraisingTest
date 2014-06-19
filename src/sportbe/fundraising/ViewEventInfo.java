package sportbe.fundraising;

import sportbe.fundraising.containers.Event;
import sportbe.util.sql.DBConnection;

import java.sql.SQLException;

/**
 * Created by stevenbelen on 05/12/13.
 */
public class ViewEventInfo extends ViewBaseObject {

    int param1 = 0;
    String sqlString = " SELECT * " +
            "               FROM fnd_events" +
            "               WHERE event_id = ? " +
            "               ORDER BY description ";

    Event event ;

    public ViewEventInfo(DBConnection jdbc,int Event_ID) throws SQLException {
        super(jdbc);
        param1=Event_ID;
        readData();
    }

    private void readData() throws SQLException {
        try {

            psMain = jdbc.getPreparedStatement(sqlString);
            psMain.setInt(1, param1);
            rsMain = psMain.executeQuery();
            this.fetch();
        } catch (SQLException exception) {
            psMain.close();
            throw exception;
        }
    }

    protected void setData() throws SQLException {
        event = new Event(rsMain.getInt("Event_ID"),rsMain.getString("Description"),rsMain.getBoolean("Active"));
        param1 = 17;
    }

    public Event getEvent() {
        return event;
    }
}
