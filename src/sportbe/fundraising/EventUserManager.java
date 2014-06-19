package sportbe.fundraising;

import sportbe.fundraising.containers.EventUser;
import sportbe.util.error.OutputException;
import sportbe.util.network.networkMagic;
import sportbe.util.sql.DBConnection;
import sportbe.util.string.stringMagic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Created by stevenbelen on 06/12/13.
 */
public class EventUserManager {

    private DBConnection dbConn;
    private String SQLString;

    public EventUserManager(DBConnection dbConn) throws SQLException {
        this.dbConn = dbConn;
        dbConn.setAutoCommit(false);
    }

    public int add(EventUser eventUser) throws SQLException {
        SQLString = "{? = call fnd_AddEventUser (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        int the_ID;
        CallableStatement csMain = null;
        try {
            csMain = dbConn.getCallableStatement(SQLString);
            csMain.registerOutParameter(1, java.sql.Types.INTEGER);
            csMain.setInt(2, eventUser.getEvent_ID());
            csMain.setString(3, eventUser.getFirstName());
            csMain.setString(4,eventUser.getLastName());
            csMain.setString(5,eventUser.getStreet());
            csMain.setString(6,eventUser.getHouseNumber());
            csMain.setString(7,eventUser.getPostalCode());
            csMain.setString(8,eventUser.getCity());
            csMain.setInt(9, eventUser.getCountry_ID());
            csMain.setInt(10, eventUser.getLanguage_ID());
            csMain.setString(11, eventUser.getEmail());
            csMain.setString(12,eventUser.getGroupName());
            csMain.setString(13,eventUser.getGroupDescription());
            csMain.setInt(14, eventUser.getFundsToRaise());
            csMain.setInt(15,eventUser.getFundsRaised());
            csMain.setString(16, eventUser.getPassword());
            csMain.setString(17,eventUser.getFacebook_ID());
            csMain.executeUpdate();
            the_ID = csMain.getInt(1);
            dbConn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            e.fillInStackTrace();
            throw e;
        } finally {
            assert csMain != null;
            csMain.close();
        }
        return the_ID;
    }

    public void update(EventUser eventUser) throws SQLException {
        SQLString = "{call fnd_UpdateEventUser (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        int the_ID;
        CallableStatement csMain = null;
        try {
            csMain = dbConn.getCallableStatement(SQLString);
            csMain.setInt(1, eventUser.getEventUser_ID());
            csMain.setInt(2, eventUser.getEvent_ID());
            csMain.setString(3,eventUser.getFirstName());
            csMain.setString(4,eventUser.getLastName());
            csMain.setString(5,eventUser.getStreet());
            csMain.setString(6,eventUser.getHouseNumber());
            csMain.setString(7,eventUser.getPostalCode());
            csMain.setString(8,eventUser.getCity());
            csMain.setInt(9,eventUser.getCountry_ID());
            csMain.setInt(10, eventUser.getLanguage_ID());
            csMain.setString(11, eventUser.getEmail());
            csMain.setString(12,eventUser.getGroupName());
            csMain.setString(13,eventUser.getGroupDescription());
            csMain.setInt(14, eventUser.getFundsToRaise());
            csMain.setInt(15,eventUser.getFundsRaised());
            csMain.setString(16, eventUser.getPassword());
            csMain.setString(17,eventUser.getFacebook_ID());
            csMain.executeUpdate();
            dbConn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            e.fillInStackTrace();
            throw e;
        } finally {
            assert csMain != null;
            csMain.close();
        }

    }


    public int login(String Facebook_ID,int Event_ID, HttpServletResponse response,int cookieLifeTime) throws SQLException, OutputException, NoSuchAlgorithmException {

        ViewEventUserInfo viewEventUserInfo = new ViewEventUserInfo(dbConn,Facebook_ID,Event_ID);
        if (viewEventUserInfo.getEventUser() != null)
        {
            // Facebook ID found in current event
            sportbe.util.network.networkMagic nm = new sportbe.util.network.networkMagic();
            nm.writeCookie(response, "fundraising_"+Event_ID+"",viewEventUserInfo.getEventUser().getEventUser_ID()+"", cookieLifeTime);
            nm.writeCookie(response, "fundraising_check_"+Event_ID+"",stringMagic.encryptString(viewEventUserInfo.getEventUser().getGroupName(), "SHA-1")+"", cookieLifeTime);

        }

        return viewEventUserInfo.getEventUser().getEventUser_ID();
    }

    public int login(String Email,String Password,int Event_ID, HttpServletResponse response,int cookieLifeTime) throws SQLException, OutputException, NoSuchAlgorithmException {

        ViewEventUserInfo viewEventUserInfo = new ViewEventUserInfo(dbConn,Email,Password,Event_ID);
        if (viewEventUserInfo.getEventUser() != null)
        {
            // Facebook ID found in current event
            sportbe.util.network.networkMagic nm = new sportbe.util.network.networkMagic();
            nm.writeCookie(response, "fundraising_"+Event_ID+"",viewEventUserInfo.getEventUser().getEventUser_ID()+"", cookieLifeTime);
            nm.writeCookie(response, "fundraising_check_"+Event_ID+"",stringMagic.encryptString(viewEventUserInfo.getEventUser().getGroupName(), "SHA-1")+"", cookieLifeTime);
        }


        return viewEventUserInfo.getEventUser().getEventUser_ID();
    }

    public int login(EventUser eventUser, HttpServletResponse response,int cookieLifeTime) throws SQLException, OutputException, NoSuchAlgorithmException {


            // Facebook ID found in current event
            sportbe.util.network.networkMagic nm = new sportbe.util.network.networkMagic();
            nm.writeCookie(response, "fundraising_"+eventUser.getEvent_ID()+"",eventUser.getEventUser_ID()+"", cookieLifeTime);
            nm.writeCookie(response, "fundraising_check_" + eventUser.getEvent_ID() + "", stringMagic.encryptString(eventUser.getGroupName(), "SHA-1") + "", cookieLifeTime);

        return eventUser.getEventUser_ID();
    }


    /**
     * Checks cookie presence
     *
     * @param request
     * @return
     * @throws sportbe.util.error.OutputException
     */
    public int checkLoginCookie(HttpServletRequest request,int Event_ID) throws OutputException {
        networkMagic nm = new networkMagic();
        if (nm.readCookie(request, "fundraising_"+Event_ID).equals("not found")) {
            //------- niet ingelogd
            return 0;
        } else {
            //------- ingelogd
            return Integer.parseInt(nm.readCookie(request,"fundraising_"+Event_ID));
        }
    }

    /**
     * Checks cookie presence
     *
     * @param request
     * @return
     * @throws sportbe.util.error.OutputException
     */
    public String checkLoginCheckCookie(HttpServletRequest request,int Event_ID) throws OutputException {
        networkMagic nm = new networkMagic();
        return nm.readCookie(request, "fundraising_check_"+Event_ID);

    }

}
