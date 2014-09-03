<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>

<script src="js/bootstrap.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
	function resetForm() {
		itemForm.reset();
	}
</script>
<script type="text/javascript">
	function refreshPage() {
		window.location.reload();
	}
</script>

<script type="text/javascript">
	function submit() {

		if (!itemForm.itemName.value) {

			$("#resultContainer")
					.html(
							"<div class='alert alert-danger' >Item name cannot be empty!</div>");
			return false;
		}

		if (!itemForm.itemQty.value) {

			$("#resultContainer")
					.html(
							"<div class='alert alert-danger' >Item quantity cannot be empty!</div>");
			return false;
		}
		if (isNaN(itemForm.itemQty.value) == true) {
			$("#resultContainer")
					.html(
							"<div class='alert alert-danger' >Quantity should be a number!</div>");
			return false;
		}

		if (itemForm.itemQty.value < 0) {
			$("#resultContainer")
					.html(
							"<div class='alert alert-danger' >Quantity cannot be negative!</div>");
			return false;
		}

		$
				.ajax({
					type : "POST",
					dataType : "json",
					url : "http://localhost:8080/InventoryManagement/Item",
					data : $('#itemForm').serialize(),
					success : function(message) {
						if (message == true) {
							$("#resultContainer")
									.html(
											"<div class='alert alert-success' style='color:green'>Item added successfully</div>");
						} else {

							$("#resultContainer")
									.html(
											"<div class='alert alert-danger'>Item already exists in inventory </div>");
						}
					},
					error : function(message) {
						$("#resultContainer")
								.html(
										"<div class='alert' style='color:green'>Failure!</div>");
					}
				});
		resetForm();

	}
</script>
<title></title>
</head>
<body>
	<button class="btn btn-primary btn-success" data-toggle="modal"
		data-target="#myModal">Add Item</button>

	<div class="modal fade" id="myModal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button> -->

					<div id="resultContainer"></div>
					<h4 class="modal-title" id="myModalLabel">Add Item</h4>
				</div>
				<div class="modal-body">
					<form id="itemForm" name="itemForm" method="post">
						<p>Enter Item Name:</p>
						<input type="text" name="itemName" id="itemName" value="" />
						<p>Enter Item Quantity:</p>
						<input type="email" name="itemQty" id="itemQty" value="" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary btn-success"
						onclick="submit()">Add</button>
					<button type="button" class="btn btn-default btn-info"
						onclick="resetForm()">Reset</button>
					<button type="button" class="btn btn-default btn-danger"
						data-dismiss="modal" onclick="refreshPage()">Close</button>
				</div>
			</div>
		</div>
	</div>


	<table class="table table-hover table-striped">
		<tbody>
			<tr>
				<th>ID
				<th>Name</th>
				<th>Quantity</th>
			</tr>
			<c:forEach items="${items}" var="item" varStatus="loopCounter">
				<tr class="${item.quantity==0? 'danger':''}">
					<td> ${loopCounter.index +1} </td>
					<td>${item.name}</td>
					<td>${item.quantity}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
