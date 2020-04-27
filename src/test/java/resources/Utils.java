package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	/*
	 * reqSpec is created as static because it wont create another instance and it
	 * is shared across all TC's for particular execution. If we are not using as
	 * static, for second execution it makes null and remaining all execution it is
	 * treated as null and remaining all TC's will not get executed.
	 * 
	 * if we are executing multiple data sets then it is necessary to use static, so
	 * that all multiple data sets gets executed without overriding
	 */ 
	public static RequestSpecification reqSpec;

	/**
	 * @impelSpec addFilter will applies to the object 'reqSpe' which will log
	 *            all.logRequestTo and logResponseTo tell where we need to log
	 *            request to either console or file or in o/p. This simplifies the
	 *            method 'log().all()' which allows you to log only in console
	 * @return reqSpec- Request which is generated
	 * @throws IOException
	 */
	public RequestSpecification requestSpecification() throws IOException {
		if (reqSpec == null) {
			PrintStream log = new PrintStream(new FileOutputStream("Logging.txt"));
			reqSpec = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseURL")).addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return reqSpec;
		}
		return reqSpec;
	}

	/**
	 * @impelSpec Below method is used to generalize the
	 *            URL:"https://rahulshettyacademy.com" because URL is generic and in
	 *            case if we need to run in different environment(i.e QA,Dev) this
	 *            will help us to parameterize.
	 * @param key - the 'baseURL' which we are getting from global.properties
	 * @return baseURL
	 * @throws IOException
	 */
	public String getGlobalValues(String key) throws IOException {
		// In Java there is class call properties. We can any file having extension
		// '.properties'
		Properties prop = new Properties();
		// Below is used to read the content from the file
		FileInputStream fis = new FileInputStream(
				"F:\\New folder\\APIAutomationBDD\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
		// We are returning 'baseURL' which is string
	}
	
	public String getJsonPath(Response response, String key) {
		String Response = response.asString();
		JsonPath js = new JsonPath(Response);
		return js.get(key).toString();
	}
}
