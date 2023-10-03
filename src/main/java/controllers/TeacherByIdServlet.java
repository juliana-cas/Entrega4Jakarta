package controllers;

import mapper.dtos.StudentDto;
import mapper.dtos.TeacherDto;
import service.StudentService;
import service.TeacherService;
import service.Impl.StudentServiceImpl;
import service.Impl.TeacherServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/teacherbyid")
public class TeacherByIdServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        TeacherService service = new TeacherServiceImpl(conn);
        String idString = req.getParameter("id");
        try {
            Long id = Long.parseLong(idString);
            TeacherDto teacher = service.byId(id);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Consulta por ID</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Docente encontrado!</h1>");
                out.println(" <h3>Este es el docente con id "+id+" :  "+ teacher + "</h3>");
                out.println(" </body>");
                out.println("</html>");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No existe un docente con este id");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
    }
}