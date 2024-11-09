#!/bin/bash

# Stop the application with docker-compose
echo "Stopping application..."
docker-compose down || { echo "Failed to stop application"; exit 1; }

echo "Application stopped successfully."
