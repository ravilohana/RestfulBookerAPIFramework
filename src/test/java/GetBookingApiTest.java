import apis.GetBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetBookingApiTest {


    private GetBookingApi getBookingApi;

    @BeforeClass
    public void initApi(){
        getBookingApi = new GetBookingApi();
    }

    @Test(description = "Basic HTTP Status check got get booking ids API")
    public void validateStatusCodeForGetBookingIdsAPI(){
        var getBookingIdsResponse =  this.getBookingApi.getAllBookingIds()
                .then().assertThat().statusCode(200);
    }

    @Test(description = "Basic HTTP Status check got get booking ids API")
    public void validateStatusCodeForGetBookingByIdAPI(){
        var getBookingByIdResponse =  this.getBookingApi.getBookingById(20)
                .then().assertThat().statusCode(200);
    }
}
