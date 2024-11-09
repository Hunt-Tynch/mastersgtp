#!/bin/bash

# Check if .env file exists
if [ ! -f .env ]; then
  echo ".env file not found! Please create a .env file with DB_USERNAME and DB_PASSWORD."
  exit 1
fi

# Start the application with docker-compose
echo "Starting application..."
docker-compose up -d || { echo "Failed to start application"; exit 1; }

echo "Application started successfully."
