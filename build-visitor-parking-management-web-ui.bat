@echo off
set visitorParkingBaseDir="F:\Dominic\Documents\Eigene Dateien\Programmierung\Java only\visitor-parking-management\visitor-parking\"
set visitorParkingUiBaseDir=%visitorParkingBaseDir%visitor-parking-web-ui\src\main\ui\
set webTargetPath=%visitorParkingBaseDir%visitor-parking-backend\src\main\resources\static\
set targetBuildOutput=%visitorParkingUiBaseDir%dist\

Rem actual build
cd %visitorParkingUiBaseDir%
call npm run build

Rem copy file back
if not exist %webTargetPath% mkdir %webTargetPath%
xcopy %targetBuildOutput% %webTargetPath% /y /s
RMDIR %targetBuildOutput% /S /Q