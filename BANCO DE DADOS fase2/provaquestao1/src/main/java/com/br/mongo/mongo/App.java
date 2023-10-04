package com.br.mongo.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Prova;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import java.util.function.Consumer;
import static com.mongodb.client.model.Updates.set; // Importe o método set


class App 
{
    public static void main( String[] args )
    {
        // Configurar o registro de codecs Pojo
    	CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
    			fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
    	// Criar uma instância do MongoClient / Inicializar a aplicação para um novo Cliente
    	MongoClient mongoClient = new MongoClient("localhost:27017", 
    			MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
    	
    	//Interliga no Java o database e a collection criada
    	MongoDatabase database = mongoClient.getDatabase("prova")//nome da dtabase
    			.withCodecRegistry(pojoCodecRegistry);
    	
    	MongoCollection<Prova> collection = database.getCollection("questaoum", Prova.class);
    
    
    	//PUT - insetir dado no MongoDB
    	collection.insertOne(new Prova(1,"Questão 1"));
    	
    	//PATCH - Atualizar dado no MongoDB
    	//collection.updateOne(new Document("_id",1), set("descricao","Questão feita "));
    
    		
    }
}
