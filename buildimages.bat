set DB_NAME=capitalone
set DB_USER=vista
set DB_PASS=vista
set SCHEMA=capitalone
set POSTGREDB_URL=jdbc:postgresql://192.168.99.100:5432

mvn clean package docker:build -Dmaven.test.skip=true