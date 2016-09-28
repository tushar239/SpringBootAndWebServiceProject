package webservice;

/**
 * Created by chokst on 3/5/15.
 */

import hello.wsdl.GetCityForecastByZIPResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class, args);
        {
            WeatherClient weatherClient = ctx.getBean(WeatherClient.class);

            String zipCode = "94304";
            if (args.length > 0) {
                zipCode = args[0];
            }
            GetCityForecastByZIPResponse response = weatherClient.getCityForecastByZip(zipCode);
            weatherClient.printResponse(response);
        }

    }

}