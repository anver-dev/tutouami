package mx.tutouami.model.enums;

public enum SecurityTypes {
	
	TOKEN("Bearer Token");
	
	private String value;
	
	SecurityTypes(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
