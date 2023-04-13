package com.jose.restapialura.domain.medico;

import com.jose.restapialura.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
//        String especialidad,
        String documento,
        DatosDireccion direccion
) {
}
