package ro.pub.elth.itee.oana.web.rest;

import ro.pub.elth.itee.oana.domain.Nota;
import ro.pub.elth.itee.oana.repository.NotaRepository;
import ro.pub.elth.itee.oana.security.AuthoritiesConstants;
import ro.pub.elth.itee.oana.security.SecurityUtils;
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
 * REST controller for managing {@link ro.pub.elth.itee.oana.domain.Nota}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NotaResource {

    private final Logger log = LoggerFactory.getLogger(NotaResource.class);

    private static final String ENTITY_NAME = "nota";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotaRepository notaRepository;

    public NotaResource(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    /**
     * {@code POST  /notas} : Create a new nota.
     *
     * @param nota the nota to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nota, or with status {@code 400 (Bad Request)} if the nota has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notas")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Nota> createNota(@Valid @RequestBody Nota nota) throws URISyntaxException {
        log.debug("REST request to save Nota : {}", nota);
        if (nota.getId() != null) {
            throw new BadRequestAlertException("A new nota cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nota result = notaRepository.save(nota);
        return ResponseEntity.created(new URI("/api/notas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notas} : Updates an existing nota.
     *
     * @param nota the nota to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nota,
     * or with status {@code 400 (Bad Request)} if the nota is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nota couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notas")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Nota> updateNota(@Valid @RequestBody Nota nota) throws URISyntaxException {
        log.debug("REST request to update Nota : {}", nota);
        if (nota.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nota result = notaRepository.save(nota);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nota.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notas} : get all the notas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notas in body.
     */
    @GetMapping("/notas")
    public ResponseEntity<List<Nota>> getAllNotas(Pageable pageable) {
        log.debug("REST request to get a page of Notas");

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Page<Nota> page = notaRepository.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());

        } else {
            // Page<Nota> page = notaRepository.findAll(pageable);
            Page<Nota> page = notaRepository.findByUserIsCurrentUser(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

        //Page<Nota> page = notaRepository.findAll(pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        //return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notas/:id} : get the "id" nota.
     *
     * @param id the id of the nota to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nota, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notas/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN +"\") || hasRole(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Nota> getNota(@PathVariable Long id) {
        log.debug("REST request to get Nota : {}", id);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Optional<Nota> nota = notaRepository.findById(id);
            return ResponseUtil.wrapOrNotFound(nota);

        } else {
            // Page<Nota> page = notaRepository.findAll(pageable);
            Optional<Nota> nota = notaRepository.findByUserIsCurrentUserById(id);
            return ResponseUtil.wrapOrNotFound(nota);
        }

       // Optional<Nota> nota = notaRepository.findById(id);
       // return ResponseUtil.wrapOrNotFound(nota);
    }

    /**
     * {@code DELETE  /notas/:id} : delete the "id" nota.
     *
     * @param id the id of the nota to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notas/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteNota(@PathVariable Long id) {
        log.debug("REST request to delete Nota : {}", id);
        notaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
