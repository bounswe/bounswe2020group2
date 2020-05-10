import { useState } from 'react'
import Head from 'next/head'

import axios from 'axios'
import MapView from '../components/MapView'
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

    const [currency, setCurrency] = useState({
        code: 'TRY',
        display: 'TL',
        symbol: 'TL',
    })

    const [language, setLanguage] = useState({
        code: 'TUR',
        name: 'Turkish',
    })

    const [country, setCountry] = useState({
        code: 'TR',
        name: 'Turkey',
    })

    const onMapClick = async event => {
        const {
            lngLat: [longitude, latitude],
        } = event
        try {
            const { data } = await axios.get(`api/getLocationInfo?lat=${latitude},lng=${longitude}`)
            const { currency, language, country } = data

            setCountry(country)
            setLanguage(language)
            setCurrency(currency)

            setCoordinates({ longitude, latitude })
        } catch (error) {
            console.error(error)
        }
    }

    return (
        <div className="container">
            <Head>
                <title>Our example API</title>
                <link rel="icon" href="/favicon.ico" />
            </Head>
            <main>
                <h1>Welcome to our demo website!</h1>
                {country && language && currency && (
                    <p>
                        You are a user from {country.name}, you speak
                        {language.name} and you buy in
                        {currency.display}
                    </p>
                )}
                <MapView onMapClick={onMapClick} coordinates={coordinates} />
            </main>
            <style jsx global>
                {`
                    html,
                    body {
                        padding: 0;
                        margin: 0;
                        font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell,
                            Fira Sans, Droid Sans, Helvetica Neue, sans-serif;
                    }

                    * {
                        box-sizing: border-box;
                    }
                `}
            </style>
        </div>
    )
}
