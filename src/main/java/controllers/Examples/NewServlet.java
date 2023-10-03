package controllers.Examples;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/tiem.html")
public class NewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // Esto indica que el servidor enviará una página HTML como respuesta.
        resp.setContentType("text/html;charset=UTF-8");
        //Establece una cabecera HTTP llamada "refresh" con un valor de "1".
        // Esto hará que la página se actualice automáticamente cada segundo en el navegador del cliente.
        resp.setHeader("refresh", "1");
        //Instancia de la clase LocalTime
        LocalTime hora = LocalTime.now();
        //Crea un objeto DateTimeFormatter para formatear la hora en el formato "hh:mm:ss"
        DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm:ss");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println(" <head>");
            out.println(" <meta charset=\"UTF-8\">");
            out.println(" <title>La hora actualizada</title>");
            out.println(" </head>");
            out.println(" <body>");
            out.println(" <h1>La hora actualizada!</h1>");
            out.println("<h3>" + hora.format(df) + "</h3>");
            out.println(" </body>");
            out.println("</html>");
        }
    }
}
