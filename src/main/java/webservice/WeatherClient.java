package webservice;

import hello.wsdl.Forecast;
import hello.wsdl.ForecastReturn;
import hello.wsdl.GetCityForecastByZIP;
import hello.wsdl.GetCityForecastByZIPResponse;
import hello.wsdl.Temp;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.text.SimpleDateFormat;
/**
 * Created by chokst on 3/5/15.
 */
public class WeatherClient extends WebServiceGatewaySupport {

    public GetCityForecastByZIPResponse getCityForecastByZip(String zipCode) throws Exception {
        GetCityForecastByZIP request = new GetCityForecastByZIP();
        request.setZIP(zipCode);

        System.out.println();
        System.out.println("Requesting forecast for " + zipCode);

        GetCityForecastByZIPResponse response = (GetCityForecastByZIPResponse) getWebServiceTemplate().marshalSendAndReceive(
                request,
                new SoapActionCallback("http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP")//soapAction from wsdl file for an operation 'GetCityForecastByZIP'
        );

        return response;
    }

    public void printResponse(GetCityForecastByZIPResponse response) {
        ForecastReturn forecastReturn = response.getGetCityForecastByZIPResult();

        if (forecastReturn.isSuccess()) {
            System.out.println();
            System.out.println("Forecast for " + forecastReturn.getCity() + ", "
                    + forecastReturn.getState());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (Forecast forecast : forecastReturn.getForecastResult().getForecast()) {
                System.out.print(format.format(forecast.getDate().toGregorianCalendar().getTime()));
                System.out.print(" ");
                System.out.print(forecast.getDesciption());
                System.out.print(" ");
                Temp temperature = forecast.getTemperatures();
                System.out.print(temperature.getMorningLow() + "\u00b0-"
                        + temperature.getDaytimeHigh() + "\u00b0 ");
                System.out.println();
            }
        } else {
            System.out.println("No forecast received");
        }
    }

}
