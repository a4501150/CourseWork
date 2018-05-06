import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.*;
import Models.Review;


public class MongoDBDataStoreUtilities {
	
	static DBCollection Reviews;

	public MongoDBDataStoreUtilities() {
		
		

	}
	

	public static void getConnection()
	{
		System.out.println("MDB initilazied");
		MongoClient mongo;
		try{
			mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("CustomerReviews");
			Reviews = db.getCollection("Reviews");
		}catch(Exception e){
			System.out.println(e);
		}
	}    
	public static void insertReview(String ProductID, String username, String reviewtext,String rating,String reviewdate){
		try{
				System.out.println(" insertReview");
				getConnection();
		        BasicDBObject doc = new BasicDBObject("title", "Reviews").
		        append("ProductID", ProductID).
		        append("Username", username).
		        append("ReviewText", reviewtext).
		        append("Rating", rating).
		        append("ReviewDate", reviewdate);
		        Reviews.insert(doc);
		        
		        MySQLDataStoreUtilities.addRate(Double.parseDouble(rating), Integer.parseInt(ProductID));
		        
		  }catch(Exception e){
			  System.out.println(e);
		  }
	  }      
	
	public static HashMap<String, ArrayList<Review>> selectReview()
	{
		getConnection();
		HashMap<String, ArrayList<Review>> reviewHashmap=new HashMap<String, ArrayList<Review>>();
		DBCursor cursor = Reviews.find();
		
		while (cursor.hasNext())
		{
			BasicDBObject obj = (BasicDBObject) cursor.next();
		
			if(! reviewHashmap.containsKey(obj.getString("ProductID")))
			{
				ArrayList<Review> arr = new ArrayList<Review>();
				reviewHashmap.put(obj.getString("ProductID"), arr);
			}
			ArrayList<Review> listReview = reviewHashmap.get(obj.getString("ProductID"));
			Review review =new Review();
			review.setProductID(obj.getString("ProductID"));
			review.setUsername(obj.getString("Username"));
			review.setText(obj.getString("ReviewText"));
			review.setRate(obj.getString("Rating"));
			review.setDate(obj.getString("ReviewDate"));
			review.setUserAge("22");
			review.setUserGender("male");
			review.setUserOccupation("some occupation");
			listReview.add(review);
		}
		
		return reviewHashmap;
	}
	






}
