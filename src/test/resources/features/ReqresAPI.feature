@test
Feature: Simple API Testing for Reqres.in

  Scenario: User send GET for single user and validate response body
    Given User send "GET" to the API "single_user"
    Then the status code is 200
    And response content type is JSON
    Then validate schema json response "singleuserschema"
    Then the body response content should be matched integer
      | key     | value          |
      | data.id | 2              |
    Then the body response content should be matched string
      | key              | value                  |
      | data.email       | janet.weaver@reqres.in |
      | data.first_name  | Janet                  |
      | data.last_name   | Weaver                 |
    Then the body response content should be not null
      | key           |
      | data.avatar   |
      | support.url   |
      | support.text  |

  Scenario: User send POST for create user and validate response body
    Given User setup bodyuser has request body
     """
      {
        "name": "morpheus",
        "job": "testot"
      }
      """
    And User send "POST" to the API "create_user"
    Then the status code is 201
    And response content type is JSON
    Then validate schema json response "createuserschema"
    Then the body response content should be matched string
      | key    | value     |
      | name   | morpheus  |
      | job    | testot    |
    Then the body response content should be not null
      | key       |
      | id        |
      | createdAt |

  Scenario: User send PATCH for single user and validate response body
    Given User setup bodyuser has request body
    """
     {
      "name": "morpheus",
      "job": "zion resident"
     }
    """
    And User send "PATCH" to the API "single_user"
    Then the status code is 200
    And response content type is JSON
    Then validate schema json response "patchuserschema"
    Then the body response content should be matched string
      | key    | value            |
      | name   | morpheus         |
      | job    | zion resident    |
    Then the body response content should be not null
      | key       |
      | updatedAt |