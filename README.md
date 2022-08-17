# performance
JMH performance tests for different Javalin versions

### Run benchmark
  Print result in console and create result csv under result directory
  -PjavalinVersion property can be any version of Javalin that already create in the external dir
  
  Run with default parameters
  ```sh
  ./gradlew -PjavalinVersion=3.0.0 benchmark
  ```
  Or
  ```sh
  ./gradlew benchmark -PjavalinVersion=3.0.0 -Piterations=20 -PiterationTime=10000
  ```
### Compare benchmark results
  Compare only run with two different version benchmark result that already created
  ```sh
  ./gradlew compare -Pbaseline=1.0.0 -PjavalinVersion=3.0.0 
  ```
