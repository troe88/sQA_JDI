package dataProviders;

import entities.Attendee;
import org.testng.annotations.DataProvider;


/**
 * Created by Roman_Iovlev on 9/2/2015.
 */
public final class AttendeeProvider {
    private AttendeeProvider() { }

    @DataProvider(name = "attendees")
    public static Object[][] attendees() {
        return new Object[][]{
                {new Attendee()}
        };
    }
}
