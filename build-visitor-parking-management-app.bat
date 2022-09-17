@echo off
Rem Aquabasilea konstants
set visitorParkingBackend=visitor-parking-backend
set visitorParkingJarName=%visitorParkingBackend%-1.0-SNAPSHOT.jar
set visitorParkingBaseDir="F:\Dominic\Documents\Eigene Dateien\Programmierung\Java only\visitor-parking-management\visitor-parking\"
set visitorParkingBuildOutputDir="%visitorParkingBaseDir%%visitorParkingBackend%\build\libs\%visitorParkingJarName%"
set GRADLE_OPTS=-Dfile.encoding=utf-8
set buildStartedAtPath=%cd%


Rem build web resources
echo "<==========================================>"
echo "<===    visitor-parking-web-ui   ====>"
echo "<==========================================>"
call build-visitor-parking-management-web-ui.bat

Rem build aquabasilea-kurs-bucher-rest-app
echo "<============================================>"
echo "<===  visitor-parking-management-backend  ===>"
echo "<============================================>"

cd %visitorParkingBaseDir%
cd %visitorParkingBackend%
call gradlew clean build -x test publishToMavenLocal
cd ..

Rem copy file back
cd %buildStartedAtPath%
cd..
xcopy "%visitorParkingBuildOutputDir%" /y /s
RMDIR %webTargetPath% /S /Q
pause