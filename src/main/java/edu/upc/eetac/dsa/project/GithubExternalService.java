package edu.upc.eetac.dsa.project;

import edu.upc.eetac.dsa.project.entity.Task;
import edu.upc.eetac.dsa.project.exceptions.GithubServiceException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by carlos on 25/05/16.
 */
public class GithubExternalService {
    public final String BASE_URL= "https://api.github.com";

    public boolean checkUserAndPass(String auth) throws IOException{

        try {
            URL url = new URL(BASE_URL + "/user");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", auth);

            if (conn.getResponseCode() == 200)  {
                conn.disconnect();
                return true;
            }
            else {
                conn.disconnect();
                return false;
            }

        }
        catch(IOException e){
            throw e;
        }
    }

    public void createIssue(String repoOwner, String repoName, Task task, String auth) throws IOException, GithubServiceException {
        try {
            URL url = new URL(BASE_URL + "/repos/"+repoOwner+"/"+repoName+"/issues");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", auth);

            //no utilizamos asignee ni labels
            String input = "{\"title\": \""+task.getTitle()+"\", \"body\": \""+task.getDescription()+"\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            int code = conn.getResponseCode();

            if(code != HttpURLConnection.HTTP_CREATED) {
                throw new GithubServiceException();
            }

            conn.disconnect();

        }
        catch(IOException e){
            throw e;
        }
    }
}
