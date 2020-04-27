package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinition extends Utils {
	RequestSpecification req;
	ResponseSpecification resSpec;
	Response response;
	static String placeID;

	TestDataBuild Data = new TestDataBuild();

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String address, String language) throws IOException {
		req = given().spec(requestSpecification()).body(Data.addPlacePayLoad(name, address, language));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		/*
		 * when we are calling the method 'ValueOf' automatically the constructor of the
		 * enum gets executed (i.e) nothing but invoke constructor with value of
		 * 'resources' like deleteAPI, GetAPI, PostAPI
		 */

		// Creating the object of the APIResources class
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("Post"))
			response = req.when().post(resourceAPI.getResource());

		else if (method.equalsIgnoreCase("Get"))
			response = req.when().get(resourceAPI.getResource());
	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
		// Assert.assertTrue(int1.equals(response.getStatusCode()));

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExcepectedValue) {

		assertEquals(getJsonPath(response, keyValue), ExcepectedValue);
	}

	@Then("Verify placeId created maps to {string} using {string}")
	public void verify_placeId_created_maps_to_using(String expectedName, String resourcename) throws IOException {
		placeID = getJsonPath(response, "place_id");
		req = given().spec(requestSpecification()).queryParam("place_id", placeID);
		user_calls_with_http_request(resourcename, "Get");
		// verify the name matches
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
	}

	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
		req = given().spec(requestSpecification()).body(Data.deletePlacePayLoad(placeID));
	}
}
