package sportbe.fundraising;

import sportbe.util.sql.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title:        ViewBaseObject<br>
 * Description:  base object<br>
 * <li> this object contains the basic functions for all other output objects<br>
 * <li> it defines the database connections, main preparestatement and resultset<br>
 * <li> load an object or the next object in a list using the fetch() method <br>
 * calls the setData() method, which every derived object should implement.<br>
 * <li> the cleanUp() method is a general method that closes all database connections<br>
 * Copyright:    Copyright (c) 2001<br>
 * Company:      Planet Internet NV<br>
 *
 * @author Alain Caluwaerts
 * @version 1.0
 */

public abstract class ViewBaseObject {
    /**
     * "dd/MM/yy" -> used by date conversion routines
     */
    public static String defaultDateFormat = "dd/MM/yy";
    public static String SHAPassword = "homer";

    /**
     * stores database connection
     */
    protected DBConnection jdbc;  // handle to database connection

    /**
     * main resultset
     */
    protected ResultSet rsMain;   // main resultset used in all derived classes

    /**
     * main prepared statement
     */
    protected PreparedStatement psMain;   // main prepared statement used in all derived classes

    /**
     * stores current row index of main resultset. Accessable via getRowIndex();
     */
    protected int rowIndex;

    /**
     * stores current language. See constructor.
     */
    protected String language = "nl"; // default language

    // ***** CONSTRUCTORS *****

    /**
     * ViewBaseObject constructor. Connection with resource string.
     *
     * @param resourceName resourcename defined in the DB.xml file.
     * @throws java.sql.SQLException
     */
    public ViewBaseObject(String resourceName) throws SQLException {
        jdbc = new DBConnection(resourceName);
    }

    /**
     * ViewBaseObject constructor. Connection with database handle.
     *
     * @param jdbc database handle.
     */
    public ViewBaseObject(DBConnection jdbc) // throws SQLException
    {
        this.jdbc = jdbc;
    }

    /**
     * Fetch record from main resultset.<br>
     * Main resultset and prepared statement automatically closed when a SQLException occures.<br>
     * Main resultset and prepared statement automatically closed after fetching last record.<br>
     * Increment rowIndex<br>
     *
     * @return boolean       if fetch succeeded return true.
     * @throws java.sql.SQLException
     */
    public boolean fetch() throws SQLException {
        try {
            if (rsMain == null) return false;

            if (rsMain.next()) {
                rowIndex++;
                setData();
                return true;
            }

            // close resultset
            rsMain.close();
            rsMain = null;

            if (psMain == null) return false;
            // close prepared statement
            psMain.close(); // psMain = null;
            psMain = null;
            // if fetch fails / return false
            return false;
        } // end try
        catch (SQLException exception) {
            //	rsMain.close(); rsMain = null;
            psMain.close();
            psMain = null;
            throw exception;
        } // end catch
    }

    /**
     * Fetch record from main resultset. Limitation = maxRows<br>
     * Main resultset and prepared statement automatically closed when a SQLException occures.<br>
     * Main resultset and prepared statement automatically closed after fetching last record or Maxrows.<br>
     * Increment rowIndex<br>
     *
     * @param maxRows Do not fetch more then maxRows.
     * @return boolean       if fetch succeeded return true.
     * @throws java.sql.SQLException
     */
    public boolean fetch(int maxRows) throws SQLException {
//System.out.println(rowIndex);
        if ((maxRows <= 0) || (rowIndex >= maxRows)) {
            // close resultset
            if (rsMain != null) rsMain.close();
            rsMain = null;

            // close prepared statement
            if (psMain != null) psMain.close();
            psMain = null;
            return false;
        }

        return this.fetch();
    }

    /**
     * Reset : set cursor back to first record
     *
     * @throws java.sql.SQLException
     */
    public void reset() throws SQLException {
        try {
            rsMain.close();
            rsMain = null;     // free resources
            rsMain = psMain.executeQuery();
        } catch (SQLException exception) {
            psMain.close();
            throw exception;
        }
    }

    /**
     * UTILITY Function :<BR>
     * Function to convert a SQL date to a string with given format<br>
     * If param date = null -> return ""<br>
     * If format equals "" -> return date in defaultDateFormat (@see : Classmembers)<br>
     *
     * @param date   Date object to convert.
     * @param format Format string used to convert Date object.
     * @return String     String representation of date object.
     */
    public static String DateToString(Date date, String format) {
        if (date == null) return "";
        if (format.equals("")) format = defaultDateFormat;
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    // getters
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Clean up resources<br>
     * Close main resultset<br>
     * Close main prepared statement<br>
     * Close database connection<br>
     *
     * @throws java.sql.SQLException
     */
    public void cleanUp() throws SQLException {
        if (rsMain != null) {
            rsMain.close();
            rsMain = null;
        }
        if (psMain != null) {
            psMain.close();
            psMain = null;
        }

        jdbc.doClose();
        jdbc = null;
        System.out.println("ViewBaseObject.cleanUp()");
    }


    /**
     * pure virtual function called by fetch() and fetch(int maxRows)<br>
     * Normally used to fill member content of derived classes.
     *
     * @throws java.sql.SQLException
     */
    protected abstract void setData() throws SQLException;
}