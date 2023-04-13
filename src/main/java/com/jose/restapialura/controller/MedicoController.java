package com.jose.restapialura.controller;

import com.jose.restapialura.domain.direccion.DatosDireccion;
import com.jose.restapialura.domain.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        Medico medico = repository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
                        medico.getId(),
                        medico.getNombre(),
                        medico.getEmail(),
                        medico.getTelefono(),
                        medico.getEspecialidad().toString(),
                        new DatosDireccion(
                                medico.getDireccion().getCalle(),
                                medico.getDireccion().getDistrito(),
                                medico.getDireccion().getCiudad(),
                                medico.getDireccion().getNumero(),
                                medico.getDireccion().getComplemento()
                        ));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 15) Pageable paginacion){
        return ResponseEntity.ok(repository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
//        return repository.findAll(paginacion).map(DatosListadoMedico::new);
    }
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = repository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                        )));
    }

    @DeleteMapping("/{id}")
    @Transactional
    // Delete lógico
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    // Delete lógico
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        var datosMedicos = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                ));
        return ResponseEntity.ok(datosMedicos);
    }
}
