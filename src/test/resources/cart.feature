Feature: Shopping cart management

Scenario: Add a product from https://www.zooplus.com/checkout/cartEmpty.htm to be routed to https://www.zooplus.com/checkout/overview 
Given user on https://www.zooplus.com/checkout/cartEmpty.htm page
When user click on add to cart button for any product from the products listed under "Need some inspiration? Check out our recommendations:"   
Then user should be routed to https://www.zooplus.com/checkout/overview
And user should see the selected product added to the cart

Scenario: Add a product from the https://www.zooplus.com/checkout/overview
Given user on the https://www.zooplus.com/checkout/overview page  
And  user scroll down to the recommendation panel
When user click on add to cart button for any product from the products listed under "Other recommended products"  
Then user should see the selected product added to the cart with the previous product
And  user should see updates in sub-total and total amounts

Scenario: Increase the quantity of any product in the shopping cart
Given user on the https://www.zooplus.com/checkout/overview page  
And  user add a product to the shopping cart
When user click on + button once to increase the quantity  
Then user should see the selected product quantity incremented by 1 
And  user should see updates in sub-total and total amounts

Scenario: Decrease the quantity of any product in the shopping cart
Given user on the https://www.zooplus.com/checkout/overview page  
And user add a product to the shopping cart
And user click on + button once to increase the quantity
Then user should see the selected product quantity incremented by 1 
When user click on - button once to decrease the quantity  
Then user should see the selected product quantity decremented by 1 
And  user should see updates in sub-total and total amounts

Scenario: Delete any product by click on delete image
Given user on the https://www.zooplus.com/checkout/overview page  
And user add a product to the shopping cart
And user click on + button twice to increase the quantity
Then user should see the selected product quantity incremented by 2 
And user should see updates in sub-total and total amounts
When user click on delete image once  
Then user should not see the deleted product in the shopping cart 
And  user should see the message in the lower middle part of the page with The item was successfully removed.
And user should see updates in sub-total and total amounts

Scenario: Delete any product that its quantity in the shopping cart is 1 by reducing the quantity to 0
Given user on the https://www.zooplus.com/checkout/overview page  
And  user add products to the shopping cart
And  user should see updates in sub-total and total amounts
When user user click on - button once
And  user should see that quantity is changed to 0 
Then user should not see that the product in the shopping cart
And  user should see the message in the lower middle part of the page with The item was successfully removed.
And  user should see updates in sub-total and total amounts

Scenario: Verify maximum order quantity for one of the products
Given user on the https://www.zooplus.com/checkout/overview page  
And   user add product to the shopping cart
When  user click on + repeatedly till the quantity of the product is not incremented
Then  user should see the message in the lower middle part of the page with You have exceeded the maximum order quantity of x for one of the items in your basket. Please review your order

Scenario: Verify that minimum order value 19.00€ without shipping costs 
Given user on the https://www.zooplus.com/checkout/overview page  
Then user decrement or delete products from shopping cart 
When the total order price without shipping cost is below 19.00€ 
Then user should see warning message in top of shopping cart products and the Total price deleted product in the shopping cart 
And  user should see that proceed to checkout button is not click able

Scenario: Verify the shipping fees for Estonia   
Given user on the https://www.zooplus.com/checkout/overview page
And default Delivery to Country is Estonia
And user add products to the shopping cart with total prices below €69.90 
Then user should see that shipping fees €4.99 under sub-total is exist  
And user should see that the Sub-total = Total + Shipping fees

Scenario: Verify the shipping fees for Ireland   
Given user on the https://www.zooplus.com/checkout/overview page
And default Delivery to Country is Ireland
And user add products to the shopping cart with total prices below €99.00 
Then user should see that shipping fees €6.99 under sub-total is exist  
And user should see that the Sub-total = Total + Shipping fees

Scenario: Verify Free delivery condition for Estonia  
Given user on the https://www.zooplus.com/checkout/overview page 
And default Delivery to Country is Estonia  
And user add products to the shopping cart with total prices above €69.90 
Then user should see that shipping fees under sub-total has been set to Free
And user should see that the Total = Sub-total 

Scenario: Verify Free delivery condition for Germany  
Given user on the https://www.zooplus.com/checkout/overview page 
And change Delivery to Country to Germany  
And user add products to the shopping cart with total prices above €49.00
Then user should see that shipping fees under sub-total has been set to Free
And user should see that the Total = Sub-total  

Scenario: verify Free delivery condition is not available for Sweden  
Given user on the https://www.zooplus.com/checkout/overview page 
And change Delivery to Country to Sweden  
And user add products to the shopping cart with total prices above €200
Then user should see that shipping fees €7.99 under sub-total is exist  
And user should see that the Sub-total = Total + Shipping fees

Scenario:Apply Coupon / Discount with valid Coupon
Given user on the https://www.zooplus.com/checkout/overview page
And user add products to the shopping cart 
And user click on Enter coupon code under Coupon / Discount    
And user enter valid coupon code with certain amount in Your Coupon Code field and click + 
Then user should see that the coupon redeemed amount has been reduced from the Total

Scenario:Verify redeem only 1 coupon per order
Given user on the https://www.zooplus.com/checkout/overview page
And user add products to the shopping cart 
And user click on Enter coupon code under Coupon / Discount    
And user enter valid coupon code with certain amount in Your Coupon Code field and click + 
And user enter another valid coupon code with certain amount in Your Coupon Code field and click +
Then user should see that the only first coupon redeemed amount only has been reduced from the Total

Scenario:Verify redeem the same valid coupon 2 times
Given user on the https://www.zooplus.com/checkout/overview page
And user add products to the shopping cart 
And user click on Enter coupon code under Coupon / Discount    
And user enter valid coupon code with certain amount in Your Coupon Code field and click + 
And user enter the same coupon code with certain amount in Your Coupon Code field and click +
Then user should see that the only first coupon redeemed amount only has been reduced from the Total
And user should see a pop up message in the lower middle side of the page.

Scenario:Apply Coupon / Discount with invalid Coupon
Given user on the https://www.zooplus.com/checkout/overview page
And user add products to the shopping cart 
And user click on Enter coupon code under Coupon / Discount    
And user enter invalid coupon code in Your Coupon Code field and click + 
Then user should see that the coupon has not been redeemed and Total remain with no change
And user should see a pop up message in the lower middle side of the page.



  
