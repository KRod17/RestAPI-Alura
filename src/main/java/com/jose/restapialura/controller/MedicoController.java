package com.jose.restapialura.controller;

import com.jose.restapialura.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;
    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        repository.save(new Medico(datosRegistroMedico));
    }
    @GetMapping
    public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){
        return repository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
//        return repository.findAll(paginacion).map(DatosListadoMedico::new);
    }
    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = repository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);

    }

    @DeleteMapping("/{id}")
    @Transactional
    // Delete lógico
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        medico.desactivarMedico();
    }


//    Borrado total de la base de datos
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico = repository.getReferenceById(id);
//        repository.delete(medico);
//
//    }
}
