@Echo Off
title Spell Check Utility By Naman Agrawal
SETLOCAL EnableDelayedExpansion
for /F "tokens=1,2 delims=#" %%a in ('"prompt #$H#$E# & echo on & for %%b in (1) do     rem"') do (
  set "DEL=%%a"
)
call :colorEcho 0e "------------------Spell Check Utility By Naman Agrawal------------------------"
echo.
call :colorEcho 0c "--- Checkes before Running the Utility ---"
echo.
call :colorEcho 0a "1- Make sure that all the URL's are listed in data_file.xls"
echo.
call :colorEcho 0a "2- Make sure that machine have the java 1.6 or above installed."
echo.
call :colorEcho 0a "3- Make sure that Spell Check Utility folder is placed in D Drive"
echo.
call :colorEcho 0a "4- Make sure that browser proxy is off and website is accessible"
:again
echo.
call :colorEcho 0d "Press Y to continue or Press X to exit-"
set /p CHECK=

IF /i "%check%"=="Y" goto checky
IF /i "%check%"=="X" goto checkx

call :colorEcho 0c "You have entered a wrong choice. Please try again"
goto again


:againbrowser
echo.
:checky
call :colorEcho 0d "To run Utility press C for chrome or press F for firefox browser-"
set /p browser=

IF /i "%browser%"=="F" goto runscriptfirfox
IF /i "%browser%"=="C" goto runscriptchrome

call :colorEcho 0c "You have entered a wrong browser. Please try again"
goto againbrowser

:checkx
goto directexit

:commonexit
pause
exit

:directexit
exit

:runscriptfirfox
call :colorEcho 0a "Utility started in Firefox browser"
echo.
D:
cd eclipse/neon/spellcheckutility
java -cp d:\eclipse\neon\spellcheckutility\lib\*;d:\eclipse\neon\spellcheckutility\bin -Dbname=firefoxfrombatch org.testng.TestNG testng.xml

:commanforbrowser
call :colorEcho 0a "Utility Execution Completed. Misspelt files are created in spell folder"
:againopen
echo.
call :colorEcho 0a "Press S to open the misspelt folder or press X to exit-"
set /p folder=

IF /i "%folder%"=="S" goto openspellfolder
IF /i "%folder%"=="X" goto directexitthanks

call :colorEcho 0c "You have entered a wrong choice. Please try again"
goto againopen

pause
goto directexit 


:runscriptchrome
call :colorEcho 0a "Utility started in Chrome browser"
echo.
D:
cd eclipse/neon/spellcheckutility
java -cp d:\eclipse\neon\spellcheckutility\lib\*;d:\eclipse\neon\spellcheckutility\bin -Dbname=chromefrombatch org.testng.TestNG testng.xml
goto commanforbrowser


:openspellfolder
cd spell
start .
goto openlogfolder

:againopenlog
echo.
:openlogfolder
call :colorEcho 0a "Press L to open the log folder or press X to exit-"
set /p logfolder=

IF /i "%logfolder%"=="L" goto openlogfolder
IF /i "%logfolder%"=="X" goto directexitthanks

call :colorEcho 0c "You have entered a wrong choice. Please try again"
goto againopenlog


:openlogfolder
cd..
cd logs
start .
goto directexitthanks

:directexitthanks
call :colorEcho 0c "Thank You for using Spell Check Utility."
echo.
call :colorEcho 0c "For any suggesion and feedback, kindly email us at "
call :colorEcho 0e " naman.agrawal@arcscorp.net
echo.
call :colorEcho 0c "Have a great day"
timeout 10
goto directexit

:colorEcho
echo off
<nul set /p ".=%DEL%" > "%~2"
findstr /v /a:%1 /R "^$" "%~2" nul
del "%~2" > nul 2>&1i