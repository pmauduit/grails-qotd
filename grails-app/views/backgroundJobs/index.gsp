<!doctype html>
<html>
    <head>
        <title>List of jobs</title>
    </head>
    <body>
        <div id="page-body" role="main">
        	<h1>List of jobs</h1>
        	<g:each var="j" in="${jobs}">
        	    <li class="controller">Job #${j.id} - Status: ${j.jobState} - Completed: ${j.progress()}%</li>
        	</g:each>
        </div>
    </body>
</html>
