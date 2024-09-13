package alexdigioia.s6l5.repositories;

import alexdigioia.s6l5.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViaggiRepository extends JpaRepository<Viaggio, UUID> {

}
