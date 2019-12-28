# testers-matcher

# Configuration
  is kept in CSV files stored in `src\main\resources`. Test configuration is in `src\test\resources`.
# To run 
  application use command line and enter:
- `./gradlew distZip` command to compile code;
- `java -jar build/libs/testersmatcher-1.0-SNAPSHOT.jar "<country_code1,country_code2,...>" "<device_name1,device_name2,...>"` to start.
# Result
  will be displayed in command line.
# Examples
- to search for testers from US with iPhone 4 device:
  - `java -jar build/libs/testersmatcher-1.0-SNAPSHOT.jar US "iPhone 4"`
  - `{Taybin Rutkin=66, Miguel Bautista=23}`
- to search for testers from US and GB with iPhone 4 and iPhone 5 devices:
  - `java -jar build/libs/testersmatcher-1.0-SNAPSHOT.jar "US,GB" "iPhone 4,iPhone 5"`
  - `{Taybin Rutkin=66, Stanley Chen=110, Miguel Bautista=53, Leonard Sutton=32}`
- to search for testers from everywhere with any kind of device:
  - `java -jar build/libs/testersmatcher-1.0-SNAPSHOT.jar ALL ALL`
  - `{Sean Wellington=116, Taybin Rutkin=125, Stanley Chen=110, Mingquan Zheng=109, Michael Lubavin=99, Miguel Bautista=114, Darshini Thiagarajan=104, Lucas Lowry=117, Leonard Sutton=106}`
- invalid input arguments
  - `java -jar build/libs/testersmatcher-1.0-SNAPSHOT.jar`
  - `applause.testersmatcher.Application main
      SEVERE: Invalid arguments passed - must be two arguments: first - country name (GB, US or ALL for any); second - device name (iPhone 4, Galasy S3 or ALL for any)`
