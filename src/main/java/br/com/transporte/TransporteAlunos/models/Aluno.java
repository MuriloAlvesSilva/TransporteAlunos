package br.com.transporte.TransporteAlunos.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

public class Aluno {
	
	private ObjectId id;
	
	private String nome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataNascimento;
	
	private Curso curso;
	
	private List<Nota> notas;
	
	private List<Habilidade> habilidades;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Nota> getNotas() {
		if(notas == null) {
			notas = new ArrayList<Nota>();
		}
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public List<Habilidade> getHabilidades() {
		if(habilidades == null) {
			habilidades = new ArrayList<Habilidade>();
		}
		return habilidades;
	}

	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public Aluno criarId() {
		setId(new ObjectId());
		return this;
	}

	public Aluno adicionar(Aluno aluno, Habilidade habilidade) {
		
		List<Habilidade> habilidades = aluno.getHabilidades();
		
		/* 1º adiciona nova habilidade a lista de habilidades */
		habilidades.add(habilidade);
		
		/* 2º adiciona nova habilidade ao aluno */
		aluno.setHabilidades(habilidades);
		
		/* 3º retorna aluno */
		return aluno;
		
	}

	public Aluno adicionar(Aluno aluno, Nota nota) {
		List<Nota> notas = aluno.getNotas();
		
		notas.add(nota);
		aluno.setNotas(notas);
		
		return aluno;
	}
	
}
