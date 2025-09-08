@echo off
REM Name of your container
SET CONTAINER_NAME=order-service-db

REM MySQL credentials
SET MYSQL_ROOT_PASSWORD=74800106
SET MYSQL_DATABASE=orderDB

REM Check if container is running
docker ps -q -f name=%CONTAINER_NAME% >nul
IF ERRORLEVEL 1 (
    echo Container not running. Starting container...
    docker start %CONTAINER_NAME%
) ELSE (
    echo Container already running.
)

REM Execute the SQL command
docker exec -i %CONTAINER_NAME% mysql -u root -p%MYSQL_ROOT_PASSWORD% %MYSQL_DATABASE% -e "SET GLOBAL log_bin_trust_function_creators = 1;"

echo Done.
exit
