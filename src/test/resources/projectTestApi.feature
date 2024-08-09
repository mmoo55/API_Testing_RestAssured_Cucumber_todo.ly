Feature: Project API
  Scenario: CRUD Project

    Given I have access todo.ly
    When I send GET /api/authentication/token.json with body
    """
    """
    Then response code should be 200
    And save "TokenString" in the variable "authToken"
    And the attribute string "TokenString" should be "Token"

    # CREATE
    When I send POST /api/items.json with body
    """
    {
      "Content":"New Item Gerson"
    }
    """
    Then response code should be 200
    And the attribute string "Content" should be "New Item Gerson"
    And save "Id" in the variable "Id_Item"

    # UPDATE
    When I send PUT /api/items/Id_Item.json with body
    """
    {
      "Content":"Item Update",
    }
    """
    Then response code should be 200
    And the attribute string "Content" should be "Item Update"

    # READ
    When I send GET /api/items/Id_Item.json with body
    """
    """
    Then response code should be 200
    And the attribute string "Content" should be "Item Update"

    # DELETE
    When I send DELETE /api/items/Id_Item.json with body
    """
    """
    Then response code should be 200
    And the attribute string "Content" should be "Item Update"
    And the attribute boolean "Deleted" should be "true"

