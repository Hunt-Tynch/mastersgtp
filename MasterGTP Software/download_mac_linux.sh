#!/bin/bash

# Load backend and frontend images
echo "Loading backend Docker image..."
docker load -i ./images/mastersgtp-backend.tar || { echo "Failed to load backend image"; exit 1; }

echo "Loading frontend Docker image..."
docker load -i ./images/mastersgtp-frontend.tar || { echo "Failed to load frontend image"; exit 1; }

echo "Docker images loaded successfully."
