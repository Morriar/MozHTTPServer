package moz;

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
import moz.http.Server;
import moz.http.routes.*;

/**
 * Server usage example.
 */
public class MozHTTPServer {

    public static void main(String[] args) {
        Server server = new Server(8080);
        server.routes.add(new CalcRoute("/calc"));
        server.routes.add(new RequestRoute("/request"));
        server.routes.add(new TimeRoute("/time"));
        server.routes.add(new HelloRoute("/"));
        server.start();
    }

}
