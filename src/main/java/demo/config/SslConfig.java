package demo.config;

import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class SslConfig {

	@Bean
	public EmbeddedServletContainerFactory servletContainer() throws IOException {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector());
		return tomcat;

	}

	private Connector createSslConnector() throws IOException {
		Connector connector = new Connector(Http11Nio2Protocol.class.getName());
		Http11Nio2Protocol protocol = (Http11Nio2Protocol) connector.getProtocolHandler();
		connector.setPort(8443);
		connector.setSecure(true);
		connector.setScheme("https");

		protocol.setSSLEnabled(true);
		protocol.setKeyAlias("mvc4");
		protocol.setKeystorePass("password");
		protocol.setKeyPass("password2");
		protocol.setKeystoreFile(new ClassPathResource("tomcat.keystore").getFile().getAbsolutePath());
		protocol.setSslProtocol("TLS");
		return connector;
	}
}
