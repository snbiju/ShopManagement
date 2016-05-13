package retailshop.shop.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import retailshop.shop.model.ShopAddress;
import retailshop.shop.service.GeoService;
import retailshop.shop.service.IOService;

@RestController
@RequestMapping("/shop")
public class ShopAddressRest {

	GeoService geo 		= new GeoService();
	IOService ioService = new IOService();
	
	@SuppressWarnings("unchecked")
	private List<ShopAddress> shopeAddressList = (List<ShopAddress>) ioService.deserialize(); 

	@RequestMapping(value = "/shopjson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ShopAddress getJsonString() {
		
		return new ShopAddress();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewallshops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<ShopAddress> getAllShopsAddress() {
		
		return (List<ShopAddress>) ioService.deserialize();
	}
	
	@RequestMapping(value = "/findshops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<ShopAddress> findShops(@RequestParam("latitude") double latitude, 
			@RequestParam("longitude") double longitude,@RequestParam("radius") Integer radius) {

		BigDecimal findLat = new BigDecimal(latitude);
		BigDecimal findLong = new BigDecimal(longitude);
		if(radius == null){
			radius = 10;
		}
		return (List<ShopAddress>) geo.getAvailableShops(findLat, findLong, radius);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public ShopAddress save(@RequestBody ShopAddress shopAddress) throws Exception {

		Map<String, BigDecimal> data = geo.getGoogleMapData(shopAddress.getPostCode());
		shopAddress.setLatitude(data.get("Latitude"));
		shopAddress.setLongitude(data.get("Longitude"));

		if(shopeAddressList == null){
			shopeAddressList = new ArrayList<ShopAddress>();
		}
		shopeAddressList.add(shopAddress);
		ioService.serialize(shopeAddressList);

		return shopAddress;
	}
}
