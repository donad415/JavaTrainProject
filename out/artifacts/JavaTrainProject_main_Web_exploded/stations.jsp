<%@ page import="models.Station" %><%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 22.12.2021
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Stations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>

    <script type="text/javascript">
        function sendStation(method) {
            let data = {
                name: document.getElementById('name').value,
                departure: document.getElementById('departure').value,
                coming: document.getElementById('coming').value,
            }
            fetch('http://localhost:8080/stations',
                {
                    method: method,
                    redirect: 'follow',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
            document.location.href = 'http://localhost:8080/stations'
        }
    </script>
</head>
<body>
<header class="p-3 bg-dark text-white">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="/" class="nav-link px-2 text-white">Routes</a></li>
                <li><a href="/stations" class="nav-link px-2 text-secondary">Stations</a></li>
                <li><a href="/trains" class="nav-link px-2 text-white">Trains</a></li>
            </ul>

        </div>
    </div>
</header>
<h1>Stations</h1>

<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    ???????????????? ??????????????
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">???????????????????? ??????????????</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="????????????????" aria-label="??????"
                               aria-describedby="name-addon"
                               id="name" name="name" required>
                        <span class="input-group-text" id="name-addon">???????????????? ??????????????</span>
                    </div>
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="..." aria-label="??????"
                               aria-describedby="name-addon"
                               id="departure" name="name" required>
                        <span class="input-group-text" id="departure-addon">?????????? ??????????????</span>
                    </div>
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="..." aria-label="??????"
                               aria-describedby="name-addon"
                               id="coming" name="name" required>
                        <span class="input-group-text" id="coming-addon">?????????? ????????????????</span>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">??????????????</button>
                <button type="button" value="POST" onclick="sendStation(this.value)" class="btn btn-primary">??????????????????
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
        <th scope="col">????????????????</th>
        <th scope="col">?????????? ??????????????</th>
        <th scope="col">?????????? ????????????????</th>
    </tr>
    </thead>
    <tbody>
    <%  int i = 1;
        for (Station item : (Station[]) request.getAttribute("stations")) {%>
    <tr>
        <th scope="row"><%=i%></th>
        <td><%=item.getName()%></td>
        <td><%=item.getDepartureTime()%></td>
        <td><%=item.getComingTime()%></td>
    </tr>
    <%  i++;
        }%>

    </tbody>
</table>





</body>
</html>
