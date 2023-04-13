package com.jose.restapialura.domain.medico;

import com.jose.restapialura.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;;

public record DatosRegistroMedico(
        // Solo letras - No puede llegar vacío
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,
        // Formato email - - No puede llegar vacío
        @NotBlank
        @Email(message = "Formato de email es inválido")
        String email,
        @NotBlank
        String telefono,
        // Sólo números - No puede llegar vacío
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") // De 4 a 6 dígitos.
        String documento,
        // Ortopedia, Ginecologia, Cardiologia, Pediatria - No puede llegar vacío
        @NotNull
        Especialidad especialidad,
        @NotNull
        @Valid
        DatosDireccion direccion) {
}
