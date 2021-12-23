package web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.DataStorage;
import models.Station;
import models.Train;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@WebServlet(name = "train", urlPatterns = {"/trains"})
public class TrainServlet extends HttpServlet {
    private DataStorage storage;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = new DataStorage();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("trains", getTrainsArray());
        req.getRequestDispatcher("/trains.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String json = req.getReader().readLine();
        try {
            Train train = mapTrain(json);
            storage.addTrain(train);
            resp.setStatus(200);
            resp.getWriter().println("Создание прошло успешно");
        } catch (Exception e) {
            //handleException(req, resp, e);
        }
    }

    private Train[] getTrainsArray() throws UnsupportedEncodingException {
        List<Train> trainList = storage.getTrains();
        Train[] collection= new Train[trainList.size()];
        for (int i = 0; i<trainList.size(); i++){
            collection[i] = trainList.get(i);
        }
        return collection;
    }

    private Train mapTrain(String json) throws IOException {
        JsonNode node = mapper.readTree(json);
        Train train = new Train();
        train.setNumber(node.get("name").asText());
        train.setBoss(node.get("boss").asText());
        return train;
    }
}
