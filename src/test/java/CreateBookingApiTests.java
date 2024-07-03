import apis.CreateBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ApiRequestHelper;


import static org.hamcrest.Matchers.*;

public class CreateBookingApiTests {


    private CreateBookingApi createBookingApi;


    @BeforeClass
    public void initApi() {
        createBookingApi = new CreateBookingApi();

    }


    @Test(description = "Create a new booking record (Using Map) and validate status code 200")
    public void createAndValidateStatusCode() {

        var createBookingPayload = ApiRequestHelper.createBookingPayload("Jack", "Smith",
                1500,true,"2024-03-04","2024-05-05","Nothing Else!");
        var createBookingApiResponse = this.createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));

    }

    @Test(description = "Create a new booking record (Using POJO's) and validate status code 200")
    public void createAndValidateStatusCodeViaPOJO() {

        var createBookingPayload = ApiRequestHelper.createBookingRequestPOJO("Stone", "Cold", 999,true,"2024-03-04","2024-05-05","Nothing Else!");
        var createBookingApiResponse = this.createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));

    }








}


