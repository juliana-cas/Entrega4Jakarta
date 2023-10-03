package controllers;

import mapper.dtos.StudentDto;
import mapper.dtos.SubjectDto;
import mapper.dtos.TeacherDto;
import repository.Impl.SubjectRepositoryLogicImpl;
import service.StudentService;
import service.SubjectService;
import service.Impl.StudentServiceImpl;
import service.Impl.SubjectServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "subjectController", value = "/subject-form")
public class SubjectController extends HttpServlet {

    private String message;

    public void init(){
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Connection conn = (Connection) request.getAttribute("conn");
        SubjectService service = new SubjectServiceImpl(conn);

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("h1>Teachers</h1>");
        out.println(service.subjectList());
        out.println("</body></html>");
    }

    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");

        Connection conn = (Connection) req.getAttribute("conn");
        SubjectService service = new SubjectServiceImpl(conn);
        String name = req.getParameter("name");
        List<String> errores = getErrors(name);
        Map<String,String> errorsmap = getErrors2(name);

        if(errorsmap.isEmpty()) {
            service.update(SubjectDto.builder()
                    .subjectName(name)
                    .build());
            System.out.println(service.subjectList());
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Resultado form</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Resultado form!</h1>");

                out.println("        <ul>");
                out.println("            <li>Name: " + name + "</li>");
                out.println("        </ul>");
                out.println("    </body>");
                out.println("</html>");
            }
        }else{
            req.setAttribute("errors", errores);
            req.setAttribute("errorsmap", errorsmap);
            getServletContext().getRequestDispatcher("/SubjectCrud.jsp").forward(req, resp);
        }
    }

    private Map<String,String> getErrors2(String name) {
        Map<String,String> errors = new HashMap<>();
        if(name==null || name.isBlank()){
            errors.put("name","El nombre es requerido");
        }
        return errors;
    }
    private List<String> getErrors(String name)
    {
        List<String> errors = new ArrayList<String>();
        if(name==null ||name.isBlank()){
            errors.add("El nombre es requerido");
        }
        return errors;
    }
    public void destroy() {

    }
}