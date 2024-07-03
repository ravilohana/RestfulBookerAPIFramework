import apis.CreateBookingApi;
import apis.DeleteBookingApi;
import apis.GetBookingApi;
import apis.UpdateBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ApiRequestHelper;

import static org.hamcrest.Matchers.*;

public class EndToEndFlowRestfulBookingApiTest {


    private GetBookingApi getBookingApi;
    private CreateBookingApi createBookingApi;
    private UpdateBookingApi updateBookingApi;
    private DeleteBookingApi deleteBookingApi;


    @BeforeClass
    public void initApi() {

        getBookingApi = new GetBookingApi();
        createBookingApi = new CreateBookingApi();
        updateBookingApi = new UpdateBookingApi();
        deleteBookingApi = new DeleteBookingApi();

    }

    @Test
    public void endToEndFlowRestfulBookingApi() {

        // create booking

        var createNewBookingRequestPayload = ApiRequestHelper.createBookingPayload("James", "Mike", 888,
                true, "2024-06-06", "2024-07-07", "Some sacks in evening with tea");
        var createNewBookingResponse = this.createBookingApi.createNewBooking(createNewBookingRequestPayload)
                                                            .then().assertThat().statusCode(200)
                                                            .and().body("bookingid", is(not(equalTo(0))));

        int bookingID = createNewBookingResponse.extract().jsonPath().getInt("bookingid");


        // get booking

        var getBookingByIdResponse = this.getBookingApi.getBookingById(bookingID)
                                                       .then().assertThat().statusCode(200);

        // update booking

        String username = System.getenv("RESTFUL_BOOKER_USERNAME");
        String password = System.getenv("RESTFUL_BOOKER_PASSWORD");

        var updateBookingRequestPayload = ApiRequestHelper.createBookingPayload("Newman", "Hokes", 555,
                false, "2024-10-10", "2024-11-11", "Nothing Else");

        var updateBookingResponse = this.updateBookingApi.updateBooking(updateBookingRequestPayload, bookingID, username, password)
                                                         .then().assertThat().statusCode(200);

        // delete booking

        var deleteBookingApiByID = deleteBookingApi.deleteBookingApiById(bookingID, username, password)
                                                   .then().assertThat().statusCode(201);


    }


}
