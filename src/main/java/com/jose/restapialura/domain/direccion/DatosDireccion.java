package com.jose.restapialura.domain.direccion;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccion(
        // Letras y números - No puede llegar vacío
        @NotBlank
        String calle,
        @NotBlank
        String distrito,
        // Letras y números - No puede llegar vacío
        @NotBlank
        String ciudad,
        //Sólo números - No puede llegar vacío
        @NotBlank
        String numero,
        // // Letras y números
        @NotBlank
        String complemento) {
}
