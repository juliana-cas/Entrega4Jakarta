package controllers;
import mapper.dtos.StudentDto;
import service.StudentService;
import service.Impl.StudentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet({"/students.xls","/students.html","/students"})

public class StudentXLS extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        StudentService service = new StudentServiceImpl(conn);
        List<StudentDto> students = service.studentList();
        resp.setContentType("text/html;charset=UTF-8");
        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");
        if (esXls) {
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment;filename=students.xls");
        }
        try (PrintWriter out = resp.getWriter()) {
            if(!esXls) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Listado de Estudiantes</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Listado de Estudiantes!</h1>");
                out.println("<p><a href=\"" + req.getContextPath() + "/students.xls" + "\">exportar a " +
                        "xls</a></p>");
            }
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>nombre</th>");
            out.println("<th>email</th>");
            out.println("<th>career</th>");
            out.println("<th>semester</th>");
            out.println("</tr>");
            students.forEach(p ->{
                out.println("<tr>");
                out.println("<td>" + p.studentId() + "</td>");
                out.println("<td>" + p.studentName() + "</td>");
                out.println("<td>" + p.studentEmail() + "</td>");
                out.println("<td>" + p.career() + "</td>");
                out.println("<td>" + p.semester() + "</td>");
                out.println("</tr>");
            });
            out.println("</table>");
            if(!esXls) {
                out.println(" </body>");
                out.println("</html>");
            }
        }
    }
}