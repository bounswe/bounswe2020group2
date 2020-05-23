import { useState } from 'react'
import Head from 'next/head'

import axios from 'axios'

import dynamic from 'next/dynamic'


const MapView = dynamic(() => import('../components/MapView'), { ssr: false })
// axios is a library used to make requests

// THIS CODE IS EXECUTED ON THE ********SERVER******** SIDE
// You can use this function to receive the request params
// and render something on the server
// the return of this function is fed into Home component

// REMEMBER: You can always console.log (either in the server or in the browser, depending on where the code is
// executed) to see what each object looks like!
export async function getServerSideProps(ctx) {
    const { req, query } = ctx
    const { url, method, headers } = req
    const context = { query, url, method }
    return { props: { context } } // This object called "context" is the same context object in Home
}

// THIS CODE IS EXECUTED ON THE ********CLIENT******** SIDE
export default function Home({ context }) {
    // <-- This "context" object is the same object return in getServerSideProps
    // REMEMBER: You can always console.log (either in the server or in the browser, depending on where the code is
    // executed) to see what each object looks like!

    const [coordinates, setCoordinates] = useState({
        longitude: 29.046874,
        latitude: 41.085212,
    })

    const [covidStatsString, setCovidStatsString] = useState()

    const [currency, setCurrency] = useState({
        code: 'TRY',
        symbol: 'TL',
        name: 'Turkish Lira',
    })

    const [language, setLanguage] = useState({
        code: 'TUR',
        name: 'Turkish',
    })

    const [country, setCountry] = useState({
        code: 'TR',
        name: 'Turkey',
    })

    // To show visually the price conversion process
    const [priceConversionText, setPriceConversionText] = useState("Use the button to try price conversion")


    const onMapClick = async ({ longitude, latitude }) => {
        try {
            console.log({ longitude, latitude })
            const { data } = await axios.get(`api/getLocationInfo?lat=${latitude}&lng=${longitude}`)
            const { valid, currency, country, language } = data
            console.log(data)

            if (!valid) {
                setCurrency(undefined)
                setCountry(undefined)
                setLanguage(undefined)
                return
            }

            setCountry({ name: country })
            setLanguage({ name: language })
            setCurrency(currency)
        } catch (error) {
            console.error(error)
            setCurrency(undefined)
            setCountry(undefined)
        } finally {
            setCoordinates({ longitude, latitude })
        }
    }

    // When the button is clicked, makes a price conversion demonstration.
    const onConvertButtonClick = async () => {
        try {
            const ipdata = await getUsersIP()
            const pricedata = await priceConverter(10, ipdata.query, "USD")
            Promise
            setPriceConversionText(`Your ip adress is ${ipdata.query} and currency of the country that this IP belongs to is ${pricedata.currency}.
            If price of a product is 10 USD, then it is equal to ${pricedata.price.toFixed(2)} ${pricedata.currency} in your currency.`)
        } catch (error) {
            console.log(error)
            setPriceConversionText("Sorry, service unavailable at this time.")
        }
    }

    // Returns the IP of the user
    const getUsersIP = async () => {
        const {data} = await axios.get("http://ip-api.com/json/")
        return data
    }

    // Using the given ip, finds the currency of the country that the given IP belongs to.
    // Then, converts the given price in srcCurrency to the new currency. 
    // Returns the result
    const priceConverter = async (pr, ip, srcCurrency) => {
        try {
            const {data} = await axios.get('api/getConvertedPrice?ip='+ip+'&price='+pr+'&srcCurrency='+srcCurrency)
            return data
            
        } catch (error) {
            console.error(error) 
        } 
    }
    //This function returns the top 10 countries with maximum number of deaths and their death statistics
    const getCountriesWithMaxDeaths = async () => {
        //Get the data from our api
        const {data} = await axios.get("api/getCountriesWithMaxDeaths")
        //Print to the front-end
        var ctx = document.getElementById("bar_deaths");
        ctx.style.visibility = "visible";
        var chart = new Chart(ctx, {
          // We are creating a bar chart for death statistics of top 10 countries
          type: 'bar',
          data: {
              labels: data.keys,
              datasets: [{
                  label: 'Number of Deaths',
                  backgroundColor: [
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
                    'rgba(130, 50, 86, 0.2)',
                    'rgba(140, 60, 240, 0.2)',
                    'rgba(230, 99, 132, 0.2)',
                    'rgba(243, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(255,99,132,1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 159, 64, 1)',
                    'rgba(130, 50, 86, 1)',
                    'rgba(140, 60, 240, 1)',
                    'rgba(230, 99, 132, 1)',
                    'rgba(243, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1,
                  data: data.values
              }]
          },
          options: {
            title: {
              display: true,
              text: "Death Numbers of Top 10 Countries"
          }
          }
      });
    }
    const getCovidStats = async () => {
        if (country.name == null) {
            setCovidStatsString('Please select a location by clicking on the map')
            return
        }
        const { data } = await axios.get(`api/getStatistics?country=${country.name}`)

        if (data.answered == 'yes') {
            setCovidStatsString(
                `Country: ${country.name} Death Toll:  ${data.deathToll} Recovered People:  ${data.recovery} Infected People: ${data.infection}`,
            )
        } else {
            setCovidStatsString('No information available about your country')
        }
    }

    const locationGreeting = () =>
        `You are a user from ${country.name}, you speak ${language.name} and you buy in ${currency.name}`


    return (
        <div className="container">
            <Head>
                <title>Our example API</title>
                <link rel="icon" href="/favicon.ico" />
                <link href="https://api.mapbox.com/mapbox-gl-js/v1.10.0/mapbox-gl.css" rel="stylesheet" />
                <link
                    rel="stylesheet"
                    href="https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-geocoder/v4.5.1/mapbox-gl-geocoder.css"
                    type="text/css"
                />
            </Head>
            <main>
                <h1>Welcome to our demo website!</h1>
                {priceConversionText && <p>{priceConversionText}</p>}<button variant='primary' onClick={onConvertButtonClick}>Price Conversion</button>
                {country && language && currency && <p>{locationGreeting()} </p>}
                <MapView onMapClick={onMapClick} coordinates={coordinates} />
                <button onClick={getCovidStats}>
                    Click me to get Covid death statistics for the country you have chosen on the map
                </button>
                {covidStatsString !== undefined && <p>{covidStatsString}</p>}
                <p>Use the button to see the Death Statistics of Top 10 Countries in a Bar Chart</p>
                <button onClick={getCountriesWithMaxDeaths}>Death Statistics of Top 10 Countries</button>
                <canvas id="bar_deaths" ></canvas>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
            </main>
            <style jsx global>
                {`
                    html,
                    body {
                        padding: 0;
                        margin: 1%;
                        font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell,
                            Fira Sans, Droid Sans, Helvetica Neue, sans-serif;
                    }
                    canvas {
                        display: none;
                      }
                    * {
                        box-sizing: border-box;
                    }
                `}
            </style>
        </div>
    )
}
