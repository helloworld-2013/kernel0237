<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="author" content="Indra Gunawan (indra0237@yahoo.com)">

		<title>Email Domain Blacklister</title>

		<!-- Bootstrap core CSS -->
	    <link href="./css/bootstrap.min.css" rel="stylesheet">
	    <link href="./css/bootstrap-table.min.css" rel="stylesheet">

		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->

		<!-- Bootstrap core JS -->
	    <script src="./js/jquery-1.11.1.min.js"></script>
	    <script src="./js/bootstrap.min.js"></script>
	    <script src="./js/bootstrap-table.min.js"></script>
	    <script src="./js/app.js"></script>
	</head>
	<body>
		<div class="container">
		    <p>
			<table id="blacklist-table"
			       data-url="http://localhost:8080/EmailDomainBlacklister/list"
			       class="table-striped" data-toggle="table" data-show-refresh="true" data-search="true" data-response-handler="responseHandler">
				<thead>
				<tr>
				    <th data-field="state" data-checkbox="true"></th>
					<th data-field="id">Domain ID</th>
					<th data-field="emailDomain">Email Domain Name</th>
				</tr>
				</thead>
			</table>
			</p>
			<p>
			<button id="deleteBtn" class="btn btn-primary disabled">Delete</button>
			<button class="btn btn-primary" data-toggle="modal" data-target="#addEmailDialog">Add Email Domain</button>
			<div id="addEmailDialog" class="modal fade" >
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                            <h4 class="modal-title" >Add Email Domain</h4>
			            </div>
			            <div class="modal-body">
                            <form role="form">
                                <div class="form-group">
                                    <label for="inputEmail">New email domain</label>
                                    <input type="text" class="form-control" id="inputEmail">
                                </div>
                                <div id="addEmailErrorMsg" class="hide bg-danger">
                                    The Email Domain is already in the list. Please enter a new different one.
                                </div>
                                <div id="addEmailSuccessMsg" class="hide bg-success">
                                    The Email Domain has been saved into the list.
                                </div>
                            </form>
			            </div>
			            <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button id="saveBtn" type="button" class="btn btn-primary disabled">Save changes</button>
			            </div>
			        </div>
			    </div>
			</div>
			</p>
		</div>
	</body>
</html>