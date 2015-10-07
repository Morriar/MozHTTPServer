/*
 * Copyright 2015 Alexandre Terrasa <alexandre@moz-code.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package moz.http;

import moz.http.routes.Route;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A HTTP Server that contains routes and respond to request.
 */
public class Server {

    // Port to listen.
    public Integer port;

    // List of routes that can be executed.
    //
    // Because UriMatchRoute matches Request with `request.uri.startsWith(uri)`,
    // URI with the longest path should be registered first to avoid conflicts.
    // 
    // For example if you want to match / and /foo with two different routes,
    // you should register the route /foo before the route /.
    public List<Route> routes = new ArrayList<>();

    public Server(Integer port) {
        this.port = port;
    }

    private BufferedReader in;
    private PrintWriter out;

    // Start listening
    //
    // This will start an infinite listening loop and you have no power to stop
    // it.
    public void start() {
        System.out.println("Listening on port " + port + "...");
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                try (Socket socket = server.accept()) {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try {
                        // New request received
                        Request request = new Request(in);
                        // Try to find a route that matches the request
                        Boolean match = false;
                        for (Route route : routes) {
                            if (route.accept(request)) {
                                route.action(this, request);
                                match = true;
                                break;
                            }
                        }
                        // No route found, send 404
                        if (!match) {
                            sendError(404, "Resource not found");
                        }
                    } catch (Exception ex) {
                        System.out.println("Warning: malformed request");
                        System.out.println(ex);
                    }
                    in = null;
                    out = null;
                } catch (Exception ex) {
                    System.out.println("Warning: Cannot accept connection");
                    System.out.println(ex);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error: Cannot open server socket on port " + port);
            System.err.println(ex);
        }
    }

    // Send a HTTP response.
    public void sendResponse(Integer status, String reason, String body) {
        Response response = new Response("HTTP/1.1", status, reason, body);
        response.headers.put("Content-Type", "text/html");
        response.send(out);
    }

    // Send an HTTP response containing an error message formated with HTML.
    public void sendError(Integer status, String reason) {
        String body = "<h1>" + status + ": " + reason + "</h1>";
        sendResponse(status, reason, body);
    }
}
