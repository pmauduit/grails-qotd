<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <asset:stylesheet src="bootstrap-all.css"/>
        <asset:stylesheet src="font-awesome.min.css"/>
    </head>
    <body>
      <div class="container">
        <div class="row">
          <div class="col-md-6">
            <h1>Job report</h1>
            ${raw(job.report.htmlReport)}
            <br/>
            <a href="${resource()}/backgroundJobs/index">Back</a>
		  </div>
		</div>
	  </div>
	</body>
</html>  