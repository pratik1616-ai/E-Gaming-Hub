#!/bin/bash

# E-Gaming Hub Database Migration Script

set -e

echo "Starting database migration..."

# Check if PostgreSQL is running
if ! command -v psql &> /dev/null; then
    echo "Error: PostgreSQL client not found"
    exit 1
fi

# Create database if it doesn't exist
echo "Creating database if not exists..."
psql -U ${DB_USER} -h ${DB_HOST} -c "CREATE DATABASE ${DB_NAME};" 2>/dev/null || echo "Database already exists"

# Run schema migration
echo "Running schema migration..."
psql -U ${DB_USER} -h ${DB_HOST} -d ${DB_NAME} -f database/schema.sql

# Run seed data
echo "Inserting seed data..."
psql -U ${DB_USER} -h ${DB_HOST} -d ${DB_NAME} -f database/seed-data.sql

echo "Database migration completed successfully!"
