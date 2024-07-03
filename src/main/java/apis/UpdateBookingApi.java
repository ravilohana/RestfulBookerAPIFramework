package apis;

import http.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.request.CreateBookingRequest;

import java.util.Map;

import static constants.ApiPaths.*;


public class UpdateBookingApi extends BaseApi {

    public UpdateBookingApi() {

        super();
        super.logAllRequestData().logAllResponseData();
        super.setContentType(ContentType.JSON);
    }


    public Response updateBooking(Map<String,Object> createBookingPayload,int bookingId,String username,String password){
        return updateBookingApiResponse(createBookingPayload,bookingId,username,password);
    }




    public Response updateBooking(CreateBookingRequest createBookingRequest,int bookingId,String username,String password){
        return updateBookingApiResponse(createBookingRequest,bookingId,username,password);
    }

    private Response updateBookingApiResponse(Object createBookingPayload,int bookingId,
                                              String username,String password) {
        super.setBasePath(UPDATE_BOOKING.getApiPath());
        super.setRequestBody(createBookingPayload);
        super.setPathParam("bookingId",bookingId);
        super.setBasicAuth(username,password);
        return super.sendRequest(UPDATE_BOOKING.getHttpMethodType());
    }


}
