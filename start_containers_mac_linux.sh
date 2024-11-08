#!/bin/bash

echo "Starting Docker containers..."
docker-compose up -d

if [ $? -eq 0 ]; then
    echo "Containers started successfully!"
else
    echo "Failed to start containers."
    exit 1
fi
