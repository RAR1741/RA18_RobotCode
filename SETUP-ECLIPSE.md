# Setting up Eclipse
* Checkout the repository
* Open `cmd` and navigate to the repository
* Run `gradlew build` to download the libraries then `gradlew eclipse` to generate the Eclipse project files
* Open Eclipse
* Select `New > Project` then select "Java Project"
* Enter name as `RA18_RobotCode` and set the project location to the project's location
* Select `Run > External Tools > Exernal Tools Configurations...`
* Create a new program launch setting
* Name it `Gradle Build`, enter location as `${workspace_loc:/RA18_RobotCode/gradlew.bat}`, and working directory as `${workspace_loc:/RA18_RobotCode}`
* Enter `build` in arguments
* Duplicate the launch setting
* Rename the new launch setting to `Gradle Deploy 1741`
* Change the argument to `deploy -x test`
* Duplicate the launch setting
* Rename the new launch setting to `Gradle Deploy 1751`
* Change the argument to `deploy -x test -PTEAM=1751`
* Do the same thing to create any other team numbers that you want to deploy to, changing `-PTEAM=####`

