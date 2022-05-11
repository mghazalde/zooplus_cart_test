# zooplus_cart_test
# Prerequisites
- JDK 1.8+
- MAVEN 3.8.4 +
- Chrome/Edge/Firefox 
# How To Use
1. Clone/Download the repository.
2. Go to the cloned project folder location.
3. The test will run on Chrome browser By default. To change browser to FIREFOX or EDGE change the BrowserName in applicationConfig.properties file located in src\test\resources 
4. Open cmd from the cloned the cloned project where the pom.xml exists.
5. Run (mvn test -Dtest="com.zooplus.cart.**") 
6. The tests will run, browser will start and testcases will be excuted as the below image.
![image](https://user-images.githubusercontent.com/102529622/167728586-0f56c32b-8f6b-4ecd-bd92-a033de6778df.png)

Browser will not be closed to allow you to check the final state after test.

#BDD Scenarios
- BDD Scenarios can be found in cart.feature file located in src\test\resources
- For BDD Scenarios with minimum order and shipping fees/free delivery condition changes regarding to the subtotal prices and shipping countries (boundary analysis should be applied for better coverage) 






