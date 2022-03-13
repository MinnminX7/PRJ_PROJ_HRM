package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\n");
      out.write("        <title>Log in</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <style>\n");
      out.write("            h1 {\n");
      out.write("                font-family: \"Segoe UI\", sans-serif;\n");
      out.write("            }\n");
      out.write("            label {\n");
      out.write("                font-size: 20px;\n");
      out.write("                font-family: \"Noto Sans\", monospace;\n");
      out.write("            }\n");
      out.write("            .custom-text {\n");
      out.write("                font-family: \"Noto Sans\", monospace;\n");
      out.write("            }\n");
      out.write("            .custom-width {\n");
      out.write("                max-width: 400px;\n");
      out.write("                margin: auto;\n");
      out.write("            }\n");
      out.write("            .custom-width2 {\n");
      out.write("                max-width: 360px;\n");
      out.write("                margin: auto;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("        <div class=\"container\">\n");
      out.write("            <h1 class=\"display-1\" align=\"center\">Log in</h1>\n");
      out.write("            ");

                String error = (String)request.getAttribute("error");
                if (error != null) {
                    out.println("<div class=\"alert alert-danger custom-text\" role=\"alert\">" + error + "</div>");
                }
            
      out.write("\n");
      out.write("            <form action=\"MainPage\">\n");
      out.write("                <div class=\"row mt-1 custom-text custom-width\">\n");
      out.write("                    <label for=\"usernameInput\" class=\"form-label\">Username / Email:</label>\n");
      out.write("                    <input type=\"text\" class=\"form-control\" id=\"usernameInput\" name=\"username\">\n");
      out.write("                </div>\n");
      out.write("                <div class=\"row mt-1 custom-text custom-width\">\n");
      out.write("                    <label for=\"passwordInput\" class=\"form-label\">Password:</label>\n");
      out.write("                    <input type=\"password\" class=\"form-control\" id=\"passwordInput\" name=\"password\">\n");
      out.write("                </div>\n");
      out.write("                <div class=\"row mt-3 custom-width2\">\n");
      out.write("                    <input class=\"btn btn-primary custom-text\" type=\"submit\" value=\"Login\">\n");
      out.write("                </div>\n");
      out.write("            </form>\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
