package br.com.jean.vancontrol.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    private String nomeResponsavel1;

    @Column(length = 11)
    private String cpfResponsavel1;

    private String nomeResponsavel2;

    @Column(length = 11)
    private String cpfResponsavel2;

    @ManyToOne(optional = false)
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    public Aluno() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeResponsavel1() {
        return nomeResponsavel1;
    }

    public void setNomeResponsavel1(String nomeResponsavel1) {
        this.nomeResponsavel1 = nomeResponsavel1;
    }

    public String getCpfResponsavel1() {
        return cpfResponsavel1;
    }

    public void setCpfResponsavel1(String cpfResponsavel1) {
        this.cpfResponsavel1 = cpfResponsavel1;
    }

    public String getNomeResponsavel2() {
        return nomeResponsavel2;
    }

    public void setNomeResponsavel2(String nomeResponsavel2) {
        this.nomeResponsavel2 = nomeResponsavel2;
    }

    public String getCpfResponsavel2() {
        return cpfResponsavel2;
    }

    public void setCpfResponsavel2(String cpfResponsavel2) {
        this.cpfResponsavel2 = cpfResponsavel2;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }
}