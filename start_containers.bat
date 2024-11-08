@echo off
echo Starting Docker containers...
docker-compose up -d

if %errorlevel% equ 0 (
    echo Containers started successfully!
) else (
    echo Failed to start containers.
    exit /b 1
)
pause
