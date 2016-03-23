if(document.forms[0]){
	var spans = document.forms[0].querySelectorAll("span");
	
	for (var i = 0; i < spans.length; i++) {
			var input = spans[i].querySelector("input");
			if(input && input.title){
				var tooltip = document.createElement('span');
				tooltip.setAttribute('class',"tooltip");
				tooltip.setAttribute('data-tooltip', input.title);
				tooltip.innerHTML = '<i class="fa fa-info-circle"></i>';				
				spans[i].appendChild(tooltip);
			}	
	}
}