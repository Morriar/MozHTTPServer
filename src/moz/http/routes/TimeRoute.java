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
package moz.http.routes;

import java.util.Date;
import moz.http.Request;
import moz.http.Server;

/**
 * A Route that responds with a HTML *Hello World*.
 */
public class TimeRoute extends UriMatchRoute {

    public TimeRoute(String uri) {
        super(uri);
    }

    @Override
    public void action(Server server, Request request) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Time machine!</h1>");
        html.append("<p>We are now the ");
        html.append(new Date());
        html.append(" .</p>");
        server.sendResponse(200, "OK", html.toString());
    }
}
