package br.com.cvc.DesafioCVC.service.impl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import br.com.cvc.DesafioCVC.model.Hotel;
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
	
	private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

	private static final String PATH = "{idCidade}";

	@PostConstruct
	public void init() {
		final HttpClient httpClient = HttpClient.create()
				.tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout * 1000))
				.tcpConfiguration(
						client -> client.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(timeout))
								.addHandlerLast(new WriteTimeoutHandler(timeout))));

		final ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

		webClient = builderInjected.baseUrl(endpoint).clientConnector(connector)
				.defaultHeader(HttpHeaders.USER_AGENT, useragent).build();
	}

	@Override
	@Cacheable("hotels")
	public HotelDTO[] getHotels(int idCidade) {
		try {

			HotelDTO[] hotels = webClient.get().uri("/avail/" + PATH, idCidade).retrieve().bodyToMono(HotelDTO[].class)
					.block();

			return hotels;
		} catch (Exception e) {
			throw (WebClientException) e.fillInStackTrace();
		}
	}

	@Override
	public List<Hotel> setTotalPrice(List<Hotel> hotels, String checkIn, String checkOut, Integer qtdPax,
			Integer qtdChd) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dtCheckIn = LocalDate.parse(checkIn, formatter);
		LocalDate dtCheckOut = LocalDate.parse(checkOut, formatter);

		long days = ChronoUnit.DAYS.between(dtCheckIn, dtCheckOut);
		
		//Stream com paralelismo, aumento de velocidade
		List<Hotel> returnHotel = hotels.stream().map(h -> {
			h.getRooms().forEach(room -> room.setTotalPrice(
					Double.valueOf(decimalFormat.format(
					(((room.getPriceDetail().getPricePerDayAdult() * days) * qtdPax) / 0.7)+
					(((room.getPriceDetail().getPricePerDayChild() * days) * qtdChd) / 0.7)
					))
			)

 			);
			return h;
		})
				.parallel()
				.collect(Collectors.toList());

		// --Adicionar a comissão de .70 para adulto e para criança * Formula para fazer
		// isso ({valorViagemAdulto}/0.7) Ex: (500/0.7);
		// --Somar tudo e você terá o totalPrice

		return returnHotel;
	}

	@Override
	public HotelDTO[] getHotelDetail(int idHotel) {
		try {

			HotelDTO[] hotels = webClient.get().uri("/" + PATH, idHotel).retrieve().bodyToMono(HotelDTO[].class)
					.block();

			return hotels;
		} catch (Exception e) {
			throw (WebClientException) e.fillInStackTrace();
		}
	}

}
