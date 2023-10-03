package controllers;


import mapper.dtos.StudentDto;
import service.GradesService;
import service.StudentService;
import service.Impl.GradesServiceImpl;
import service.Impl.StudentServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name = "gradeController", value = "/deletegrade")
public class GradeDeleteServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Connection conn = (Connection) request.getAttribute("conn");
        GradesService service = new GradesServiceImpl(conn);
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("h1>Nota eliminada</h1>");
        out.println(service.gradesList());
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        Connection conn = (Connection) req.getAttribute("conn");
        GradesServiceImpl service = new GradesServiceImpl(conn);
        String idString = req.getParameter("id");
        try {
            Long id = Long.parseLong(idString);
            service.delete(id);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Consulta por ID</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Registro de notas eliminado</h1>");
                out.println(" <h3>El registro de notas con id "+id+" fue eliminado , aqui esta la lista :  "+ service.gradesList() + "</h3>");
                out.println(" </body>");
                out.println("</html>");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No existe una asignacion de notas con el id ingresado");
        }
    }
}