package model;

public class FixedCard {
	private String card_id=null;
	private String seat_id=null;
	private String name=null;
	private String address=null;
	private String car_num=null;
	private int seat_state=0;
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getSeat_id() {
		return seat_id;
	}
	public void setSeat_id(String seat_id) {
		this.seat_id = seat_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCar_num() {
		return car_num;
	}
	public void setCar_num(String car_num) {
		this.car_num = car_num;
	}
	public int getSeat_state() {
		return seat_state;
	}
	public void setSeat_state(int seat_state) {
		this.seat_state = seat_state;
	}
}
