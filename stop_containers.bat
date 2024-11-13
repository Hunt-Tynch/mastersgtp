@echo off
echo Stopping Docker containers...
docker-compose down

if %errorlevel% equ 0 (
    echo Containers stopped successfully!
) else (
    echo Failed to stop containers.
    exit /b 1
)

