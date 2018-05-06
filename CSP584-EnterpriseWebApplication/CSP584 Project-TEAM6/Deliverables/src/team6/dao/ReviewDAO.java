package team6.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;

import team6.entity.Review;

public class ReviewDAO {
	private MongoDatabase db;
	
	public ReviewDAO() {
		db = MongoDBDatabaseOperator.INSTANCE.getMongoDatabase();
	}

	public void insertReview(Review review) {
		MongoCollection<Document> reviewCollection = db.getCollection("review");
		Document doc = new Document();
		
		doc	.append("hotel-name", review.getHotelName())
			.append("hotel-address", review.getHotelAddress())
			.append("hotel-city", review.getHotelCity())
			.append("hotel-state", review.getHotelState())
			.append("hotel-zip", review.getHotelZip())
			.append("room-name", review.getRoomName())
		;
		
		Date checkInDate = Date.from(review.getCheckInDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date checkOutDate = Date.from(review.getCheckOutDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date reviewDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		doc	.append("check-in-date", checkInDate)
			.append("check-out-date", checkOutDate)
			.append("review-date", reviewDate)
		;
		
		doc	.append("first-name", review.getFirstName())
			.append("last-name", review.getLastName())
			.append("rating", review.getRating())
			.append("comment", review.getComment())
		;
		
		reviewCollection.insertOne(doc);
	}

	public Map<String, Double> selectTopHotelByRating(int limit) {
		MongoCollection<Document> reviewCollection = db.getCollection("review");
		Map<String, Double> result = new LinkedHashMap<>(limit);
		Block<Document> toResult = new Block<Document>() {
			@Override
			public void apply(final Document document) {
				result.put(document.getString("_id"), 
				document.getDouble("average-rating"));
			}
		};
		reviewCollection.aggregate(
			Arrays.asList(
				Aggregates.group("$hotel-name", Accumulators.avg("average-rating", "$rating"))
				, Aggregates.limit(limit)
				, Aggregates.sort(Sorts.descending("average-rating"))
			)
		).forEach(toResult);
		return result;
	}

	public Map<String, Integer> selectTopZipByReviewCount(int limit) {
		MongoCollection<Document> reviewCollection = db.getCollection("review");
		Map<String, Integer> result = new LinkedHashMap<>(limit);
		Block<Document> toResult = new Block<Document>() {
			@Override
			public void apply(final Document document) {
				result.put(document.getString("_id"), 
				document.getInteger("review-count"));
				
			}
		};
		reviewCollection.aggregate(
			Arrays.asList(
				  new Document("$group", 
					new Document("_id", 
						new Document("zip", "$hotel-zip")
						.append("hotel", "$hotel-name")
					)
				  )
				, new Document("$group",
					new Document("_id", "$_id.zip")
					.append("review-count", new Document("$sum", 1))
				  )
				, new Document("$limit", limit)
				, new Document("$sort", new Document("count", -1))
			)
		)
		.forEach(toResult);

		return result;
	}

	public Map<String, Integer> selectTopHotelByOrder(int limit) {
		MongoCollection<Document> reviewCollection = db.getCollection("review");
		Map<String, Integer> result = new LinkedHashMap<>(limit);
		Block<Document> toResult = new Block<Document>() {
			@Override
			public void apply(final Document document) {
				System.out.println(document.toJson());
				result.put(document.getString("_id"), 
				document.getInteger("review-count"));
			}
		};

		reviewCollection.aggregate(
			Arrays.asList(
				Aggregates.group("$hotel-name", Accumulators.sum("review-count", 1))
				, Aggregates.limit(limit)
				, Aggregates.sort(Sorts.descending("review-count"))
			)
		)
		.forEach(toResult);

		return result;
	}
	
}
