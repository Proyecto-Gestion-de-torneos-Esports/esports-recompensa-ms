package esports.Recompensas.assemblers;

import esports.Recompensas.controller.RecompensaController;
import esports.Recompensas.dto.RecompensaResponseDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecompensaModelAsemblers implements RepresentationModelAssembler<RecompensaResponseDTO, RecompensaResponseDTO> {
    @Override
    public RecompensaResponseDTO toModel(RecompensaResponseDTO recompensa) {
        recompensa.add(linkTo(methodOn(RecompensaController.class).buscarPorId(recompensa.getRecompensaId())).withSelfRel());

        recompensa.add(linkTo(methodOn(RecompensaController.class).obtenerTodos()).withRel("todas-las-recompensas"));

        return recompensa;
    }
}
