package br.com.yomu.gamificacaoDaLeitura.dto;

import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoProgresso;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgressoCreateDTO {
    
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;
    
    @NotNull(message = "Tipo de progresso é obrigatório")
    private TipoProgresso tipoProgresso;
}