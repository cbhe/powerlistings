<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>APIs Test</title>
</head>
<body>
	<h1>PowerListings APIs Test Page</h1>
	<hr>
	<h2>Search Test</h2>
	<form action="search" method="GET">
		<table>
			<tr>
				<td>name</td>	
				<td><input type="text" name="name" value="五道口"></td>
			</tr>
			<tr>
				<td>address</td>
				<td><input type="text" name="address"></td>
			</tr>
			<tr>
				<td>address2</td>
				<td><input type="text" name="address2"></td>
			</tr>
			<tr>
				<td>sublocality</td>
				<td><input type="text" name="sublocality"></td>
			</tr>
			<tr>
				<td>city</td>
				<td><input type="text" name="city"><td>
			</tr>
			<tr>
				<td>state</td>
				<td><input type="text" name="state"></td>
			</tr>
			<tr>
				<td>zip</td>
				<td><input type="text" name="zip"></td>
			</tr>
			<tr>
				<td>countryCode</td>
				<td><input type="text" name="countryCode"></td>
			</tr>
			<tr>
				<td>latlng</td>
				<td><input type="text" name="latlng" value="39.992949,116.338042"><td>
			</td>
			<tr>
				<td>radius</td>
				<td><input type="text" name="radius"></td>
			</tr>
			<tr>
				<td>phone</td>
				<td><input type="text" name="phone"></td>
			</tr>
			<tr>
				<td>type</td>
				<td><input type="text" name="type"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="提交"></td>
			</tr>
		</table>
	</form>
	<hr>
	<h2>Detail Test</h2>
	<form action="detail" method = "GET">
		ID<input type = "text" name = "id" value = "1_D1000443103746">
		<input type = "submit" name = "summit">
	</form>
	<hr>

	<h2>Order Test</h2>
	<form action="order" method="post">
		Input Json into this textbox:<br>
		<input type="text" name="jsonString" value="{&quot;yextId&quot;:486070,&quot;status&quot;:&quot;ACTIVE&quot;,&quot;name&quot;:&quot;CliqCandyShop&quot;,&quot;address&quot;:{&quot;postalCode&quot;:&quot;36701&quot;,&quot;countryCode&quot;:&quot;US&quot;,&quot;sublocality&quot;:null,&quot;visible&quot;:true,&quot;address&quot;:&quot;307HighlandAvenue&quot;,&quot;state&quot;:&quot;AL&quot;,&quot;address2&quot;:&quot;Rt.14&quot;,&quot;city&quot;:&quot;Selma&quot;,&quot;displayAddress&quot;:&quot;IntheSelmaShoppingMall&quot;},&quot;phones&quot;:[{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;9174721894&quot;},&quot;type&quot;:&quot;MAIN&quot;,&quot;description&quot;:&quot;Main&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530861&quot;},&quot;type&quot;:&quot;ALTERNATE&quot;,&quot;description&quot;:&quot;Alt&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530862&quot;},&quot;type&quot;:&quot;FAX&quot;,&quot;description&quot;:&quot;Fax&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530863&quot;},&quot;type&quot;:&quot;MOBILE&quot;,&quot;description&quot;:&quot;Mobile&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530864&quot;},&quot;type&quot;:&quot;TOLL_FREE&quot;,&quot;description&quot;:&quot;Toll-Free&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530865&quot;},&quot;type&quot;:&quot;TTY&quot;,&quot;description&quot;:&quot;TTY&quot;}],&quot;categories&quot;:[{&quot;id&quot;:&quot;4083665573&quot;,&quot;name&quot;:&quot;Food;;Other&quot;}],&quot;description&quot;:&quot;CliqCandyshophasbeeninbusinesssince1971,andwemakethebestcandy,sweets,icecream,andfudgeinallthecounty!\r\n\r\nTrue!&quot;,&quot;emails&quot;:[{&quot;address&quot;:&quot;Cliq@search.com&quot;,&quot;description&quot;:&quot;Owner&quot;},{&quot;address&quot;:&quot;info@cliqcandy.com&quot;,&quot;description&quot;:&quot;Info&quot;}],&quot;geoData&quot;:{&quot;displayLatitude&quot;:32.431252,&quot;displayLongitude&quot;:-87.035238,&quot;routableLongitude&quot;:0,&quot;routableLatitude&quot;:0},&quot;hours&quot;:[{&quot;intervals&quot;:[],&quot;day&quot;:&quot;MONDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;10:00:00&quot;,&quot;end&quot;:&quot;21:30:00&quot;}],&quot;day&quot;:&quot;TUESDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;10:00:00&quot;,&quot;end&quot;:&quot;21:30:00&quot;}],&quot;day&quot;:&quot;WEDNESDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;10:00:00&quot;,&quot;end&quot;:&quot;21:30:00&quot;}],&quot;day&quot;:&quot;THURSDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;10:00:00&quot;,&quot;end&quot;:&quot;21:30:00&quot;}],&quot;day&quot;:&quot;FRIDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;00:00:00&quot;,&quot;end&quot;:&quot;00:00:00&quot;}],&quot;day&quot;:&quot;SATURDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;00:00:00&quot;,&quot;end&quot;:&quot;02:00:00&quot;},{&quot;start&quot;:&quot;11:00:00&quot;,&quot;end&quot;:&quot;20:00:00&quot;}],&quot;day&quot;:&quot;SUNDAY&quot;}],&quot;hoursText&quot;:{&quot;additional&quot;:&quot;OpenonNewYears!&quot;,&quot;display&quot;:&quot;MClosed,Tu-F10am-9:30pm,Sa24hr,Su12am-2am,11am-8pm(OpenonNewYears!)&quot;},&quot;images&quot;:[{&quot;height&quot;:225,&quot;width&quot;:225,&quot;type&quot;:&quot;LOGO&quot;,&quot;url&quot;:&quot;http://www.yext-static.com/cms/454b0f69-54a9-4d98-a67d-1e54fe53a188.jpeg&quot;},{&quot;height&quot;:239,&quot;width&quot;:211,&quot;type&quot;:&quot;GALLERY&quot;,&quot;url&quot;:&quot;http://www.yext-static.com/cms/dc6db60b-907f-4933-9509-64424bdf1ff4.jpeg&quot;},{&quot;height&quot;:201,&quot;description&quot;:&quot;Ourmapleseasonalspecialty!&quot;,&quot;width&quot;:251,&quot;type&quot;:&quot;GALLERY&quot;,&quot;url&quot;:&quot;http://www.yext-static.com/cms/99bf3535-a977-4f8b-a05e-dee773d3be6f.jpeg&quot;},{&quot;height&quot;:185,&quot;description&quot;:&quot;Owneronthejob!&quot;,&quot;width&quot;:272,&quot;type&quot;:&quot;STOREFRONT&quot;,&quot;url&quot;:&quot;http://www.yext-static.com/cms/8804153d-c7f9-4007-9c79-caf8ad2aa133.jpeg&quot;}],&quot;videos&quot;:[{&quot;description&quot;:&quot;Howtomakecandy&quot;,&quot;url&quot;:&quot;http://www.youtube.com/watch?v=seCYbfHpQ5w&quot;},{&quot;description&quot;:&quot;HomemadeCandy&quot;,&quot;url&quot;:&quot;http://www.youtube.com/watch?v=RfZWEpJsShE&quot;}],&quot;specialOffer&quot;:{&quot;message&quot;:&quot;LearnhowtomakecandythisThursday!&quot;,&quot;url&quot;:&quot;http://pl.yext.com/plclick?pid=a1b2c3d4e5&ids=486070&continue=http%3A%2F%2Fcandy.about.com%2Fod%2Fcandybasics%2Fa%2Fcandy_beginners.htm&target=specialOffer&quot;},&quot;paymentOptions&quot;:[&quot;AmericanExpress&quot;,&quot;Cash&quot;,&quot;Check&quot;,&quot;DinersClub&quot;,&quot;Discover&quot;,&quot;Financing&quot;,&quot;Invoice&quot;,&quot;MasterCard&quot;,&quot;Traveler'sCheck&quot;,&quot;Visa&quot;],&quot;urls&quot;:[{&quot;type&quot;:&quot;WEBSITE&quot;,&quot;description&quot;:&quot;website&quot;,&quot;url&quot;:&quot;http://www.yext.com&quot;},{&quot;type&quot;:&quot;RESERVATION&quot;,&quot;description&quot;:&quot;reservation&quot;,&quot;displayUrl&quot;:&quot;http://opentable.com&quot;,&quot;url&quot;:&quot;http://opentable.com&quot;},{&quot;type&quot;:&quot;MENU&quot;,&quot;description&quot;:&quot;menu&quot;,&quot;url&quot;:&quot;http://www.menupages.com&quot;},{&quot;type&quot;:&quot;ORDER&quot;,&quot;description&quot;:&quot;order&quot;,&quot;displayUrl&quot;:&quot;http://seamless.com&quot;,&quot;url&quot;:&quot;http://seamless.com&quot;}],&quot;attribution&quot;:{&quot;image&quot;:{&quot;height&quot;:20,&quot;description&quot;:&quot;YextPowerListings&quot;,&quot;width&quot;:143,&quot;url&quot;:&quot;http://www.yext-static.com/cms/pl-synced/pl-synced.png&quot;},&quot;attributionUrl&quot;:&quot;http://www.yext.com&quot;},&quot;keywords&quot;:[&quot;Candy&quot;,&quot;Sweets&quot;,&quot;Fudge&quot;],&quot;lists&quot;:[{&quot;id&quot;:7523547,&quot;name&quot;:&quot;Breakfast&quot;,&quot;type&quot;:&quot;MENU&quot;,&quot;description&quot;:&quot;Menu&quot;},{&quot;id&quot;:3612738,&quot;name&quot;:&quot;KnickKnacks&quot;,&quot;type&quot;:&quot;PRODUCTS&quot;,&quot;description&quot;:&quot;Products&quot;}],&quot;closed&quot;:false,&quot;closeDate&quot;:&quot;2014-01-01&quot;,&quot;specialties&quot;:[&quot;ProvidingInsuranceAndFinancialServices&quot;,&quot;MultiLineDiscountsAvailable&quot;,&quot;CallAStateFarmAgentForAQuote24/7&quot;],&quot;brands&quot;:[&quot;Hershey&quot;],&quot;products&quot;:[&quot;Sweets&quot;,&quot;Chocolates&quot;],&quot;services&quot;:[&quot;Baking&quot;,&quot;Sweetening&quot;],&quot;yearEstablished&quot;:&quot;1971&quot;,&quot;associations&quot;:[&quot;LocalFoodAcademy&quot;],&quot;languages&quot;:[&quot;English&quot;,&quot;Spanish&quot;]}">
		<input type="submit" name="submit">
	</form>
	<hr>
	<h2>Update Test</h2>
	<!--
	<form action="486070" method="put">
		Input Json into this textbox:<br>
		<input type="text" name="jsonString" value="{&quot;yextId&quot;:486070,&quot;partnerId&quot;:&quot;559633471&quot;,&quot;status&quot;:&quot;ACTIVE&quot;,&quot;name&quot;:&quot;CliqCandyShop&quot;,&quot;address&quot;:{&quot;postalCode&quot;:&quot;36701&quot;,&quot;countryCode&quot;:&quot;US&quot;,&quot;sublocality&quot;:null,&quot;visible&quot;:true,&quot;address&quot;:&quot;307HighlandAvenue&quot;,&quot;state&quot;:&quot;AL&quot;,&quot;address2&quot;:&quot;Rt.14&quot;,&quot;city&quot;:&quot;Selma&quot;,&quot;displayAddress&quot;:&quot;IntheSelmaShoppingMall&quot;},&quot;phones&quot;:[{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;9174721894&quot;},&quot;type&quot;:&quot;MAIN&quot;,&quot;description&quot;:&quot;Main&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530861&quot;},&quot;type&quot;:&quot;ALTERNATE&quot;,&quot;description&quot;:&quot;Alt&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530862&quot;},&quot;type&quot;:&quot;FAX&quot;,&quot;description&quot;:&quot;Fax&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530863&quot;},&quot;type&quot;:&quot;MOBILE&quot;,&quot;description&quot;:&quot;Mobile&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530864&quot;},&quot;type&quot;:&quot;TOLL_FREE&quot;,&quot;description&quot;:&quot;Toll-Free&quot;},{&quot;number&quot;:{&quot;countryCode&quot;:&quot;1&quot;,&quot;number&quot;:&quot;2032530865&quot;},&quot;type&quot;:&quot;TTY&quot;,&quot;description&quot;:&quot;TTY&quot;}],&quot;categories&quot;:[{&quot;id&quot;:&quot;4083665573&quot;,&quot;name&quot;:&quot;Food;;Other&quot;}],&quot;description&quot;:&quot;CliqCandyshophasbeeninbusinesssince1971,andwemakethebestcandy,sweets,icecream,andfudgeinallthecounty!\r\n\r\nTrue!&quot;,&quot;emails&quot;:[{&quot;address&quot;:&quot;Cliq@search.com&quot;,&quot;description&quot;:&quot;Owner&quot;},{&quot;address&quot;:&quot;info@cliqcandy.com&quot;,&quot;description&quot;:&quot;Info&quot;}],&quot;geoData&quot;:{&quot;displayLatitude&quot;:32.431252,&quot;displayLongitude&quot;:-87.035238,&quot;routableLongitude&quot;:0,&quot;routableLatitude&quot;:0},&quot;hours&quot;:[{&quot;intervals&quot;:[],&quot;day&quot;:&quot;MONDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;10:00:00&quot;,&quot;end&quot;:&quot;21:30:00&quot;}],&quot;day&quot;:&quot;TUESDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;10:00:00&quot;,&quot;end&quot;:&quot;21:30:00&quot;}],&quot;day&quot;:&quot;WEDNESDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;10:00:00&quot;,&quot;end&quot;:&quot;21:30:00&quot;}],&quot;day&quot;:&quot;THURSDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;10:00:00&quot;,&quot;end&quot;:&quot;21:30:00&quot;}],&quot;day&quot;:&quot;FRIDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;00:00:00&quot;,&quot;end&quot;:&quot;00:00:00&quot;}],&quot;day&quot;:&quot;SATURDAY&quot;},{&quot;intervals&quot;:[{&quot;start&quot;:&quot;00:00:00&quot;,&quot;end&quot;:&quot;02:00:00&quot;},{&quot;start&quot;:&quot;11:00:00&quot;,&quot;end&quot;:&quot;20:00:00&quot;}],&quot;day&quot;:&quot;SUNDAY&quot;}],&quot;hoursText&quot;:{&quot;additional&quot;:&quot;OpenonNewYears!&quot;,&quot;display&quot;:&quot;MClosed,Tu-F10am-9:30pm,Sa24hr,Su12am-2am,11am-8pm(OpenonNewYears!)&quot;},&quot;images&quot;:[{&quot;height&quot;:225,&quot;width&quot;:225,&quot;type&quot;:&quot;LOGO&quot;,&quot;url&quot;:&quot;http://www.yext-static.com/cms/454b0f69-54a9-4d98-a67d-1e54fe53a188.jpeg&quot;},{&quot;height&quot;:239,&quot;width&quot;:211,&quot;type&quot;:&quot;GALLERY&quot;,&quot;url&quot;:&quot;http://www.yext-static.com/cms/dc6db60b-907f-4933-9509-64424bdf1ff4.jpeg&quot;},{&quot;height&quot;:201,&quot;description&quot;:&quot;Ourmapleseasonalspecialty!&quot;,&quot;width&quot;:251,&quot;type&quot;:&quot;GALLERY&quot;,&quot;url&quot;:&quot;http://www.yext-static.com/cms/99bf3535-a977-4f8b-a05e-dee773d3be6f.jpeg&quot;},{&quot;height&quot;:185,&quot;description&quot;:&quot;Owneronthejob!&quot;,&quot;width&quot;:272,&quot;type&quot;:&quot;STOREFRONT&quot;,&quot;url&quot;:&quot;http://www.yext-static.com/cms/8804153d-c7f9-4007-9c79-caf8ad2aa133.jpeg&quot;}],&quot;videos&quot;:[{&quot;description&quot;:&quot;Howtomakecandy&quot;,&quot;url&quot;:&quot;http://www.youtube.com/watch?v=seCYbfHpQ5w&quot;},{&quot;description&quot;:&quot;HomemadeCandy&quot;,&quot;url&quot;:&quot;http://www.youtube.com/watch?v=RfZWEpJsShE&quot;}],&quot;specialOffer&quot;:{&quot;message&quot;:&quot;LearnhowtomakecandythisThursday!&quot;,&quot;url&quot;:&quot;http://pl.yext.com/plclick?pid=a1b2c3d4e5&ids=486070&continue=http%3A%2F%2Fcandy.about.com%2Fod%2Fcandybasics%2Fa%2Fcandy_beginners.htm&target=specialOffer&quot;},&quot;paymentOptions&quot;:[&quot;AmericanExpress&quot;,&quot;Cash&quot;,&quot;Check&quot;,&quot;DinersClub&quot;,&quot;Discover&quot;,&quot;Financing&quot;,&quot;Invoice&quot;,&quot;MasterCard&quot;,&quot;Traveler'sCheck&quot;,&quot;Visa&quot;],&quot;urls&quot;:[{&quot;type&quot;:&quot;WEBSITE&quot;,&quot;description&quot;:&quot;website&quot;,&quot;url&quot;:&quot;http://www.yext.com&quot;},{&quot;type&quot;:&quot;RESERVATION&quot;,&quot;description&quot;:&quot;reservation&quot;,&quot;displayUrl&quot;:&quot;http://opentable.com&quot;,&quot;url&quot;:&quot;http://opentable.com&quot;},{&quot;type&quot;:&quot;MENU&quot;,&quot;description&quot;:&quot;menu&quot;,&quot;url&quot;:&quot;http://www.menupages.com&quot;},{&quot;type&quot;:&quot;ORDER&quot;,&quot;description&quot;:&quot;order&quot;,&quot;displayUrl&quot;:&quot;http://seamless.com&quot;,&quot;url&quot;:&quot;http://seamless.com&quot;}],&quot;attribution&quot;:{&quot;image&quot;:{&quot;height&quot;:20,&quot;description&quot;:&quot;YextPowerListings&quot;,&quot;width&quot;:143,&quot;url&quot;:&quot;http://www.yext-static.com/cms/pl-synced/pl-synced.png&quot;},&quot;attributionUrl&quot;:&quot;http://www.yext.com&quot;},&quot;keywords&quot;:[&quot;Candy&quot;,&quot;Sweets&quot;,&quot;Fudge&quot;],&quot;lists&quot;:[{&quot;id&quot;:7523547,&quot;name&quot;:&quot;Breakfast&quot;,&quot;type&quot;:&quot;MENU&quot;,&quot;description&quot;:&quot;Menu&quot;},{&quot;id&quot;:3612738,&quot;name&quot;:&quot;KnickKnacks&quot;,&quot;type&quot;:&quot;PRODUCTS&quot;,&quot;description&quot;:&quot;Products&quot;}],&quot;closed&quot;:false,&quot;closeDate&quot;:&quot;2014-01-01&quot;,&quot;specialties&quot;:[&quot;ProvidingInsuranceAndFinancialServices&quot;,&quot;MultiLineDiscountsAvailable&quot;,&quot;CallAStateFarmAgentForAQuote24/7&quot;],&quot;brands&quot;:[&quot;Hershey&quot;],&quot;products&quot;:[&quot;Sweets&quot;,&quot;Chocolates&quot;],&quot;services&quot;:[&quot;Baking&quot;,&quot;Sweetening&quot;],&quot;yearEstablished&quot;:&quot;1971&quot;,&quot;associations&quot;:[&quot;LocalFoodAcademy&quot;],&quot;languages&quot;:[&quot;English&quot;,&quot;Spanish&quot;]}">
		<input type="submit" name="submit">
	</form>
	-->
		<hr>
	<h2>Suppress Test</h2>
	<form action="suppress" method="POST">
		<table>
			<tr>
				<td>jsonString</td>
				<td><input type="text" name="jsonString"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="提交"></td>
			</tr>
		</table>
	</form>
		<h2>Servlet Test</h2>
	<a href="test">点击此处进行测试</a>
	<hr>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
</body>
</html>
 