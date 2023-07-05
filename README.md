## Donatr
Donatr is an android mobile application that enables a simulated experience of donating to charities by **simply swiping left or right.**

<p align="center"><img width="600" src="https://github.com/jrbjrb1212/Donatr/blob/readme_pics/Logo.png?raw=true"></p>
<!-- <p align="center"><img width="600" src="readme_pics/Logo.png?raw=true"></p> -->


## Motivation and *Super Cool Facts*
Did you that Americans gave more than $471 billion to charities in 2020 ([soruce](https://www.definefinancial.com/blog/charitable-giving-statistics/#:~:text=In%20fact%2C%20Americans%20gave%20a,giving%20despite%20a%20global%20pandemic.))?

In 2020, Nigeria had a nominal gross domestic product (GDP) of $429 billion. The industry of charitable given just within the United States was **larger than** the nominal GDP of **167 countries** ([source](https://statisticstimes.com/economy/countries-by-gdp.php)). To put that in perspective, the combined revenue of Microsoft and Alphabet (Google) in 2022 was $492.2 billion and 2023 was the first year that these companies eclipsed donations ([source](https://companiesmarketcap.com/largest-companies-by-revenue/)). 

**Wow! There seems to be a market for charitable giving!**

## Background
Donatr is a proof of concept that hopes to modernize the charitable giving process. Donatr has the functionality to allow users to simulate donating to charities by simply swiping left or right.

## Check Out This UI
<p align="center"><img width="600" src="https://github.com/jrbjrb1212/Donatr/blob/readme_pics/Demo.png?raw=true"></p>
<!-- <p align="center"><img width="200" src="readme_pics/Demo.png?raw=true"></p> -->

Similar to the functionality of popular dating mobile app Tinder, Donatr implements a **swipe by card interface**. A user sees one card at a time. Each card contains a logo of the charitable organization, the organization's name, and a short bio of the organization. The **user may review** the card and decided to **swipe left or right**. Swiping left does not initiate a donation and brings up another card. Swiping right initiates a donation and brings up another card. For accessibility, the user also allows for a on tap and on hover feature for saying yes or no to a given charity. These features are available in the bottom portion of the UI under the card. The **application tracks recent transactions** by the user and allows the user to upload more funds.


## Application Architecture
<p align="center"><img width="600" src="https://github.com/jrbjrb1212/Donatr/blob/readme_pics/Architecture.png?raw=true"></p>
<!-- <p align="center"><img width="600" src="readme_pics/Architecture.png?raw=true"></p> -->

Donatr's application architecture is simplified into the design above. The UI and application logic are created in [Kotlin](https://github.com/JetBrains/kotlin) within [Android Studio](https://developer.android.com/studio). Any data relevant to the user or the charities is stored in the [Firebase backend](https://firebase.google.com/). Firebase's free tier is perfect for this proof of concept application. The Glide library is used to make web requests to the web images stored on the internet. Charitable organization logo pictures are big far the largest data element for this application. To reduce the overall storage footprint, the [Glide library](https://github.com/bumptech/glide) makes a web request to an image url.

1. On first use, the user will create a Donatr account. This will trigger a new account to be generated and storied in the Firebase backend.    
    - After that first initial account creation, the user can load back into their account the next time the application is opened. 
2. While a user swipes on charities, there are constant API web requests made to the Firebase backend to load a new charity's UI card. 
3. With each new UI card, there is also a web request made to an image URL that is brought in from Firebase via the Glide library. 
4. Each time a user makes a transaction, there is a request made to the backend to record the size of the donation and the organization the donation was made to.
5. At any point, a user can view the transaction history page, which triggers a backend request to query all recent transactions/donations by the user.

## Possible Future Development

As already stated, this version of Donatr is a proof of concept for creating an application for modernizing the donation process. There are several possible future developments that could come from this project.

1. Port the application to IOS by rewriting the Kotlin code with Swift or Objective-C code
    - Would need pay the developer fee to receive access to Apple's IOS development SDK.
    - When completed ported, one would need to publish to the IOS store and go through the review process.
2. Flesh out the charitable organization intake process.
    - Currently, only manually added applications be used.
    - This process can be updated by taking advantage of modern web scraping technologies.
    - Alternatively, there may be a existing database or API for getting active organizations.
    - Be careful to not flood the backend, there are over 1.5 million charitable organization in the US alone ([source](https://www.zippia.com/advice/nonprofit-statistics/#:~:text=How%20many%20nonprofits%20are%20there,organizations%20in%20the%20United%20States.)).
3. Add forum features to the working version
    - It is no secret people like bragging about their charitable donations for ego purposes.
    - A feature to fuel this could allow for a higher user interactivity.
4. Add leaderboard feature to the working version
    - Similar to previous feature proposal in motivation.
    - Any application that gamifies a simple activity can lead to an increase in userbase and interactivity.
5. Add better security and authenticate to the sign in and payment layer of the architecture
    - The current version adds no password checking
    - It is currently possible for any user to sign in to another user's account and view sensitive information
    - This project was made by software developers without experience in anything security related
6. Add client-side caches for the 10-50 charity cards
    - While using the working version, a user may suffer API latency as a result of the multiple calls to the backend and the Glide library.
    - The application should create a client-side cache to prefetch and asynchronously make backend and Glide calls.
7. Migrate the backend from Firebase to the a more sophisticated backend
    - Firebase free version is quite limited in functionality
    - A more sophisticated backend like Google Cloud Platform Firestore, AWS DynamoDB, MongoDB or PostgreSQL 
    - Any one of these choices or similar backends would allow for the removal of the Glide library middle layer.



Thank you for the taking the time to check out this[project](https://www.youtube.com/watch?v=dQw4w9WgXcQ)! 