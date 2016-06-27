
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class PYDeepLink {

    private static final String URL_BASE_UP = "http://.../up?v=";
    private static final String URL_BASE_GET = "http://.../get?v=";
    private static final int TIME_OUT = 2000;


    void callUp(String key){
        httpUp(URL_BASE_UP + key);
    }

    void callGet(String key){
        httpGet(URL_BASE_GET + key);
    }

    private void httpUp(final String httpUrl) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(TIME_OUT);
            inputStream = httpURLConnection.getInputStream();

            byte[]  str = new byte[1];
            int readValue = inputStream.read(str);
						// 
            if (readValue == -1 || !new String(str).equals("t") || httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Up server return error");
            }

        } catch (Exception e) {
            System.out.println("connect error");
        } finally {
            if(null != inputStream)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (null != httpURLConnection)
                httpURLConnection.disconnect();

        }
    }

    private void httpGet(final String httpUrl) {

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(TIME_OUT);

            inputStream = httpURLConnection.getInputStream();

            byte[]  str = new byte[18];
            int readValue = inputStream.read(str);
						// 
            if (readValue == -1 || !new String(str).equals("t") || httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Get server return error");
            }


        } catch (Exception e) {
            System.out.println("connect error");
        } finally {
            if (null != inputStream)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (null != httpURLConnection)
                httpURLConnection.disconnect();
        }
    }
}