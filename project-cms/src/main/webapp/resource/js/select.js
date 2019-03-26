var repaintSelect = function() {
	const selects = document.getElementsByClassName("select-repaint");
	for (var i = 0; i < selects.length; i++) {
		var select = selects.item(i);
		repaintSingleSelect(select);
	}
}
var repaintedSelectors = [];
var repaintSingleSelect = function(select){
	//增加一个addChild函数
	if (!Element.prototype.addChild){
		Element.prototype.addChild = function(tagName){
			var element = document.createElement(tagName);
			this.appendChild(element);
			return element;
		}
	}
	if(select.repainted){
		if (select.selector) {
			for ( var i in select.options){
				if (select.options[i].selected){
					select.selector.getElementsByClassName("leftText").item(0).textContent = select.options[i].textContent;
					break;
				}
			}
		}
		return;
	}
	select.repainted = true;
	var selectStyle = select.getAttribute("style");
	select.style.display = "none";
	const options = select.options;

	const selector = document.createElement("span");
	repaintedSelectors.push(selector);
	select.parentNode.insertBefore(selector, select);
	selector.className = "selector";
	selector.style = selectStyle;
	selector.showing = false;
	select.selector = selector;

	const optDisplayer = document.createElement("span");
	optDisplayer.className = "optDisplayer";
	optDisplayer.style = selectStyle;
	selector.appendChild(optDisplayer);
	const leftText = document.createElement("span");
	optDisplayer.leftText = leftText;
	leftText.selector = selector;
	leftText.className = "leftText";
	optDisplayer.appendChild(leftText);
	//做个关联
	optDisplayer.selector = selector;
	selector.optDisplayer = optDisplayer;

	if(select.getAttribute("placeholder")){
		if(select.options[0]){
			select.options[0].selected = true;
		}
		leftText.textContent = select.getAttribute("placeholder");
		leftText.style.color = "#ccc";
		leftText.style.fontSize = "12px";
	}else{
		if(select.options[0]){
			leftText.textContent = select.options[select.selectedIndex].textContent;
		}else{
			//do nothing
		}
	}
	const downArrow = document.createElement("span");
	downArrow.selector = selector;
	var arrow = document.createElement("span");
	arrow.selector = selector;
	arrow.className = "arrow";
	downArrow.appendChild(arrow);
	downArrow.className = "downArrow";
	optDisplayer.appendChild(downArrow);

	const optContainer = document.createElement("span");
	//做个关联
	selector.optContainer = optContainer;
	optContainer.selector = selector;


	optContainer.className = "optContainer";
	selector.appendChild(optContainer);

	var hideSelectors = function(event){
		if (event.target.repainted || event.target.selector){
			for (const repaintedSelector of repaintedSelectors) {
				if (repaintedSelector == event.target.selector) {
					continue;
				}
				repaintedSelector.hide();
			}
			return;
		}
		for (const repaintedSelector of repaintedSelectors) {
			repaintedSelector.hide();
		}
	}

	selector.show = function(){
		this.showing = true;
		this.optContainer.style.display = "inline";
		document.addEventListener('click',hideSelectors);
	}
	selector.hide = function(){
		this.showing = false;
		this.optContainer.style.display = "none";
	}
	selector.showOrHide = function(){
		if (this.showing){
			this.hide();
		}else{
			this.show();
		}
	}
	selector.onclick = function(event){
		this.showOrHide();
	}

	for (var j = 0; j < options.length; j++) {
		const option = options[j];
		const opt = document.createElement("span");
		opt.optionRelative = option;
		opt.selectRelative = select;
		opt.textContent = option.textContent;
		opt.selector = selector;
		opt.onclick = function() {
			this.optionRelative.selected = true;
			leftText.textContent = opt.selectRelative.options[opt.selectRelative.selectedIndex].textContent;
			leftText.style.color = "";//清除内联样式
			leftText.style.fontSize = "";
			optContainer.style.display = "none";
			selector.showing = false;
			if (opt.selectRelative.onchange) {
				opt.selectRelative.onchange();
			}
			opt.selector.showOrHide();
		}
		optContainer.appendChild(opt);
	}
	//为select增加一个disable方法和enable方法
	select.disable = function(){
		//leftText 和 downArrow 添加一个禁用样式
		optDisplayer.style.backgroundColor = '#dadada';
		optDisplayer.style.borderColor = '#dadada';
		downArrow.style.backgroundColor = '#c8edfc';
		optDisplayer.style.color = '#89817f';
		select.disabled = true;
		optDisplayer.removeEventListener('click',doSelect);
	}
	select.enable = function(){
		//leftText 和 downArrow 删除内联样式
		optDisplayer.style.backgroundColor = '';
		optDisplayer.style.borderColor = '';
		downArrow.style.backgroundColor = '';
		optDisplayer.style.color = '';
		select.disabled = false;
		optDisplayer.addEventListener('click',doSelect);
	}
	//新增选项,用于二级联动
	select.addOption = function(value,text){
		var oneOfOtp = document.createElement("option");
		oneOfOtp.value = value;
		oneOfOtp.textContent = text;

		select.appendChild(oneOfOtp);
		const opt = document.createElement("span");
		opt.optionRelative = oneOfOtp;
		opt.selectRelative = select;
		opt.textContent = oneOfOtp.textContent;
		opt.selector = selector;
		opt.onclick = function() {
			this.optionRelative.selected = true;
			leftText.textContent = opt.selectRelative.options[opt.selectRelative.selectedIndex].textContent;
			leftText.style.color = "";//清除内联样式
			leftText.style.fontSize = "";
			optContainer.style.display = "none";
			selector.showing = false;
			if (opt.selectRelative.onchange) {
				opt.selectRelative.onchange();
			}
			opt.selector.showOrHide();
		}
		this.selector.optContainer.appendChild(opt);
	}
	select.removeAllOptions = function(){
		select.innerHTML = "";
		this.selector.optContainer.innerHTML = "";
		this.selector.optDisplayer.leftText.textContent = this.getAttribute("placeholder");
		this.selector.optDisplayer.leftText.style.color = "#cccccc";
		this.selector.optDisplayer.leftText.style.fontSize = "12px";
	}
}