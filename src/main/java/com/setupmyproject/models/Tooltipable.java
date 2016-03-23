package com.setupmyproject.models;


/**
 * Em geral os addons precisam ter uma descrição do que oferecem. Essa interface define o métod que deve ser usado 
 * para isso.
 * @author alberto
 *
 */
public interface Tooltipable {

	/**
	 * 
	 * @return chave que vai ser usada para encontrar o texto.
	 */
	default String getTooltipKey() {
		return "tooltip."+this.name();
	}
	
	/**
	 * Hack. Essa interface, geralmente vai ser usdada por alguma classe/enum que vai virar um checkbox ou radio button 
	 * na página. Como a maioria é enum e a mesma já possui um método chamado name(), eu estou aproveitando.
	 * @return
	 */
	public String name();
}
