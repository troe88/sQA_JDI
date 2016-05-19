import dataProviders.CustomerProvider;
import entities.Customer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import site.EpamSite;
import site.pages.RequestInfoPage;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static enums.Preconditions.HOME_PAGE;
import static site.EpamSite.requestInfoPage;

/**
 * Created by Iuliia_Petrova on 4/12/2016.
 */
public class RequestInfoPageTests extends TestsBase{

    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(HOME_PAGE, method);
    }

    @Test(dataProvider = "customers", dataProviderClass = CustomerProvider.class)
    public void sendFeedbackTest(Customer customer) {

        requestInfoPage.open();
        requestInfoPage.checkOpened();
        requestInfoPage.registrationForm.submit(customer);
        requestInfoPage.registrationForm.verify(customer);
    }

}
