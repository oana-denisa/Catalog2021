package ro.pub.elth.itee.oana.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Disciplina.
 */
@Entity
@Table(name = "disciplina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "denumire", length = 15, nullable = false)
    private String denumire;

    @NotNull
    @Size(min = 30)
    @Column(name = "descriere", nullable = false)
    private String descriere;

    @NotNull
    @Min(value = 2)
    @Max(value = 6)
    @Column(name = "puncte_credit", nullable = false)
    private Integer puncteCredit;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    @Column(name = "an_de_studiu", nullable = false)
    private Integer anDeStudiu;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public Disciplina denumire(String denumire) {
        this.denumire = denumire;
        return this;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public Disciplina descriere(String descriere) {
        this.descriere = descriere;
        return this;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getPuncteCredit() {
        return puncteCredit;
    }

    public Disciplina puncteCredit(Integer puncteCredit) {
        this.puncteCredit = puncteCredit;
        return this;
    }

    public void setPuncteCredit(Integer puncteCredit) {
        this.puncteCredit = puncteCredit;
    }

    public Integer getAnDeStudiu() {
        return anDeStudiu;
    }

    public Disciplina anDeStudiu(Integer anDeStudiu) {
        this.anDeStudiu = anDeStudiu;
        return this;
    }

    public void setAnDeStudiu(Integer anDeStudiu) {
        this.anDeStudiu = anDeStudiu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disciplina)) {
            return false;
        }
        return id != null && id.equals(((Disciplina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disciplina{" +
            "id=" + getId() +
            ", denumire='" + getDenumire() + "'" +
            ", descriere='" + getDescriere() + "'" +
            ", puncteCredit=" + getPuncteCredit() +
            ", anDeStudiu=" + getAnDeStudiu() +
            "}";
    }
}
