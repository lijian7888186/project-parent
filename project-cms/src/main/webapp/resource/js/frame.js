var createMenu = function(nodes){
	var menu = document.getElementsByClassName("menu").item(0);
	//菜单
	for (var i in nodes) {
		var node = nodes[i];
		//父级菜单
		var fatherMenu = document.createElement("div");
		fatherMenu.className = "fatherMenu";
		menu.appendChild(fatherMenu);
		//父级菜单名称
		var nameText = document.createElement("span");
		nameText.className = "nameText";
		nameText.name = node.name;
		nameText.textContent = node.name;
		fatherMenu.appendChild(nameText);
		//父级菜单小箭头
		var nameArrow = document.createElement("span");
		nameArrow.className = "nameArrow";
		fatherMenu.appendChild(nameArrow);
		//子级菜单容器
		var childContainer = document.createElement("div");
		childContainer.className = "childContainer";
		fatherMenu.appendChild(childContainer);
		for (var j in node.children) {
			//子菜单
			var childNode = node.children[j];
			//每个子菜单
			var childMenu = document.createElement("div");
			childMenu.className = "childMenu";
			childMenu.href = childNode.url;
			childMenu.name = childNode.name;
			childMenu.textContent = childNode.name;
			childContainer.appendChild(childMenu);
		}
	}
	
	//右侧容器
	var contentContainer = document.getElementsByClassName("contentContainer").item(0);
	//右侧头部
	var contentHead = document.getElementsByClassName("contentHead").item(0);
	
	//标签容器
	var frameTagContainer = contentContainer.getElementsByClassName("frameTagContainer").item(0);
	
	//窗口容器
	var frameContainer = contentContainer.getElementsByClassName("frameContainer").item(0);
	
	//为父菜单添加点击事件
	var parentMenus = document.getElementsByClassName("fatherMenu");
	for (var i = 0; i < parentMenus.length; i++) {
		var parentMenu = parentMenus.item(i);
		parentMenu.onclick = showChildMenus;
	}
	
	//为子菜单添加点击事件
	var childMenus = document.getElementsByClassName("childMenu");
	for (var i = 0; i < childMenus.length; i++) {
		var childMenu = childMenus.item(i);
		childMenu.onclick = childMenuClick;
	}
}

var showChildMenus = function (eve) {
	let list = this.childNodes;
	for (let i = 0; i < list.length; i++) {
		if (list.item(i).className == "childContainer") {
			if (list.item(i).style.display.trim() == "block") {
				list.item(i).style.display = "none";
			} else {
				list.item(i).style.display = "block";
			}
		}
	}
}

var childMenuClick = function (event) {
	event.stopPropagation();
	var childMenus = document.getElementsByClassName("childMenu");
	for (var i = 0; i < childMenus.length; i++) {
		removeClass(childMenus.item(i), "selected");
	}
	addClass(this, "selected");
	// window.open(this.href,"tag1");
	//点击检查是否存在标签
	if (checkTag(this.href)) {
		checkTag(this.href).click();
	} else {//如果不存在那么创建一个标签
		var tagContainer = document.getElementsByClassName("frameTagContainer").item(0);
		var tag = document.createElement("span");
		tag.textContent = this.name;
		tag.className = "tag";
		tagContainer.appendChild(tag);
		tag.href = this.href;
		//为tag添加X号
		var x = document.createElement("span");
		x.textContent = "x";
		x.className = "closeTagButton"
		tag.appendChild(x);
		//为x号添加移动和点击事件
		x.onclick = closeTag;
		//tag为选中状态
		//先移除其他tag的选中状态
		for (var i = 0; i < tagContainer.childNodes.length; i++) {
			removeClass(tagContainer.childNodes.item(i), "tagSelected");
			addClass(tagContainer.childNodes.item(i), "tagNormal");
			tagContainer.childNodes.item(i).selected = false;
		}
		addClass(tag, "tagSelected");
		//创建iframe
		var frameContainer = document.getElementsByClassName("frameContainer").item(0);
		
		hideAllFrame();
		var iframe = document.createElement("iframe");
		iframe.name = this.name;
		iframe.style.display = "block";
		iframe.src = this.href;
		frameContainer.appendChild(iframe);
		
		//标签与iframe绑定起来
		tag.frameRelation = iframe;
		tag.menuRelation = this;
		tag.selected = true;
		//为标签添加onclick
		tag.onclick = changeTag;
	}
}

//点击x号
var closeTag = function (event) {
	event.stopPropagation();
	if (this.parentNode.selected && this.parentNode.parentNode.childNodes.length - 2 >= 0) {
		this.parentNode.parentNode.childNodes.item(this.parentNode.parentNode.childNodes.length - 2).click();
	}
	//如果是唯一的元素,顺便把左侧菜单栏的选中状态清空
	if (this.parentNode.parentNode.childNodes.length == 1) {
		var childMenus = document.getElementsByClassName("childMenu");
		for (var i = 0; i < childMenus.length; i++) {
			removeClass(childMenus.item(i), "selected");
		}
	}
	this.parentNode.parentNode.removeChild(this.parentNode);
	this.parentNode.frameRelation.parentNode.removeChild(this.parentNode.frameRelation);
}

//切换标签方法
var changeTag = function (event) {
	if (event.target != this) {
		return;
	}
	var frameTags = document.getElementsByClassName("tag");
	for (var i = 0; i < frameTags.length; i++) {
		var frameTag = frameTags.item(i);
		removeClass(frameTag, "tagSelected");
		addClass(frameTag, "tagNormal");
		frameTags.item(i).selected = false;
	}
	this.selected = true;
	addClass(this, "tagSelected");
	//iframe联动
	hideAllFrame();
	this.frameRelation.style.display = "block";
	if (this.menuRelation.parentNode.style.display.indexOf("none") != -1) {
		this.menuRelation.parentNode.style.display = "block";
	}
	//菜单联动
	var childMenus = document.getElementsByClassName("childMenu");
	for (var i = 0; i < childMenus.length; i++) {
		removeClass(childMenus.item(i), "selected");
	}
	addClass(this.menuRelation, "selected");
}

var checkTag = function (tagHref) {
	var tags = document.getElementsByClassName("tag");
	for (var i = 0; i < tags.length; i++) {
		if (tags.item(i).href == tagHref) {
			return tags.item(i);
		}
	}
	return null;
}

//函数,隐藏所有frame
var hideAllFrame = function () {
	//先把所有iframe隐藏起来
	var frameContainer = document.getElementsByClassName("frameContainer").item(0);
	var allFrames = frameContainer.childNodes;
	for (var i = 0; i < allFrames.length; i++) {
		allFrames.item(i).style.display = "none";
	}
}

//添加class
var addClass = function (dom, className) {
	if (!dom.className) {
		dom.className = className;
		return;
	}
	if (dom.className.indexOf(className) == -1) {
		dom.className += " " + className;
	}
}

//移除class
var removeClass = function (dom, className) {
	if (!dom.className) {
		return;
	}
	if (dom.className.indexOf(className) > 0) {
		dom.className = dom.className.replace(className, "");
	}
}
