package controllers.Examples;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(value = "/test")
public class Test extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        //Esta cabecera obtiene el metodo HTTP de la solicitud (GET,DELETE, etc...)
        String metodoHttp = req.getMethod();
        //Esta cabecera obtiene la URI de la solicitud
        String requestUri = req.getRequestURI();
        //Esta cabecera obtiene la URL completa de la solicitud
        String requestUrl = req.getRequestURL().toString();
        //Esta cabecera obtiene la ruta de contexto de la aplicacion web
        String contexPath = req.getContextPath();
        //Esta lunea obtiene la ruta del servlet de la solicitud anterior
        String servletPath = req.getServletPath();
        //Obtiene la direccion IP del cliente que realiza la solicitud
        String ipCliente = req.getRemoteAddr();
        //Obtiene la direccion Ip local del servidor
        String ip = req.getLocalAddr();
        //Obtiene el puerto local del servidor
        int port = req.getLocalPort();
        // Obtiene el esquema de la solicitud (http o https)
        String scheme = req.getScheme();
        //En estas 3 lineas se construye la URL completa del servidor
        String host = req.getHeader("host");
        String url = scheme + "://" + host + contexPath + servletPath;
        String url2 = scheme + "://" + ip + ":" + port + contexPath + servletPath;

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println(" <head>");
            out.println(" <meta charset=\"UTF-8\">");
            out.println(" <title>Cabeceras HTTP Request</title>");
            out.println(" </head>");
            out.println(" <body>");
            out.println(" <h1>Cabeceras HTTP Request!</h1>");
            out.println("<ul>");
            out.println("<li>metodo http: " + metodoHttp + "</li>");
            out.println("<li>request uri: " + requestUri + "</li>");
            out.println("<li>request url: " + requestUrl + "</li>");
            out.println("<li>context path: " + contexPath + "</li>");
            out.println("<li>servlet path: " + servletPath + "</li>");
            out.println("<li>ip local: " + ip + "</li>");
            out.println("<li>ip cliente: " + ipCliente + "</li>");
            out.println("<li>puerto local: " + port + "</li>");
            out.println("<li>scheme: " + scheme + "</li>");
            out.println("<li>host: " + host + "</li>");
            out.println("<li>url: " + url + "</li>");
            out.println("<li>url2: " + url2 + "</li>");
            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String cabecera = headerNames.nextElement();
                out.println("<li>"+ cabecera + ": " + req.getHeader(cabecera) + "</li>");
            }
            out.println("</ul>");
            out.println(" </body>");
            out.println("</html>");
        }
    }
}
