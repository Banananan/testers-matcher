# testers-matcher

# Configuration
  is kept in data.sql file stored in `src\main\resources`. It's value is read on each application startup. Hibernate's schema model is recreated and data reinserted each time.
# To run backend
  application use command line and enter:
- `./gradlew clean build` command to compile code;
- `java -jar build/libs/testersmatcher-1.0-SNAPSHOT.jar` to start.
# To run frontend
  application use command line and enter:
- `cd src/main/js/tester-matcher-app/` to go to Angular's app source code;
- `ng serve --open` to start.
# Result
  can be displayed in webpage:
- go to browser and enter `http://localhost:4200/` in address bar.
  or in JSON form:
- go to brower and enter `http://localhost:8080/match?countries=US&devices=iPhone%204,iPhone%205` (sample request) in address bar.
# Examples
- to search for testers from US with iPhone 4 device:
  - enter `US` in `Country` field and `iPhone 4` in `Device` field and tap Search;
  - send request `http://localhost:8080/match?countries=US&devices=iPhone%204`;
  - result will be`{"Taybin Rutkin":66,"Miguel Bautista":23}`.
- to search for testers from US and GB with iPhone 4 and iPhone 5 devices:
  - enter `US,GB` in `Country` field and `iPhone 4,iPhone 5` in `Device` field; and tap Search;
  - send request `http://localhost:8080/match?countries=US,GB&devices=iPhone%204,iPhone%205`;
  - result will be `{"Taybin Rutkin":66,"Stanley Chen":110,"Miguel Bautista":53,"Leonard Sutton":32}`.
- to search for testers from everywhere with any kind of device:
  - enter `ALL` in `Country` field and `ALL` in `Device` field and tap Search;
  - send request `http://localhost:8080/match?countries=ALL&devices=ALL`;
  - result will be `{"Sean Wellington":116,"Taybin Rutkin":125,"Stanley Chen":110,"Mingquan Zheng":109,"Michael Lubavin":99,"Miguel Bautista":114,"Darshini Thiagarajan":104,"Leonard Sutton":106,"Lucas Lowry":117}`.
