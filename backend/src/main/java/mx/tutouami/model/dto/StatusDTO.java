package mx.tutouami.model.dto;

import java.io.Serializable;

import lombok.Data;
import mx.tutouami.model.enums.StudentStatusTypes;

@Data
public class StatusDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -541984637348326761L;
	private StudentStatusTypes status;
}
