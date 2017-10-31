package example;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class Hello {
	
	private static final String SUFFIX = "/";
	
	public String myHandler(int myCount, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("received : " + myCount * 3);
		return String.valueOf(myCount * 3);
	}

	public String myNewHandler(String content, Context context) {

		try {
			LambdaLogger logger = context.getLogger();
			logger.log("received : " + content);
			AWSCredentials credentials = new BasicAWSCredentials("AKIAIO62HKXVYZYS2ZAA",
					"Vk4MGyg8ks2dk+ep9e8cDWudpAobcXF5cN6BUi+V");

			// create a client connection based on credentials
			AmazonS3 s3client = new AmazonS3Client(credentials);

			// create bucket - name must be unique for all S3 users
			String bucketName = "subhbucketa";
			s3client.createBucket(bucketName);

			// list buckets
			/*for (Bucket bucket : s3client.listBuckets()) {
				System.out.println(" - " + bucket.getName());
			}*/

			// create folder into bucket
			String folderName = "testfolder";
			// createFolder(bucketName, folderName, s3client);
			
			
			String srcText = content;
			
			System.out.println("In doPost@@@@@@@@@@@"+srcText);
			
			String newStr = srcText.substring(srcText.lastIndexOf("base64")+7);
			System.out.println("In doPost@@@@@@@@@@@"+newStr);
			
			byte[] imgByteArray = Base64.decodeBase64(newStr);

			//byte[] data = Files.readAllBytes(path);

			InputStream is = new ByteArrayInputStream(imgByteArray);

			
			 /* s3client.putObject(new PutObjectRequest(bucketName, fileName, new
			 * File("C:\\Subhashish\\Learning\\Repo\\String_To_Image.png"))
			 * .withCannedAcl(CannedAccessControlList.PublicRead));*/
			 
			
			String fileName = folderName + SUFFIX + "String_To_Image22.png";

			s3client.putObject(new PutObjectRequest(bucketName, fileName, is, null)
					.withCannedAcl(CannedAccessControlList.PublicRead));

		} catch (AmazonServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure";
		} catch (AmazonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure";
		}

		return "success";
	}
}