let currentPage = 0;
let pageSize = 5;
let lastPage = 0;

const getAll = () => {
	let pageCategorys = [];
	$.ajax({
		type: "GET",
		url: "/api/categorys",
		data: {
			page: currentPage,
			size: pageSize
		},
		success: function(resp) {
			pageCategorys = resp.content;
			console.log(pageCategorys);
			lastPage = resp.totalPages - 1;
			$("#dataCategory").html(pageCategorys.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>${item.categoryParent.name}</td>
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

const doCreate = () => {
	let name = $("#name").val();
	let categoryParent = $("#categoryParent").val();
	let isActive = document.querySelector('input[name="isActive"]:checked').value;
	let Category = {
		name: name,
		isActive: isActive,
		categoryParent: {
			id: categoryParent
		}
	};
	if (name.length === 0) {
		$("#name_error").text("Tên danh mục con không được để trống!");
		return;
	} else {
		$("#name_error").text("");
	}

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/api/categorys",
		data: JSON.stringify(Category),
		dataType: 'json',
		success: function() {
			$("#name").val("");
			document.querySelector('input[name="isActive"]').checked = true;
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

const doDelete = (cateId) => {
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
				url: "/api/categorys/" + cateId,
				success: function() {
					Swal.fire("Hoàn thành", "Xóa thành công! Mã danh mục con " + '[' + cateId + ']', "success");
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

const doEdit = (cateId) => {
	let url = "/api/categorys/" + cateId;
	$.get(url).done(function(data) {
		$('#idEdit').val(data.id);
		$('#nameEdit').val(data.name);
		$('#categoryParentEdit').val(data.categoryParent.id);
	})
}

const doUpdate = () => {
	let id = $("#idEdit").val();
	let name = $("#nameEdit").val();
	let categoryParent = $('#categoryParentEdit').val();
	let Category = {
		id: id,
		name: name,
		categoryParent: {
			id: categoryParent
		}
	};
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: "/api/categorys",
		data: JSON.stringify(Category),
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
		url: "/api/categorys/search?keyword=" + keyword,
		dataType: 'json',
		success: function(resp) {
			console.log(resp);
			$("#dataCategory").html(resp.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>${item.categoryParent.name}</td>
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
	})
}