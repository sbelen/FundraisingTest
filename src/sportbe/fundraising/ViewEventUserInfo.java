package sportbe.fundraising;

import sportbe.fundraising.containers.Event;
import sportbe.fundraising.containers.EventUser;
import sportbe.util.sql.DBConnection;

import java.sql.SQLException;

/**
 * Created by stevenbelen on 05/12/13.
 */
public class ViewEventUserInfo extends ViewBaseObject {

    int param1 = 0;
    String strParam1 = "";
    String sqlString = " SELECT e.description,e.active,eu.* " +
            "               FROM fnd_eventusers eu" +
            "                    LEFT JOIN fnd_events e on e.fnd_event_id = eu.fnd_event_id"+
            "               WHERE fnd_eventuser_id = ? " +
            "               ORDER BY eu.groupname ";

    Event event ;
    EventUser eventUser;

    public ViewEventUserInfo(DBConnection jdbc, int EventUser_ID) throws SQLException {
        super(jdbc);
        try {

            psMain = jdbc.getPreparedStatement(sqlString);
            psMain.setInt(1, EventUser_ID);
            rsMain = psMain.executeQuery();
            this.fetch();
        } catch (SQLException exception) {
            psMain.close();
            throw exception;
        }
    }

    public ViewEventUserInfo(DBConnection jdbc, String Facebook_ID,int Event_ID) throws SQLException {
        super(jdbc);
        sqlString = " SELECT e.description,e.active,eu.* " +
                "               FROM fnd_eventusers eu" +
                "                    LEFT JOIN fnd_events e on e.fnd_event_id = eu.fnd_event_id"+
                "               WHERE facebook_id = ? and e.fnd_event_id = ?" +
                "               ORDER BY eu.groupname ";
        try {

            psMain = jdbc.getPreparedStatement(sqlString);
            psMain.setString(1, Facebook_ID);
            psMain.setInt(2,Event_ID);
            rsMain = psMain.executeQuery();
            this.fetch();
        } catch (SQLException exception) {
          //  psMain.close();
            throw exception;
        }
    }

    public ViewEventUserInfo(DBConnection jdbc, String Email,String Password,int Event_ID) throws SQLException {
        super(jdbc);
        sqlString = " SELECT e.description,e.active,eu.* " +
                "               FROM fnd_eventusers eu" +
                "                    LEFT JOIN fnd_events e on e.fnd_event_id = eu.fnd_event_id"+
                "               WHERE email = ? and password = ? and e.fnd_event_id = ?" +
                "               ORDER BY eu.groupname ";
        try {

            psMain = jdbc.getPreparedStatement(sqlString);
            psMain.setString(1, Email);
            psMain.setString(2,Password);
            psMain.setInt(3,Event_ID);
            rsMain = psMain.executeQuery();
            this.fetch();
        } catch (SQLException exception) {
            psMain.close();
            throw exception;
        }
    }

    public ViewEventUserInfo(DBConnection jdbc, int Event_ID,String Email) throws SQLException {
        super(jdbc);
        sqlString = " SELECT e.description,e.active,eu.* " +
                "               FROM fnd_eventusers eu" +
                "                    LEFT JOIN fnd_events e on e.fnd_event_id = eu.fnd_event_id"+
                "               WHERE email = ? and e.fnd_event_id = ?" +
                "               ORDER BY eu.groupname ";
        try {

            psMain = jdbc.getPreparedStatement(sqlString);
            psMain.setString(1, Email);
            psMain.setInt(2,Event_ID);
            rsMain = psMain.executeQuery();
            this.fetch();
        } catch (SQLException exception) {
            psMain.close();
            throw exception;
        }
    }



    protected void setData() throws SQLException {
        event = new Event(rsMain.getInt("fnd_Event_ID"),rsMain.getString("Description"),rsMain.getBoolean("Active"));
        eventUser = new EventUser(
                event,
                rsMain.getInt("fnd_EventUser_ID"),
                rsMain.getInt("fnd_Event_ID"),
                rsMain.getString("FirstName"),
                rsMain.getString("LastName"),
                rsMain.getString("Street"),
                rsMain.getString("HouseNumber"),
                rsMain.getString("PostalCode"),
                rsMain.getString("City"),
                rsMain.getInt("Country_ID"),
                rsMain.getInt("Language_ID"),
                rsMain.getString("Email"),
                rsMain.getString("Password"),
                rsMain.getString("Facebook_ID"),
                rsMain.getString("GroupName"),
                rsMain.getString("GroupDescription"),
                rsMain.getInt("FundsToRaise"),
                rsMain.getInt("FundsRaised")

                );


    }

    public Event getEvent() {
        return event;
    }

    public EventUser getEventUser() {
        return eventUser;
    }
}
