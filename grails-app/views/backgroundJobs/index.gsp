<html ng-app="myApp">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <asset:stylesheet src="bootstrap-all.css"/>
        <asset:stylesheet src="font-awesome.min.css"/>
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
      <div class="container" ng-controller="BackgroundJobCtrl" ng-cloak>
        <div class="row">
          <div class="col-md-6">
            <h1>Jobs listing</h1>
            <ul class="list-group">
			  <li ng-repeat="j in backgroundJobs.jobs" ng-if="j.jobState == 'NOT_STARTED'" class="list-group-item list-group-item-warning">
			  	Job #{{j.identifier}} - {{j.jobName}}
					<div class="progress">
                      <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="0"
                        aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                        0%
                      </div>
                    </div>
                    <div>
	                    <button class="btn btn-default" ng-click="start(j)">
	                    	<span class="badge fa fa-play" />
	                    </button>
                    </div>
			  </li>
			  <li ng-repeat="j in backgroundJobs.jobs" ng-if="j.jobState == 'RUNNING'" class="list-group-item list-group-item-info">
			  	Job #{{j.identifier}} - {{j.jobName}}
                    <div class="progress">
                      <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="{{j.progress}}"
                        aria-valuemin="0" aria-valuemax="100" style="width: {{j.progress}}%">
                        {{j.progress}}%
                      </div>
                    </div>
			  </li>
			  <li ng-repeat="j in backgroundJobs.jobs" ng-if="j.jobState == 'FINISHED'" class="list-group-item list-group-item-success">
			  	Job #{{j.identifier}} - {{j.jobName}}
                    <div class="progress">
                      <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100"
                        aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                        100%
                      </div>
                    </div>
                    <div>
	                    <button class="btn btn-default" ng-click="remove(j)">
	                    	<span class="badge fa fa-eject" />
	                    </button>
	                    <button class="btn btn-default">
	                    	<span class="badge fa fa-file-text-o" />
	                    </button>
	                    
                    </div>
			  </li>
            </ul>
          </div>
        </div>
		<div class="col-md-6">
        	<h1>Create a new job</h1>
                <button class="btn btn-default" ng-click="create()">
                	<span class="badge fa fa-plus" />
                </button>        	
        </div>
      </div>
        <script type="text/javascript">
            new Services().init()
            new Controllers().init()
            new Application().init()
        </script>
    </body>
</html>
