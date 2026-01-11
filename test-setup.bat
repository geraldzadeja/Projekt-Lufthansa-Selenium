@echo off
echo Testing Java and Maven Setup...
echo.

set JAVA_HOME=C:\Users\User\Downloads\java-1.8.0-openjdk-1.8.0.392-1.b08.redhat.windows.x86_64
set PATH=%JAVA_HOME%\bin;C:\Apache\apache-maven-3.9.12\bin;%PATH%

echo === Testing Java ===
java -version
echo.

echo === Testing Maven ===
mvn -version
echo.

echo === Compiling Project ===
mvn clean compile

pause
