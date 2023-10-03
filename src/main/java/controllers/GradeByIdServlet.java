package controllers;

import mapper.dtos.GradesDto;
import mapper.dtos.StudentDto;
import service.GradesService;
import service.StudentService;
import service.Impl.GradesServiceImpl;
import service.Impl.StudentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/gradesbyid")
public class GradeByIdServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        GradesService service = new GradesServiceImpl(conn);
        String idString = req.getParameter("id");
        try {
            Long id = Long.parseLong(idString);
            GradesDto grades = service.byId(id);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Consulta por ID</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Estudiante encontrado!</h1>");
                out.println(" <h3>Este es el estudiante con id "+id+" :  "+ grades + "</h3>");
                out.println(" </body>");
                out.println("</html>");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No existe una asignacion de notas con este id");
        }
    }
}