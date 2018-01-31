package ru.shaldnikita.starter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.shaldnikita.starter.backend.model.TerminalState;
import ru.shaldnikita.starter.backend.repositories.TerminalRepository;

/**
 * @author n.shaldenkov on 17/01/2018
 */

@Service
public class TerminalStateService extends CrudService<TerminalState> {

    @Autowired
    TerminalRepository terminalRepository;

    @Override
    protected JpaRepository<TerminalState, Long> getRepository() {
        return terminalRepository;
    }
}
