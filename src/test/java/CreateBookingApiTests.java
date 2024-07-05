import apis.CreateBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ApiRequestHelper;
import util.TestDataHelper;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class CreateBookingApiTests {


    private CreateBookingApi createBookingApi;


    @BeforeClass
    public void initApi() {
        createBookingApi = new CreateBookingApi();

    }


    @Test(description = "Create a new booking record (Using Map) and validate status code 200",dataProvider = "bookingDataWithForLoop")
    public void createAndValidateStatusCode(String firstname, String lastname, Long totalPrice, Boolean depositPaid, String additionalNeeds,
                                            String checkInDate, String checkOutDate) {

        var createBookingPayload = ApiRequestHelper.createBookingPayload(firstname, lastname, Math.toIntExact(totalPrice),
                depositPaid, checkInDate, checkOutDate, additionalNeeds);
        var createBookingApiResponse = this.createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));

    }

    @Test(description = "Create a new booking record (Using POJO's) and validate status code 200",dataProvider = "bookingDataWithForLoop")
    public void createAndValidateStatusCodeViaPOJO(String firstname, String lastname, Long totalPrice, Boolean depositPaid, String additionalNeeds,
                                                   String checkInDate, String checkOutDate) {

        var createBookingPayload = ApiRequestHelper.createBookingRequestPOJO(firstname, lastname, Math.toIntExact(totalPrice),
                depositPaid, checkInDate, checkOutDate, additionalNeeds);
        var createBookingApiResponse = this.createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));

    }


    // Data provider
    // With for loop
    @DataProvider(name = "bookingDataWithForLoop")
    public Object[][] bookingDataProviderWithLoop(){
        var faker =  TestDataHelper.getFaker();
        var name = faker.name();
        var dateFormatter =  DateTimeFormatter.ISO_DATE;
        var numberOfPlusDays = TestDataHelper.getRandomInt(2);

        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Object[] objects = new Object[]{name.firstName(), name.lastName(), faker.number().randomNumber(3, true)
                    , faker.bool().bool(), faker.food().dish(), TestDataHelper.getFutureDate(numberOfPlusDays, dateFormatter)
                    , TestDataHelper.getFutureDate(numberOfPlusDays+4, dateFormatter)};
            list.add(objects);
        }
        return list.toArray(new Object[0][]);
    }








}


