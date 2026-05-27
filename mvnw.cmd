@echo off
setlocal enabledelayedexpansion

set MAVEN_VERSION=3.9.6
set M2_DIR=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%
set M2_HOME=%M2_DIR%\apache-maven-%MAVEN_VERSION%

if not exist "%M2_HOME%\bin\mvn.cmd" (
    echo [mvnw] Apache Maven %MAVEN_VERSION% nao encontrado. Fazendo download...
    if not exist "%M2_DIR%" mkdir "%M2_DIR%"
    powershell -Command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip', '%M2_DIR%\maven.zip') }"
    powershell -Command "Expand-Archive -Path '%M2_DIR%\maven.zip' -DestinationPath '%M2_DIR%' -Force"
    del "%M2_DIR%\maven.zip"
    echo [mvnw] Download concluido.
)

"%M2_HOME%\bin\mvn.cmd" %*
