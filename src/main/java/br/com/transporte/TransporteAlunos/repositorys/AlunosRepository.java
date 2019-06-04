package br.com.transporte.TransporteAlunos.repositorys;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.transporte.TransporteAlunos.codecs.AlunoCodec;
import br.com.transporte.TransporteAlunos.models.Aluno;

@Repository
public class AlunosRepository {
	
	private MongoClient cliente;
	private MongoDatabase bancoDeDados;
	
	
	private void criarConexao() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		
		AlunoCodec alunoCodec = new AlunoCodec(codec);
		
		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(alunoCodec));
		
		MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(registro).build();
		
		
		this.cliente = new MongoClient("localhost:27017", opcoes);
		this.bancoDeDados = cliente.getDatabase("test");

	}
	
	
	public void salvar(Aluno aluno) {
		
		criarConexao();		
		
		MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunosSpringBoot", Aluno.class);
		
		if(aluno.getId() == null) {
			alunos.insertOne(aluno);					
		}else {
			alunos.updateOne(Filters.eq("_id", aluno.getId()), new Document("$set", aluno));
		}
		
		cliente.close();
	}
	
	
	public List<Aluno> obterTodosAlunos(){
		
		criarConexao();
		
		MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunosSpringBoot", Aluno.class);
		
		MongoCursor<Aluno> resultado = alunos.find().iterator();
		
		List<Aluno> alunosEncontrados = new ArrayList<>();
		
		while(resultado.hasNext()) {
			Aluno aluno = resultado.next();
			alunosEncontrados.add(aluno);
		}
		
		cliente.close();
		return alunosEncontrados;
	}
	
	public Aluno obterAlunoPor(String id) {
		criarConexao();
		MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunosSpringBoot", Aluno.class);
		Aluno aluno = alunos.find(Filters.eq("_id", new ObjectId(id))).first();
		cliente.close();
		return aluno;
	}
	
}
