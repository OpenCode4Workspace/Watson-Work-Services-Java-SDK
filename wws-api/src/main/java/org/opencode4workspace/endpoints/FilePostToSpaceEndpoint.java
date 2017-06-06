package org.opencode4workspace.endpoints;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.FileResponse;
import org.opencode4workspace.bo.PhotoResponse;
import org.opencode4workspace.json.ResultParser;

/**
 * @author Paul Withers
 * @since 0.7.0
 * 
 *        Endpoint for posting a new photo for the current user
 *
 */
public class FilePostToSpaceEndpoint extends AbstractWWGraphQLEndpoint {

	public FilePostToSpaceEndpoint(IWWClient client) {
		super(client);
	}
	
	public FileResponse postfile(File file, String spaceId, String imageSize) throws WWException {
		HttpPost post = preparePost(spaceId);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.addBinaryBody(
				    "file",
				    file
				);
			if (null != imageSize && !"".equals(imageSize)) {
				builder.addTextBody("dim", imageSize);
			}

			HttpEntity multipart = builder.build();
			post.setEntity(multipart);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 201) {
				String content = EntityUtils.toString(response.getEntity());
				FileResponse fileResponse = new ResultParser<FileResponse>(FileResponse.class).parse(content);
				return fileResponse;
			} else {
				throw new WWException("Execution failed: " + response.getStatusLine().getReasonPhrase());
			}
		} catch (Exception e) {
			throw new WWException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @return HttpPost containing the relevant headers
	 * 
	 * @since 0.7.0
	 */
	private HttpPost preparePost(String spaceId) {
		HttpPost post = new HttpPost(WWDefinedEndpoints.V1_SPACE_ID + spaceId + "/files");
		post.addHeader("Authorization", "Bearer " + getClient().getJWTToken());
		post.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
		return post;
	}

}
