package anver.tutouami.com.model.enums;

public enum SecurityTypes {
	
	TOKEN("Bearer ");
	
	private String value;
	
	SecurityTypes(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
