/**
 obj:
 {
    elem:div元素ID
    totalCount:总条数
    size:页容量
    skip:回调函数:两个参数,
    cur:当前页
  }
 */

var uooziPageDscripter = {};
function toolPage(obj){
	var pageSize = 10;
	var curPage = 1;
	var indexes = [];

	var jump = obj.skip;
	if(obj.size){
		pageSize = obj.size;
	}
	var totalCount = obj.totalCount;
	var pageCount = Math.ceil(totalCount / pageSize);
	curPage = obj.cur;
	var startItem = (curPage - 1) * pageSize + 1;
	var endItem = ((curPage * pageSize) < totalCount)?(curPage * pageSize):(totalCount);
	//获得容器
	var container = document.getElementById(obj.elem);
	uooziPageDscripter[obj.elem] = {pageSize:pageSize,curPage:curPage,indexes:[]};
	//uooziPageDscripter[obj.elem].indexes
	//清空容器
	container.innerHTML = "";
	container.className = "page clearfix";
	//左边的div
	var leftDiv = document.createElement("div");
	leftDiv.className = "pageLeft";
	//左边div添加到容器
	container.appendChild(leftDiv);
	//想左边DIV添加内容
	leftDiv.innerHTML += "当前显示";
	//当前显示起
	var curStart = document.createElement("span");
	curStart.innerHTML = startItem;
	leftDiv.appendChild(curStart);
	leftDiv.innerHTML += "到";
	//当前显示止
	var curEnd = document.createElement("span");
	curEnd.innerHTML = endItem;
	leftDiv.appendChild(curEnd);
	leftDiv.innerHTML += "项,共";
	//总条数
	var totalRecord = document.createElement("span");
	totalRecord.innerHTML = totalCount;
	leftDiv.appendChild(totalRecord);
	leftDiv.innerHTML += "项 单页";
	//页面容量选择器
	var sizeSelector = document.createElement("select");
	var optionArray = [];
	var opt10 = document.createElement("option");
	opt10.value="10";
	opt10.innerHTML="10";
	optionArray.push(opt10);
	var opt20 = document.createElement("option");
	opt20.value="20";
	opt20.innerHTML="20";
	optionArray.push(opt20);
	var opt50 = document.createElement("option");
	opt50.value="50";
	opt50.innerHTML="50";
	optionArray.push(opt50);

	//添加option
	for(var i in optionArray){
		if(optionArray[i].value == uooziPageDscripter[obj.elem].pageSize){
			optionArray[i].selected = "selected";
		}
		sizeSelector.appendChild(optionArray[i]);
	}

	//每页条数变化时调用函数
	sizeSelector.onchange=function(){
		uooziPageDscripter[obj.elem].pageSize = this.value;
		uooziPageDscripter[obj.elem].curPage = 1;
		jump(uooziPageDscripter[obj.elem].curPage,uooziPageDscripter[obj.elem].pageSize);
	}

	leftDiv.appendChild(sizeSelector);

	var rightDiv = document.createElement("div");
	rightDiv.className = "pageRight clearfix";
	container.appendChild(rightDiv);
	//上一页箭头
	var prevPageButton = document.createElement("div");
	prevPageButton.className="pre";
	//添加上一页事件
	prevPageButton.onclick=function(){
		if (uooziPageDscripter[obj.elem].curPage == 1) {
			return;
		}
		jump(--uooziPageDscripter[obj.elem].curPage,uooziPageDscripter[obj.elem].pageSize);
	}
	rightDiv.appendChild(prevPageButton);

	//中间页码容器
	realPageContainer = document.createElement("ul");
	realPageContainer.className="clearfix";
	rightDiv.appendChild(realPageContainer);

	//清空数组
	uooziPageDscripter[obj.elem].indexes = [];

	//定位原则:
	//第一个和最后一个必须显示
	//当前元素的前两个必须显示,若前边元素不足两个,则后边增加不足的
	//当前元素的后两个必须显示,若后边元素不足两个,则前边增加不足的
	//若总数小于等于5个,全部显示
	if(pageCount <= 5 ){
		for(var i = 1 ; i <= pageCount ; i++){
			var pageSelector = document.createElement("li");
			pageSelector.innerHTML = i;
			uooziPageDscripter[obj.elem].indexes.push(pageSelector);
		}
	}else{
		//当前页是小于4
		if(uooziPageDscripter[obj.elem].curPage <= 4){
			for(var i = 1 ; i <= 4 ; i++){
				var pageSelector = document.createElement("li");
				pageSelector.innerHTML = i;
				uooziPageDscripter[obj.elem].indexes.push(pageSelector);
			}
			//填充物
			var firstFiller = document.createElement("li");
			firstFiller.className = "spr";
			firstFiller.innerHTML = "...";
			uooziPageDscripter[obj.elem].indexes.push(firstFiller);
			//最后一页
			var lastPage = document.createElement("li");
			lastPage.innerHTML = pageCount;
			uooziPageDscripter[obj.elem].indexes.push(lastPage);
		}//当前页是大于倒数第四
		else if (uooziPageDscripter[obj.elem].curPage >= pageCount - 4) {
			//第一页
			var firstPage = document.createElement("li");
			firstPage.innerHTML = 1;
			uooziPageDscripter[obj.elem].indexes.push(firstPage);
			//填充物
			var firstFiller = document.createElement("li");
			firstFiller.className = "spr";
			firstFiller.innerHTML = "...";
			uooziPageDscripter[obj.elem].indexes.push(firstFiller);
			//倒数4页
			for(var i = pageCount - 4 ; i <= pageCount ; i++){
				var pageSelector = document.createElement("li");
				pageSelector.innerHTML = i;
				uooziPageDscripter[obj.elem].indexes.push(pageSelector);
			}
		}//当前页大于4并小于倒数4
		else {
			//第一页
			var firstPage = document.createElement("li");
			firstPage.innerHTML = 1;
			uooziPageDscripter[obj.elem].indexes.push(firstPage);
			//填充物
			var firstFiller = document.createElement("li");
			firstFiller.className = "spr";
			firstFiller.innerHTML = "...";
			uooziPageDscripter[obj.elem].indexes.push(firstFiller);
			//中间3页
			for(var i = uooziPageDscripter[obj.elem].curPage - 1 ; i <= parseInt(uooziPageDscripter[obj.elem].curPage) + 1 ; i++){
				var pageSelector = document.createElement("li");
				pageSelector.innerHTML = i;
				uooziPageDscripter[obj.elem].indexes.push(pageSelector);
			}
			//填充物
			var lastFiller = document.createElement("li");
			lastFiller.className = "spr";
			lastFiller.innerHTML = "...";
			uooziPageDscripter[obj.elem].indexes.push(lastFiller);
			//最后一页
			var lastPage = document.createElement("li");
			lastPage.innerHTML = pageCount;
			uooziPageDscripter[obj.elem].indexes.push(lastPage);
		}
	}



	//为当前页增加一个on 的class

	for(var i in uooziPageDscripter[obj.elem].indexes){
		var index = uooziPageDscripter[obj.elem].indexes[i];
		realPageContainer.appendChild(index);
		//...不处理
		if (uooziPageDscripter[obj.elem].indexes[i].className == "spr") {
			continue;
		}
		index.onclick=function(){
			uooziPageDscripter[obj.elem].curPage = this.innerHTML;
			jump(uooziPageDscripter[obj.elem].curPage,uooziPageDscripter[obj.elem].pageSize);
		}
		index.className = "";
		if(index.innerHTML == uooziPageDscripter[obj.elem].curPage){
			index.className = "on";
		}
	}
	//下一页箭头
	var nextPageButton = document.createElement("div");
	nextPageButton.className="pro";
	//添加上一页事件
	nextPageButton.onclick=function(){
		if (uooziPageDscripter[obj.elem].curPage == pageCount) {
			return;
		}
		jump(++uooziPageDscripter[obj.elem].curPage,uooziPageDscripter[obj.elem].pageSize);
	}
	rightDiv.appendChild(nextPageButton);

	//跳页输入框
	var skipInput = document.createElement("input");
	rightDiv.appendChild(skipInput);
	var goButton = document.createElement("div");
	goButton.className = "go";
	goButton.innerHTML = "GO";
	goButton.onclick = function(){
		uooziPageDscripter[obj.elem].curPage = skipInput.value;
		jump(uooziPageDscripter[obj.elem].curPage,uooziPageDscripter[obj.elem].pageSize);
	}
	rightDiv.appendChild(goButton);
}
function getCurrentPage(elem){
	return uooziPageDscripter[elem].curPage;
}
