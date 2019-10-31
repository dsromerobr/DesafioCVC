package br.com.cvc.DesafioCVC.model.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "roomID", "categoryName", "price" })
public class RoomDTO {

	@JsonProperty("roomID")
	private Integer roomID;
	@JsonProperty("categoryName")
	private String categoryName;
	@JsonProperty("price")
	private PriceDTO price;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("roomID")
	public Integer getRoomID() {
		return roomID;
	}

	@JsonProperty("roomID")
	public void setRoomID(Integer roomID) {
		this.roomID = roomID;
	}

	@JsonProperty("categoryName")
	public String getCategoryName() {
		return categoryName;
	}

	@JsonProperty("categoryName")
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@JsonProperty("price")
	public PriceDTO getPrice() {
		return price;
	}

	@JsonProperty("price")
	public void setPrice(PriceDTO price) {
		this.price = price;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}