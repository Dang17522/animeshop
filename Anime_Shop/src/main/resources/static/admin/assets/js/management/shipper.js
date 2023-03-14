let currentPage = 0;
let pageSize = 5;
let lastPage = 0;

const getAll = () => {
	let pageShippers = [];
	$.ajax({
		type: "GET",
		url: "/api/shippers",
		data: {
			page: currentPage,
			size: pageSize
		},
		success: function(resp) {
			pageShippers = resp.content;
			console.log(pageShippers);
			lastPage = resp.totalPages - 1;
			$("#dataShipper").html(pageShippers.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>${item.phone}</td>
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
	let name = $("#name").val();
	let phone = $("#phone").val();

	let Shipper = {
		name: name,
		phone: phone
	};
	if (name.length === 0) {
		$("#name_error").text("Tên shipper không được để trống!");
		return;
	} else {
		$("#name_error").text("");
	}
	if (phone.length < 9 || phone.length > 10) {
		$("#phone_error").text("SDT khong hop le!");
		return;
	} else {
		$("#phone_error").text("");
	}
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/api/shippers",
		data: JSON.stringify(Shipper),
		dataType: 'json',
		success: function() {
			$("#name").val("");
			$("#phone").val("");
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

const doDelete = (shipperId) => {
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
				url: "/api/shippers/" + shipperId,
				success: function() {
					Swal.fire("Hoàn thành", "Xóa thành công! Mã đơn vị " + '[' + shipperId + ']', "success");
					getAll();
				},
				error: function(e) {
					console.log("ERROR : ", e);
					Swal.fire("Thất bại", "Xóa thất bại !", "error");
				}
			});
		}
	});
}
const doEdit = (shipperId) => {
	let url = "/api/shippers/" + shipperId;
	$.get(url).done(function(data) {
		$('#idEdit').val(data.id);
		$('#nameEdit').val(data.name);
		$('#phoneEdit').val(data.phone);
	})
}
const btn_first = () => {
	currentPage = 0;
	getAll();
}

const btn_previous = () => {
	currentPage--;
	getAll();
}

const btn_next = () => {
	if (currentPage >= lastPage) {
		return;
	}
	currentPage++;
	getAll();
}

const btn_last = () => {
	currentPage = lastPage;
	getAll();
}

const doUpdate = () => {
	let id = $("#idEdit").val();
	let name = $("#nameEdit").val();
	let phone = $("#phoneEdit").val();
	let Shipper = {
		id: id,
		name: name,
		phone: phone
	};
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: "/api/shippers",
		data: JSON.stringify(Shipper),
		dataType: 'json',
		success: function() {
			Swal.fire("Hoàn thành", "Cập nhật thành công !", "success");
			$("#editModal").modal("hide");
			getAll();
		},
		error: function(e) {
			console.log("ERROR : ", e);
			Swal.fire("Thất bại", "Cập nhật thất bại !", "error");
		}
	});
}

const doSearch = () => {
	let keyword = $("#keyword_search").val();
	console.log(keyword);
	$.ajax({
		type: "GET",
		url: "/api/shippers/search?keyword=" + keyword,
		dataType: 'json',
		success: function(resp) {
			console.log(resp);
			$("#dataShipper").html(resp.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>${item.phone}</td>
					<td>
						<a class='mr-4'> <span class='fas fa-pencil-alt text-warning' data-bs-toggle="modal"
											data-bs-target="#editModal" onclick="doEdit(${item.id})"></span> </a> 
						<a class='mr-4 delet'> <span class='fas fa-trash-alt text-danger' onclick="doDelete(${item.id})"></span> </a>
					</td>
					</tr>
					`
			}))
		}
	})
}