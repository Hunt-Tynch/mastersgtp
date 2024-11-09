@echo off

echo Downloading docker images locally...
docker load -i ./images/mastersgtp-backend.tar
docker load -i ./images/mastersgtp-frontend.tar

if %errorlevel% equ 0 (
    echo Images loaded locally!
) else (
    echo Error loading images.
    exit /b 1
)

echo NEXT STEPS: Set your username and password in the .env file to match your username and password for MySql!!!
echo Then you can run the start script to launch your containers, and stop script to stop them.
pause