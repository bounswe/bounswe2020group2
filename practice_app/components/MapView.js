import { useState, useRef } from 'react'

import DeckGL from '@deck.gl/react'

import { IconLayer } from '@deck.gl/layers'

import MapGL from 'react-map-gl'

import Geocoder from 'react-map-gl-geocoder'

import style from './MapView.module.css'

const MAPBOX_ACCESS_TOKEN =
    'pk.eyJ1IjoibWVoZGlzYWZmYXIiLCJhIjoiY2thMHdldHhoMWNobTN0cGtpN2oyZ2IyMCJ9.Mi8mEiVIRdc-UWTpJxGkRA'

/**
 * Shows a map on which the user can click to update his/her current location
 */
export default function MapView({ coordinates, onMapClick }) {
    const [viewState, setViewState] = useState({
        ...coordinates,
        zoom: 13,
        pitch: 0,
        bearing: 0,
    })

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

    const mapRef = useRef()

    const onResult = viewState => {
        console.log(viewState)
        const { longitude, latitude } = viewState
        onMapClick({ latitude, longitude })
        setViewState(viewState)
    }

    const _onMapClick = event => {
        const {
            center: [longitude, latitude],
        } = event
        onMapClick({ latitude, longitude })
    }

    return (
        <div className={style.mapContainer}>
            <MapGL
                ref={mapRef}
                {...viewState}
                width="100%"
                height="100%"
                onViewportChange={onViewStateChange.log}
                mapboxApiAccessToken={MAPBOX_ACCESS_TOKEN}>
                <Geocoder
                    mapRef={mapRef}
                    onViewportChange={onResult}
                    mapboxApiAccessToken={MAPBOX_ACCESS_TOKEN}
                    position="top-left"
                />
            </MapGL>

            <DeckGL
                width="100%"
                height="100%"
                initialViewState={viewState}
                onViewStateChange={onViewStateChange}
                controller
                onClick={_onMapClick}
                layers={[iconLayer]}
            />
        </div>
    )
}
