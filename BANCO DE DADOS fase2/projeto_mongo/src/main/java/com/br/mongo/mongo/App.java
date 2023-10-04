package com.br.mongo.mongo;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.*;


class App 
{
    public static void main(String[] args) throws IOException {
    	 // Configurar o registro de codecs Pojo
    	CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
    			fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
    	// Criar uma instância do MongoClient / Inicializar a aplicação para um novo Cliente
    	MongoClient mongoClient = new MongoClient("localhost:27017", 
    			MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
    	
    	//Interliga no Java o database e a collection criada
    	MongoDatabase database = mongoClient.getDatabase("projeto")//nome da dtabase
    			.withCodecRegistry(pojoCodecRegistry);
    	
    	MongoCollection<Temperatura> collection = database.getCollection("Temperatura", Temperatura.class);
    
    
		String urlCampinaGrande = "https://www.climatempo.com.br/previsao-do-tempo/15-dias/cidade/255/campinagrande-pb";
		String urlJoaoPessoa = "https://www.climatempo.com.br/previsao-do-tempo/cidade/256/joaopessoa-pb";

		Document doc = Jsoup.connect(urlCampinaGrande).get();
		Elements elementos = doc.select("span._margin-r-15");
		if (!elementos.isEmpty()) {
						
		        int id = 1; // Comece com o ID 1
		        for (Element elemento : elementos) {
		            String conteudo = elemento.text();
		            if(conteudo.contains("%")) {
		            	continue;
		            }
		            // Crie uma instância da classe Temperatura com o conteúdo
		            Temperatura temperatura = new Temperatura();
		            temperatura.setId(id); // Configure o ID
		            temperatura.setValor(conteudo);

		            // Insira a instância no MongoDB
		            collection.insertOne(temperatura);

		            id++; // Incrementa o ID para a próxima instância
		        }
		    } else {
		        System.out.println("Elemento não encontrado");
		    }
		
		
    }
}
