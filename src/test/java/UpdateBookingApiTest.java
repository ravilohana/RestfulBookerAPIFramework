import apis.CreateBookingApi;
import apis.GetBookingApi;
import apis.UpdateBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ApiRequestHelper;

import static org.hamcrest.Matchers.*;

public class UpdateBookingApiTest {


    private UpdateBookingApi updateBookingApi;
    private CreateBookingApi createBookingApi;
    private GetBookingApi getBookingApi;

    @BeforeClass

    public void initApi() {
        updateBookingApi = new UpdateBookingApi();
        createBookingApi = new CreateBookingApi();
        getBookingApi = new GetBookingApi();

    }

    @Test(description = "Create new booking and update same booking and validate status code")
    public void createNewBookingAndUpdate() {

        // create new booking
        var createNewBookingRequestPayload = ApiRequestHelper.createBookingPayload("James", "Mike", 888,
                true, "2024-06-06", "2024-07-07", "Some sacks in evening with tea");
        var createNewBookingResponse = this.createBookingApi.createNewBooking(createNewBookingRequestPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));

        int bookingID = createNewBookingResponse.extract().jsonPath().getInt("bookingid");

        String username = System.getenv("RESTFUL_BOOKER_USERNAME");
        String password = System.getenv("RESTFUL_BOOKER_PASSWORD");

        System.out.println(username);
        System.out.println(password);


        // update the same booking

        var updateBookingRequestPayload = ApiRequestHelper.createBookingPayload("Newman", "Hokes", 555,
                false, "2024-10-10", "2024-11-11", "Nothing Else");


        var updateBookingResponse = this.updateBookingApi.updateBooking(updateBookingRequestPayload, bookingID, username, password)
                .then().assertThat().statusCode(200);
    }


    @Test(description = "Update existing booking and validate status code")
    public void updateExistingBooking() {



        int bookingID = 389;

        var getBookingByIdResponse = this.getBookingApi.getBookingById(bookingID);


        System.out.println("Existing Booking Details: ");
        System.out.println(getBookingByIdResponse.prettyPrint());


        String username = System.getenv("RESTFUL_BOOKER_USERNAME");
        String password = System.getenv("RESTFUL_BOOKER_PASSWORD");

        System.out.println(username);
        System.out.println(password);


        // update the same booking

        var updateBookingRequestPayload = ApiRequestHelper.createBookingPayload("Newman", "Hokes", 555,
                false, "2024-10-10", "2024-11-11", "Nothing Else");


        var updateBookingResponse = this.updateBookingApi.updateBooking(updateBookingRequestPayload, bookingID, username, password)
                .then().assertThat().statusCode(200)
                .and().assertThat().body("firstname",is(equalTo("Newman")))
                .and().assertThat().body("lastname",is(equalTo("Hokes")))
                .and().assertThat().body("totalprice",is(equalTo(555)));







    }


}
