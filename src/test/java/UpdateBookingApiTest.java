import apis.CreateBookingApi;
import apis.DeleteBookingApi;
import apis.GetBookingApi;
import apis.UpdateBookingApi;
import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ApiRequestHelper;
import util.TestDataHelper;

import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;

public class UpdateBookingApiTest {



    private final Faker faker = TestDataHelper.getFaker();



    @Test(description = "Create new booking and update same booking and validate status code", dataProvider = "bookingDataWithStream")
    public void createNewBookingAndUpdate(String firstname, String lastname, Long totalPrice, Boolean depositPaid, String additionalNeeds,
                                          String checkInDate, String checkOutDate) {


        CreateBookingApi createBookingApi = new CreateBookingApi();
        UpdateBookingApi updateBookingApi = new UpdateBookingApi();
        DeleteBookingApi deleteBookingApi = new DeleteBookingApi();

        // create new booking
        var createNewBookingRequestPayload = ApiRequestHelper.createBookingPayload(firstname, lastname, Math.toIntExact(totalPrice),
                depositPaid, checkInDate, checkOutDate, additionalNeeds);
        var createNewBookingResponse = createBookingApi.createNewBooking(createNewBookingRequestPayload)
                                                            .then().assertThat().statusCode(200)
                                                            .and().body("bookingid", is(not(equalTo(0))));

        int bookingID = createNewBookingResponse.extract().jsonPath().getInt("bookingid");

        String username = System.getenv("RESTFUL_BOOKER_USERNAME");
        String password = System.getenv("RESTFUL_BOOKER_PASSWORD");

        System.out.println(username);
        System.out.println(password);


        // update the same booking

        var updateBookingRequestPayload = ApiRequestHelper.createBookingPayload(this.faker.name().firstName().concat(" Update fn"),
                this.faker.name().lastName().concat(" Update ln"),
                Math.toIntExact(this.faker.number().randomNumber(3,true)),
                this.faker.bool().bool(),
                TestDataHelper.getFutureDate(10,DateTimeFormatter.ISO_DATE),
                TestDataHelper.getFutureDate(14,DateTimeFormatter.ISO_DATE),
                this.faker.food().dish().concat(" Update needs"));


        var updateBookingResponse = updateBookingApi.updateBooking(updateBookingRequestPayload, bookingID, username, password)
                                                         .then().assertThat().statusCode(200);


        // delete booking api

        var deleteBookingApiByID = deleteBookingApi.deleteBookingApiById(bookingID, username, password)
                                                   .then().assertThat().statusCode(201);
    }


    @Test(description = "Update existing booking and validate status code")
    public void updateExistingBooking() {

        UpdateBookingApi updateBookingApi = new UpdateBookingApi();
        GetBookingApi getBookingApi = new GetBookingApi();

        var getBookingIdsResponse = getBookingApi.getAllBookingIds()
                                                      .then().assertThat().statusCode(200);

        int bookingID = getBookingIdsResponse.extract().jsonPath().getInt("[0].bookingid");

        var getBookingByIdResponse = getBookingApi.getBookingById(bookingID);


        System.out.println("Existing Booking Details: ");
        System.out.println(getBookingByIdResponse.prettyPrint());


        String username = System.getenv("RESTFUL_BOOKER_USERNAME");
        String password = System.getenv("RESTFUL_BOOKER_PASSWORD");

        System.out.println(username);
        System.out.println(password);


        // update the same booking

        var updateBookingRequestPayload = ApiRequestHelper.createBookingPayload("Newman", "Hokes", 555,
                false, "2024-10-10", "2024-11-11", "Nothing Else");


        var updateBookingResponse = updateBookingApi.updateBooking(updateBookingRequestPayload, bookingID, username, password)
                                                         .then().assertThat().statusCode(200)
                                                         .and().assertThat().body("firstname", is(equalTo("Newman")))
                                                         .and().assertThat().body("lastname", is(equalTo("Hokes")))
                                                         .and().assertThat().body("totalprice", is(equalTo(555)));
    }


    // Data provider
    // With Stream
    @DataProvider(name = "bookingDataWithStream",parallel = true)
    public Object[][] bookingDataProviderWithStream() {
        var faker = this.faker;
        var name = faker.name();
        var dateFormatter = DateTimeFormatter.ISO_DATE;

        return IntStream.range(0, 5)
                        .mapToObj(i -> {

                            var numberOfPlusDays = TestDataHelper.getRandomInt(2);
                           return new Object[]{name.firstName(), name.lastName(), faker.number().randomNumber(3, true)
                                    , faker.bool().bool(), faker.food().dish(), TestDataHelper.getFutureDate(numberOfPlusDays, dateFormatter)
                                    , TestDataHelper.getFutureDate(numberOfPlusDays+4, dateFormatter)};
                        })
                        .toArray(Object[][]::new);
    }


}
