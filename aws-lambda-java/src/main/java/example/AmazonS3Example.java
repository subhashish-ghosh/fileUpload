package example;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AmazonS3Example {
	
	private static final String SUFFIX = "/";
	
	public static void main(String[] args) {
		try {
			// credentials object identifying user for authentication
			// user must have AWSConnector and AmazonS3FullAccess for 
			// this example to work
			AWSCredentials credentials = new BasicAWSCredentials(
					"AKIAIO62HKXVYZYS2ZAA", 
					"Vk4MGyg8ks2dk+ep9e8cDWudpAobcXF5cN6BUi+V");
			
			// create a client connection based on credentials
			AmazonS3 s3client = new AmazonS3Client(credentials);
			
			// create bucket - name must be unique for all S3 users
			String bucketName = "subhbucketa";
			s3client.createBucket(bucketName);
			
			// list buckets
			for (Bucket bucket : s3client.listBuckets()) {
				System.out.println(" - " + bucket.getName());
			}
			
			// create folder into bucket
			String folderName = "testfolder";
			//createFolder(bucketName, folderName, s3client);
			
			// upload file to folder and set it to public
			String fileName = folderName + SUFFIX + "String_To_Image11.png";
			
			Path path = Paths.get("C:\\Subhashish\\Learning\\Repo\\String_To_Image.png");
			
			byte[] data = Files.readAllBytes(path);
			
			InputStream is = new ByteArrayInputStream(data);
			
			
			/*s3client.putObject(new PutObjectRequest(bucketName, fileName, 
					new File("C:\\Subhashish\\Learning\\Repo\\String_To_Image.png"))
					.withCannedAcl(CannedAccessControlList.PublicRead));*/
			
			s3client.putObject(new PutObjectRequest(bucketName, fileName, 
					is,null)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			
			
			
			//s3client.putObject(new PutObjectRequest(bucketName, fileName,is,null)
		} catch (AmazonServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AmazonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//deleteFolder(bucketName, folderName, s3client);
		
		// deletes bucket
		//s3client.deleteBucket(bucketName);
	}
	
	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				folderName + SUFFIX, emptyContent, metadata);

		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}

	/**
	 * This method first deletes all the files in given folder and than the
	 * folder itself
	 */
	/*public static void deleteFolder(String bucketName, String folderName, AmazonS3 client) {
		List fileList = 
				client.listObjects(bucketName, folderName).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			client.deleteObject(bucketName, file.getKey());
		}
		client.deleteObject(bucketName, folderName);
	}*/
}
