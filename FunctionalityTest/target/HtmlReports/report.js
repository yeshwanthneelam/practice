$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/Features/Functionality.feature");
formatter.feature({
  "name": "Testing the functionality of Demo Web Shop",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "To validate complete flow of Requesting access for Multiple Drop Down Application along with Approval and Deletion.",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "Login in to demowebshop with \"valid\" user",
  "keyword": "Given "
});
formatter.match({
  "location": "StepDefinitions.TotalApplication.login_in_to_demowebshop_with_user(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Clear cart and shop for books and add quantity more than 1",
  "keyword": "Then "
});
formatter.match({
  "location": "StepDefinitions.TotalApplication.shop_for_books_and_add_quantity_more_than(java.lang.Integer)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Check whether the item got added in the cart and validate the price then checkout",
  "keyword": "Then "
});
formatter.match({
  "location": "StepDefinitions.TotalApplication.check_whether_the_item_got_added_in_the_cart_and_validate_the_price_then_checkout()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Fill all the manditory fields in the check out tab and make the order confirm",
  "keyword": "Then "
});
formatter.match({
  "location": "StepDefinitions.TotalApplication.fill_all_the_manditory_fields_in_the_check_out_tab_and_make_the_order_confirm()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Logout the application",
  "keyword": "Then "
});
formatter.match({
  "location": "StepDefinitions.TotalApplication.logout_the_application()"
});
formatter.result({
  "status": "passed"
});
});