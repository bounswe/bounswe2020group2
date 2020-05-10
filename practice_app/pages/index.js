import { useState } from 'react'
import Head from 'next/head'

import DeckGL from '@deck.gl/react'
import { IconLayer } from '@deck.gl/layers'
import { StaticMap, Marker } from 'react-map-gl'

// axios is a library used to make requests
import axios from 'axios'

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

const MAPBOX_ACCESS_TOKEN =
    'pk.eyJ1IjoibWVoZGlzYWZmYXIiLCJhIjoiY2thMHdldHhoMWNobTN0cGtpN2oyZ2IyMCJ9.Mi8mEiVIRdc-UWTpJxGkRA'

// THIS CODE IS EXECUTED ON THE ********CLIENT******** SIDE
export default function Home({ context }) {
    // <-- This "context" object is the same object return in getServerSideProps
    // REMEMBER: You can always console.log (either in the server or in the browser, depending on where the code is
    // executed) to see what each object looks like!

    // this contains the number
    // to know more look at React Hooks documentation

    const [coordinates, setCoordinates] = useState({
        longitude: 29.046874,
        latitude: 41.085212,
    })

    const [viewState, setViewState] = useState({
        ...coordinates,
        zoom: 13,
        pitch: 0,
        bearing: 0,
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
    const onViewStateChange = ({ viewState }) => setViewState(viewState)

    const iconLayer = new IconLayer({
        id: 'marker',
        data: [{ position: [coordinates.longitude, coordinates.latitude, 0], icon: 'marker', size: 30 }],
        iconAtlas: '/marker.png',
        getSize: d => d.size,
        iconMapping: {
            marker: {
                x: 0,
                y: 0,
                width: 100,
                height: 121,
                anchorY: 121,
                mask: false,
            },
        },
    })

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
                <div className="map_container">
                    <DeckGL
                        width="100%"
                        height="100%"
                        initialViewState={viewState}
                        onViewStateChange={onViewStateChange}
                        controller
                        onClick={onMapClick}
                        layers={[iconLayer]}>
                        <StaticMap mapboxApiAccessToken={MAPBOX_ACCESS_TOKEN} />
                    </DeckGL>
                </div>
            </main>
            <footer>CMPE 352 - Group 2 - 2020</footer>

            <style jsx>
                {`
                    .map_container {
                        position: relative;
                        width: 100vw;
                        height: 300px;
                    }
                `}
            </style>

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
