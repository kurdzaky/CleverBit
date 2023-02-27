Feature: Currency Conversion
  As a user
  1) I want to be able to specify source and target currency
  2) be able to specify number and obtain conversion amount.
  3) be able to enter the whole integer and decimal numeric amount
  4) User should be able to swap source and target currencies by clicking the ‘Invert Currencies’ button.
  Once the button is clicked on, the conversion is made.
  5) The conversion values presented for the amount specified (e.g. 10 USD = 8.90909 EUR) and the 1 unit values
  // should be mathematically correct. i.e. if 1 USD = 0.890909 EUR, then 10 USD should equate to 8.90909 EUR.
  6) Whenever user performs a conversion (or reverses it), the page URI should be updated to reflect the amount,
  source and target currency for the conversion.
  7)If users specify negative numeric values, an error message should be displayed but conversion happens anyway.
  8)If users specify non-numeric values, the system should give an error.
  9)Users should be able to access a conversion page directly by specifying the right query string parameters.

  Scenario: Specify source and target currencies, type integer and get result
    Given When I'm at Converter Page
    When I skip cookies
    And Select a source currency and assert it is selected
    And Select a target currency and assert it is selected
    And Type whole integer
    And Obtain result: conversion amount & conversion rate of a single unit for both currencies
    Then Mathematical assertion for the conversion


    Scenario: Swap source and target currencies and conversion happens
    Given When I'm at Converter Page
      When I skip cookies
      And Type whole integer
      And Obtain result: conversion amount & conversion rate of a single unit for both currencies
      Then I click swap currencies and conversion happens


      Scenario: Whenever user performs a conversion (or reverses it), the page URI should be updated to reflect the amount,
      source and target currency for the conversion.
        Given When I'm at Converter Page
        When I skip cookies
        And Type whole integer
        And Obtain result: conversion amount & conversion rate of a single unit for both currencies
        Then URI should be updated to reflect the amount, source and target currency for the conversion.

        Scenario: If users specify negative numeric values, an error message should be displayed but conversion happens anyway.
          Given When I'm at Converter Page
          When I skip cookies
          And Type whole integer
          And Obtain result: conversion amount & conversion rate of a single unit for both currencies
          Then Entered Negative number and error message should be displayed but conversion happens anyway.

Scenario: If users specify non-numeric values, the system should give an error.
  Given When I'm at Converter Page
  When I skip cookies
  And Type whole integer
  And Obtain result: conversion amount & conversion rate of a single unit for both currencies
  Then user specify non-numeric values, the system should give an error.

  Scenario: User should be able to enter decimal numeric amount
    Given When I'm at Converter Page
    When I skip cookies
    Then Decimal amount is typed and processed


  Scenario: Users should be able to access a conversion page directly by specifying the right query string parameters.
    Given Users is able to access a conversion page by specifying the right query string parameters