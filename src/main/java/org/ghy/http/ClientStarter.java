package org.ghy.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;

public class ClientStarter {
    public static void main(String[] args) {
        HttpClient httpClient = null;
        HttpResponse httpResponse = null;
        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            configBuilder.setConnectionRequestTimeout(1000);
            configBuilder.setConnectTimeout(1000);
            configBuilder.setSocketTimeout(10000);
            RequestConfig config = configBuilder.build();

            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            clientBuilder.setDefaultRequestConfig(config);
            clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
            httpClient = clientBuilder.build();

            HttpGet httpGet = new HttpGet("https://localhost:8443");
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    ((CloseableHttpClient) httpClient).close();
                }
                if (httpResponse != null) {
                    ((CloseableHttpResponse) httpResponse).close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
