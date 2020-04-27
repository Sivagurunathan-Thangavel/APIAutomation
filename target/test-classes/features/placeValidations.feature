Feature: Validating Place API's

@AddPlace
Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<address>" "<language>"
    When user calls "AddPlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And Verify placeId created maps to "<name>" using "GetPlaceAPI"
    
    
Examples:
    
    |name     |address |language  |
    |Sivaguru |Karur   |Tamil     |  
#   |Pradeep  |Salem   |Hindi     |
#   |Jithin   |Palakkad|Malayalam |

@DeletePlace
Scenario: Verify if Delete place functionality is working fine
    Given DeletePlace Payload
    When user calls "DeletePlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"