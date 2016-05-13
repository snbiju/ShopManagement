package retailshop.shop.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import retailshop.shop.model.GoogleResponse;
import retailshop.shop.model.Result;
import retailshop.shop.model.ShopAddress;

public class GeoService {

	private final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

	public Map<String, BigDecimal> getGoogleMapData(String postCode) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		try {
			BigDecimal latitude;
			BigDecimal longitude;

			URL url = new URL(URL + "?address=" + URLEncoder.encode(postCode, "UTF-8") + "&sensor=false");
			URLConnection conn = url.openConnection();

			InputStream in = conn.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			GoogleResponse response = (GoogleResponse) mapper.readValue(in, GoogleResponse.class);
			in.close();

			if (response.getStatus().equals("OK")) {
				for (Result result : response.getResults()) {
					map.put("Latitude", new BigDecimal(result.getGeometry().getLocation().getLat()));
					map.put("Longitude", new BigDecimal(result.getGeometry().getLocation().getLng()));
				}
			} else {
				latitude = new BigDecimal("0");
				longitude = new BigDecimal("0");
				map.put("Latitude", latitude);
				map.put("Longitude", longitude);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public List<ShopAddress> getAvailableShops(BigDecimal findLat, BigDecimal findLong, Integer radius){
		
		List<ShopAddress> filteredList = new ArrayList<ShopAddress>();
		List<ShopAddress> shopAddressList = (List<ShopAddress>) new IOService().deserialize();
		for(ShopAddress addr : shopAddressList){
			if(hasShopWithInRange(findLat, findLong, addr.getLatitude(), addr.getLongitude(), radius)){
				filteredList.add(addr);
			}
		}
		return filteredList;
	}
	
	public Boolean hasShopWithInRange(BigDecimal findLat, BigDecimal findLong, BigDecimal lat, BigDecimal lon, Integer radius){
		
		double distance = distance(
							findLat.doubleValue(),
							findLong.doubleValue(),
							lat.doubleValue(),
							lon.doubleValue(),
							"K"
							);
		return distance <= radius;
	}

	private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
