#!/bin/bash

# This script is used to initialize a MongoDB database with a user and password.
# It reads the username and password from Docker secrets and creates a user with readWrite access to the "catchcare" database.

# It's needed because the MongoDB container is started with the --auth flag, which requires authentication, and the env
# MongoDB image variables only create root user with access to the admin database.
# If not executed, the microservices will not be able to connect to the MongoDB catchare database.

# Read the username and password from Docker secrets
MONGO_USER=$(cat /run/secrets/mongodb_user_username)
MONGO_PASSWORD=$(cat /run/secrets/mongodb_user_password)

# Run MongoDB commands for user and database creation
mongosh <<EOF
    use catchcare;
    db.createUser({
        user: "$MONGO_USER",
        pwd: "$MONGO_PASSWORD",
        roles: ["readWrite"],
    });
EOF

