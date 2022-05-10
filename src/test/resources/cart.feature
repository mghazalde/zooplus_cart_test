Feature: Shipping cart management

Scenario: Add product from https://www.zooplus.com/checkout/cartEmpty.htm 
Given user on https://www.zooplus.com/checkout/cartEmpty.htm page
When user click on add to cart button for any product from the products listed under "Need some inspiration? Check out our recommendations:"   
Then user should be routed to https://www.zooplus.com/checkout/overview
And user should see the selected product added to the cart

Scenario: Add product from the https://www.zooplus.com/checkout/overview
Given user on the https://www.zooplus.com/checkout/overview page  
And  user scroll down to the recommendation panel
When user click on add to cart button for any product from the products listed under "Other recommended products"  
Then user should see the selected product added to the cart with the previous product
And  user should see updates in sub-total and total amounts

Scenario: increase the quantity of any product in the shopping cart
Given user on the https://www.zooplus.com/checkout/overview page  
And user added products to the shopping cart
When user click on + button once to increase the quantity  
Then user should see the selected product quantity incremented by 1 
And  user should see updates in sub-total and total amounts

Scenario: decrease the quantity of any product in the shopping cart
Given user on the https://www.zooplus.com/checkout/overview page  
And user added products to the shopping cart
When user click on - button once to decrease the quantity  
Then user should see the selected product quantity decremented by 1 
And  user should see updates in sub-total and total amounts

Scenario: delete any product 
Given user on the https://www.zooplus.com/checkout/overview page  
And user added products to the shopping cart
When user click on delete button once  
Then user should not see the deleted product in the shopping cart 
And  user should see updates in sub-total and total amounts

  
And user enters the following details 
| First Name | Chitrali| 
| Last Name | Sharma| 
| Password | Inquiry@1234 | 
| Date | 17| | Month | 02| | Year | 1992 |  
And user clicks "register button"