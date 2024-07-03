import apis.DeleteBookingApi;
import apis.GetBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class DeleteBookingApiTests {


    private DeleteBookingApi deleteBookingApi;
    private GetBookingApi getBookingApi;


    @BeforeClass
    public void initApi() {

        this.deleteBookingApi = new DeleteBookingApi();
        this.getBookingApi = new GetBookingApi();

    }


    @Test(description = "Delete the exiting booking")

    public void deleteBooking() {

        var getBookingIdsResponse = this.getBookingApi.getAllBookingIds()
                                                      .then().assertThat().statusCode(200);

        int bookingID = getBookingIdsResponse.extract().jsonPath().getInt("bookingid[0]");

        System.out.println(bookingID);

        var getBookingByIdResponse = this.getBookingApi.getBookingById(bookingID);


        System.out.println("Existing Booking Details: ");
        System.out.println(getBookingByIdResponse.prettyPrint());


        String username = System.getenv("RESTFUL_BOOKER_USERNAME");
        String password = System.getenv("RESTFUL_BOOKER_PASSWORD");

//        System.out.println(username);
//        System.out.println(password);

        var deleteBookingApiByID = deleteBookingApi.deleteBookingApiById(bookingID, username, password)
                                                   .then().assertThat().statusCode(201);


    }
}
