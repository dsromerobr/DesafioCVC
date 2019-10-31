package br.com.cvc.DesafioCVC.service.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import br.com.cvc.DesafioCVC.model.dto.HotelDTO;
import br.com.cvc.DesafioCVC.service.HotelsService;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;


@Service
public class HotelsServiceImpl implements HotelsService {

	@Value("${hotels.endpoint}")
	private String endpoint;

	@Value("${hotels.useragent}")
	private String useragent;

	@Value("${hotels.timeout}")
	private Integer timeout;
	
	@Inject
	private WebClient.Builder builderInjected;

	private WebClient webClient;
	
	private static final String PATH = "{idCidade}";
	
	@PostConstruct
	public void init() {
		final HttpClient httpClient = HttpClient.create().tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout * 1000))
				.tcpConfiguration(client -> client.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(timeout)).addHandlerLast(new WriteTimeoutHandler(timeout))));

		final ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

		webClient = builderInjected.baseUrl(endpoint).clientConnector(connector).defaultHeader(HttpHeaders.USER_AGENT, useragent)
				    .build();
	}
	
	@Override
	public HotelDTO[] getHotels(int idCidade) {
		try {

			HotelDTO[] hotels = webClient.get().uri("/" + PATH, idCidade).retrieve().bodyToMono(HotelDTO[].class).block();

			return hotels;
		} catch (Exception e) {
			throw (WebClientException)e.fillInStackTrace();
		}
	}

}
