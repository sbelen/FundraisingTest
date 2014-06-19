package sportbe.fundraising.containers;

/**
 * Created by stevenbelen on 04/12/13.
 */
public class EventUser {

    Event event ;


    int EventUser_ID;
    int Event_ID;
    String FirstName,LastName,Street,HouseNumber,PostalCode,City,Email;
    int Country_ID,Language_ID;
    String Password;
    String Facebook_ID;

    String GroupName,GroupDescription;
    int FundsToRaise,FundsRaised;

    public EventUser() {
    }

    public EventUser(Event event, int eventUser_ID, int event_ID, String firstName, String lastName, String street, String houseNumber, String postalCode, String city,  int country_ID, int language_ID,String email,String password, String facebook_ID, String groupName, String groupDescription, int fundsToRaise, int fundsRaised) {
        this.event = event;
        EventUser_ID = eventUser_ID;
        Event_ID = event_ID;
        FirstName = firstName;
        LastName = lastName;
        Street = street;
        HouseNumber = houseNumber;
        PostalCode = postalCode;
        City = city;
        Email = email;
        Country_ID = country_ID;
        Language_ID = language_ID;
        Password = password;
        Facebook_ID = facebook_ID;
        GroupName = groupName;
        GroupDescription = groupDescription;
        FundsToRaise = fundsToRaise;
        FundsRaised = fundsRaised;
    }

    public int getLanguage_ID() {
        return Language_ID;
    }

    public void setLanguage_ID(int language_ID) {
        Language_ID = language_ID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getEventUser_ID() {
        return EventUser_ID;
    }

    public void setEventUser_ID(int eventUser_ID) {
        EventUser_ID = eventUser_ID;
    }

    public int getEvent_ID() {
        return Event_ID;
    }

    public void setEvent_ID(int event_ID) {
        Event_ID = event_ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        HouseNumber = houseNumber;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFacebook_ID() {
        return Facebook_ID;
    }

    public void setFacebook_ID(String facebook_ID) {
        Facebook_ID = facebook_ID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupDescription() {
        return GroupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        GroupDescription = groupDescription;
    }

    public int getFundsToRaise() {
        return FundsToRaise;
    }

    public void setFundsToRaise(int fundsToRaise) {
        FundsToRaise = fundsToRaise;
    }

    public int getFundsRaised() {
        return FundsRaised;
    }

    public void setFundsRaised(int fundsRaised) {
        FundsRaised = fundsRaised;
    }
}
