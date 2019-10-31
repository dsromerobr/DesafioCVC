package br.com.cvc.DesafioCVC.mapper;

import java.util.List;

import br.com.cvc.DesafioCVC.model.Hotel;
import br.com.cvc.DesafioCVC.model.dto.HotelDTO;

public interface HotelMapper {
	
	public List<Hotel> mapper(HotelDTO[] hotelDTO);

}
