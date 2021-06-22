# covid-tweeter

Tweets out available covid19-vaccine time slots in Gothenburg fetched from: https://www.vgregion.se/ov/vaccinationstider/bokningsbara-tider/



## Published data

```
Göteborg: Närhälsan Scandinavium
Lediga tider: 3
Boka: https://link-too-booking-site

Åldergrupp: Född 1976 eller tidigare.
Uppdaterad: 2021-06-21 09:09
```



## Setup

Configure your twitter4j properties. Either in `src/resources/twitter4j.properies` or via environment variables. See resources for more details.

```
debug=true
oauth.consumerKey=*********************
oauth.consumerSecret=******************************************
oauth.accessToken=**************************************************
oauth.accessTokenSecret=******************************************
```



## Resources

Timeslot information: https://www.vgregion.se/ov/vaccinationstider/bokningsbara-tider/

Configure twitter4j: https://twitter4j.org/en/configuration.html

