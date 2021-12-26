package com.felipe.minhasfinancas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

    private boolean valid;

    private String messageException;

}
