let currentPage = 0;
let pageSize = 5;
let lastPage = 0;
let imageUpload;
let imageUpdateUpload;

const getCustomers = () => {
	let pageUser = [];
	$.ajax({
		type: "GET",
		url: "/api/users/customer",
		data: {
			page: currentPage,
			size: pageSize
		},
		success: function(resp) {
			pageUser = resp.content;
			console.log(pageUser);
			lastPage = resp.totalPages - 1;
			$("#dataUser").html(pageUser.map(function(item) {
				return `
					<tr>
					<td>${item.username}</td>
					<td>${item.fullname}</td>
					<td>${item.email}</td>
					<td><img src="/assets/images/${item.avatar}" style="width:50px; height: 50px;" class="rounded"></td>
					<td>${item.gender ? 'Nam' : 'Nữ'}</td>
					<td>${item.createdBy ? item.createdBy : 'Trống'}</td>
					<td>${item.updatedBy ? item.updatedBy : 'Trống'}</td>
					<td>${item.createDate ? item.createDate : 'Trống'}</td>
					<td>${item.updateDate ? item.updateDate : 'Trống'}</td>
					<td>
						<a class='mr-4'> <span class='fas fa-pencil-alt text-warning' data-bs-toggle="modal"
											data-bs-target="#editModal" onclick="doEdit(${item.id})"></span> </a> 
						<a class='mr-4 delet'> <span class='fas fa-trash-alt text-danger' onclick="doDelete(${item.id})"></span> </a>
					</td>
					</tr>
					`
			}))
		}
	});
}
const doCreate = () => {
	let username = $("#username").val();
	let fullname = $("#fullname").val();
	let email = $("#email").val();
	let password = $("#password").val();
	let gender = document.querySelector('input[name="gender"]:checked').value;
	let isActive = document.querySelector('input[name="isActive"]:checked').value;
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	let User = {
		username: username,
		fullname: fullname,
		email: email,
		password: password,
		gender: gender,
		isActive: isActive,
		avatar: imageUpload
	};
	
	if (username.length == 0) {
		$("#username_error").text("Tên người dùng không được để trống!");
		return;
	}
	if (fullname.length == 0) {
		$("#fullname_error").text("Họ tên người dùng không được để trống!");
		return;
	}
	if (email.length == 0) {
		$("#email_error").text("Email người dùng không được để trống!");
		return;
	}
	if (!filter.test(email)) {
		$("#email_error").text("Sai định dạng email!");
		return;
	}
	if (password.length == 0 || password.length < 3) {
		$("#password_error").text("Pssword người dùng không được để trống và lớn hơn 3 kí tự!");
		return;
	}
	$.ajax({
	type: "POST",
	contentType: "application/json",
	url: "/api/users",
	data: JSON.stringify(User),
	dataType: 'json',
	success: function() {
		$("#username").val("");
		$("#fullname").val("");
		$("#email").val("");
		$("#password").val("");
		Swal.fire("Hoàn thành", "Thêm mới thành công !", "success");
		$("#addModal").modal("hide");
		getAll();
	},
	error: function(e) {
		console.log("ERROR : ", e);
		Swal.fire("Thất bại", "Thêm mới thất bại !", "error");
	}
});
}

const doDelete = (userId) => {
	Swal.fire({
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Delete',
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		title: 'Bạn có muốn xóa không?',
	}).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				type: "DELETE",
				url: "/api/users/" + userId,
				success: function() {
					Swal.fire("Hoàn thành", "Xóa thành công! Mã đơn vị " + '[' + userId + ']', "success");
					getCustomers();
				},
				error: function(e) {
					console.log("ERROR : ", e);
					Swal.fire("Thất bại", "Xóa thất bại !", "error");
				}
			});
		}
	});
}
const doEdit = (userId) => {
	let url = "/api/users/" + userId;
	$.get(url).done(function(data) {
		$('#idEdit').val(data.id);
		$('#usernameEdit').val(data.username);
		$('#fullnameEdit').val(data.fullname);
		$('#emailEdit').val(data.email);
		$('#passwordEdit').val(data.password);
		$('#genderEdit').val(data.gender);
	})
}
const btn_first = () => {
	currentPage = 0;
	getCustomers();
}

