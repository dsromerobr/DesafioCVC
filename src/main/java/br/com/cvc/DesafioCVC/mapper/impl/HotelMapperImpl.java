package br.com.cvc.DesafioCVC.mapper.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import br.com.cvc.DesafioCVC.mapper.HotelMapper;
import br.com.cvc.DesafioCVC.model.Hotel;
import br.com.cvc.DesafioCVC.model.Rooms;
import br.com.cvc.DesafioCVC.model.dto.HotelDTO;
import br.com.cvc.DesafioCVC.model.dto.RoomDTO;

@Component
public class HotelMapperImpl implements HotelMapper {

	public List<Hotel> mapper(HotelDTO[] hotelDTO) {

		ModelMapper modelMapper = new ModelMapper();

		PropertyMap<RoomDTO, Rooms> roomsMap = new PropertyMap<RoomDTO, Rooms>() {
			protected void configure() {
				map().getPriceDetail().setPricePerDayAdult(source.getPrice().getAdult());
				map().getPriceDetail().setPricePerDayChild(source.getPrice().getChild());
			}
		};

		modelMapper.addMappings(roomsMap);

		Hotel[] returnHotels = modelMapper.map(hotelDTO, Hotel[].class);

		List<Hotel> response = Arrays.asList(returnHotels);

		return response;

	}

}
