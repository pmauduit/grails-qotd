<!doctype html>
<html ng-app="myApp">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <asset:stylesheet src="bootstrap-all.css"/>
        <asset:stylesheet src="font-awesome-all.css"/>
        <asset:javascript src="grooscript/grooscript.min.js" />
        <asset:javascript src="grooscript/grooscript-tools.js" />
        <asset:javascript src="angular/angular.min.js" />
        <asset:javascript src="angular/angular-route.min.js" />
        <asset:javascript src="angular/angular-resource.min.js" />
        <asset:javascript src="app/Services.js" />
        <asset:javascript src="app/Controllers.js" />
        <asset:javascript src="app/Application.js" />
    </head>
    <body>
      <div class="container" ng-controller="BackgroundJobCtrl">
        <div class="row">
          <div class="col-md-6">
            <h1>Jobs listing</h1>
            <ul class="list-group">
              <g:each var="j" in="${jobs}">
                <g:if test="${j.jobState.toString() == 'NOT_STARTED'}">
                  <li class="list-group-item list-group-item-warning">
                   Job #${j.identifier}
                    <div class="progress">
                      <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="0"
                        aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                        0%
                      </div>
                    </div>
                </g:if>
                <g:elseif test="${j.jobState.toString() == 'RUNNING'}">
                  <li class="list-group-item list-group-item-info">
                   Job #${j.identifier}
                    <div class="progress">
                      <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="${j.progress()}"
                        aria-valuemin="0" aria-valuemax="100" style="width: ${j.progress()}%">
                        ${j.progress()}%
                      </div>
                    </div>
                </g:elseif>
                <g:elseif test="${j.jobState.toString() == 'FINISHED'}">
                  <li class="list-group-item list-group-item-success">
                   Job #${j.identifier}
                    <div class="progress">
                      <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100"
                        aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                        100%
                      </div>
                    </div>
                </g:elseif>
                <g:else>
                  <li class="list-group-item">
                   Job #${j.identifier}
                </g:else>
                  <span class="badge"></span>
                </li>
              </g:each>
            </ul>
          </div>
        </div>
      </div>
        <script type="text/javascript">
            new Services().init()
            new Controllers().init()
            new Application().init()
        </script>
    </body>
</html>
