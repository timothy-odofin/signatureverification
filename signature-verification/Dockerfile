# Stage 1: Build Angular application
FROM node:16.15.0 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to container
COPY package*.json ./

# Install Node.js dependencies
RUN npm install

# Copy the entire Angular project to the container
COPY . .

# Build the Angular application
RUN npm run build

# Stage 2: Create a lightweight web server to host the built Angular application
FROM nginx:alpine

# Copy the built Angular app from the previous stage into the Nginx web server directory
COPY --from=build /app/dist/signature-verification /usr/share/nginx/html

# Expose the port that the Nginx web server will listen on (default: 80)
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
