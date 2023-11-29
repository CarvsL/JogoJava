package mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mycompany.jogocerto.Carregamento;
import com.mycompany.jogocerto.Load;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

public class saveMongo {
    public void getValues() {
    System.out.println("Método getValues()");
    MongoClient mongo = new MongoClient("localhost", 27017);
    MongoDatabase db = mongo.getDatabase("JogoL");
    MongoCollection<Document> docs = db.getCollection("JogoL");
    for (Document doc : docs.find()) {
        
        
    }
    System.out.println("getValues() - ok - finalizou");
   }     
    
    public void showTable() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Name");
    model.addColumn("Score");

    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);

    MongoClient mongo = new MongoClient("localhost", 27017);
    MongoDatabase db = mongo.getDatabase("JogoL");
    MongoCollection<Document> docs = db.getCollection("JogoL");

    
    List<Document> sortedDocs = docs.find().sort(Sorts.descending("score")).into(new ArrayList<>());

    for (Document doc : sortedDocs) {
        String name = doc.getString("name");

        
        if (doc.containsKey("score") && doc.get("score") != null) {
            int score = doc.getInteger("score");
            model.addRow(new Object[]{name, score});
        } else {
            
            model.addRow(new Object[]{name, "N/A"});
        }
    }

    JOptionPane.showMessageDialog(null, scrollPane);
}
    
    public Entrada findValuesPassword(String password) {
    MongoClient mongo = new MongoClient("localhost", 27017);
    MongoDatabase db = mongo.getDatabase("JogoL");
    MongoCollection<Document> docs = db.getCollection("JogoL");

    Document userDocument = docs.find(Filters.eq("password", password)).first(); 

    if (userDocument != null) {
        Entrada entrada = new Entrada();
        entrada.setName(userDocument.getString("name")); 
        

        
        new Carregamento().setVisible(true);
        JOptionPane.showMessageDialog(null, "Bem Vindo: " + entrada.getName() , "Aviso", JOptionPane.WARNING_MESSAGE);
        return entrada;
    } else {
        
        JOptionPane.showMessageDialog(null, "Senha não encontrada", "Aviso", JOptionPane.WARNING_MESSAGE);
        new Load().setVisible(true);
        return null;
    }
}
    
   public void insertValues(String name,int score) {
        System.out.println("Método insertValues()");
        
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase db = mongo.getDatabase("JogoL");
        MongoCollection<Document> docs = db.getCollection("JogoL");
        
        Entrada user = createUser(name, score);
        Document doc = createDocument(user);
        docs.insertOne(doc);
        getValues();
        System.out.println("insertValues ok");
    }

   public Entrada createUser(String name, int score) {
        Entrada u = new Entrada();
        
        u.setName(name);
        
       
        
        u.setScore(score);
       
        return u;
    }

   public Document createDocument(Entrada user) {
        Document docBuilder = new Document();
        docBuilder.append("name", user.getName());
        docBuilder.append("score", user.getScore());
        
       
        return docBuilder;
    }

   

    

   

   

    }

    

    

