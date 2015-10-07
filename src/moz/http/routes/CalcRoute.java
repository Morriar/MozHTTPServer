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
public class CalcRoute extends UriMatchRoute {

    public CalcRoute(String uri) {
        super(uri);
    }

    @Override
    public void action(Server server, Request request) {
        if(request.method.equals("POST")) {
            doCalc(server, request);
        } else {
            showForm(server, request);
        }
    }
    
    protected void showForm(Server server, Request request) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Calculator</h1>");
        html.append("<form method=\"post\" action=\"");
        html.append(uri);
        html.append("\">");
        html.append("<input type=\"text\" name=\"a\"/>");
        html.append("<select name=\"op\">");
        html.append("<option selected=\"selected\">+</option>");
        html.append("<option>-</option>");
        html.append("<option>*</option>");
        html.append("<option>/</option>");
        html.append("</select>");
        html.append("<input type=\"text\" name=\"b\"/>");
        html.append("<input type=\"submit\" value=\"=\"/>");
        html.append("</form>");
        server.sendResponse(200, "OK", html.toString());
    }
    
    protected void doCalc(Server server, Request request) {
        int a = 0;
        int b = 0;
        String op = "+";
        int res;
        switch(op) {
            case "+":
                res = a + b;
                break;
            case "-":
                res = a - b;
                break;
            case "*":
                res = a * b;
                break;
            case "/":
                res = a / b;
                break;
            default:
                server.sendError(400, "Unknown " + op + " operation");
                return;
        }
        
        StringBuilder html = new StringBuilder();
        html.append("<h1>Calculator</h1>");
        html.append(request);
        html.append("<p>");
        html.append(a);
        html.append(" ");
        html.append(op);
        html.append(" ");
        html.append(b);
        html.append(" = ");
        html.append(res);
        html.append("</p>");
        server.sendResponse(200, "OK", html.toString());
    }
}
