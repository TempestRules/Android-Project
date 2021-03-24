FROM postgres

# Set user
ENV POSTGRES_USER=root

# Set password
ENV POSTGRES_PASSWORD=secure

# Adding sql script to the image
# All scripts in the following directory are automatically executed.
COPY ./sql-files/ /docker-entrypoint-initdb.d/

# Create/build the image
# - docker build -t database .

# Run it with the following command
# - docker run -p 5432:5432 database
# Add -d if you want it to run in the background

# If you wanna see the database remember to enter it after the container has started.
# - docker exec -it CONTAINERNAME/ID bash