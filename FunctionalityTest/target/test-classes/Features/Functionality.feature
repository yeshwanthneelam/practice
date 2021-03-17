#Author: Yeshwanth Neelam
Feature: Testing the functionality of Demo Web Shop 
Scenario: To validate complete flow of Requesting access for Multiple Drop Down Application along with Approval and Deletion. 
  	
  	Given Login in to demowebshop with "valid" user
  	Then Clear cart and shop for books and add quantity more than 1
  	Then Check whether the item got added in the cart and validate the price then checkout
  	Then Fill all the manditory fields in the check out tab and make the order confirm
  	Then Logout the application
  	

	