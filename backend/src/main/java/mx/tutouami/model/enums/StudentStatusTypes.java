package mx.tutouami.model.enums;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum StudentStatusTypes {
	
	AVAILABLE("DISPONIBLE"),
	LOW_AVAILABILITY("POCA DISPONIBILIDAD");
	
	private String value;
	
	StudentStatusTypes(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
