# Use a stable version of Node.js as the base image for the frontend
FROM node:18

# Set working directory
WORKDIR /app

# Copy the frontend project files to the container
COPY . /app

# Install dependencies
RUN npm install

# Build the frontend for production
RUN npm run build

# Use a minimal web server to serve the frontend files
RUN npm install -g serve

# Expose port 3000 for the frontend
EXPOSE 3000

# Start the frontend server in offline mode
CMD ["serve", "-s", "build"]
