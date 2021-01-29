package ro.pub.elth.itee.oana.web.rest;

import ro.pub.elth.itee.oana.CatalogApp;
import ro.pub.elth.itee.oana.domain.Disciplina;
import ro.pub.elth.itee.oana.repository.DisciplinaRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DisciplinaResource} REST controller.
 */
@SpringBootTest(classes = CatalogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DisciplinaResourceIT {

    private static final String DEFAULT_DENUMIRE = "AAAAAAAAAA";
    private static final String UPDATED_DENUMIRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIERE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIERE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_PUNCTE_CREDIT = 2;
    private static final Integer UPDATED_PUNCTE_CREDIT = 3;

    private static final Integer DEFAULT_AN_DE_STUDIU = 1;
    private static final Integer UPDATED_AN_DE_STUDIU = 2;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisciplinaMockMvc;

    private Disciplina disciplina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplina createEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
            .denumire(DEFAULT_DENUMIRE)
            .descriere(DEFAULT_DESCRIERE)
            .puncteCredit(DEFAULT_PUNCTE_CREDIT)
            .anDeStudiu(DEFAULT_AN_DE_STUDIU);
        return disciplina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplina createUpdatedEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
            .denumire(UPDATED_DENUMIRE)
            .descriere(UPDATED_DESCRIERE)
            .puncteCredit(UPDATED_PUNCTE_CREDIT)
            .anDeStudiu(UPDATED_AN_DE_STUDIU);
        return disciplina;
    }

    @BeforeEach
    public void initTest() {
        disciplina = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisciplina() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();
        // Create the Disciplina
        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getDenumire()).isEqualTo(DEFAULT_DENUMIRE);
        assertThat(testDisciplina.getDescriere()).isEqualTo(DEFAULT_DESCRIERE);
        assertThat(testDisciplina.getPuncteCredit()).isEqualTo(DEFAULT_PUNCTE_CREDIT);
        assertThat(testDisciplina.getAnDeStudiu()).isEqualTo(DEFAULT_AN_DE_STUDIU);
    }

    @Test
    @Transactional
    public void createDisciplinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina with an existing ID
        disciplina.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDenumireIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setDenumire(null);

        // Create the Disciplina, which fails.


        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isBadRequest());

        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriereIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setDescriere(null);

        // Create the Disciplina, which fails.


        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isBadRequest());

        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPuncteCreditIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setPuncteCredit(null);

        // Create the Disciplina, which fails.


        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isBadRequest());

        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnDeStudiuIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setAnDeStudiu(null);

        // Create the Disciplina, which fails.


        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isBadRequest());

        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisciplinas() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].denumire").value(hasItem(DEFAULT_DENUMIRE)))
            .andExpect(jsonPath("$.[*].descriere").value(hasItem(DEFAULT_DESCRIERE)))
            .andExpect(jsonPath("$.[*].puncteCredit").value(hasItem(DEFAULT_PUNCTE_CREDIT)))
            .andExpect(jsonPath("$.[*].anDeStudiu").value(hasItem(DEFAULT_AN_DE_STUDIU)));
    }
    
    @Test
    @Transactional
    public void getDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disciplina.getId().intValue()))
            .andExpect(jsonPath("$.denumire").value(DEFAULT_DENUMIRE))
            .andExpect(jsonPath("$.descriere").value(DEFAULT_DESCRIERE))
            .andExpect(jsonPath("$.puncteCredit").value(DEFAULT_PUNCTE_CREDIT))
            .andExpect(jsonPath("$.anDeStudiu").value(DEFAULT_AN_DE_STUDIU));
    }
    @Test
    @Transactional
    public void getNonExistingDisciplina() throws Exception {
        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Update the disciplina
        Disciplina updatedDisciplina = disciplinaRepository.findById(disciplina.getId()).get();
        // Disconnect from session so that the updates on updatedDisciplina are not directly saved in db
        em.detach(updatedDisciplina);
        updatedDisciplina
            .denumire(UPDATED_DENUMIRE)
            .descriere(UPDATED_DESCRIERE)
            .puncteCredit(UPDATED_PUNCTE_CREDIT)
            .anDeStudiu(UPDATED_AN_DE_STUDIU);

        restDisciplinaMockMvc.perform(put("/api/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisciplina)))
            .andExpect(status().isOk());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getDenumire()).isEqualTo(UPDATED_DENUMIRE);
        assertThat(testDisciplina.getDescriere()).isEqualTo(UPDATED_DESCRIERE);
        assertThat(testDisciplina.getPuncteCredit()).isEqualTo(UPDATED_PUNCTE_CREDIT);
        assertThat(testDisciplina.getAnDeStudiu()).isEqualTo(UPDATED_AN_DE_STUDIU);
    }

    @Test
    @Transactional
    public void updateNonExistingDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinaMockMvc.perform(put("/api/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        int databaseSizeBeforeDelete = disciplinaRepository.findAll().size();

        // Delete the disciplina
        restDisciplinaMockMvc.perform(delete("/api/disciplinas/{id}", disciplina.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
