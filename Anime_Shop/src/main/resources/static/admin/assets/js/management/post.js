let currentPage = 0;
let pageSize = 9;
let lastPage = 0;
let imageUpload;
let imageUpdateUpload;

const getAll = () => {
	let pagePost = [];
	$.ajax({
		type: "GET",
		url: "/api/posts",
		data: {
			page: currentPage,
			size: pageSize
		},
		success: function(resp) {
			pagePost = resp.content;
			console.log(pagePost);
			lastPage = resp.totalPages - 1;
			$("#dataPost").html(pagePost.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td>${item.subject}</td>
					<td style="display: -webkit-box;
					-webkit-line-clamp: 10;
 					 -webkit-box-orient: vertical;
  					overflow: hidden;">${item.body}</td>
					<td>${item.view}</td>
					<td><img src="/assets/images/${item.image}" style="width:50px; height: 50px;" class="rounded"></td>
					<td>${item.description}</td>
					<td>${item.createdBy ? item.createdBy : 'Trống'}</td>
					<td>${item.updatedBy ? item.updatedBy : 'Trống'}</td>
					<td>${item.createDate ? item.createDate : 'Trống'}</td>
					<td>${item.updateDate ? item.updateDate : 'Trống'}</td>
					<td style="display: -webkit-box;
					-webkit-line-clamp: 10;
 					 -webkit-box-orient: vertical;
  					overflow: hidden;">${item.product.name}</td>
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
	let subject = $("#subject").val();
	let body = $("#body").val();
	let view = $("#view").val();
	let slug = $("#slug").val();
	let productId = $("#productId").val();
	let description = $("#description").val();
	let isActive = document.querySelector('input[name="isActive"]:checked').value;
	let post = {
		subject: subject,
		body: body,
		view: view,
		slug: slug,
		image: imageUpload,
		description: description,
		isActive: isActive,
		product: {
			id: productId
		}
	};
	if (subject.length === 0) {
		$("#subject_error").text("Tiêu đề bài viết không được để trống!");
		return;
	} else {
		$("#subject_error").text("");
	}
	if (body.length === 0) {
		$("#body_error").text("Nội dung bài viết không được bỏ trống!");
		return;
	} else {
		$("#body_error").text("");
	}
	if (slug.length === 0) {
		$("#slug_error").text("Mã hóa bài viết không được bỏ trống!");
		return;
	} else {
		$("#slug").text("");
	}
	if (imageUpload == null) {
		$("#image_error").text("Hình ảnh bài viết không được bỏ trống!");
		return;
	} else {
		$("#image_error").text("");
	}


	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/api/posts",
		data: JSON.stringify(post),
		dataType: 'json',
		success: function() {
			$("#subject").val("");
			$("#body").val("");
			$("#view").val("");
			$("#slug").val("");
			$("#description").val("");
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

const doDelete = (postId) => {
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
				url: "/api/posts/" + postId,
				success: function() {
					Swal.fire("Hoàn thành", "Xóa thành công! Mã đơn vị " + '[' + postId + ']', "success");
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
const doEdit = (postId) => {
	let url = "/api/posts/" + postId;
	$.get(url).done(function(data) {
		$('#idEdit').val(data.id);
		$('#subjectEdit').val(data.subject);
		$('#bodyEdit').val(data.body);
		$('#slugEdit').val(data.slug);
		$('#viewEdit').val(data.view);
		$('#productIdEdit').val(data.product.id);
		$('#descriptionEdit').val(data.description);
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
	let subject = $("#subjectEdit").val();
	let body = $("#bodyEdit").val();
	let view = $("#viewEdit").val();
	let slug = $("#slugEdit").val();
	let productId = $("#productIdEdit").val();
	let description = $("#descriptionEdit").val();
	let post = {
		id: id,
		subject: subject,
		body: body,
		view: view,
		slug: slug,
		image: imageUpdateUpload,
		description: description,
		product: {
			id: productId
		}
	};
	if (subject.length === 0) {
		$("#subject_error").text("Tiêu đề bài viết không được để trống!");
		return;
	} else {
		$("#subject_error").text("");
	}
	if (body.length === 0) {
		$("#body_error").text("Nội dung bài viết không được bỏ trống!");
		return;
	} else {
		$("#body_error").text("");
	}
	if (slug.length === 0) {
		$("#slug_error").text("Mã hóa bài viết không được bỏ trống!");
		return;
	} else {
		$("#slug").text("");
	}

	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: "/api/posts",
		data: JSON.stringify(post),
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
	let keyword = $('#keyword_search').val();
	let pagePost = [];
	$.ajax({
		type: "GET",
		url: "/api/posts/search?keyword=" + keyword,
		data: {
			page: currentPage,
			size: pageSize
		},
		success: function(resp) {
			pagePost = resp.content;
			console.log(pagePost);
			lastPage = resp.totalPages - 1;
			$("#dataPost").html(pagePost.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td>${item.subject}</td>
					<td style="display: -webkit-box;
					-webkit-line-clamp: 10;
 					 -webkit-box-orient: vertical;
  					overflow: hidden;">${item.body}</td>
					<td>${item.view}</td>
					<td><img src="/assets/images/${item.image}" style="width:50px; height: 50px;" class="rounded"></td>
					<td>${item.description}</td>
					<td>${item.createdBy ? item.createdBy : 'Trống'}</td>
					<td>${item.updatedBy ? item.updatedBy : 'Trống'}</td>
					<td>${item.createDate ? item.createDate : 'Trống'}</td>
					<td>${item.updateDate ? item.updateDate : 'Trống'}</td>
					<td style="display: -webkit-box;
					-webkit-line-clamp: 10;
 					 -webkit-box-orient: vertical;
  					overflow: hidden;">${item.product.name}</td>
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