package patterns.combined.djview;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DJViewServlet extends HttpServlet {

    /**
     * 当Servlet第一次创建时，init()方法会被调用
     *
     * @throws ServletException
     */
    public void init() throws ServletException {
        BeatModel beatModel = new BeatModel();
        beatModel.initialize();
        // 传入一个beatModel的引用给ServletContext，好让ServletContext可以访问beatModel
        getServletContext().setAttribute("beatModel", beatModel);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        BeatModel beatModel = (BeatModel) getServletContext().getAttribute("beatModel");

        String bpm = request.getParameter("bpm");
        if (bpm == null) bpm = beatModel.getBPM() + "";
        String set = request.getParameter("set");
        if (set != null) {
            int bpmNumber = 90;
            bpmNumber = Integer.parseInt(bpm);
            beatModel.setBPM(bpmNumber);
        }
        String decrease = request.getParameter("decrease");
        if (decrease != null) beatModel.setBPM(beatModel.getBPM() - 1);
        String increase = request.getParameter("increase");
        if (increase != null) beatModel.setBPM(beatModel.getBPM() + 1);
        String on = request.getParameter("on");
        if (on != null) beatModel.on();
        String off = request.getParameter("off");
        if (off != null) beatModel.off();

        request.setAttribute("beatModel", beatModel);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/djview.jsp");
        dispatcher.forward(request, response);
    }
}
