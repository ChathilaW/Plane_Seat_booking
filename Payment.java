package PlaneSeatsApp;

public class Payment{
	private String email;
	private double payment_amount;
	
	public Payment(String email, double payment_amount){
		this.email = email;
		this.payment_amount = payment_amount;
	}
	
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	public double getPayment_amount(){
		return payment_amount;
	}
	public void setPayment_amount(double payment_amount){
		this.payment_amount = payment_amount;
	}
	
	public void printPayment(){
		System.out.println(" Email:" + email + " " + "Payment:" + payment_amount);
	}
}