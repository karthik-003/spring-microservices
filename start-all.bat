
start /b java -jar -Dspring.profiles.active=local %~dp0product-composite-service\target\product-composite-service-0.0.1-SNAPSHOT.jar &
start /b java -jar -Dspring.profiles.active=local %~dp0product-service\target\product-service-0.0.1-SNAPSHOT.jar &
start /b java -jar -Dspring.profiles.active=local %~dp0review-service\target\review-service-0.0.1-SNAPSHOT.jar &
start /b java -jar -Dspring.profiles.active=local %~dp0recommendation-service\target\recommendation-service-0.0.1-SNAPSHOT.jar 
