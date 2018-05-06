/*
* Copyright 2004 The Apache Software Foundation
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

package compressionFilters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Very Simple test servlet to test compression filter
 * @author Amy Roh
 * @version $Revision: 267129 $, $Date: 2004-03-18 11:40:35 -0500 (Thu, 18 Mar 2004) $
 */

public class CompressionFilterTestServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        ServletOutputStream out = response.getOutputStream();
        response.setContentType("text/plain");

        Enumeration e = ((HttpServletRequest)request).getHeaders("Accept-Encoding");
        while (e.hasMoreElements()) {
            String name = (String)e.nextElement();
            out.println(name);
            if (name.indexOf("gzip") != -1) {
                out.println("gzip supported -- able to compress");
            }
            else {
                out.println("gzip not supported");
            }
        }


        out.println("Compression Filter Test Servlet");
        out.close();
    }

}

