@echo off
setlocal enabledelayedexpansion
set APP_HOME=%~dp0
set PROPERTIES=%APP_HOME%gradle\wrapper\gradle-wrapper.properties
for /f "tokens=1,* delims==" %%A in ('findstr /b "distributionUrl=" "%PROPERTIES%"') do set DIST_URL=%%B
set "DIST_URL=%DIST_URL:\:=:%"
for %%F in ("%DIST_URL%") do set ZIP_NAME=%%~nF
set ZIP_NAME=%ZIP_NAME:-bin=%
set GRADLE_USER_HOME=%USERPROFILE%\.gradle
if not defined GRADLE_USER_HOME set GRADLE_USER_HOME=%USERPROFILE%\.gradle
set INSTALL_DIR=%GRADLE_USER_HOME%\manual-wrapper\%ZIP_NAME%
if not exist "%INSTALL_DIR%\bin\gradle.bat" (
  if not exist "%GRADLE_USER_HOME%\manual-wrapper" mkdir "%GRADLE_USER_HOME%\manual-wrapper"
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$u='%DIST_URL%'; $z=Join-Path $env:TEMP 'shopping-notepad-gradle.zip'; Invoke-WebRequest -Uri $u -OutFile $z; Expand-Archive -Path $z -DestinationPath '%GRADLE_USER_HOME%\manual-wrapper' -Force; Remove-Item $z"
  if errorlevel 1 exit /b %errorlevel%
)
call "%INSTALL_DIR%\bin\gradle.bat" -p "%APP_HOME%" %*
exit /b %errorlevel%
