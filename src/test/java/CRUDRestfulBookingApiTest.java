import apis.CreateBookingApi;
import apis.DeleteBookingApi;
import apis.GetBookingApi;
import apis.UpdateBookingApi;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ApiRequestHelper;
import util.TestDataHelper;

import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;

public class CRUDRestfulBookingApiTest extends BaseTest {


    @Test(description = "CRUD Restful booker API , GET, POST,PUT,DELETE", dataProvider = "bookingDataWithForLoop")
    public void crudRestfulBookingApi(String firstname, String lastname, Long totalPrice, Boolean depositPaid, String additionalNeeds,
                                      String checkInDate, String checkOutDate) {


        GetBookingApi getBookingApi = new GetBookingApi();
        CreateBookingApi createBookingApi = new CreateBookingApi();
        UpdateBookingApi updateBookingApi = new UpdateBookingApi();
        DeleteBookingApi deleteBookingApi = new DeleteBookingApi();


        // create booking

        var createNewBookingRequestPayload = ApiRequestHelper.createBookingPayload(firstname, lastname, Math.toIntExact(totalPrice),
                depositPaid, checkInDate, checkOutDate, additionalNeeds);
        var createNewBookingResponse = createBookingApi.createNewBooking(createNewBookingRequestPayload)
                                                       .then().assertThat().statusCode(200)
                                                       .and().body("bookingid", is(not(equalTo(0))));

        int bookingID = createNewBookingResponse.extract().jsonPath().getInt("bookingid");


        // get booking

        var getBookingByIdResponse = getBookingApi.getBookingById(bookingID);

        // Retrieve this created booking data using booking ID
        this.validateRetrieveBookingDataFromGetApi(firstname, lastname, totalPrice, depositPaid, additionalNeeds, checkInDate, checkOutDate, getBookingByIdResponse);


        // update booking

        String username = System.getenv("RESTFUL_BOOKER_USERNAME");
        String password = System.getenv("RESTFUL_BOOKER_PASSWORD");

        String updatedFirstName = this.faker.name().firstName().concat(" Update fn");
        String updatedLastName = this.faker.name().lastName().concat(" Update ln");
        int updatedTotalPrice = Math.toIntExact(this.faker.number().randomNumber(3, true));
        Boolean updatedDepositPaid = this.faker.bool().bool();


        String updateCheckInData = TestDataHelper.getFutureDate(10, DateTimeFormatter.ISO_DATE);
        String updateCheckOutDate = TestDataHelper.getFutureDate(14, DateTimeFormatter.ISO_DATE);
        String updateAdditionalNeeds = this.faker.food().dish().concat(" Update needs");
        var updateBookingRequestPayload = ApiRequestHelper.createBookingPayload(
                updatedFirstName,
                updatedLastName,
                updatedTotalPrice,
                updatedDepositPaid,
                updateCheckInData,
                updateCheckOutDate,
                updateAdditionalNeeds);

        var updateBookingResponse = updateBookingApi.updateBooking(updateBookingRequestPayload, bookingID, username, password)
                                                    .then().assertThat().statusCode(200)
                                                    .and().body("firstname", is(equalTo(updatedFirstName)))
                                                    .and().body("lastname", is(equalTo(updatedLastName)))
                                                    .and().body("totalprice", is(equalTo(updatedTotalPrice)))
                                                    .and().body("depositpaid", is(equalTo(updatedDepositPaid)))
                                                    .and().body("additionalneeds", is(equalTo(updateAdditionalNeeds)))
                                                    .and().rootPath("bookingdates")
                                                    .and().body("checkin", is(equalTo(updateCheckInData)))
                                                    .and().body("checkout", is(equalTo(updateCheckOutDate)))
                                                    .and().detachRootPath("bookingdates");

        // delete booking

        var deleteBookingApiByID = deleteBookingApi.deleteBookingApiById(bookingID, username, password)
                                                   .then().assertThat().statusCode(201);

        // get the deleted booking id , and should have status code 404

        getBookingApi.getBookingById(bookingID).then().assertThat().statusCode(404);


    }

    private  void validateRetrieveBookingDataFromGetApi(String firstname, String lastname, Long totalPrice, Boolean depositPaid, String additionalNeeds, String checkInDate, String checkOutDate, Response getBookingByIdResponse) {
        getBookingByIdResponse
                .then().assertThat().statusCode(200)
                .and().body("firstname", is(equalTo(firstname)))
                .and().body("lastname", is(equalTo(lastname)))
                .and().body("totalprice", is(equalTo(Math.toIntExact(totalPrice))))
                .and().body("depositpaid", is(equalTo(depositPaid)))
                .and().body("additionalneeds", is(equalTo(additionalNeeds)))
                .and().rootPath("bookingdates")
                .and().body("checkin", is(equalTo(checkInDate)))
                .and().body("checkout", is(equalTo(checkOutDate)))
                .and().detachRootPath("bookingdates");
    }


}
