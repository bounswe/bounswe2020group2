import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'

import { AppContextProvider } from './context/AppContext'

// only launch mock service worker when in development environment
if (process.env.NODE_ENV === 'development') {
    const { worker } = require('./mocks/browser')
    worker.start()
}

ReactDOM.render(
    // <React.StrictMode>
    <AppContextProvider>
        <App />
    </AppContextProvider>,
    // </React.StrictMode>,
    document.getElementById('root'),
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: rep ortWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
