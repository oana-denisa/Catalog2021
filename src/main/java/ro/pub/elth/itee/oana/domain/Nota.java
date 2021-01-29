package ro.pub.elth.itee.oana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Nota.
 */
@Entity
@Table(name = "nota")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "numar_puncte", nullable = false)
    private Integer numarPuncte;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "nota", nullable = false)
    private Integer nota;

    @NotNull
    @Column(name = "data", nullable = false)
    private Instant data;

    @ManyToOne
    @JsonIgnoreProperties(value = "notas", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "notas", allowSetters = true)
    private Disciplina disciplina;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumarPuncte() {
        return numarPuncte;
    }

    public Nota numarPuncte(Integer numarPuncte) {
        this.numarPuncte = numarPuncte;
        return this;
    }

    public void setNumarPuncte(Integer numarPuncte) {
        this.numarPuncte = numarPuncte;
    }

    public Integer getNota() {
        return nota;
    }

    public Nota nota(Integer nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Instant getData() {
        return data;
    }

    public Nota data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public Nota user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Nota disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nota)) {
            return false;
        }
        return id != null && id.equals(((Nota) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nota{" +
            "id=" + getId() +
            ", numarPuncte=" + getNumarPuncte() +
            ", nota=" + getNota() +
            ", data='" + getData() + "'" +
            "}";
    }
}
