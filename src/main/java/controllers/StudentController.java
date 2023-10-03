package controllers;

import domain.models.Student;
import mapper.dtos.StudentDto;
import repository.Impl.StudentRepositoryImpl;
import repository.Impl.StudentRepositoryLogicImpl;
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
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "studentController", value = "/student-form")
public class StudentController extends HttpServlet {

    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Connection conn = (Connection) request.getAttribute("conn");
        StudentService service = new StudentServiceImpl(conn);
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("h1>Students</h1>");
        out.println(service.studentList());
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        Connection conn = (Connection) req.getAttribute("conn");
        StudentServiceImpl service = new StudentServiceImpl(conn);
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String career = req.getParameter("career");
        String semester = req.getParameter("semester");
        List<String> errores = getErrors(name,semester,email,career);
        Map<String,String> errorsmap = getErrors2(name,semester,email,career);
        if(errorsmap.isEmpty()) {
            service.update(StudentDto.builder()
                    .studentName(name)
                    .studentEmail(email)
                    .career(career)
                    .semester(semester)
                    .build());
            System.out.println(service.studentList());
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Resultado form</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Resultado form!</h1>");
                out.println(" <ul>");
                out.println(" <li>Name: " + name +
                        "</li>");
                out.println(" <li>Email: " + email +
                        "</li>");
                out.println(" <li>Semester: " + semester
                        + "</li>");
                out.println(" <li>Career: " + career
                        + "</li>");
                out.println(" </ul>");
                out.println(" </body>");
                out.println("</html>");
            }
        }
        else{
/* errores.forEach(error -> {
out.println("<li>" + error + "</li>");
});
out.println("<p><a href=\"/student.jsp\">volver</a></p>");*/
            req.setAttribute("errors", errores);
            req.setAttribute("errorsmap", errorsmap);
            getServletContext().getRequestDispatcher("/StudentCrud.jsp").forward(req, resp);
        }
    }

    private Map<String,String> getErrors2(String name, String semester, String
            email, String degree) {
        Map<String,String> errors = new HashMap<>();
        if(name==null ||name.isBlank()){
            errors.put("name","El nombre es requerido");
        }
        if(email==null ||email.isBlank()){
            errors.put("email","El email es requerido");
        }
        if(degree==null ||degree.isBlank()){
            errors.put("career","La carrera es requerida");
        }
        if(semester==null ||semester.isBlank()){
            errors.put("semester","El semestre es requerido");
        }
        return errors;
    }
    private List<String> getErrors(String name, String semester, String email,String degree)
    {
        List<String> errors = new ArrayList<String>();
        if(name==null ||name.isBlank()){
            errors.add("El nombre es requerido");
        }
        if(email==null ||email.isBlank()){
            errors.add("El email es requerido");
        }
        if(semester==null ||semester.isBlank()){
            errors.add("El semestre es requerido");
        }
        return errors;
    }


    public void destroy() {

    }
}