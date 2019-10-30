package br.com.cvc.DesafioCVC.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.DesafioCVC.model.Hotel;

@RestController
public class HotelsApiControllerImpl implements HotelsApi {

	@Override
	public ResponseEntity<Hotel> getHotels(@NotNull @Valid Integer cityCode, @NotNull @Valid String checkIn,
			@NotNull @Valid String checkOut, @NotNull @Valid Integer qtdPax, @NotNull @Valid Integer qtdChd) {

		String teste = "";
		System.out.print("--->" + teste);

		return HotelsApi.super.getHotels(cityCode, checkIn, checkOut, qtdPax, qtdChd);
	}

}
