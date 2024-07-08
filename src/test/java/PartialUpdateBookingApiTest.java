import apis.*;
import org.testng.annotations.Test;
import util.ApiRequestHelper;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class PartialUpdateBookingApiTest extends BaseTest {


    @Test(description = "Create new booking and partial update same booking and validate status code",dataProvider = "bookingDataWithForLoop")
    public void createNewBookingAndPartialUpdate(String firstname, String lastname, Long totalPrice, Boolean depositPaid, String additionalNeeds,
                                                 String checkInDate, String checkOutDate) {


        CreateBookingApi createBookingApi = new CreateBookingApi();
        PartialUpdateBookingApi partialUpdateBookingApi = new PartialUpdateBookingApi();


        // create new booking
        var createNewBookingRequestPayload = ApiRequestHelper.createBookingPayload(firstname, lastname, Math.toIntExact(totalPrice),
                depositPaid, checkInDate, checkOutDate, additionalNeeds);
        var createNewBookingResponse = createBookingApi.createNewBooking(createNewBookingRequestPayload)
                                                       .then().assertThat().statusCode(200)
                                                       .and().body("bookingid", is(not(equalTo(0))));

        int bookingID = createNewBookingResponse.extract().jsonPath().getInt("bookingid");

        String username = System.getenv("RESTFUL_BOOKER_USERNAME");
        String password = System.getenv("RESTFUL_BOOKER_PASSWORD");

        // partial update the same booking

        Map<String, Object> partialUpdateRequestPayload = new HashMap<>();

        partialUpdateRequestPayload.put("firstname", this.faker.name().firstName());
        partialUpdateRequestPayload.put("lastname", this.faker.name().lastName());

        var partialUpdateBookingRequestPayload = partialUpdateBookingApi.partialUpdateBooking(partialUpdateRequestPayload,
                bookingID, username, password);

        partialUpdateBookingRequestPayload.then().assertThat().statusCode(200)
                                          .and().assertThat().body("firstname",is(equalTo(partialUpdateRequestPayload.get("firstname"))))
                                          .and().assertThat().body("lastname",is(equalTo(partialUpdateRequestPayload.get("lastname"))));


    }


}
