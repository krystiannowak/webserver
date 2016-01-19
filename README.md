# Web Server

An example of a simple web server in Java.

Run the webserver with Docker
-----------------------------

To build the webserver Docker image execute:

    mvn clean install docker:build

and then to run the webserver within the Docker container (serving on port 31337):

	docker run -p 31337:31337 -it --rm webserver
