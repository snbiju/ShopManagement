# ShopManagement
Simple application for Shop Tracking
Please follow below step 
------------------------

Basic Software Required

1. Tomcat
2. Rest client in your Mozilla firefox browser
3. JDK


Build & Execute
---------------

Run Maven 
mvn clean,install
for running  Application
step
1. Go to cmd prompt
2. Change Directroy to ShopMangement/shop
3. mvn clean,install
4. mvn spring-boot:run

URL Access
-----------

save service
-------------
URL: http://localhost:8080/shop/save

Method: POST
POST
	Headers : Content-Type: application/json
Body : 
{
"number": 1001,
"shopName": "Shop1",
"postCode": "560029",
"longitude": 0,
"latitude": 0
}

Response Body (out put)
-------------
[
  {
    "number": 1001,
    "shopName": "Shop1",
    "postCode": "560029",
    "longitude": 77.6043993,
    "latitude": 12.9342565
  }
]


findshops service
-----------------
	
URL	: http://localhost:8080/shop/findshops?atitude=12.9342565&longitude=77.6043993&radius=25
 

 
Method 	: GET

Output
-----
[
  {
    "number": 1001,
    "shopName": "Shop1",
    "postCode": "560029",
    "longitude": 77.6043993,
    "latitude": 12.9342565
  }
]



viewallshops service
--------------------
URL: http://localhost:8080/shop/viewallshops
 
Method : GET

	

Output
------
[
  {
    "number": 1001,
    "shopName": "Shop1",
    "postCode": "560029",
    "longitude": 77.6043993,
    "latitude": 12.9342565
  },
  {
    "number": 1002,
    "shopName": "Shop2",
    "postCode": "560040",
    "longitude": 77.5871009,
    "latitude": 12.914528
  }
]
