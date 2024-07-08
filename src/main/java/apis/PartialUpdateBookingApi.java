package apis;

import http.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.request.CreateBookingRequest;

import java.util.Map;

import static constants.ApiPaths.PARTIAL_UPDATE_BOOKING;

public class PartialUpdateBookingApi extends BaseApi {

    public PartialUpdateBookingApi() {
        super();
        super.logAllRequestData().logAllResponseData();
        super.setContentType(ContentType.JSON);
    }


    // request payload via map
    public Response partialUpdateBooking(Map<String,Object> partialUpdateBookingPayload,int bookingId,String username,String password){
        return partialUpdateBookingApiResponse(partialUpdateBookingPayload,bookingId,username,password);
    }



    // request payload via  POJO
    public Response partialUpdateBooking(CreateBookingRequest createBookingRequest,int bookingId,String username,String password){
        return partialUpdateBookingApiResponse(createBookingRequest,bookingId,username,password);
    }

    private Response partialUpdateBookingApiResponse(Object createBookingPayload,int bookingId,
                                              String username,String password) {
        super.setBasePath(PARTIAL_UPDATE_BOOKING.getApiPath());
        super.setRequestBody(createBookingPayload);
        super.setPathParam("bookingId",bookingId);
        super.setBasicAuth(username,password);
        return super.sendRequest(PARTIAL_UPDATE_BOOKING.getHttpMethodType());
    }







}
