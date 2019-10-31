package br.com.cvc.DesafioCVC.api;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.DesafioCVC.mapper.impl.HotelMapperImpl;
import br.com.cvc.DesafioCVC.model.Hotel;
import br.com.cvc.DesafioCVC.model.dto.HotelDTO;
import br.com.cvc.DesafioCVC.service.impl.HotelsServiceImpl;

@RestController
public class HotelsApiController implements HotelsApi {

	@Inject
	private HotelsServiceImpl hotelsServiceImpl;

	@Inject
	private HotelMapperImpl hotelMapperImpl;

	@Override
	public ResponseEntity<List<Hotel>> getHotels(@NotNull @Valid Integer cityCode, @NotNull @Valid String checkIn,
			@NotNull @Valid String checkOut, @NotNull @Valid Integer qtdPax, @NotNull @Valid Integer qtdChd) {

		HotelDTO[] hotelDTO = hotelsServiceImpl.getHotels(cityCode);
		List<Hotel> hotels = hotelMapperImpl.mapper(hotelDTO);

		return ResponseEntity.ok(hotels);
	}

}
