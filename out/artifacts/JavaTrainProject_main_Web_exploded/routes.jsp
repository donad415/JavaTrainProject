<%@ page import="models.Route" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="models.Station" %>
<%@ page import="models.Train" %><%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 22.12.2021
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Routes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script type="text/javascript">
        function deleteRoute(val) {
            fetch('http://localhost:8080/?id=' + val,
                {
                    redirect: 'follow',
                    method: 'DELETE'
                });
            document.location.href = 'http://localhost:8080/'
        }
    </script>
    <script type="text/javascript">
        function getSelectValues(select) {
            var result = [];
            var options = select && select.options;
            var opt;

            for (var i = 0, iLen = options.length; i < iLen; i++) {
                opt = options[i];

                if (opt.selected) {
                    result.push(opt.value || opt.text);
                }
            }
            return result;
        }

        function sendRoute(method) {
            let data = {
                name: document.getElementById('name').value,
                departure: document.getElementById('departure').value,
                coming: document.getElementById('coming').value,
                intermediate: getSelectValues(document.getElementById('intermediate')),
                train: document.getElementById('train').value
            }
            fetch('http://localhost:8080/',
                {
                    method: method,
                    redirect: 'follow',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
            document.location.href = 'http://localhost:8080/'
        }
    </script>
</head>
<body>
<header class="p-3 bg-dark text-white">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                    <use xlink:href="#bootstrap"></use>
                </svg>
            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="/" class="nav-link px-2 text-secondary">Routes</a></li>
                <li><a href="/stations" class="nav-link px-2 text-white">Stations</a></li>
                <li><a href="/trains" class="nav-link px-2 text-white">Trains</a></li>
            </ul>

        </div>
    </div>
</header>
<h1>Routes</h1>

<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    Добавить маршрут
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Добавление маршрута</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Название" aria-label="Имя"
                               aria-describedby="name-addon"
                               id="name" name="name" required>
                        <span class="input-group-text" id="name-addon">Название станции</span>
                    </div>
                    <div class="input-group mb-3">
                        <label class="input-group-text">Станция отправления</label>
                        <select class="form-select m-2" aria-label="Default select example" id="departure"
                                name="departure">
                            <%
                                for (Station item : (Station[]) request.getAttribute("stations")) {
                                    String id = item.getId().toString();
                            %>
                            <option selected
                                    value="<%=id%>"><%=item.getName()%>
                            </option>
                            <%}%>
                        </select>
                    </div>
                    <div class="input-group mb-3">
                        <label class="input-group-text">Станция прибытия</label>
                        <select class="form-select m-2" aria-label="Default select example" id="coming" name="coming">
                            <%
                                for (Station item : (Station[]) request.getAttribute("stations")) {
                                    String id = item.getId().toString();
                            %>
                            <option selected
                                    value="<%=id%>"><%=item.getName()%>
                            </option>
                            <%}%>
                        </select>
                    </div>
                    <div class="input-group mb-3">
                        <label class="input-group-text">Промежуточные станции</label>
                        <select class="form-select m-2" aria-label="Default select example" id="intermediate"
                                name="intermediate" multiple required>
                            <%
                                for (Station item : (Station[]) request.getAttribute("stations")) {
                                    String id = item.getId().toString();
                            %>
                            <option selected
                                    value="<%=id%>"><%=item.getName()%>
                            </option>
                            <%}%>
                        </select>
                    </div>
                    <div class="input-group mb-3">
                        <label class="input-group-text">Поезд</label>
                        <select class="form-select m-2" aria-label="Default select example" id="train" name="train">
                            <%
                                for (Train item : (Train[]) request.getAttribute("trains")) {
                                    String id = item.getId().toString();
                            %>
                            <option selected
                                    value="<%=id%>"><%=item.getNumber()%> <%=item.getBoss()%>
                            </option>
                            <%}%>
                        </select>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                <button type="button" value="POST" onclick="sendRoute(this.value)" class="btn btn-primary">Сохранить
                </button>
            </div>
        </div>
    </div>
</div>

<hr>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Название</th>
        <th scope="col">Поезд</th>
        <th scope="col">Начальная станция</th>
        <th scope="col">Конечная станция</th>
        <th scope="col">Промежуточные пункты</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
    <% int i = 1;
        for (Route item : (Route[]) request.getAttribute("routes")) {%>
    <tr>
        <th scope="row"><%=i%>
        </th>
        <td><%=item.getName()%>
        </td>
        <td><%=item.getTrain().getNumber()%> <%=item.getTrain().getBoss()%>
        </td>
        <td>
            <%=item.getDepartureStation().getName()%>
            <%=item.getDepartureStation().getComingTime()%> - <%=item.getDepartureStation().getDepartureTime()%>
        </td>
        <td>
            <%=item.getComingStation().getName()%>
            <%=item.getComingStation().getComingTime()%> - <%=item.getComingStation().getDepartureTime()%>
        </td>
        <td>
            <% for (Station s : item.getIntermediateStations()) {%>
            <div>
                <%=s.getName()%>
                <%=s.getComingTime()%> - <%=s.getDepartureTime()%>
            </div>
            <%}%>
        </td>
        <td>
            <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 0 24 24" width="24px" fill="#000000">
                <path d="M0 0h24v24H0z" fill="none"/>
                <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"/>
            </svg>
            <button type="button" class="btn btn-danger" onclick="deleteRoute(this.value)"
                    value="<%=item.getId()%>">Удалить
            </button>
        </td>
    </tr>
    <% i++;
    }%>

    </tbody>
</table>


</body>
</html>
