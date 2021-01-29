package ro.pub.elth.itee.oana.web.rest;

import ro.pub.elth.itee.oana.CatalogApp;
import ro.pub.elth.itee.oana.domain.Nota;
import ro.pub.elth.itee.oana.repository.NotaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NotaResource} REST controller.
 */
@SpringBootTest(classes = CatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotaResourceIT {

    private static final Integer DEFAULT_NUMAR_PUNCTE = 1;
    private static final Integer UPDATED_NUMAR_PUNCTE = 2;

    private static final Integer DEFAULT_NOTA = 1;
    private static final Integer UPDATED_NOTA = 2;

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotaMockMvc;

    private Nota nota;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nota createEntity(EntityManager em) {
        Nota nota = new Nota()
            .numarPuncte(DEFAULT_NUMAR_PUNCTE)
            .nota(DEFAULT_NOTA)
            .data(DEFAULT_DATA);
        return nota;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nota createUpdatedEntity(EntityManager em) {
        Nota nota = new Nota()
            .numarPuncte(UPDATED_NUMAR_PUNCTE)
            .nota(UPDATED_NOTA)
            .data(UPDATED_DATA);
        return nota;
    }

    @BeforeEach
    public void initTest() {
        nota = createEntity(em);
    }

    @Test
    @Transactional
    public void createNota() throws Exception {
        int databaseSizeBeforeCreate = notaRepository.findAll().size();
        // Create the Nota
        restNotaMockMvc.perform(post("/api/notas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nota)))
            .andExpect(status().isCreated());

        // Validate the Nota in the database
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeCreate + 1);
        Nota testNota = notaList.get(notaList.size() - 1);
        assertThat(testNota.getNumarPuncte()).isEqualTo(DEFAULT_NUMAR_PUNCTE);
        assertThat(testNota.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testNota.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createNotaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notaRepository.findAll().size();

        // Create the Nota with an existing ID
        nota.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotaMockMvc.perform(post("/api/notas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nota)))
            .andExpect(status().isBadRequest());

        // Validate the Nota in the database
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumarPuncteIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaRepository.findAll().size();
        // set the field null
        nota.setNumarPuncte(null);

        // Create the Nota, which fails.


        restNotaMockMvc.perform(post("/api/notas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nota)))
            .andExpect(status().isBadRequest());

        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaRepository.findAll().size();
        // set the field null
        nota.setNota(null);

        // Create the Nota, which fails.


        restNotaMockMvc.perform(post("/api/notas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nota)))
            .andExpect(status().isBadRequest());

        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaRepository.findAll().size();
        // set the field null
        nota.setData(null);

        // Create the Nota, which fails.


        restNotaMockMvc.perform(post("/api/notas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nota)))
            .andExpect(status().isBadRequest());

        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotas() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);

        // Get all the notaList
        restNotaMockMvc.perform(get("/api/notas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nota.getId().intValue())))
            .andExpect(jsonPath("$.[*].numarPuncte").value(hasItem(DEFAULT_NUMAR_PUNCTE)))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getNota() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);

        // Get the nota
        restNotaMockMvc.perform(get("/api/notas/{id}", nota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nota.getId().intValue()))
            .andExpect(jsonPath("$.numarPuncte").value(DEFAULT_NUMAR_PUNCTE))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingNota() throws Exception {
        // Get the nota
        restNotaMockMvc.perform(get("/api/notas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNota() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);

        int databaseSizeBeforeUpdate = notaRepository.findAll().size();

        // Update the nota
        Nota updatedNota = notaRepository.findById(nota.getId()).get();
        // Disconnect from session so that the updates on updatedNota are not directly saved in db
        em.detach(updatedNota);
        updatedNota
            .numarPuncte(UPDATED_NUMAR_PUNCTE)
            .nota(UPDATED_NOTA)
            .data(UPDATED_DATA);

        restNotaMockMvc.perform(put("/api/notas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNota)))
            .andExpect(status().isOk());

        // Validate the Nota in the database
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeUpdate);
        Nota testNota = notaList.get(notaList.size() - 1);
        assertThat(testNota.getNumarPuncte()).isEqualTo(UPDATED_NUMAR_PUNCTE);
        assertThat(testNota.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testNota.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingNota() throws Exception {
        int databaseSizeBeforeUpdate = notaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotaMockMvc.perform(put("/api/notas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nota)))
            .andExpect(status().isBadRequest());

        // Validate the Nota in the database
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNota() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);

        int databaseSizeBeforeDelete = notaRepository.findAll().size();

        // Delete the nota
        restNotaMockMvc.perform(delete("/api/notas/{id}", nota.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
