package br.com.cvc.DesafioCVC.api;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.DesafioCVC.mapper.HotelMapper;
import br.com.cvc.DesafioCVC.model.Hotel;
import br.com.cvc.DesafioCVC.model.dto.HotelDTO;
import br.com.cvc.DesafioCVC.service.HotelsService;

@RestController
public class HotelsApiController implements HotelsApi {

	@Inject
	private HotelsService hotelsService;

	@Inject
	private HotelMapper hotelMapper;

	@Override
	public ResponseEntity<List<Hotel>> getHotels(@NotNull @Valid Integer cityCode, @NotNull @Valid String checkIn,
			@NotNull @Valid String checkOut, @NotNull @Valid Integer qtdPax, @NotNull @Valid Integer qtdChd) {

		HotelDTO[] hotelDTO = hotelsService.getHotels(cityCode);
		List<Hotel> hotels = hotelMapper.mapper(hotelDTO);
		hotels = hotelsService.setTotalPrice(hotels, checkIn, checkOut, qtdPax, qtdChd);

		if(hotels.isEmpty()) {
			return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(hotels);
	}

	@Override
	public ResponseEntity<List<Hotel>> getHotelDetail(Integer idHotel, @NotNull @Valid String checkIn,
			@NotNull @Valid String checkOut, @NotNull @Valid Integer qtdPax, @NotNull @Valid Integer qtdChd) {

		HotelDTO[] hotelDTO = hotelsService.getHotelDetail(idHotel);
		List<Hotel> hotels = hotelMapper.mapper(hotelDTO);
		hotels = hotelsService.setTotalPrice(hotels, checkIn, checkOut, qtdPax, qtdChd);
		
		if(hotels.isEmpty()) {
			return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(hotels);

	}
	


}
