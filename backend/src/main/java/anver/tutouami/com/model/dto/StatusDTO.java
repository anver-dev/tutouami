package anver.tutouami.com.model.dto;

import java.io.Serializable;

import anver.tutouami.com.model.enums.StudentStatusTypes;
import lombok.Data;

@Data
public class StatusDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -541984637348326761L;
	private StudentStatusTypes status;
}
