package mx.tutouami.model.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.tutouami.model.entity.Account;

/**
 * DTO de usuarios
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    
    private long idAccount;
    
    @NotEmpty
    private String email; // No vacío, no numérico
    
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
    private String password;
    
    private TokenDTO token; // No vacío
        
    /**
     * Este método permite generar un DTO a partir de la entidad
     * nota: es un método de clase y no se necesita un objeto
     * para invocarlo. Se invoca como UsuarioDto.crea(param)
           * @param usuario la entidad
     * @return dto obtenido a partir de la entidad
     */
    public static AccountDTO generate(Account account, TokenDTO token) {
        return AccountDTO.builder()
        		.idAccount(account.getId())
        		.email(account.getEmail())
        		.token(token)
        		.build();
    }
}