const btn_previous = () => {
	currentPage--;
	getCustomers();
}

const btn_next = () => {
	if (currentPage >= lastPage) {
		return;
	}
	currentPage++;
	getCustomers();
}

const btn_last = () => {
	currentPage = lastPage;
	getCustomers();
}

const doUpdate = () => {
	let id = $("#idEdit").val();
	let username = $("#usernameEdit").val();
	let fullname = $("#fullnameEdit").val();
	let email = $("#emailEdit").val();
	let password = $("#passwordEdit").val();
	let gender = document.querySelector('input[name="gender"]:checked').value;
	let isActive = document.querySelector('input[name="isActive"]:checked').value;
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	let User = {
		id: id,
		username: username,
		fullname: fullname,
		email: email,
		password: password,
		gender: gender,
		isActive: isActive,
		avatar: imageUpdateUpload
	};
	if (username.length == 0) {
		$("#username_error").text("Tên người dùng không được để trống!");
		return;
	}
	if (fullname.length == 0) {
		$("#fullname_error").text("Họ tên người dùng không được để trống!");
		return;
	}
	if (email.length == 0) {
		$("#email_error").text("Email người dùng không được để trống!");
		return;
	}
	if (!filter.test(email)) {
		$("#email_error").text("Sai định dạng email!");
		return;
	}
	if (password.length == 0 || password.length < 3) {
		$("#password_error").text("Pssword người dùng không được để trống và lớn hơn 3 kí tự!");
		return;
	}
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: "/api/users",
		data: JSON.stringify(User),
		dataType: 'json',
		success: function() {
			Swal.fire("Hoàn thành", "Cập nhật thành công !", "success");
			$("#editModal").modal("hide");
			getCustomers();
		},
		error: function(e) {
			console.log("ERROR : ", e);
			Swal.fire("Thất bại", "Cập nhật thất bại !", "error");
		}
	});
}

const doSearch = () => {
	let keyword = $('#keyword_search').val();
	let pageUser = [];
	$.ajax({
		type: "GET",
		url: "/api/users/search?keyword=" + keyword,
		data: {
			page: currentPage,
			size: pageSize
		},
		success: function(resp) {
			pageUser = resp.content;
			console.log(pageUser);
			lastPage = resp.totalPages - 1;
			$("#dataUser").html(pageUser.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td>${item.username}</td>
					<td>${item.fullname}</td>
					
					<td>
						<a class='mr-4'> <span class='fas fa-pencil-alt text-warning' data-bs-toggle="modal"
											data-bs-target="#editModal" onclick="doEdit(${item.id})"></span> </a> 
						<a class='mr-4 delet'> <span class='fas fa-trash-alt text-danger' onclick="doDelete(${item.id})"></span> </a>
					</td>
					</tr>
					`
			}))
		}
	});
}
function uploadFile() {
	let fileInput = document.getElementById("fileInput");
	let file = fileInput.files[0];
	let formData = new FormData();
	formData.append("file", file);

	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "/api/uploads/images",
		data: formData,
		processData: false,
		contentType: false,
		cache: false,
		success: function(data) {
			imageUpload = data.name;
			console.log(imageUpload);
		},
		error: function(e) {
			console.log("ERROR : ", e);
		}
	});
}

function uploadUpdateFile() {
	let fileEdit = document.getElementById("fileEdit");
	let file = fileEdit.files[0];
	let formData = new FormData();
	formData.append("file", file);

	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "/api/uploads/images",
		data: formData,
		processData: false,
		contentType: false,
		cache: false,
		success: function(data) {
			imageUpdateUpload = data.name;
			console.log(imageUpdateUpload);
		},
		error: function(e) {
			console.log("ERROR : ", e);
		}
	});
}