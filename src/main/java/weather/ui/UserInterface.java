package weather.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;
import weather.ctrl.MyExeption;
import weather.ctrl.WeatherController;

public class UserInterface
{

	private WeatherController ctrl = new WeatherController();
	private Longitude longitude_berlin = new Longitude(13);
	private Latitude latitude_berlin = new Latitude(52);
	GeoCoordinates berlin = new GeoCoordinates(longitude_berlin, latitude_berlin);

	private Longitude longitude_wien = new Longitude(16);
	private Latitude latitude_wien = new Latitude(48);
	GeoCoordinates wien = new GeoCoordinates(longitude_wien, latitude_wien);

	private Longitude longitude_rom = new Longitude(12);
	private Latitude latitude_rom = new Latitude(42);
	GeoCoordinates rom = new GeoCoordinates(longitude_rom, latitude_rom);



	public void getWeatherForCity1(){

		//TODO enter the coordinates
		try {
			ctrl.process(berlin);
		} catch (MyExeption e) {
			e.printStackTrace();
		}

	}

	public void getWeatherForCity2(){
		//TODO enter the coordinates
		try {
			ctrl.process(wien);
		} catch (MyExeption e) {
			e.printStackTrace();
		}

	}

	public void getWeatherForCity3(){
		//TODO enter the coordinates
		try {
			ctrl.process(rom);
		} catch (MyExeption e) {
			e.printStackTrace();
		}

	}

	public void getWeatherByCoordinates() {
		//TODO read the coordinates from the cmd
		//TODO enter the coordinates
		System.out.println("Geben Sie die longitude an:");
		Scanner enter_longitude = new Scanner(System.in);
		double enter_long = enter_longitude.nextDouble();
		Longitude longitude_console = new Longitude(enter_long);

		System.out.println("Geben Sie die latitude an:");
		Scanner enter_latitude = new Scanner(System.in);
		double enter_lat = enter_latitude.nextDouble();
		Latitude latitude_console = new Latitude(enter_lat);

		GeoCoordinates console = new GeoCoordinates(longitude_console, latitude_console);

		try {
			ctrl.process(console);
		} catch (MyExeption e) {
			e.printStackTrace();
		}

	}

	public void start() {
		Menu<Runnable> menu = new Menu<>("Weather Infos");
		menu.setTitel("Wählen Sie eine Stadt aus:");
		menu.insert("a", "Berlin", this::getWeatherForCity1);
		menu.insert("b", "Wien", this::getWeatherForCity2);
		menu.insert("c", "Rom", this::getWeatherForCity3);
		menu.insert("d", "City via Coordinates:",this::getWeatherByCoordinates);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			choice.run();
		}
		System.out.println("Program finished");
	}


	protected String readLine()
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit)
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}