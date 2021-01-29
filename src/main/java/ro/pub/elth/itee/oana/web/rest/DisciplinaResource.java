package ro.pub.elth.itee.oana.web.rest;

import ro.pub.elth.itee.oana.domain.Disciplina;
import ro.pub.elth.itee.oana.repository.DisciplinaRepository;
import ro.pub.elth.itee.oana.security.AuthoritiesConstants;
import ro.pub.elth.itee.oana.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ro.pub.elth.itee.oana.domain.Disciplina}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaResource.class);

    private static final String ENTITY_NAME = "disciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaResource(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    /**
     * {@code POST  /disciplinas} : Create a new disciplina.
     *
     * @param disciplina the disciplina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplina, or with status {@code 400 (Bad Request)} if the disciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disciplinas")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Disciplina> createDisciplina(@Valid @RequestBody Disciplina disciplina) throws URISyntaxException {
        log.debug("REST request to save Disciplina : {}", disciplina);
        if (disciplina.getId() != null) {
            throw new BadRequestAlertException("A new disciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Disciplina result = disciplinaRepository.save(disciplina);
        return ResponseEntity.created(new URI("/api/disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disciplinas} : Updates an existing disciplina.
     *
     * @param disciplina the disciplina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplina,
     * or with status {@code 400 (Bad Request)} if the disciplina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disciplinas")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Disciplina> updateDisciplina(@Valid @RequestBody Disciplina disciplina) throws URISyntaxException {
        log.debug("REST request to update Disciplina : {}", disciplina);
        if (disciplina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Disciplina result = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplina.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /disciplinas} : get all the disciplinas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplinas in body.
     */
    @GetMapping("/disciplinas")
    public ResponseEntity<List<Disciplina>> getAllDisciplinas(Pageable pageable) {
        log.debug("REST request to get a page of Disciplinas");
        Page<Disciplina> page = disciplinaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disciplinas/:id} : get the "id" disciplina.
     *
     * @param id the id of the disciplina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<Disciplina> getDisciplina(@PathVariable Long id) {
        log.debug("REST request to get Disciplina : {}", id);
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disciplina);
    }

    /**
     * {@code DELETE  /disciplinas/:id} : delete the "id" disciplina.
     *
     * @param id the id of the disciplina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disciplinas/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete Disciplina : {}", id);
        disciplinaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
