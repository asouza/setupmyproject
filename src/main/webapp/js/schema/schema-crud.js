schemaCrudTags = {		
	models : {
		attrs : {},
		children : [ "model" ]
	},
	model : {
		attrs : {
			name : null,
			viewController : [ "true", "false" ]
		},
		children : [ "field" ]
	},
	field : {
		attrs : {
			name : null,
			type : null,
			javaType : [ "true", "false" ],
			formInputType : [ "select", "text","radioButton","checkbox","date","email","password" ],
			formInputName : null,
			selectLabelField : null
		}
	}
};
