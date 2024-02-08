
@tag
Feature: Google Maps Search

  @tag1
  Scenario: Valid Search for Dublin
    Given I am at Google Maps page
    When I write "Dublin" on Search bar and press Enter
    Then I should have "Dublin" on the title
    And I click on the directions icon
    And I should have "Dublin" has destination
