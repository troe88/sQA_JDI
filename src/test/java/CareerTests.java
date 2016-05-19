import com.epam.web.matcher.testng.Check;
import dataProviders.AttendeeProvider;
import entities.Attendee;
import enums.HeaderMenu;
import enums.Preconditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import site.EpamSite;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;


public class CareerTests extends TestsBase {
    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(Preconditions.HOME_PAGE, method);
    }

    @Test(dataProvider = "attendees", dataProviderClass = AttendeeProvider.class)
    public void sendCVTest(Attendee attendee) {
        EpamSite.headerMenu.select(HeaderMenu.CAREERS);
        EpamSite.careerPage.checkOpened();
        EpamSite.careerPage.jobFilter.search(attendee.filter);

        EpamSite.jobListingPage.checkOpened();
        new Check("Table is not empty").isFalse(EpamSite.jobListingPage.jobsList::isEmpty);
        EpamSite.jobListingPage.getJobRowByName("Senior QA Automation Engineer");

        EpamSite.jobDescriptionPage.addCVForm.submit(attendee);
        new Check("Captcha").contains(() -> EpamSite.jobDescriptionPage.captcha.getAttribute("class"), "form-field-error");
    }
}
