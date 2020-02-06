package model;

public class Seat {
	private String seat_id;
	private String seat_section;
	private int seat_state=0;
	private String seat_tag;
	
	public String getSeat_id() {
		return seat_id;
	}
	public void setSeat_id(String seat_id) {
		this.seat_id = seat_id;
	}
	public String getSeat_section() {
		return seat_section;
	}
	public void setSeat_section(String seat_section) {
		this.seat_section = seat_section;
	}
	public int getSeat_state() {
		return seat_state;
	}
	public void setSeat_state(int seat_state) {
		this.seat_state = seat_state;
	}
	public String getSeat_tag() {
		return seat_tag;
	}
	public void setSeat_tag(String seat_tag) {
		this.seat_tag = seat_tag;
	}
	
	
}
