# Web Server

An example of a simple web server in Java.

Run the webserver with Docker
-----------------------------

To build the webserver Docker image execute:

    mvn clean install docker:build

and then to run the webserver within the Docker container:

	docker run -it --rm webserver
