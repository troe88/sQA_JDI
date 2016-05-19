import com.epam.web.matcher.junit.Assert;
import com.epam.web.matcher.testng.Check;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.commons.LinqUtils.select;
import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static enums.Preconditions.HOME_PAGE;
import static site.EpamSite.jobListingPage;


/**
 * Created by Iuliia_Petrova on 4/15/2016.
 */
public class TableTests extends TestsBase {
    @BeforeMethod
    public void before(Method method) throws IOException {
        isInState(HOME_PAGE, method);
    }

    @DataProvider(name = "jobFeatures")
    public Object[][] jobFeatures() {
        return new Object[][]{
                {"Software Engineering", "Australia"},
                {"Software Architecture", "Belarus"},
                {"Sales, Marketing & PR", "Sweden"},
                {"Maintenance & Support", "Ukraine"},
                {"Consulting & Business Analysis", "France"}
        };
    }

    // This procedure filters Job List by Job Category and Job Location (using data from DataProvider "jobFeatures") and
    // then verify if each row of the result table contains (in corresponding columns) the expected Job Category and Job Location names
    @Test(dataProvider = "jobFeatures")
    public void CheckJobCategoryLocationColumns(String jobCategory, String jobLocation) {
        jobListingPage.open();
        jobListingPage.checkOpened();
        jobListingPage.categories.select(jobCategory);
        jobListingPage.location.select(jobLocation);
        jobListingPage.location.select("All Cities in " + jobLocation);
          new Check("Table is not empty").isFalse(jobListingPage.jobsList::isEmpty);

        List<String> row = select(jobListingPage.jobsList.column(3), el -> el.value.getText());
        Assert.eachElementOf(row.toArray()).matches(".*" + jobLocation + ".*", "Not found!");
        row = select(jobListingPage.jobsList.column(2), el -> el.value.getText());
        Assert.eachElementOf(row.toArray()).matches(".*" + jobCategory + ".*", "Nor found!");

    }
}
