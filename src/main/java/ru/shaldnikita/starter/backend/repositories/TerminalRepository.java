package ru.shaldnikita.starter.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaldnikita.starter.backend.model.TerminalState;

/**
 * @author n.shaldenkov on 17/01/2018
 */


public interface TerminalRepository extends JpaRepository<TerminalState, Long> {


}

