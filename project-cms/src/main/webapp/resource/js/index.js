window.onload = function () {
	checkLogin();
	loadModule();
}
function loadModule(){
	tool.ajax({
		url:'login/getModule',
		success:function(data){
			createMenu(data.data);
		}
	})
}
function checkLogin(){
	tool.ajax({
		url:'login/checkLogin',
		success:function(data){
			if (data.code == "10001") {
				location.href = "login.html";
			}
		}
	})
}
function logout(){
	tool.ajax({
		url:"login/logout",
		success:function(data){
			location.href = "login.html";
		},
		error:function(){
			location.href = "login.html";
		}
	})
}

function showChangePasswordDialog(){
	$(".layer").show();
	$("#changePasswordDialog").show();
	$("#curPassword").val('');
	$("#newPassword").val('');
	$("#checkNewPassword").val('');
}

function hideChangePasswordDialog(){
	$(".layer").hide();
	$("#changePasswordDialog").hide();
	$("#curPassword").val('');
	$("#newPassword").val('');
	$("#checkNewPassword").val('');
}
function confirmChangePassword() {
	var curPassword = $("#curPassword").val();
	var newPassword = $("#newPassword").val();
	var checkNewPassword = $("#checkNewPassword").val();
	if (checkNewPassword != newPassword){
		tool.alert("两次密码输入不一致");
	}
	tool.ajax({
		data:{currentPassword:curPassword,newPassword:newPassword},
		url:'admin/changePassword',
		success:function(data){
			if (data.code == '00000') {
				tool.alert("修改成功,请使用新密码登录").then(function () {
					logout();
				});
			}
		}
	})
}

