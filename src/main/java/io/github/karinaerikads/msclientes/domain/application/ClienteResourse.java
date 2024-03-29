package io.github.karinaerikads.msclientes.domain.application;

import io.github.karinaerikads.msclientes.domain.application.representation.ClienteSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteResourse {

    private final ClienteService service;
    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        var cliente = request.toModel();
        service.save(cliente);
        URI hearderLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(hearderLocation).build();
    }

    @GetMapping (params = "cpf")
    public ResponseEntity dadosClientes(@RequestParam("cpf") String cpf){
        var cliente = service.getByCpf(cpf);
        if (cliente.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cliente);
    }
}
